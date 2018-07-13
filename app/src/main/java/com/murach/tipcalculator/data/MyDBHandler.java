package com.murach.tipcalculator.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.murach.tipcalculator.Tip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class MyDBHandler extends SQLiteOpenHelper {

    //define variables
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "database.db";
    public static final String TABLE_DATABASE = "database";
    public static final String COLUMN_ID = "_id";
    public static final int COLUMN_ID_NO = 1;
    public static final String COLUMN_BILLDATE = "bill_date";
    public static final int COLUMN_BILLDATE_NO = 2;
    public static final String COLUMN_BILLAMOUNT = "bill_amount";
    public static final int COLUMN_BILLAMOUNT_NO = 3;
    public static final String COLUMN_TIP = "tip_percent";
    public static final int COLUMN_TIP_NO = 4;


    //define the SQL lite database variable
    private SQLiteDatabase database;

    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creating a table for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_DATABASE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BILLDATE + " TEXT NOT NULL, " +
                COLUMN_BILLAMOUNT + " TEXT NOT NULL," +
                COLUMN_TIP + "TEXT NOT NULL" +");";

        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //delete the entire table if it exists
        db.execSQL("DROP TABLE if EXISTS " + TABLE_DATABASE);
        //recreate the table with the new properties
        onCreate(db);


    }

    public MyDBHandler open() throws SQLException {

        database = getWritableDatabase();           //get reference to the database
        return this;
    }

    public void closeDatabase(){

        if(database != null){
            database.close();
        }
    }

    //add bill data
   /* public void addBill(Database data){

        //content values is built into android that allows you to add serveral value in one statement
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILLDATE, data.getBill_date());
        values.put(COLUMN_BILLAMOUNT, data.getBill_amount());
        values.put(COLUMN_TIP, data.getTip_percent());

        //call the open method to get reference to the database
        open();
        database.insert(TABLE_DATABASE, null, values);

        //once you are done with database then close it out to give mermory back
        close();

    }
*/
/*
        public ArrayList<Tip> getTips() {
            ArrayList<Tip> tips = new ArrayList<Tip>();

            // Select All Query
            String selectQuery = "SELECT * FROM " + TABLE_DATABASE;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Tip all = new Tip();
                    all.setId(Integer.parseInt(c.getString(0)));
                    all.setDateMillis(Integer.parseInt(c.getString(1)));
                    all.setBillAmount(Float.parseFloat(c.getString(2)));
                    all.setTipPercent(Float.parseFloat(c.getString(3)));
            // Adding contact to list
                    tips.add(all);
                } while (c.moveToNext());
            }

        // return contact list
            return tips;
        }
        */

    public ArrayList<Tip> getTips() {

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Tip> tips = new ArrayList<Tip>();

        // cursor point to a location in your results
        Cursor c = db.query(TABLE_DATABASE, null, null, null, null, null, null);
        // move to the first row in your results


        while(c.moveToNext()) {
            Tip tip  = new Tip(
                   c.getLong(COLUMN_ID_NO),
                    c.getLong(COLUMN_BILLDATE_NO),
                    c.getFloat(COLUMN_BILLAMOUNT_NO),
                    c.getFloat(COLUMN_TIP_NO)
            );
            tips.add(tip);

        }

        if(c != null){
            c.close();
        }

        this.closeDatabase();
        //return dbString;
        return tips;
    }


}
