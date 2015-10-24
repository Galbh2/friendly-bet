package com.bet.gal.friendlybet;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


import static com.bet.gal.friendlybet.MyJson.*;


public class MainActivity extends Activity {

    GamesList gamesList;
    LinearLayout layout;

    private String mockAddress;
    private String serverAddress;
    private int serverPort;

    Button sendButton;
    TextView dateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        //Layout Init
        LayoutInflater inflater = getLayoutInflater();
        layout = (LinearLayout) inflater.inflate(R.layout.list_layout, null);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new SendButtonLister());
        dateText = (TextView) findViewById(R.id.dateText);

        scrollView.addView(layout);

        //Loads the games from the content server
        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settingsButton :

              //  Class settings = Class.forName("com.bet.gal.friendlybet.Settings");
                Intent i = new Intent(MainActivity.this, Settings.class);
                startActivityForResult(i,1);
                break;

            case R.id.refreshButton :

                refresh();

                break;

            case R.id.aboutButton :

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.aboutMsg));
                builder.setTitle("About");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getBooleanExtra("needsRefresh", false) == true) {

            Log.d("refresh", "gal refresh called");

            Log.d("mockAdress", mockAddress);
            refresh();
        }
    }



    private void init() {

        Game[] games = {new Game("israel", "italy"),
                new Game("england", "germany"),
                new Game("A", "B"),
                new Game("A", "B"),
                new Game("A", "B"),
                new Game("A", "B")
                , new Game("A", "B")
                , new Game("A", "B")
                , new Game("A", "B")
                , new Game("A", "B")
                , new Game("A", "B")
                , new Game("A", "B")};

        gamesList = new GamesList(games);

    }

    private void populateLayout() {

        layout.removeAllViewsInLayout();

        Iterator<Game> itr = gamesList.getItr();

        while (itr.hasNext()) {

            layout.addView(itr.next().getLayout(getLayoutInflater()), 0);

        }
    }



    private class SendButtonLister implements View.OnClickListener {

        @Override
        public void onClick(View v) {


            Log.d("Send Button", "Has been clicked");

            new AsyncTask<Void, Integer, String>() {
                ProgressDialog dialog;

                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(MainActivity.this, "FriendlyBet", "Loading...");
                }

                @Override
                protected String doInBackground(Void... params) {

                    int responseCode = 0;

                    try {


                        URL url = new URL("http", serverAddress, serverPort, "");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setConnectTimeout(5000);

                        mapper.writeValue(connection.getOutputStream(), gamesList);

                        responseCode = connection.getResponseCode();

                        Log.d("Post Code: ", Integer.toString(responseCode));

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String s;


                    return Integer.toString(responseCode);
                }


                @Override
                protected void onPostExecute(String s) {
                    dialog.dismiss();

                    if (s.equals("200")) {
                        Toast.makeText(MainActivity.this, "Your bet was delivered to the server", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(MainActivity.this, "Something bad happened, code: " + s, Toast.LENGTH_LONG).show();
                }


            }.execute();


        }
    }



    private void clearLayout() {
        Log.d("remove views", "true");
        layout.removeAllViewsInLayout();
        layout.invalidate();

    }

    private void refresh() {

        gamesList = null;
        clearLayout();

        DateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        dateText.setText(f.format(new Date()));

        //new MyTask().execute(mockAddress);


    }




}
