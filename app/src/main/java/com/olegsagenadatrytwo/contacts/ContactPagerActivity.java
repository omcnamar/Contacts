package com.olegsagenadatrytwo.contacts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class ContactPagerActivity extends AppCompatActivity {

    private static final String EXTRA_CONTACT_ID = "com.olegsagenadatrytwo.contacts";
    private static final String TAG = "ContactPagerActivity";
    private ArrayList<MyContact> contacts;

    public  static Intent newIntent(Context packageContext, int id){
        Log.i(TAG, "BookPagerActivity: newIntent");
        Intent intent = new Intent(packageContext, ContactPagerActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_CONTACT_ID, 0);

        setContentView(R.layout.activity_contact_pager);
        //initialize ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.activity_contact_pager_view_pager);

        contacts = ContactsLab.getInstance(this).getContacts();
        //get Fragment Manager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //set Adapter for mViewPager
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Log.i(TAG, "BookPagerActivity: getItem");
                MyContact contact = contacts.get(position);
                return ContactFragment.newInstance(contact.getId());
            }

            @Override
            public int getCount() {
                return contacts.size();
            }
        });

        for(int i = 0; i< contacts.size(); i++){
            if(contacts.get(i).getId() == id){
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
