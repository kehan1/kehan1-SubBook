package com.example.kehanwang.kehan1_subbook;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


/**
 * if the user enter the invalid info, then this page will be invoke
 * Date: Feb 5. 2018
 * @author Kehan Wang
 * @version 1.0
 */
public class Exceptions extends AppCompatActivity {
    private TextView Wrong;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exceptions);

        Wrong = (TextView)findViewById(R.id.wrongmessage);

    }
}
