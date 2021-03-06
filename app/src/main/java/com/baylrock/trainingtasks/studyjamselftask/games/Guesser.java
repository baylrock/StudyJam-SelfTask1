package com.baylrock.trainingtasks.studyjamselftask.games;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baylrock.trainingtasks.studyjamselftask.R;

import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by baylrock on 05.04.2016.
 */
public class Guesser {

    private Activity activity;
    private int guess_state = 1;
    private Button[] guess_buts_array = new Button[12];
    private boolean inGame = false;
    private Toast main_toast;
    private int data[];
    private LinkedHashMap <Button,Integer> dataMap;

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    private ScoreBoard scoreBoard = new ScoreBoard();

    public Guesser(Activity activity) {
        this.activity = activity;
        startGueser();
    }


    /**
     * Game preparing. Loading buttons in array.
     */
    public void startGueser() {

        for (int i = 1; i < 13; i++) {
            if ((guess_buts_array[i - 1] = (Button) activity.findViewById(activity.getResources().getIdentifier("button" + i, "id", activity.getPackageName()))) == null) {
                Log.e("Cant find butt with id", "button" + i);
                return;
            }
        }
    }

    /**
     * Game initiating. Preparing and demonstrating guessing data.
     */
    public void guesserInit() {
        Button guess_bt;
        inGame = false;
        dataMap = new LinkedHashMap<>();
        data  = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        guess_state = 1;
        gameSort(data);

        for (int i = 0; i < 12; i++) {
            guess_bt = guess_buts_array[i];
            dataMap.put(guess_bt,data[i]);
            guess_bt.setTextColor(activity.getResources().getColor(R.color.main_menu_bt));
            guess_bt.setText("" + data[i]);
            guess_bt.setEnabled(true);

        }
        new Timer().schedule(new GamePause(), 5000);
    }

    /**
     *
     * @param view
     * Checking users guess and game progress.
     */
    public void guess(View view) {
        if (!inGame) return;
        Button guess_but = (Button) view;

        if (dataMap.get(guess_but) == guess_state) {
            if (guess_state == 12) {
                if (main_toast != null) main_toast.cancel();
                main_toast = Toast.makeText(activity.getApplicationContext(), "YOU WIN!", Toast.LENGTH_SHORT);
                scoreBoard.addScore(guess_state-1);
                main_toast.show();
                setGuessButtsEnabled(false);

            } else {
                if (main_toast != null) main_toast.cancel();
                main_toast = Toast.makeText(activity.getApplicationContext(), "Ready " + (guess_state) + "/12", Toast.LENGTH_SHORT);
                main_toast.show();

            }

        } else {
            setGuessButtsEnabled(false);
            Toast.makeText(activity.getApplicationContext(), "You loose!", Toast.LENGTH_LONG).show();
            scoreBoard.addScore(guess_state-1);
            for (int i = 0; i < 12; i++) {
                if (guess_buts_array[i].getText().equals("")) {
                    guess_buts_array[i].setText("" + data[i]);
                    guess_buts_array[i].setTextColor(Color.RED);


                }

            }
            return;
        }

        guess_but.setText("" + guess_state++);
    }

    private void setGuessButtsEnabled(boolean state) {
        for (int i = 0; i < 12; i++) {
            guess_buts_array[i].setEnabled(state);
        }
    }

    private void setGuessButtsText(String text) {
        for (int i = 0; i < 12; i++) {
            guess_buts_array[i].setText(text);
        }
    }


    /**
     * Making delay for memorize the task/numbers
     */
    class GamePause extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setGuessButtsText("");
                    inGame = true;
                }
            });

        }
    }

    /**
     *
     * @param data array for sorting
     * Random array sort for making task
     */

    private void gameSort(int[] data) {
        Random rnd = new Random();
        for (int i = 0, buff, randIndex; i < data.length; i++) {
            buff = data[i];
            data[i] = data[randIndex = rnd.nextInt(data.length)];
            data[randIndex] = buff;
        }
        this.data = data;
    }




}
