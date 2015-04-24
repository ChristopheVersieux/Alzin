package com.wazabe.folklore.guindailles;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wazabe.folklore.guindailles.bo.ParoleApi;

/**
 * Created by versieuxchristophe on 08/01/15.
 */
public class DetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // everything else that doesn't update UI
        }
        setTitle("Chargement en cours...");
        Ion.with(this)
                .load("https://www.kimonolabs.com/api/480wksdk?apikey=6VWkomxEx2eH0P9zWbDkvYAJQ6g8P3Sd&kimpath3=" + getIntent().getExtras().getString("url").replace("http://www.guindaille-factory.be/chants/view/", ""))
                .as(new TypeToken<ParoleApi>() {
                })
                .setCallback(new FutureCallback<ParoleApi>() {
                    @Override
                    public void onCompleted(Exception e, ParoleApi api) {
                        String paroles = api.results.chant.get(0).paroles.toString();
                        String titre = api.results.chant.get(0).titre.toString();

                        ((TextView) findViewById(R.id.text)).setText(paroles.substring(titre.length(), paroles.length()));
                        setTitle(titre);

                    }

                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
