package com.d2.truth_or_dare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToDPicking extends AppCompatActivity {

    private TextView truthChoice;
    private TextView dareChoice;
    private View decorView;

    public static final String PICK_KEY = "D1.D2.TOD.CHOICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking);

        this.truthChoice = findViewById(R.id.truthChoice);
        this.dareChoice = findViewById(R.id.dareChoice);

        this.truthChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("truth");
            }
        });

        this.dareChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo("dare");
            }
        });

        this.decorView = getWindow().getDecorView();

        // Detect visibility change
        this.decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == 0) {
                    decorView.setSystemUiVisibility(hideSystemUI());
                }
            }
        });
    }

    private void goTo(String choice) {
        if (choice.equalsIgnoreCase("truth") &&
                DataHost.dataBase.checkIfTruthsEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "No more Truths left :(",Toast.LENGTH_SHORT).show();

            return;
        } else if (choice.toLowerCase().equalsIgnoreCase("dare") &&
                DataHost.dataBase.checkIfDaresEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "No more Dares left :(",Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(this, TruthOrDarePresentation.class);
            intent.putExtra(this.PICK_KEY, choice);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {}


    /*
    Making Activity Fullscreen
    */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.decorView.setSystemUiVisibility(hideSystemUI());
        }
    }

    private int hideSystemUI() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
    }
}
