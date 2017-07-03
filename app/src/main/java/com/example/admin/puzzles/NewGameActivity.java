package com.example.admin.puzzles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST = 1;
    static final String SIZE_EXTRA = "SIZE";
    private Uri uri;
    private int selected;
    private ImageView preview;
    int[] complexity_sizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        findViewById(R.id.choose_button).setOnClickListener(this);
        findViewById(R.id.start_button).setOnClickListener(this);

        preview = (ImageView) findViewById(R.id.preview_view);
        preview.setImageResource(R.drawable.boozeena);


        String[] complexity = getResources().getStringArray(R.array.complexities);
        complexity_sizes = new int[complexity.length];
        for (int i = 0; i < complexity.length; i++) {
            int n = 5 + i*3;
            complexity_sizes[i] = 5+i*3;
            complexity[i] += "(" + n + "x" + n + ")";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, complexity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Choose complexity");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_button: {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST);
                break;
            }
            case R.id.start_button: {
                Intent intent = new Intent(NewGameActivity.this, GameActivity.class);
                intent.putExtra(SIZE_EXTRA, complexity_sizes[selected]);
                intent.setData(uri);
                startActivity(intent);
                break;
            }
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            super.onActivityResult(requestCode, resultCode, data);
            preview.setImageURI(selectedImage);
            uri = selectedImage;
        }
    }
}
