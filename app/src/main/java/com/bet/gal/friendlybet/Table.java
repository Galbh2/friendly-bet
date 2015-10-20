package com.bet.gal.friendlybet;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * This class holds the users and the games that associate with it.
 */
public class Table {

    private int adminId;
    private int index;
    private int id;
    private String name;
    private Bitmap icon;
    private ArrayList<Player> playersList = new ArrayList<Player>();
    private GamesList futureGames = null;
    private GamesList pastGames = null;



    public Table (String name) {
        this.name = name;
    }
    @JsonCreator
    public Table (@JsonProperty("id") int id, @JsonProperty ("name") String name, @JsonProperty ("icon") Bitmap icon) {

        this(name);
        this.id = id;
        this.icon = icon;

    }

    private boolean donwloadPlayers(int id) {


        return true;
    }



}
