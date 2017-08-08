package com.olegsagenadatrytwo.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private static final String TAG = "AddContactActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText name;
    private EditText number;
    private EditText address;
    private EditText email;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        name = (EditText) findViewById(R.id.etName);
        number = (EditText) findViewById(R.id.etNumber);
        address = (EditText) findViewById(R.id.etAddress);
        email = (EditText) findViewById(R.id.etEmail);
        image = (ImageView) findViewById(R.id.ivAddImageInNewContact);

    }

    public void submitContact(View view) {
        String nameString = name.getText().toString();
        String numberString = number.getText().toString();
        String addressString = address.getText().toString();
        String emailString = email.getText().toString();
        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        MyContact newContact = new MyContact(nameString, numberString, bitmap, addressString, emailString);
        ContactsLab contactsLab = ContactsLab.getInstance(this);
        long result = contactsLab.addContact(newContact);
        if(result == -1){
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void takeImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }
}
