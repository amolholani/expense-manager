package com.codeitsuisse.team91.expensetracker;

/**
 * Created by Aditya Sodhiya on 14-09-2015.
 */

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.sql.SQLException;

public class Data_in  {
    private static final int DATABASE_VERSION = 1;
    private static final String DATEBASE_NAME = "inptable";
    public static final String COLUMN_ID = "_id";
    public static final String TABLE_EXP = "myTable";
    public static final String COLUMN_BUDGET = "budget";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MOUNTH = "month";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DESC = "DESC";

    private Dbhelper ourhelper;
    private final Context ourcontext;
    private SQLiteDatabase OurDatabase;

    public long createEntry(String bud_name, String expense, String category, String d, String m, String y) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET, bud_name );
        values.put(COLUMN_DAY, d);
        values.put(COLUMN_MOUNTH, m);
        values.put(COLUMN_YEAR, y);
        values.put(COLUMN_AMOUNT, expense);
        values.put(COLUMN_CATEGORY, category);
        return  OurDatabase.insert(TABLE_EXP, null, values);
    }

    public String getData1(String budget_name) {
        String[] column = new String[]{COLUMN_ID, COLUMN_BUDGET, COLUMN_DAY, COLUMN_MOUNTH, COLUMN_YEAR, COLUMN_AMOUNT, COLUMN_CATEGORY};
        Cursor c = OurDatabase.query(TABLE_EXP, column, null, null, null, null, null);

        String result = "", name = "";

        int i_id = c.getColumnIndex(COLUMN_ID);
        int i_bud = c.getColumnIndex(COLUMN_BUDGET);
        int i_day = c.getColumnIndex(COLUMN_DAY);
        int i_mm = c.getColumnIndex(COLUMN_MOUNTH);
        int i_yy = c.getColumnIndex(COLUMN_YEAR);
        int i_am = c.getColumnIndex(COLUMN_AMOUNT);
        int i_cat = c.getColumnIndex(COLUMN_CATEGORY);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            name = c.getString(i_bud);
            if (name.equals(budget_name)) {
                result += c.getString(i_id) + "    " + c.getString(i_day) + "/" + c.getString(i_mm) + "/" + c.getString(i_yy) + "\namount: ₹" + c.getString(i_am) + "  " + c.getString(i_cat) + "\n";

            }
        }
        return  result;


    }

    // public Data_exp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    //   super(context, DATEBASE_NAME, factory, DATABASE_VERSION);
    //}

    private static class Dbhelper extends SQLiteOpenHelper {

        public Dbhelper(Context context){
            super(context, DATEBASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_EXP + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_BUDGET + " TEXT NOT NULL, "
                    + COLUMN_DAY + " TEXT NOT NULL, "
                    + COLUMN_MOUNTH + " TEXT NOT NULL, "
                    + COLUMN_YEAR + " TEXT NOT NULL, "
                    + COLUMN_AMOUNT + " TEXT NOT NULL, "
                    + COLUMN_CATEGORY + " TEXT NOT NULL);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXP);
            onCreate(db);
        }


        public void deletedata(String p) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_EXP + " WHERE " + COLUMN_AMOUNT + "=\"" + p + "\";");
        }

        public String databasetoexp() {
            String dbstring = "";
            SQLiteDatabase db = getWritableDatabase();
            String q = "SELECT * FROM " + TABLE_EXP + " WHERE 1";

            Cursor c = db.rawQuery(q, null);
            c.moveToFirst();

            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("amount")) != null) {
                    dbstring += "\n";

                }
            }
            db.close();
            return dbstring;
        }
    }
    public String getData() {
        String[] column = new String[]{COLUMN_ID, COLUMN_BUDGET, COLUMN_DAY, COLUMN_MOUNTH, COLUMN_YEAR, COLUMN_AMOUNT, COLUMN_CATEGORY};
        return "hi";
    }

    public Data_in(Context c){
        ourcontext = c;
    }

    public Data_in open()throws SQLException{
        ourhelper = new Dbhelper(ourcontext);
        OurDatabase = ourhelper.getWritableDatabase();
        return  this;
    }

    public void close(){
        ourhelper.close();
    }
}


