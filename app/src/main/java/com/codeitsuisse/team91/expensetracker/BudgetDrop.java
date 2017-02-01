package com.codeitsuisse.team91.expensetracker;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class BudgetDrop{
    ArrayList<String> BudgetCopy;

    public static final String KEY_ROWID = "_id";
    public static final String KEY_BUDGET = "budget_name";
    public static final String KEY_AMOUNT = "initial_bal";
    public static final String KEY_DESCRIPTION = "description";

    private static final String DATABASE_NAME = "BudgetDrop";
    private static final String DATABASE_TABLE = "Budget_Table";
    private static final int DATABASE_VERSION = 1;

    private DbHelper OurHelper;
    private final Context OurContext;
    private SQLiteDatabase OurData;

    public BudgetDrop(Context c) {
        OurContext = c;
    }



    private static class DbHelper extends SQLiteOpenHelper  {


        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_BUDGET + " TEXT NOT NULL, " +
                            KEY_DESCRIPTION + " TEXT NOT NULL, " +
                            KEY_AMOUNT + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }


    }
    public BudgetDrop open() {
        OurHelper = new DbHelper(OurContext);
        OurData = OurHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        OurHelper.close();
    }
    //delete whole budget
    public void deleteAllEntry() {
        OurData.delete(DATABASE_TABLE, null, null);
    }

    public long createEntry(String budName, String amount, String descript) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_BUDGET, budName);
        cv.put(KEY_AMOUNT, amount);
        cv.put(KEY_DESCRIPTION, descript);

        return OurData.insert(DATABASE_TABLE, null, cv);
    }


    public Boolean checkBudget(String budname){
        String[] columns = new String[]{KEY_BUDGET, KEY_DESCRIPTION, KEY_AMOUNT};
        Cursor c = OurData.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int x = 0;
        String result = null;

        int iBudget = c.getColumnIndex(KEY_BUDGET);
        int iAmount = c.getColumnIndex(KEY_AMOUNT);
        int iDescript = c.getColumnIndex(KEY_DESCRIPTION);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = c.getString(iBudget);
            if (result.equals(budname))
                return true;
        }

        return false;
    }
    public String getData()   {
        String[] columns = new String[]{KEY_BUDGET, KEY_DESCRIPTION, KEY_AMOUNT};
        Cursor c = OurData.query(DATABASE_TABLE, columns, null, null, null, null, null);

        int x = 0;
        String result = null;
        BudgetCopy = new ArrayList<String>();

        int iBudget = c.getColumnIndex(KEY_BUDGET);
        int iAmount = c.getColumnIndex(KEY_AMOUNT);
        int iDescript = c.getColumnIndex(KEY_DESCRIPTION);

       for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = c.getString(iBudget);
            if (result.equals("General"))
                x = 1;
        }

        if(x == 0){
            createEntry("General", "00", "00");
        }


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = c.getString(iBudget);
            if (result != "")
                BudgetCopy.add(result);
        }

        return result;
    }

    //delete budget
    public void deleteEntry(String dbudName) {
        OurData.delete(DATABASE_TABLE, KEY_BUDGET + " = '" + dbudName + "'", null);
    }

    //update budget
    public void updateEntry(String budName, String uInitBal, String uDescript) throws  SQLException{
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_AMOUNT, uInitBal);
        cvUpdate.put(KEY_DESCRIPTION, uDescript);
        OurData.update(DATABASE_TABLE, cvUpdate, KEY_BUDGET + " = '" + budName + "'", null);
    }

    public String getSaving(String Budname)   {
        String[] columns = new String[]{KEY_BUDGET, KEY_DESCRIPTION, KEY_AMOUNT};
        Cursor c = OurData.query(DATABASE_TABLE, columns, null, null, null, null, null);
        // Cursor c = OurData.rawQuery("SELECT KEY_AMOUNT FROM " + DATABASE_TABLE + " WHERE " + KEY_BUDGET + " = " + Budname , null);

        String result = "00";//= c.toString(); //null,
        String name = null;

        //c.close();
        int iBudget = c.getColumnIndex(KEY_BUDGET);
        int iAmount = c.getColumnIndex(KEY_AMOUNT);
        int iDescript = c.getColumnIndex(KEY_DESCRIPTION);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            name = c.getString(iBudget).toString();

            //result = c.getString(iAmount);
            //  result = name;
            if (name.equals(Budname)) {
                result = c.getString(iDescript);
                return result;
            }
            // break;
        }
        c.close();
        OurData.close();
        return result;
    }

    public String getAmount(String Budname)   {
        String[] columns = new String[]{KEY_BUDGET, KEY_DESCRIPTION, KEY_AMOUNT};
        Cursor c = OurData.query(DATABASE_TABLE, columns, null, null, null, null, null);
        // Cursor c = OurData.rawQuery("SELECT KEY_AMOUNT FROM " + DATABASE_TABLE + " WHERE " + KEY_BUDGET + " = " + Budname , null);

        String result = "00";//= c.toString(); //null,
        String name = null;

        //c.close();
        int iBudget = c.getColumnIndex(KEY_BUDGET);
        int iAmount = c.getColumnIndex(KEY_AMOUNT);
        int iDescript = c.getColumnIndex(KEY_DESCRIPTION);



        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            name = c.getString(iBudget).toString();

            //result = c.getString(iAmount);
            //  result = name;
            if (name.equals(Budname)) {
                result = c.getString(iAmount);
                return result;
            }
            // break;
        }
        c.close();
        OurData.close();
        return result;
    }

}
