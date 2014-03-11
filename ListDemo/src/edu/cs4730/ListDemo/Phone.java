package edu.cs4730.ListDemo;

/*
 * This code is copied from http://techdroid.kbeanie.com/2009/07/custom-listview-for-android.html
 */

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Phone extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);

        ListView list = (ListView) findViewById(R.id.ListView01);
        list.setClickable(true);

        final List<Phonebook> listOfPhonebook = new ArrayList<Phonebook>();
        listOfPhonebook.add(new Phonebook("Test", "9981728", "test@test.com"));
        listOfPhonebook.add(new Phonebook("Test1", "1234455", "test1@test.com"));
        listOfPhonebook.add(new Phonebook("Test2", "00000", "test2@test.com"));
        listOfPhonebook.add(new Phonebook("Test3", "00000", "test3@test.com"));
        listOfPhonebook.add(new Phonebook("Test4", "00000", "test4test.com"));
        listOfPhonebook.add(new Phonebook("Test5", "00000", "test5@test.com"));
        listOfPhonebook.add(new Phonebook("Test6", "00000", "test6@test.com"));

        PhonebookAdapter adapter = new PhonebookAdapter(this, listOfPhonebook);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long index) {
                showToast(listOfPhonebook.get(position).getName());
            }
        });

        list.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

