package com.example.lefei.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;


/**
 * Created by Lefei on 10/16/2015.
 */
public class AfterFacebook extends Activity {

    EditText fn;
    EditText ln;
    EditText em;
    EditText mb;
    EditText ps;
    TextView er;
    ImageView img;
    Button done;

    boolean retype = false;

    int keyDel = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_facebook_login);

        fn = (EditText) findViewById(R.id.user_first_name);
        ln = (EditText) findViewById(R.id.user_last_name);
        em = (EditText) findViewById(R.id.user_email);
        mb = (EditText) findViewById(R.id.user_mobile);
        ps = (EditText) findViewById(R.id.user_password);
        er = (TextView) findViewById(R.id.user_error_message);
        img = (ImageView) findViewById(R.id.user_profile_pic);
        done = (Button) findViewById(R.id.user_done);

        mb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int len = mb.getText().toString().trim().length();
                if (hasFocus) {
                    if(len == 0) {
                        mb.setText("(");
                        mb.setSelection(1);
                    }
                } else {
                    if (mb.getText().toString().trim().equals("(")) {
                        mb.setText("");
                    }
                    retype = true;
                }
            }
        });

        mb.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mb.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = mb.getText().toString().trim().length();

                    if(len == 4) {
                        mb.setText(mb.getText().toString() + ")");
                        mb.setSelection(mb.getText().toString().trim().length());
                    }
                    if(len == 8) {
                        mb.setText(mb.getText().toString() + "-");
                        mb.setSelection(mb.getText().toString().trim().length());
                    }
                    if(!retype && len == 13) {
                        ps.requestFocus();
                    }
                } else {
                    keyDel = 0;
                }
            }



            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String id = extras.getString("id");

            SaveSharedPreference.setUserName(AfterFacebook.this, name);

            int pos_space = name.indexOf(" ");
            String first_name = name.substring(0, pos_space);
            String last_name = name.substring(pos_space + 1);

            fn.setText(first_name);
            ln.setText(last_name);

            em.requestFocus();


            //Bitmap bitmap = getFacebookProfilePicture(id);
            String url_id = "https://graph.facebook.com/" + id + "/picture?type=large";
            Picasso.with(getBaseContext()).load(url_id).resize(80, 80).centerCrop().into(img);

            done.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String fname = fn.getText().toString();
                    String lname = ln.getText().toString();
                    String email = em.getText().toString();
                    String mobile = mb.getText().toString();
                    String password = ps.getText().toString();
                    if (fname.trim().equals("")){
                        er.setText("* Please enter a valid first name.");
                    }
                    else if (lname.trim().equals("")){
                        er.setText("* Please enter a valid last name.");
                    }
                    else if (!checkEmail(email)){
                        er.setText("* Please enter a valid email address.");
                    }
                    else if (!checkMobile(mobile)){
                        er.setText("* Please enter a valid phone number.");
                    }
                    else if (password.length()==0){
                        er.setText("* Please enter password.");
                    }
                    else if (password.length()<6){
                        er.setText("* Password should be longer than 6 characters.");
                    }
                    else {
                        String name = fname + " " + lname;
                        SaveSharedPreference.setUserName(AfterFacebook.this, name);
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                    }
                }
            });

        }

    }

    public static Bitmap getFacebookProfilePicture(String userID) {
        Bitmap bitmap = null;
        try {
            URL imageUrl = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bitmap;

    }

    public static Bitmap getBitmapFromURL(String userID) {
        try {
            URL url = new URL(userID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();

    }

    public boolean checkMobile(String number){
        boolean valid = true;

        if (number.length()!=13)
            valid = false;
        if (number.indexOf("(")!=0)
            valid = false;
        if (number.indexOf(")")!=4)
            valid = false;
        if (number.indexOf("-")!=8)
            valid = false;
        StringBuilder sb = new StringBuilder(number);
        sb.deleteCharAt(0);
        sb.deleteCharAt(3);
        sb.deleteCharAt(6);
        String resultString = sb.toString();
        String regex = "[0-9]+";
        if(!resultString.matches(regex))
            valid=false;

        return valid;
    }
}
