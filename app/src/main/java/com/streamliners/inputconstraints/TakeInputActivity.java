package com.streamliners.inputconstraints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.streamliners.inputconstraints.databinding.ActivityTakeInputBinding;

import java.util.ArrayList;

public class TakeInputActivity extends AppCompatActivity {

    private ActivityTakeInputBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityTakeInputBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        textEditRemoveError();
    }

    //Remove Error when text changed
    public void textEditRemoveError(){
        TextWatcher textWatcher= new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                b.textField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        b.textField.getEditText().addTextChangedListener(textWatcher);
    }

    /**
     * Validate input and send back input
     * @param view
     */
    public void sendInput(View view){
        String input = b.textField.getEditText().getText().toString().trim();
        Bundle bundle = getIntent().getBundleExtra(Constants.CHECK_BUNDLE);

        //Validate LowerCase
        int flagLowerCase = 1;
        if (!bundle.getBoolean(Constants.LOWERCASE)){
            char ch = 'a';
            for (int i=0;i<26;i++){
                if (input.contains(Character.toString(ch))){
                    flagLowerCase = 0;
                    break;
                }
                ch++;
            }
        }

        //Validate UpperCase
        int flagUpperCase = 1;
        if (!bundle.getBoolean(Constants.UPPERCASE)){
            char ch = 'A';
            for (int i=0;i<26;i++){
                if (input.contains(Character.toString(ch))){
                    flagUpperCase = 0;
                    break;
                }
                ch++;
            }
        }

        //Validate Digits
        int flagDigits = 1;
        if (!bundle.getBoolean(Constants.DIGITS)){
            for (int i=0;i<10;i++){
                if (input.contains(Integer.toString(i))){
                    flagDigits = 0;
                    break;
                }
            }
        }

        //Validate Mathematical Operations
        int flagMath = 1;
        if (!bundle.getBoolean(Constants.MATH_OPERATIONS)){
            String str = "+-/*%=";
            for (int i=0;i<str.length();i++){
                if (input.contains(Character.toString(str.charAt(i)))){
                    flagMath = 0;
                    break;
                }
            }
        }

        //Validate Other Symbols
        int flagOtherSym = 1;
        if (!bundle.getBoolean(Constants.OTHER_SYMBOLS)){
            String str = "!@#$^&()_?/:;,><";
            for (int i=0;i<str.length();i++){
                if (input.contains(Character.toString(str.charAt(i)))){
                    flagOtherSym = 0;
                    break;
                }
            }
        }

        //Checks where input constraints are violated
        ArrayList<String> arrayList = new ArrayList<>();
        if (!bundle.getBoolean(Constants.UPPERCASE)){
            if (flagUpperCase == 0){
                arrayList.add("Remove uppercase alphabets (A-Z)");
            }
        }

        if (!bundle.getBoolean(Constants.LOWERCASE)){
            if (flagLowerCase == 0) {
                arrayList.add("Remove lowercase alphabets (a-z)");
            }
        }

        if (!bundle.getBoolean(Constants.DIGITS)){
            if (flagDigits == 0) {
                arrayList.add("Remove digits (0-9)");
            }
        }

        if (!bundle.getBoolean(Constants.MATH_OPERATIONS)){
            if (flagMath == 0) {
                arrayList.add("Remove mathematical symbols (*, /, -, etc.)");
            }
        }

        if (!bundle.getBoolean(Constants.OTHER_SYMBOLS)){
            if (flagOtherSym == 0) {
                arrayList.add("Remove other symbols (&, #, etc.)");
            }
        }
        StringBuilder sb = new StringBuilder();
        //Shows error on textField and return
        if (!arrayList.isEmpty()){
            for (int i=0;i<arrayList.size();i++){
                sb.append("- " + arrayList.get(i) + "\n");
            }
            b.textField.setError(sb.toString());
            return;
        }

        //Send input to main activity
        Intent intent = new Intent();
        intent.putExtra(Constants.FINAL_VALUE,input);
        setResult(RESULT_OK,intent);

        //Close Activity
        finish();
    }
}