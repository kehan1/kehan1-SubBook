package com.example.kehanwang.kehan1_subbook;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * This is the mainActivity of SubBook
 *
 * @author Kehan Wang
 *@version 1.0
 *
 *
 */

//This is the main part
public class MainActivity extends AppCompatActivity {



//initialize the objects
    private static final String FILENAME = "file.sav";
    private EditText name;
    private EditText date;
    private EditText monthlyCharge;
    private EditText comment;
    private double totalCharge;
    private ListView oldsubscriptions;

    private Handler mHandler = new Handler();

    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> adapter;


//create the add subscription under the main page of this app
//also add the item_list, the totalcharge textview and the button on the main page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.name1);
        date = (EditText) findViewById(R.id.date1);
        monthlyCharge = (EditText) findViewById(R.id.charge1);
        comment = (EditText) findViewById(R.id.comment1);


//initialize the oldsubscription list
        oldsubscriptions = (ListView) findViewById(R.id.subscriptionList);
        Button saveButton = (Button) findViewById(R.id.save);

        //save user input
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                double newValue = 0.00;

                String subscriptionName = name.getText().toString();
                String subscriptionDate = date.getText().toString();
                String subscriptionmonthlyCharge = monthlyCharge.getText().toString();
                String subscriptionComment = comment.getText().toString();
                //check the input number whether a double
                try{
                    newValue = Double.parseDouble(subscriptionmonthlyCharge);
                }catch (Exception e){

                    Intent intent = new Intent(MainActivity.this, Exceptions.class);
                    startActivity(intent);
                }
                //check conditions
                if (subscriptionName.isEmpty() || subscriptionDate.isEmpty() || subscriptionDate.length() != 10 || subscriptionmonthlyCharge.isEmpty() || newValue<0.00 || subscriptionName.length() > 20 ||  subscriptionComment.length()>30){

                    Intent intent = new Intent(MainActivity.this, Exceptions.class);
                    startActivity(intent);

                 //add it into subscription list
                }else {Subscription newsubscription = new Subscription(subscriptionName, subscriptionDate, newValue, subscriptionComment);
                    subscriptions.add(newsubscription);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
                finish();
                //return to the main page and update the totalcharge
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);


            }

        });



    }



    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();

        //intialize the totalcharge and calculate the total charge.
        totalCharge = 0.00;
        for (Subscription s : subscriptions){
            totalCharge = totalCharge + s.getmonthlyCharge();
        }
        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView totalMonCharge = (TextView) findViewById(R.id.total);

        totalMonCharge.setTextSize(20);
        totalMonCharge.setText("Total monthly charge: "+ formatter.format(totalCharge));

        //update the old item_list
        adapter = new ArrayAdapter<Subscription>(this,
                R.layout.list_item,subscriptions);
        oldsubscriptions.setAdapter(adapter);
        oldsubscriptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int index, long id) {
                Intent intent = new Intent(MainActivity.this, SubscriptionDetail.class);
                intent.putExtra("idx", index);
                startActivity(intent);
            }
        });

        saveInFile();
    }

//learned from lonelyTwitter
    private void loadFromFile() {
        //laod subscription list from gson
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptions = gson.fromJson(in, listType);
            fis.close();
         //create a new subscription list
        } catch (FileNotFoundException e) {
            subscriptions = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

//learned from lonlyTwitter
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            //transfer subscription list to gson
            gson.toJson(subscriptions, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


}
