package com.bet.gal.friendlybet;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class will hold all the tables of the user.
 */
public class Lobby {


    private ArrayList<Table> tablesList = null;
    private ArrayList<Table> pendingTables = null;
    private static Lobby lobby;

    private Lobby(){

        tablesList = downloadTabels();
    }

    public static Lobby getInstnace() {

        if (lobby == null) {
            lobby = new Lobby();
        }

        return lobby;
    }


    /**
     * Gets the tables from the server
     * @return
     */

    private ArrayList<Table> downloadTabels() {

        return null;
    }

    /**
     * Uploads the tables to the server
     * @param tables
     * @return
     */

    private boolean uploadTables (ArrayList<Table> tables) {

        return true;
    }

    private void removeTable (int index){

        tablesList.remove(index);

    }


    private void addTable(Table table) {

        tablesList.add(table);
    }

    public Iterator<Table> getItr() {
        return tablesList.iterator();
    }

    public Iterator<Table> getPendingTableItr() {
        return pendingTables.iterator();
    }
}
