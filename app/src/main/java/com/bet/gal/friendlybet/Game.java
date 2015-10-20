package com.bet.gal.friendlybet;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * This class represents a single game with all of it's properties.
 * The Game object is responsible for providing the layout that represents it.
 * This class is Jason compatible.
 */
public class Game {

    private Score score;
    private String teamA, teamB;
    private int teamAIcon, teamBIcon;
    private String date;
    private int teamAScore = 0, teamBScore = 0, id;



    Game (String teamA, String teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        date = "";
        setIcons();
    }

    Game (String teamA, String teamB, Date timeToBet) {

        this(teamA,teamB);
        this.date = date;
        setIcons();
    }

    @JsonCreator
    Game (@JsonProperty("teamA") String teamA, @JsonProperty("teamB") String teamB, @JsonProperty("id") int id) {

        this(teamA,teamB);
        this.id = id;
        date = "";
        setIcons();
    }

    private void setIcons() {

        teamAIcon = getTeamIcon(teamA);
        teamBIcon = getTeamIcon(teamB);


    }

    public int getTeamAIcon() {
        return teamAIcon;
    }

    public int getTeamBIcon() {
        return teamBIcon;
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(int teamAScore) {
        this.teamAScore = teamAScore;
    }

    public int getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(int teamBScore) {
        this.teamBScore = teamBScore;
    }

    public void  setScore (Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public View getLayout(LayoutInflater inflater) {


        View layout = inflater.inflate(R.layout.game_layout_2, null);


        TextView teamAText = (TextView) layout.findViewById(R.id.teamA);
        TextView teamBText = (TextView) layout.findViewById(R.id.teamB);
        ImageView teamAImg = (ImageView) layout.findViewById(R.id.teamAImg);
        ImageView teamBImg = (ImageView) layout.findViewById(R.id.teamBImg);
        Spinner spinner = (Spinner) layout.findViewById(R.id.scoreSpinner);

        teamAText.setText(teamA);
        teamBText.setText(teamB);
        teamAImg.setImageResource(getTeamIcon(teamA));
        teamBImg.setImageResource(getTeamIcon(teamB));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setScore(Score.valueOf(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return layout;
    }



    public int getTeamIcon (String teamName) {

        switch (teamName) {

            case "ITA":
                return (R.mipmap.italy);

            case "GER":
                return (R.mipmap.germany);

            case "ISR":
                return (R.mipmap.israel);

            case "ENG":
                return (R.mipmap.england);

            default:
                return (R.mipmap.def);
        }
    }
}
