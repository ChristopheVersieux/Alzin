package com.wazabe.folklore.guindailles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.library.internal.Card;

public class CustomCard extends Card {

    protected TextView mContent;
    protected String contentText;

    /**
     * Constructor with a custom inner layout
     * @param context
     */
    public CustomCard(Context context,String text) {
        this(context, R.layout.customcardcontent);
        this.contentText=text;
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    public CustomCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    /**
     * Init
     */
    private void init(){

        //No Header

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        mContent = (TextView) parent.findViewById(R.id.desc);

        if (mContent!=null)
            mContent.setText(contentText);

    }
}