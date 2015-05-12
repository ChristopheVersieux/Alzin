package com.wazabe.folklore.guindailles;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wazabe.folklore.guindailles.bo.ChantApi;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * Created by versieuxchristophe on 01/01/15.
 */
public class ListChantsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<Card> cards = new ArrayList<>();
        //Staggered grid view
        final CardRecyclerView mRecyclerView = (CardRecyclerView) getActivity().findViewById(R.id.carddemo_recyclerview);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (getArguments().getString("key").contentEquals("")) {
            getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
            ((TextView) getView().findViewById(R.id.info)).setText(R.string.empty);
            return;
        }

        Ion.with(getActivity())
                .load("http://alzin.byethost33.com/" + getArguments().getString("key"))
                .as(new TypeToken<ChantApi>() {
                })
                .setCallback(new FutureCallback<ChantApi>() {
                                 @Override
                                 public void onCompleted(Exception e, ChantApi api) {
                                     try {
                                         if (e != null) {
                                             e.printStackTrace();
                                             if (e instanceof UnknownHostException || e instanceof TimeoutException) {
                                                 getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                                 ((TextView) getView().findViewById(R.id.info)).setText(R.string.network);

                                             } else {
                                                 getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                                 ((TextView) getView().findViewById(R.id.info)).setText(e.getLocalizedMessage());
                                             }
                                         } else if (api != null && api.results != null && api.results.chants != null) {
                                             getView().findViewById(R.id.container).setVisibility(View.GONE);
                                             for (final ChantApi.Result.Chant chant : api.results.chants) {
                                                 CustomCard card = new CustomCard(getActivity(), chant.item.description);
                                                 CardHeader header = new CardHeader(getActivity());
                                                 header.setTitle(chant.item.text);
                                                 card.addCardHeader(header);
                                                 CardThumbnail thumb = new CardThumbnail(ListChantsFragment.this.getActivity());
                                                 thumb.setUrlResource(chant.item.image);
                                                 card.addCardThumbnail(thumb);
                                                 card.setOnClickListener(new Card.OnCardClickListener() {
                                                     @Override
                                                     public void onClick(Card card, View view) {
                                                         startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("url", chant.item.href));
                                                     }
                                                 });
                                                 cards.add(card);
                                             }


                                             CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(getActivity(), cards);

                                             //Set the empty view
                                             if (mRecyclerView != null) {
                                                 mRecyclerView.setAdapter(mCardArrayAdapter);
                                             }
                                         } else {
                                             getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                             ((TextView) getView().findViewById(R.id.info)).setText(R.string.unknown);
                                         }


                                     } catch (Exception e1) {
                                         e1.printStackTrace();
                                     }
                                 }
                             }

                );
    }
}
