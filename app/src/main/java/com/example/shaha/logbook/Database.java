package com.example.shaha.logbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;

public class Database extends SQLiteOpenHelper {
    final static String DBNAME = "logbook.db";
    final static String TBNAME = "logbook_tb";
    final static String COL_1 = "DATE";
    final static String COL_2 = "TYPE";
    final static String COL_3 = "REGISTRATION";
    final static String COL_4 = "CALLSIGN";
    final static String COL_5 = "FLIGHT_TIME";
    final static String COL_6 = "START_TIME";
    final static String COL_7 = "END_TIME";


    public Database(Context context) {
        super(context, DBNAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TBNAME+ " (DATE DATE, TYPE TEXT, REGISTRATION TEXT, CALLSIGN TEXT, FLIGHT_TIME DEC, START_TIME TIME, END_TIME TIME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TBNAME);
        onCreate(db);

    }

    public boolean insertData(String Date, String Type, String Registration, String Callsign, String Flight_Time, String Start_Time, String End_Time){
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, Date);
        cv.put(COL_2, Type);
        cv.put(COL_3, Registration);
        cv.put(COL_4, Callsign);
        cv.put(COL_5, Flight_Time);
        cv.put(COL_6, Start_Time);
        cv.put(COL_7, End_Time);
        result = db.insert(TBNAME, null, cv);
        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData(){
        Cursor result;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.rawQuery("SELECT * FROM "+TBNAME, null);
        return result;
    }

    // removes tests from DB
    public void removeTEST(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBNAME, "TYPE = ?",new String[] {"TEST"});
    }

    public double getTotalTime(){
        double totalTime = 0.0;
        Cursor data;
        SQLiteDatabase db = this.getWritableDatabase();

        data = db.rawQuery("SELECT SUM(Flight_Time) AS Total FROM "+TBNAME,null);

        if(data.moveToFirst()){
            DecimalFormat df = new DecimalFormat("#,##0.0");
            totalTime = data.getDouble(data.getColumnIndex("Total"));
            totalTime = Double.valueOf(df.format(totalTime)); // round to nearest tenth

        }
        return totalTime;
    }

}
