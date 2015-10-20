package com.bet.gal.friendlybet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class holds a list of Game objects
 * This class is Jason compatible.
 */



public class GamesList {

    private ArrayList<Game> games;

    GamesList () {

        games = new ArrayList<Game>();
    }

    GamesList (Game[] gamesArray) {

        this();
        addGames(gamesArray);

    }


    public void addGame (Game game) {

        games.add(game);

    }

    public Iterator<Game> getItr () {

        return games.iterator();
    }

    public void addGames (Game[] gamesArray) {

        games.addAll(Arrays.asList(gamesArray));

    }

    public void clearList (){
        games.clear();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public int getSize() {
        return games.size();
    }

    public Game getGame(int index) {
        return games.get(index);
    }
}
