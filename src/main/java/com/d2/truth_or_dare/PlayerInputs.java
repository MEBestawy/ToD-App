package com.d2.truth_or_dare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerInputs extends AppCompatActivity {


    //public DataCollector dataBase;

    private View decorView;
    private int playerCount, currentPlayer;
    private TextView playerNumIndicator;
    private EditText nameInput, truthInput1, truthInput2, dareInput1, dareInput2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_inputs);

        Intent intent = getIntent();
        this.playerCount = intent.getIntExtra(MainActivity.NUM_KEY, 1);

        this.playerNumIndicator = findViewById(R.id.playerIndicator);
        this.nameInput = findViewById(R.id.nameInput);
        this.truthInput1 = findViewById(R.id.truthInput1);
        this.truthInput2 = findViewById(R.id.truthInput2);
        this.dareInput1 = findViewById(R.id.dareInput1);
        this.dareInput2 = findViewById(R.id.dareInput2);
        this.currentPlayer = 1;

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

    public void nextPlayer(View v) {
        if (validApplication()) {
            this.currentPlayer++;
            gatherPlayerInfo();
            if (this.currentPlayer <= this.playerCount) {
                // Proceed to the next player
                clearPlayerInput();
                this.playerNumIndicator.setText("Player "+ this.currentPlayer);

            } else {
                // No more players, go to the next page
                toChoosingStage();
            }
        }
    }

    private boolean validApplication() {
        // Player needs to input a name
        if (!this.nameInput.getText().toString().trim().isEmpty()) {
            // No repeated names
            if ( DataHost.dataBase.checkNameIsUnique(this.nameInput.getText().toString())) {
                // At least one truth and dare has to be filled in
                if ((!this.truthInput1.getText().toString().trim().isEmpty() ||
                        !this.truthInput2.getText().toString().trim().isEmpty()) &&
                        (!this.dareInput1.getText().toString().trim().isEmpty() ||
                                !this.dareInput2.getText().toString().trim().isEmpty())) {
                    // Passed the test, player application is valid
                    return true;
                }

                // No truth or dares
                Toast.makeText(getApplicationContext(),
                        "You need to input at least one truth and one dare.",
                        Toast.LENGTH_SHORT).show();

            } else {
                // Repeated Name
                Toast.makeText(getApplicationContext(),
                        "Repeated names are not allowed.",Toast.LENGTH_SHORT).show();
            }

        } else {
            // No name
            Toast.makeText(getApplicationContext(),
                    "Please input your name.",Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void gatherPlayerInfo() {
        DataHost.dataBase.addName(nameInput.getText().toString());
        DataHost.dataBase.addTruth(truthInput1.getText().toString());
        DataHost.dataBase.addTruth(truthInput2.getText().toString());
        DataHost.dataBase.addDare(dareInput1.getText().toString());
        DataHost.dataBase.addDare(dareInput2.getText().toString());
    }

    private void clearPlayerInput() {
        nameInput.setText("");
        truthInput1.setText("");
        truthInput2.setText("");
        dareInput1.setText("");
        dareInput2.setText("");
    }

    private void toChoosingStage() {
        Intent intent = new Intent(this, RandomPlayerPicking.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        clearPlayerInput();
        this.currentPlayer = 1;
        this.playerNumIndicator.setText("Player 1");
        DataHost.dataBase.clear();
        super.onResume();

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
