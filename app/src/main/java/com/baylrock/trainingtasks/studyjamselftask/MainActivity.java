package com.baylrock.trainingtasks.studyjamselftask;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baylrock.trainingtasks.studyjamselftask.games.Guesser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Guesser guesser_game;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    static private MainActivity thisActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_layout);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setContentView(R.layout.activity_main);
                    }
                });
            }
        }, 5000);
        thisActivity = this;

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * @param view click event source
     *             Set activity layout in game layout / starting game
     */
    public void startGueser(View view) {
        setContentView(R.layout.guesser_layout);
        guesser_game = new Guesser(this);
    }

    /**
     * @param view click event source
     *             Event listener for gameProcess/inGame buttons
     */
    public void guesser_click(View view) {
        int id;
        if ((id = view.getId()) == getResources().getIdentifier("button14", "id", this.getPackageName())) {
            guesser_game.guesserInit();
            return;
        }
        if (id == getResources().getIdentifier("button13", "id", this.getPackageName())) {
            //next
            return;
        }
        guesser_game.guess(view);

    }

    public void scoreBoardLayout(View view) {
        setContentView(R.layout.score_bord_layout);
        guesser_game.getScoreBoard().init();
    }

    /**
     * @param key string of resource name
     * @return value from key-value pair, using key input
     * Loading game scores from resources
     */
    public String getScore(String key) {
        return sharedPreferences.getString(getStringResourceByName(key), "0");
    }

    /**
     * @param key   string of resource name
     * @param value int-value for saving in recourse
     *              <p/>
     *              Saving game scores in resource files
     */
    public void saveScore(String key, String value) {
        editor.putString(getStringResourceByName(key), value);
        editor.commit();
    }

    public static MainActivity getActivity() {
        return thisActivity;
    }


    /**
     * @param aString Resource name
     * @return value of string resource
     * Getting string value of resource using resource name
     */
    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources()
                .getIdentifier(aString, "string", packageName);
        if (resId == 0) {
            return aString;
        } else {
            Log.d("Debug: ", "String resource exists");
            return getString(resId);
        }
    }

    public void menuLayout(View view) {
        setContentView(R.layout.activity_main);
    }

    public void infoLayout(View view) {
        setContentView(R.layout.info_layout);
    }

    public void exit(View view) {
        finish();
        System.exit(0);
    }

}
