package com.d2.truth_or_dare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View decorView;
    private TextView amountText;
    private SeekBar seekB;

    public static final String NUM_KEY = "D1.D2.TOD.NUM-P";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.amountText = findViewById(R.id.amount);
        this.seekB = findViewById(R.id.numBar);
        this.decorView = getWindow().getDecorView();
        this.seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekB) {}

            @Override
            public void onProgressChanged(SeekBar seekB, int progressValue, boolean fromUser) {
                amountText.setText(1 + seekB.getProgress() + "");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekB) {}
        });

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

    public void getStarted(View v) {
        Intent intent = new Intent(this, PlayerInputs.class);
        intent.putExtra(this.NUM_KEY, this.seekB.getProgress() + 1);
        startActivity(intent);
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
