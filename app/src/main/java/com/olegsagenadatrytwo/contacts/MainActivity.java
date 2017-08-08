package com.olegsagenadatrytwo.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityContacts";
    private RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_contact) {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
    }

    //this method updates the user interface if any changes were made
    private void updateUI() {
        ContactsLab contactsLab = ContactsLab.getInstance(this);
        List<MyContact> contacts = contactsLab.getContacts();
        if(contactsAdapter == null) {
            contactsAdapter = new ContactsAdapter(contacts);
            contactsRecyclerView.setAdapter(contactsAdapter);
        }else{
            //updated single item
            // mAdapter.notifyItemChanged(saved);
            //update multiple
            contactsAdapter.setContacts(contacts);
            contactsAdapter.notifyDataSetChanged();
        }
//        //here we need to update the toolbars number of crimes when returning with back button
//        updateSubtitle();
//        Log.i(TAG, "CrimeLF: end updateUI");
    }

    //adapter
    //Adapter
    private class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder>{
        private List<MyContact> contacts;

        public ContactsAdapter(List<MyContact> contacts){
            this.contacts = contacts;
        }

        @Override
        public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.list_initial_info, parent, false);
            return new ContactsHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactsHolder holder, int position) {
            MyContact contact = contacts.get(position);
            holder.bindContact(contact);
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public void setContacts(List<MyContact> contacts) {
            this.contacts = contacts;
        }
    }

    //holder
    //ViewHolder
    private class ContactsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        private TextView tvName;
        private MyContact contact;

        public ContactsHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.ivImageInitial);
            tvName  = itemView.findViewById(R.id.tvNameInitial);
        }

        public void bindContact(MyContact contactIn){
            contact = contactIn;
            tvName.setText(contact.getName());
            if(contact.getImage() != null){
                image.setImageBitmap(contact.getImage());
            }
        }
        public void onClick(View v){

//            saved = getAdapterPosition();
            Intent intent = ContactPagerActivity.newIntent(getApplicationContext(),contact.getId());
            startActivity(intent);
        }
    }
}
