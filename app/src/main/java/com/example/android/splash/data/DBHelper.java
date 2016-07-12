package com.example.android.splash.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME="reports.db";

    public DBHelper(Context context) {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String TABLE1=" CREATE TABLE IF NOT EXISTS "+ DataContract.Report.TABLE_NAME+" ( " + DataContract.Report.DATA+" TEXT NOT NULL , "+DataContract.Report.LATITUDE+" TEXT NOT NULL , "+DataContract.Report.LONGITUDE+" TEXT NOT NULL , "+DataContract.Report.IMEI+" TEXT NOT NULL , "+ DataContract.Report.IMAGE+" TEXT )";
        final String TABLE2=" CREATE TABLE IF NOT EXISTS "+ DataContract.Report.TABLE_NAME2+" ( " + DataContract.Report.DATA+" TEXT NOT NULL , "+DataContract.Report.LATITUDE+" TEXT NOT NULL , "+DataContract.Report.LONGITUDE+" TEXT NOT NULL , "+DataContract.Report.IMEI+" TEXT NOT NULL , "+ DataContract.Report.IMAGE+" TEXT )";
        sqLiteDatabase.execSQL(TABLE1);
        sqLiteDatabase.execSQL(TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+ DataContract.Report.TABLE_NAME);
        final String TABLE1=" CREATE TABLE IF NOT EXISTS "+ DataContract.Report.TABLE_NAME+" ( " + DataContract.Report.DATA+" TEXT NOT NULL , "+DataContract.Report.LATITUDE+" TEXT NOT NULL , "+DataContract.Report.LONGITUDE+" TEXT NOT NULL , "+DataContract.Report.IMEI+" TEXT NOT NULL , "+ DataContract.Report.IMAGE+" TEXT )";
        sqLiteDatabase.execSQL(TABLE1);

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+ DataContract.Report.TABLE_NAME2);
        final String TABLE2=" CREATE TABLE IF NOT EXISTS "+ DataContract.Report.TABLE_NAME2+" ( " + DataContract.Report.DATA+" TEXT NOT NULL , "+DataContract.Report.LATITUDE+" TEXT NOT NULL , "+DataContract.Report.LONGITUDE+" TEXT NOT NULL , "+DataContract.Report.IMEI+" TEXT NOT NULL , "+ DataContract.Report.IMAGE+" TEXT )";
        sqLiteDatabase.execSQL(TABLE2);
    }

    public boolean insertReport_pending(String report,String latitude,String longitude,String imei,String image){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.Report.DATA, report);
        contentValues.put(DataContract.Report.IMAGE,image);
        contentValues.put(DataContract.Report.LATITUDE,latitude);
        contentValues.put(DataContract.Report.LONGITUDE,longitude);
        contentValues.put(DataContract.Report.IMEI,imei);


        db.insert(DataContract.Report.TABLE_NAME, null, contentValues);
        return true;

    }
    public boolean insertReport_previous(String report,String latitude,String longitude,String imei,String image){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.Report.DATA, report);
        contentValues.put(DataContract.Report.IMAGE,image);
        contentValues.put(DataContract.Report.LATITUDE,latitude);
        contentValues.put(DataContract.Report.LONGITUDE,longitude);
        contentValues.put(DataContract.Report.IMEI,imei);


        db.insert(DataContract.Report.TABLE_NAME2, null, contentValues);
        return true;

    }
    public Cursor getListReportPending(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( " select * from report_pending ", null );
        return res;
    }
    public Cursor getListReportPrev(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( " select * from report_prev ", null );
        return res;
    }
    public void deleteReportPrev (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ DataContract.Report.TABLE_NAME +" WHERE data in (SELECT data FROM "+ DataContract.Report.TABLE_NAME+" LIMIT 1 OFFSET "+id+" )" );
    }


}
