package com.baylrock.trainingtasks.studyjamselftask.games;

import android.app.Activity;
import android.provider.Settings;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baylrock.trainingtasks.studyjamselftask.MainActivity;
import com.baylrock.trainingtasks.studyjamselftask.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by baylrock on 06.04.2016.
 */
public class ScoreBoard {

    /**
     * Date - date of score getting
     * Integer - points
     */
    private HashMap<String, Integer> scores;
    private Activity activity;

    public ScoreBoard(Activity activity) {
        scores = new HashMap<>();
        loadScore();
        this.activity = activity;
    }

    private boolean loadScore() {
        FileInputStream f_in = null;
        try {

            f_in = new FileInputStream("myobject.data");
            ObjectInputStream obj_in = new ObjectInputStream(f_in);
            Object obj = obj_in.readObject();
            if (obj == null)  {
                saveScore();
                Log.e("Error:", "Failed in read score data. Created new score data file.");
                return true;

            }
            if (obj instanceof HashMap) {
                scores = (HashMap<String,Integer>) obj;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void saveScore() {
        FileOutputStream f_out = null;
        ObjectOutputStream obj_out = null;

        try {
            f_out = new FileOutputStream("scores.data");
            obj_out = new ObjectOutputStream(f_out);
            obj_out.writeObject(scores);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addScore(int score) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, ''yy");
        scores.put(sdf.format(cal.getTime()),score);
    }


    public void init() {
        TableLayout ll = (TableLayout) activity.findViewById(R.id.displayLinear);

        int iterates = 0;
        for (String scorDate : scores.keySet()) {

            TableRow row = new TableRow(activity);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView date = new TextView(activity);
            TextView score = new TextView(activity);
            date.setText(scorDate);
            score.setText(scores.get(scorDate));
            row.addView(date);
            row.addView(score);
            ll.addView(row, iterates);
            iterates++;
        }
    }

}
