package com.streamliners.inputconstraints;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.streamliners.inputconstraints.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private static final int REQUEST_COUNT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
    }

    /**
     * Send constraints to input activity
     * @param view
     */
    public void sendConstraints(View view){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, TakeInputActivity.class);

        //Check which checkboxes are checked and add boolean to bundle accordingly
        if (b.upperCaseBox.isChecked()) bundle.putBoolean(Constants.UPPERCASE,true);
        else bundle.putBoolean(Constants.UPPERCASE,false);

        if (b.lowerCaseBox.isChecked()) bundle.putBoolean(Constants.LOWERCASE,true);
        else bundle.putBoolean(Constants.LOWERCASE,false);

        if (b.digitsBox.isChecked()) bundle.putBoolean(Constants.DIGITS,true);
        else bundle.putBoolean(Constants.DIGITS,false);

        if (b.mathOperationsBox.isChecked()) bundle.putBoolean(Constants.MATH_OPERATIONS,true);
        else bundle.putBoolean(Constants.MATH_OPERATIONS,false);

        if (b.otherSymBox.isChecked()) bundle.putBoolean(Constants.OTHER_SYMBOLS,true);
        else bundle.putBoolean(Constants.OTHER_SYMBOLS,false);

        //Send bundle to input activity
        intent.putExtra(Constants.CHECK_BUNDLE,bundle);
        startActivityForResult(intent,REQUEST_COUNT);
    }

    /**
     * Recieves Data from Main Activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_COUNT && resultCode == RESULT_OK){
            //get data
            String str = data.getStringExtra(Constants.FINAL_VALUE);

            //Show data
            b.result.setText("Input : " + str);
            b.result.setVisibility(View.VISIBLE);
        }
    }
}