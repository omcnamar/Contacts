package com.olegsagenadatrytwo.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import com.olegsagenadatrytwo.contacts.ContactsDbSchema.ContactsTable;

/**
 * Created by omcna on 8/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + ContactsTable.NAME + "(" +
                ContactsTable.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                ContactsTable.Columns.NAME + " TEXT, " +
                ContactsTable.Columns.NUMBER + " TEXT, " +
                ContactsTable.Columns.IMAGE + "  BLOB, " +
                ContactsTable.Columns.ADDRESS + " TEXT, " +
                ContactsTable.Columns.EMAIL + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContactsTable.NAME);
        onCreate(sqLiteDatabase);
    }

    public long saveNewContact(MyContact myContact)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //convert Bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myContact.getImage().compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        contentValues.put(ContactsTable.Columns.NAME, myContact.getName());
        contentValues.put(ContactsTable.Columns.NUMBER, myContact.getNumber());
        contentValues.put(ContactsTable.Columns.IMAGE, byteArray);
        contentValues.put(ContactsTable.Columns.ADDRESS, myContact.getAddress());
        contentValues.put(ContactsTable.Columns.EMAIL, myContact.getEmail());
        long saved = database.insert(ContactsTable.NAME, null, contentValues);
        return saved;
    }

    public long updateExistingContact(MyContact myContact)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //convert Bitmap to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        myContact.getImage().compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        contentValues.put(ContactsTable.Columns.NAME, myContact.getName());
        contentValues.put(ContactsTable.Columns.NUMBER, myContact.getNumber());
        contentValues.put(ContactsTable.Columns.IMAGE, byteArray);
        contentValues.put(ContactsTable.Columns.ADDRESS, myContact.getAddress());
        contentValues.put(ContactsTable.Columns.EMAIL, myContact.getEmail());

        String[] v = new String[]{String.valueOf(myContact.getId())};
        long result = database.update(ContactsTable.NAME, contentValues, "id=?", v);
        //another way to update.
//        database.execSQL("UPDATE " + ContactsTable.NAME + " SET " +
//        ContactsTable.Columns.NAME + " = " + "'" +myContact.getName() + "'" +
//        " WHERE " + ContactsTable.Columns.ID + " =" + String.valueOf(myContact.getId()));
        return result;
    }

    public long deleteContact(MyContact contact){
        SQLiteDatabase database = this.getWritableDatabase();
        String[] v = new String[]{String.valueOf(contact.getId())};
        long result = database.delete(ContactsTable.NAME, "id=?", v);
        return result;
    }

    public ArrayList<MyContact> getContacts(){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + ContactsTable.NAME;
        ArrayList<MyContact> listOfContacts = new ArrayList<>();

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                //BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                byte[] array = cursor.getBlob(3);
                Bitmap bitmap  = BitmapFactory.decodeByteArray(array, 0, array.length);
                MyContact myContact = new MyContact(cursor.getString(1),
                        cursor.getString(2),
                        bitmap,
                        cursor.getString(4),
                        cursor.getString(5));
                myContact.setId(Integer.parseInt(cursor.getString(0)));
                listOfContacts.add(myContact);
            }while(cursor.moveToNext());
        }
        return listOfContacts;
    }
}