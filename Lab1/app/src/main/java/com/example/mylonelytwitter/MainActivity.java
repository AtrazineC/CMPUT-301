package com.example.mylonelytwitter;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mylonelytwitter.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tweet tweet = new NormalTweet("hello world");
        Tweet rockTweet = new ImportantTweet("hello from the rock");
        ImportantTweet rock2Tweet = new ImportantTweet("hello again");

        // Keep a feed
        ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
        tweetList.add(new NormalTweet("hello"));
        tweetList.add(new ImportantTweet("hello"));

        ArrayList<Mood> moodList = new ArrayList<Mood>();
        moodList.add(new Happy());
        moodList.add(new Happy(new Date()));
        moodList.add(new Sad());
        moodList.add(new Sad(new Date()));

        Log.d("Testing", moodList.get(0).getMood());
        Log.d("Testing", moodList.get(0).getDate().toString());
        moodList.get(0).setDate(new Date(2000, 5, 5));
        Log.d("Testing", moodList.get(0).getDate().toString());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}