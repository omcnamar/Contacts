package com.olegsagenadatrytwo.contacts;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by omcna on 8/7/2017.
 */

public class ContactsLab {

    private Context context;
    private static ContactsLab contactsLab;
    private ArrayList<MyContact> contacts = new ArrayList<>();

    //create a private constructor so no one can create an instance of this class, use getInstance
    private ContactsLab(Context context){
        this.context = context;
    }

    //create a static method that returns a static ContactsLab
    public static ContactsLab getInstance(Context context){
        if(contactsLab == null){
            return new ContactsLab(context);
        }else{
            return contactsLab;
        }
    }

    public ArrayList<MyContact> getContacts() {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getContacts();
    }

    public void setContacts(ArrayList<MyContact> contacts) {
        this.contacts = contacts;
    }

    public long addContact(MyContact contact){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.saveNewContact(contact);
    }

    public long updateContact(MyContact contact){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.updateExistingContact(contact);
    }

    public long deleteContact(MyContact contact){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.deleteContact(contact);
    }


}
