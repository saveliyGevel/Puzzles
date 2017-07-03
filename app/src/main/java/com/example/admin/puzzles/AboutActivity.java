package com.example.admin.puzzles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        SharedPreferences prefs = getSharedPreferences("com.example.admin.puzzles", MODE_PRIVATE);

        int games = prefs.getInt(GameView.TOTAL_GAMES_EXTRA, 1);
        double midTime = (double)prefs.getInt(GameView.TOTAL_SECONDS_EXTRA, 0) / games;

        TextView total = (TextView) findViewById(R.id.total_games);
        total.setText(getString(R.string.total_games) + games);

        TextView mid = (TextView) findViewById(R.id.mid_time);
        mid.setText(getString(R.string.mid_time) + midTime);
    }
}
