package com.example.admin.puzzles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.new_game_button).setOnClickListener(this);
        findViewById(R.id.about_button).setOnClickListener(this);
        findViewById(R.id.settings_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_game_button: {
                Intent intent = new Intent(MainActivity.this, NewGameActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.about_button: {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.settings_button: {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
