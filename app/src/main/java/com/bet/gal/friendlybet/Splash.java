package com.bet.gal.friendlybet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Gal on 26/07/2015.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Class mainActiviy = Class.forName("com.bet.gal.friendlybet.MainActivity");
                        Intent openMain = new Intent(Splash.this, mainActiviy);
                        startActivity(openMain);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
