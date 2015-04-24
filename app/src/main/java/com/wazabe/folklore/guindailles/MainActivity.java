package com.wazabe.folklore.guindailles;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import it.neokree.materialnavigationdrawer.MaterialAccount;
import it.neokree.materialnavigationdrawer.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.MaterialSection;
import it.neokree.materialnavigationdrawer.MaterialSectionListener;


public class MainActivity extends MaterialNavigationDrawer implements MaterialAccountListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init(Bundle bundle) {


        // add first account
        MaterialAccount account = new MaterialAccount("Tof162", "christophe.versieux@gmail.com", new ColorDrawable(Color.parseColor("#FF000000")), this.getResources().getDrawable(R.drawable.baner));
        this.addAccount(account);

        this.setAccountListener(this);

        MaterialSection section;

        section = this.newSection(getResources().getString(R.string.section2),newInstance("8vuseq3a"));
        this.addSection(section);
        section = this.newSection(getResources().getString(R.string.section4), newInstance("8ym52w3e"));
        this.addSection(section);
        section = this.newSection(getResources().getString(R.string.section5), newInstance("5ddf2cxm"));
        this.addSection(section);
        section = this.newSection(getResources().getString(R.string.section6), newInstance("agqu2adi"));
        this.addSection(section);
        section = this.newSection(getResources().getString(R.string.section7), newInstance("cbdl4k22"));
        this.addSection(section);

        this.addMultiPaneSupport();

        this.setBackPattern(MaterialNavigationDrawer.BACKPATTERN_BACK_TO_FIRST);

        findViewById(R.id.user_nome ).setPadding(0,30,0,0);

    }

    public static final ListChantsFragment newInstance(String key)
    {
        ListChantsFragment f = new ListChantsFragment();
        Bundle bdl = new Bundle(1);
        bdl.putString("key", key);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onAccountOpening(MaterialAccount materialAccount) {

    }

    @Override
    public void onChangeAccount(MaterialAccount materialAccount) {

    }
}
