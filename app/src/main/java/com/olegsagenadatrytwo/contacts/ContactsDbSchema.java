package com.olegsagenadatrytwo.contacts;

/**
 * Created by omcna on 8/7/2017.
 */

public class ContactsDbSchema {

    public static final class ContactsTable{
        public static final String NAME = "Contacts";

        public static final class Columns{
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String NUMBER = "number";
            public static final String IMAGE = "image";
            public static final String ADDRESS = "address";
            public static final String EMAIL = "email";
        }
    }
}
