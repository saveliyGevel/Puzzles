package com.example.admin.puzzles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, Runnable {
    private GameView view;
    private TextView secondsText;
    private int seconds = 0;
    private Handler secondsHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        view = (GameView) findViewById(R.id.game_view);
        findViewById(R.id.watch_button).setOnClickListener(this);
        findViewById(R.id.restart_button).setOnClickListener(this);
        secondsText = (TextView)findViewById(R.id.seconds_text);

        secondsHandler = new Handler();
        secondsHandler.post(this);
    }
    @Override
    public void run() {
        seconds++;
        secondsText.setText(getString(R.string.seconds) + seconds);
        secondsHandler.postDelayed(this, 1000);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Uri uri = intent.getData();

        int NxN = intent.getIntExtra(NewGameActivity.SIZE_EXTRA, 5);

        if (uri != null) {
            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                view.init(img, NxN);
            } catch (IOException e) {
                Toast.makeText(this, "Cannot load image", Toast.LENGTH_LONG).show();
            } catch (RuntimeException e) {
                Log.d("Puzzle", "RuntimeException", e);
            }
        } else {
            Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.boozeena);
            view.init(img, NxN);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.watch_button:
                view.togglePreview();
                view.postInvalidate();
                break;
            case R.id.restart_button: {
                view.newGame();
                view.postInvalidate();
                seconds = 0;
                break;
            }
        }
    }
    int getSeconds() {
        return seconds;
    }
}