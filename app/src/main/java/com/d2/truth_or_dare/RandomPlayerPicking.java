package com.d2.truth_or_dare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RandomPlayerPicking extends AppCompatActivity {

   // public DataCollector dataBase;

    private TextView chosenNameView;
    private View decorView;
    private Button rollButton;
    private String chosenPlayer;


    private Runnable separateThread = new Runnable() {
        @Override
        public void run() {
            roll();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_player_picking);

        this.chosenPlayer = "";
        this.chosenNameView = findViewById(R.id.chosenNameView);
        this.rollButton = findViewById(R.id.rollButton);

        this.rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollButton.setText("RE-ROLL");
                Thread t = new Thread(separateThread);
                t.start();
            }
        });

        this.decorView = getWindow().getDecorView();

        // Detect visibility change
        this.decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
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


    public void roll() {
        this.chosenPlayer = "";
        showNameAnimation();
        this.chosenPlayer = chosenNameView.getText().toString();
    }


    public void showNameAnimation() {
        // 2 Seconds
        int adder = 50;
        long endTime = System.currentTimeMillis() + (2 * 1000);
        long interval = System.currentTimeMillis() + adder;

        while (System.currentTimeMillis() < endTime) {
            if (interval < System.currentTimeMillis()) {
                chosenNameView.setText( DataHost.dataBase.getAName());
                adder *= 1.15;
                interval = System.currentTimeMillis() + adder;
            }
        }
    }

    public void tryToProceed(View v) {
        if (this.chosenPlayer.equals("")) {
            // No name rolled
            Toast.makeText(getApplicationContext(),
                    "Please roll to choose a name.",Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(this, ToDPicking.class);
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
