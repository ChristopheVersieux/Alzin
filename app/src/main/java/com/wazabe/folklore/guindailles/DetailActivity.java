package com.wazabe.folklore.guindailles;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.wazabe.folklore.guindailles.bo.ParoleApi;

import java.nio.charset.Charset;

/**
 * Created by versieuxchristophe on 08/01/15.
 */
public class DetailActivity extends ActionBarActivity {

    MediaPlayer mySound;
    boolean playing=false;
    private int forwardTime = 15000;
    private int timeElapsed = 0, finalTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySound = MediaPlayer.create(this, R.raw.chasseur);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // everything else that doesn't update UI
        }

        String musique = getIntent().getExtras().getString("musique");
        String img = getIntent().getExtras().getString("img");

        setTitle("Chargement en cours...");
        Ion.with(this)
                .load(getIntent().getExtras().getString("url").replace("http://www.guindaille-factory.be/chants/view/", "http://alzin.byethost33.com/Paroles/") + ".txt")
                .asString(Charset.forName("UTF-8"))
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {
                    @Override
                    public void onCompleted(Exception e, Response<String> api) {
                        if(e!=null){
                            ((TextView) findViewById(R.id.text)).setText(R.string.errorgenericdetail+" -> "+e.getMessage());
                            setTitle(R.string.errorgeneric);
                            return;
                        }
                        switch(api.getHeaders().code()){
                            case 200:
                                ParoleApi paroles= new Gson().fromJson(api.getResult(), ParoleApi.class);
                                String text = paroles.results.chant.get(0).paroles.toString();
                                String titre = paroles.results.chant.get(0).titre.toString();

                                ((TextView) findViewById(R.id.text)).setText(text.substring(titre.length(), text.length()));
                                setTitle(titre);
                                break;
                            case 404:
                                ((TextView) findViewById(R.id.text)).setText(R.string.error404detail);
                                setTitle(R.string.error404);
                                break;
                        }


                    }

                })
                ;

        Ion.with(this).load(img).intoImageView((ImageView)findViewById(R.id.img));
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

    public void retour(View view) {
        finalTime = mySound.getDuration();
        if ((timeElapsed - forwardTime) > 0) {
            timeElapsed = timeElapsed - forwardTime;
            mySound.seekTo((int) timeElapsed);
        }
        else {
            mySound.seekTo((int) 0);
        }
    }

    public void toggle(View view) {
        if(playing){
            ((ImageButton)view).setImageResource(android.R.drawable.ic_media_play);
            mySound.start();
            timeElapsed = mySound.getCurrentPosition();
        }else{
            ((ImageButton)view).setImageResource(android.R.drawable.ic_media_pause);
            mySound.pause();
        }
        playing=!playing;


    }


    public void avancer(View view) {
        finalTime = mySound.getDuration();
        if ((timeElapsed + forwardTime) <= finalTime) {
            timeElapsed = timeElapsed + forwardTime;
            mySound.seekTo((int) timeElapsed);
        }

    }
}
