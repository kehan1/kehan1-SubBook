package com.example.kehanwang.kehan1_subbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

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
 * This is the edit existing subsciption page
 *
 * @author Kehan Wang
 * @version 1.0
 */

public class EditSubscription extends AppCompatActivity {

    private static final String FILENAME = "file.sav";

    private ArrayList<Subscription> subscriptions;
    private ArrayAdapter<Subscription> adapter;

    private EditText newName;
    private EditText newDate;
    private EditText newComment;
    private EditText newmonthlyCharge;

    private int index;

    @Override
    //create the editing page
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);
        loadFromFile();

        Intent intent = getIntent();
        index = intent.getExtras().getInt("idx");

        newName = (EditText) findViewById(R.id.newName);
        newDate = (EditText) findViewById(R.id.newDate);
        newComment = (EditText) findViewById(R.id.newComment);
        newmonthlyCharge = (EditText) findViewById(R.id.newmonthlyCharge);

        //edit save button created
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                double newValue = 0.00;

                String nameStr = newName.getText().toString();
                String dateStr = newDate.getText().toString();
                String monthlyChargeStr = newmonthlyCharge.getText().toString();
                String CommentStr = newComment.getText().toString();

                //check monthlycharge condition
                if (monthlyChargeStr.equals("") == false) {
                    try {
                        newValue = Double.parseDouble(monthlyChargeStr);
                    } catch (Exception e) {

                        Intent intent = new Intent(EditSubscription.this, Exceptions.class);
                        intent.putExtra("idx", index);
                        startActivity(intent);
                    }
                }
                //check empty condition
                if (nameStr.isEmpty() || dateStr.isEmpty() || dateStr.length() != 10 || monthlyChargeStr.isEmpty() || newValue<0.00 || nameStr.length() > 20 || CommentStr.length()>30) {

                    Intent intent = new Intent(EditSubscription.this, Exceptions.class);
                    startActivity(intent);
                }
                //check date
                if (dateStr.equals("")==false && dateStr.length() == 10){
                    (subscriptions.get(index)).setDate(dateStr);
                    saveInFile();
                }
                //check comment
                if (CommentStr.equals("") == false && CommentStr.length() < 30) {
                    (subscriptions.get(index)).setComment(CommentStr);
                    saveInFile();
                }
                //check name
                if (nameStr.equals("") == false && nameStr.length() < 20) {
                    (subscriptions.get(index)).setName(nameStr);
                    saveInFile();
                }
                if (monthlyChargeStr.equals("") == false  && newValue != 0.00) {
                    (subscriptions.get(index)).setmonthlyCharge(newValue);
                    saveInFile();
                }


            }

        });



    }

//learned from lonlyTwitter
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType =  new TypeToken<ArrayList<Subscription>>() {}.getType();
            subscriptions = gson.fromJson(in,listType);
            fis.close();
        } catch (FileNotFoundException e) {
            subscriptions = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

//learned from lonelyTwitter
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
