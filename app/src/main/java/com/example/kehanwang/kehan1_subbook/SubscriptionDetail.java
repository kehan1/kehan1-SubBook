package com.example.kehanwang.kehan1_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;


/**
 * User can see the detail about the subscription and Edit and Delete that subscription.
 *
 * Date: Feb 5, 2017
 *
 * @author Kehan Wang
 * @version 1.0
 */


public class SubscriptionDetail extends AppCompatActivity {

    private static final String FILENAME = "file.sav";

    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> adapter;
    //intialize the textView
    private int index;
    private TextView name;
    private TextView date;
    private TextView monthlyCharge;
    private TextView comment;
    //initialize the strings
    private String nameStr;
    private String dateStr;
    private double monthlyChargeInt;
    private String commentStr;

    @Override

    //create the the page and textviews
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile();
        setContentView(R.layout.activity_subscripiton_detail);
        Intent intent = getIntent();
        index = intent.getExtras().getInt("idx");


        name = (TextView)findViewById(R.id.nameText);
        date = (TextView)findViewById(R.id.dateText);
        monthlyCharge = (TextView)findViewById(R.id.monthlyChargeText);
        comment = (TextView)findViewById(R.id.commentText);
    }

    //show detail subsciptions of the page
    protected void onStart(){
        super.onStart();
        nameStr = (subscriptions.get(index)).getName();
        dateStr = (subscriptions.get(index)).getDate();
        monthlyChargeInt = (subscriptions.get(index)).getmonthlyCharge();
        commentStr = (subscriptions.get(index)).getComment();

        name.setTextSize(20);
        name.setText("Name: "+nameStr);
        date.setTextSize(20);
        date.setText("Date: "+dateStr);
        monthlyCharge.setTextSize(20);
        monthlyCharge.setText("Count: "+monthlyChargeInt);
        comment.setTextSize(20);
        comment.setText("Comment: "+commentStr);

        //add edit and delete button
        Button Edit = (Button) findViewById(R.id.Edit);
        Button delete = (Button) findViewById(R.id.delete);

        Edit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionDetail.this, EditSubscription.class);
                intent.putExtra("idx", index);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                subscriptions.remove(index);
                saveInFile();
                Intent backToMain = new Intent(SubscriptionDetail.this, MainActivity.class);
                startActivity(backToMain);
            }
        });
        saveInFile();
    }

//learned from lonlyTwitter
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptions = gson.fromJson(in, listType);

            fis.close();

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
