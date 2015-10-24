package com.bet.gal.friendlybet;

import android.content.Context;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;

import static com.bet.gal.friendlybet.MyJson.mapper;

/**
 * Created by Gal on 24/10/2015.
 */
public class NetOps {

    private static String mockAddress;
    private static String serverAddress;
    private static int serverPort;

    public static String getMockAddress() {
        return mockAddress;
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void loadSettings(Context c) {

        Log.d("loadSettings", "activate");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);

        mockAddress = sp.getString("CONTENT_SERVER_ADDRESS", c.getString(R.string.defaultContentServer));
        serverAddress = sp.getString("DELIVERY_SERVER_ADDRESS",  c.getString(R.string.defaultDeliveryServer));
        serverPort = Integer.parseInt(sp.getString("DELIVERY_SERVER_PORT", c.getString(R.string.defaultDeliveryServerPort)));

    }

    public static String httpRequest(String u) throws  Exception{

        StringBuilder s = new StringBuilder();

        URL url = new URL(u);
        URLConnection connection = url.openConnection();

        HttpURLConnection httpConnection = (HttpURLConnection) connection;

        httpConnection.connect();

        int responseCode = httpConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            InputStreamReader is = new InputStreamReader(httpConnection.getInputStream());
            BufferedReader br = new BufferedReader(is);
            String line;
            line = br.readLine();

            while ((line != null)) {

                s.append(line);
                line = br.readLine();
            }
        } else {
            Log.d("request status: ", Integer.toString(responseCode));
        }

        return s.toString();
    }




    public static class DownloadGameList implements Callable<GamesList> {

        GamesList gamesList;
        Context context;

        public DownloadGameList (Context c) {
            context = c;
            loadSettings(c);
        }

        @Override
        public GamesList call() throws Exception {

            String s = httpRequest(mockAddress);
            gamesList = mapper.readValue(s, GamesList.class);
            return gamesList;
        }
    }
}


