package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBContact extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "data";
    private static final String TABLE_NAME = "contact";
    private static final String ID="id";
    private static final String NAME = "name";
    private static final String NUMBER = "number";
    private Context context;

    public DBContact(Context context) {


        super(context, DATABASE_NAME, null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " %s TEXT, %s TEXT)",
                TABLE_NAME, ID, NAME, NUMBER);
        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfylly", Toast.LENGTH_SHORT).show();
    }

    //Add new a contact
    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getHoTen());
        values.put(NUMBER, contact.getSoDT());
        //Neu de null thi khi value bang null thi loi
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /*
    Select a contact by Name
     */


    public boolean checkContact(Contact contact)
    {
        String ht=contact.getHoTen();
        String dt=contact.getSoDT();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;
        cursor = db.query(TABLE_NAME, new String[]{NAME, NUMBER}, NAME + "=? AND " + NUMBER+"=?",
                new String[]{ht,dt}, null, null, null, null);
        int sl=cursor.getCount();
        cursor.close();
        db.close();
        return sl>0;
    }

    /*
    Update name of contact
     */

    /*
     Getting All Contact
      */

    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> listContact = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1),cursor.getString(2));
                listContact.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listContact;
    }
}
