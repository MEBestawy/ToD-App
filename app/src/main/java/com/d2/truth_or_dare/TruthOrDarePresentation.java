package com.d2.truth_or_dare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TruthOrDarePresentation extends AppCompatActivity {
    private View decorView;
    private TextView choiceTitle, choiceDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truth_or_dare_presentation);

        this.choiceTitle = findViewById(R.id.choiceTitle);
        this.choiceDesc = findViewById(R.id.choiceDesc);

        Intent intent = getIntent();
        intent.getStringExtra(ToDPicking.PICK_KEY);

        String choice = intent.getStringExtra(ToDPicking.PICK_KEY);
        this.choiceTitle.setText(choice.toUpperCase());
        this.choiceDesc.setText(pickAQuestion(choice));

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

    @Override
    public void onBackPressed() {}


    public String pickAQuestion(String choice) {
        if (choice.equalsIgnoreCase("dare")) {
            return DataHost.dataBase.getADare();
        } else if (choice.equalsIgnoreCase("truth")) {
            return DataHost.dataBase.getATruth();
        } else {
            return "Something wrong happened :/";
        }
    }


    public void goToNextRound(View v) {
        if (DataHost.dataBase.checkIfDaresEmpty() && DataHost.dataBase.checkIfTruthsEmpty()) {
            Intent intent = new Intent(this, GameFinished.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(this, RandomPlayerPicking.class);
            startActivity(intent);
        }
    }

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
