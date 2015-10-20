package com.bet.gal.friendlybet;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * This class isn't in use.
 */

public class GameLayout extends LinearLayout {

    ImageView teamAImg, teamBImg;
    TextView teamAText, teamBText;
    Spinner spinner;
    Game game;
    Button showDialog;

    public GameLayout(Context context, Game game) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);

        this.game = game;

        teamAImg = new ImageView(context);
        teamAText = new TextView(context);
        teamBImg = new ImageView(context);
        teamBText = new TextView(context);

        teamAText.setText(game.getTeamA());
        teamAText.setTextSize(26);

        teamBText.setText(game.getTeamB());
        teamBText.setTextSize(26);


        spinner = new Spinner(context);
        ArrayAdapter<Score> adp = new ArrayAdapter<Score>(context,android.R.layout.simple_spinner_item, Score.values());
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
        setUpSpinnerListener();

        /*
        showDialog = new Button(context);
        showDialog.setText("show dialog");
        showDialoglistner(context);
        */

        this.addView(teamAImg);
        this.addView(teamAText);
        this.addView(teamBImg);
        this.addView(teamBText);
        this.addView(spinner);
       // this.addView(showDialog);

    }


    private void setUpSpinnerListener() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                game.setScore(Score.valueOf(parent.getItemAtPosition(position).toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void popMsg(Context context) {

        Dialog dialog = new Dialog(context);
        TextView txt = new TextView(context);
        txt.setText(game.getScore().toString());
        dialog.setContentView(txt);
        dialog.show();

    }


    private void showDialoglistner(final Context context){

        showDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                popMsg(context);

            }
        });

    }


}
