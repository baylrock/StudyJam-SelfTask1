package com.baylrock.trainingtasks.studyjamselftask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baylrock.trainingtasks.studyjamselftask.games.Guesser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int guess_state = 1;
    private Button[] guess_buts_array = new Button[12];
    private boolean inGame = false;
    private Toast main_toast;
    Guesser guesser_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//
    public void startGueser(View view) {
        setContentView(R.layout.guesser_layout);
        guesser_game = new Guesser(this);
    }

    public void guesser_click(View view) {
        int id;
        if ((id  = view.getId()) == getResources().getIdentifier("button14","id",this.getPackageName())) {
            guesser_game.guesserInit();
            return;
        }
        if (id == getResources().getIdentifier("button13","id",this.getPackageName())) {
            //next
            return;
        }
        guesser_game.guess(view);

    }

}
