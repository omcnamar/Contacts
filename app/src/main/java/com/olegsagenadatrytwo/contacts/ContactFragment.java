package com.olegsagenadatrytwo.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by omcna on 8/8/2017.
 */

public class ContactFragment extends Fragment {
    private static final String TAG = "ContactFragment";
    private static final String ARGS_CONTACT_ID = "contactId";
    private static final int RESULT_LOAD_IMAGE = 1;

    private MyContact contact;
    private EditText name;
    private EditText number;
    private EditText address;
    private EditText email;
    private ImageView image;
    private Button submit;
    private Button call;
    private Button delete;

    private ArrayList<MyContact> contacts;

    public static ContactFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(ARGS_CONTACT_ID,id);
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = (int)getArguments().getSerializable(ARGS_CONTACT_ID);
        contacts = ContactsLab.getInstance(getActivity()).getContacts();
        contact = getContact(id);
        setHasOptionsMenu(true);
    }

    public MyContact getContact(int id){
        for(int i = 0; i<contacts.size(); i++){
            if(contacts.get(i).getId() == id){
                return contacts.get(i);
            }
        }
        return null;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "BookFragment: onCreateView");
        View v = inflater.inflate(R.layout.view_contact, container, false);
        name = (EditText) v.findViewById(R.id.etName);
        number = (EditText) v.findViewById(R.id.etNumber);
        address = (EditText) v.findViewById(R.id.etAddress);
        email = (EditText) v.findViewById(R.id.etEmail);
        submit = (Button) v.findViewById(R.id.btSubmit);
        delete = (Button) v.findViewById(R.id.btDelete);
        call = (Button) v.findViewById(R.id.btnCall);
        image = (ImageView) v.findViewById(R.id.ivImageView);

        //now set the text to what ever it is and add a listener to each edit text
        name.setText(contact.getName());
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact.setName(editable.toString());
            }
        });

        image.setImageBitmap(contact.getImage());

        number.setText(contact.getNumber());
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact.setNumber(editable.toString());
            }
        });

        address.setText(contact.getAddress());
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setAddress(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact.setAddress(editable.toString());
            }
        });

        email.setText(contact.getEmail());
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                contact.setEmail(editable.toString());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = name.getText().toString();
                String numberString = number.getText().toString();
                String addressString = address.getText().toString();
                String emailString = email.getText().toString();
                contact.setName(nameString);
                contact.setNumber(numberString);
                contact.setAddress(addressString);
                contact.setEmail(emailString);
                contact.setImage(((BitmapDrawable)image.getDrawable()).getBitmap());
                ContactsLab contactsLab = ContactsLab.getInstance(getActivity());
                long result = contactsLab.updateContact(contact);
                if(result == -1){
                    Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete this contact?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContactsLab contactsLab = ContactsLab.getInstance(getActivity());
                                long result = contactsLab.deleteContact(contact);
                                if(result == -1){
                                    Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                }
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact.getNumber(), null));
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

}
