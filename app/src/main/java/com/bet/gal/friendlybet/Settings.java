package com.bet.gal.friendlybet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

/**
 * This class holds the global settings for the app
 */
public class Settings extends Activity implements View.OnClickListener {

    EditText contentServerAddress, deliveryServerAddress, deliveryServerPort;
    Button saveButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_layout);

        contentServerAddress = (EditText) findViewById(R.id.contentServerAddress);
        deliveryServerAddress = (EditText) findViewById(R.id.deliveryServerAddress);
        deliveryServerPort = (EditText) findViewById(R.id.deliveryServerPort);
        saveButton = (Button) findViewById(R.id.saveButton);
        resetButton = (Button) findViewById(R.id.resetButton);


    //Sets listener for the Save Button
        saveButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        loadPrefs();

    }

    /**
     * Provides the ability to retrieve data from the SharedPref
     */

    private void loadPrefs(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        contentServerAddress.setText(sp.getString("CONTENT_SERVER_ADDRESS",getString(R.string.defaultContentServer)));
        deliveryServerAddress.setText(sp.getString("DELIVERY_SERVER_ADDRESS",getString(R.string.defaultDeliveryServer)));
        deliveryServerPort.setText(sp.getString("DELIVERY_SERVER_PORT", getString(R.string.defaultDeliveryServerPort)));

    }

    /**
     * Provides the ability to store String values in the
     * shared preferences
     * @param key
     * The key for accessing the data
     * @param value
     * The actual String data
     */

    private void savePrefs (String key, String value) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    //In order to edit the data in the SharedPref we need to call the editor
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }


    @Override
    public void onClick(View v) {



        switch (v.getId()){

            case R.id.saveButton :

                savePrefs("CONTENT_SERVER_ADDRESS", contentServerAddress.getText().toString());
                savePrefs("DELIVERY_SERVER_ADDRESS", deliveryServerAddress.getText().toString());
                savePrefs("DELIVERY_SERVER_PORT", deliveryServerPort.getText().toString());
                break;

            case  R.id.resetButton :

                resetSettings();
                break;
        }
    //Returns a flag to the calling activity asking for settings refresh
        Intent i = getIntent();
        i.putExtra("needsRefresh", true);
        setResult(RESULT_OK, i);
        finish();
    }

    private void resetSettings(){


        savePrefs("CONTENT_SERVER_ADDRESS", getString(R.string.defaultContentServer));
        savePrefs("DELIVERY_SERVER_ADDRESS", getString(R.string.defaultDeliveryServer));
        savePrefs("DELIVERY_SERVER_PORT", getString(R.string.defaultDeliveryServerPort));

        loadPrefs();

    }

    @Override
    public void onBackPressed() {

    }
}
