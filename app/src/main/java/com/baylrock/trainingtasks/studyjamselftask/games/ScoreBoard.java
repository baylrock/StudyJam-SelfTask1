package com.baylrock.trainingtasks.studyjamselftask.games;

import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baylrock.trainingtasks.studyjamselftask.MainActivity;
import com.baylrock.trainingtasks.studyjamselftask.R;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by baylrock on 06.04.2016.
 */
public class ScoreBoard {

    private LinkedList<Integer> scoresList;
    private MainActivity activity;

    public ScoreBoard() {
        this.activity = MainActivity.getActivity();
        scoresList = new LinkedList<>();
    }

    /**
     *
     * @param score
     * Adding score to resource file after game ending
     */
    public void addScore(int score) {
        scoresList.add(score);
        saveScore();
    }

    /**
     * Loading scores from resources and building TableView for displaying score data
     */
    public void init() {
        loadScore();
        TableLayout ll = (TableLayout) activity.findViewById(R.id.displayLinear);
        int iterates = 1;
        for (Integer score : scoresList) {

            TableRow row = new TableRow(activity);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TextView numText = new TextView(activity);
            TextView scoreText = new TextView(activity);
            numText.setText(String.valueOf(iterates));
            numText.setPadding(10, 10, 100, 10);
            numText.setTextSize(20);
            numText.setGravity(Gravity.CENTER_HORIZONTAL);

            scoreText.setText(String.valueOf(score));
            scoreText.setPadding(100, 10, 10, 10);
            scoreText.setTextSize(20);
            scoreText.setGravity(Gravity.CENTER_HORIZONTAL);
            row.addView(numText);
            row.addView(scoreText);
            ll.addView(row);
            iterates++;
        }
    }

    /**
     * Saving scores in resource file
     */

    public void saveScore() {
        int iterates = 0;
        Integer[] array = scoresList.toArray(new Integer[scoresList.size()]);
        array = arraySort(array);
        scoresList = new LinkedList<>(Arrays.asList(array));
        for (Integer score : scoresList) {
            activity.saveScore("sc_" + iterates, "" + score);
            if (iterates == 9) {
                return;
            }
            iterates++;

        }
    }

    /**
     * Loading scores from resource file
     */

    private void loadScore() {
        scoresList = new LinkedList<>();
        String score;
        int i = 0;
        while (!(score = activity.getScore("sc_" + i)).equals("0")) {
            Log.d("LoadScores:", "Score exists sc_" + i + "  =  " + score);
            scoresList.add(Integer.valueOf(score));
            i++;
        }
    }

    /**
     *
     * @param arrInt array to sort
     * @return  sorted array
     * Array sort from max to min for making scoreboard
     */

    private Integer[] arraySort(Integer[] arrInt) {
        int n = arrInt.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int v = 1; v < (n - i); v++) {
                if (arrInt[v - 1] < arrInt[v]) {
                    temp = arrInt[v - 1];
                    arrInt[v - 1] = arrInt[v];
                    arrInt[v] = temp;
                }

            }
        }
        return arrInt;
    }


}
