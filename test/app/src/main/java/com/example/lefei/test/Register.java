package com.example.lefei.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * Created by Lefei on 10/16/2015.
 */
public class Register extends Activity {

    LoginButton connectfb;
    Button done;
    CallbackManager callbackManager;

    EditText fn;
    EditText ln;
    EditText em;
    EditText mb;
    EditText ps;
    TextView er;

    boolean retype = false;

    int keyDel = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logOut();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.v("LoginActivity", response.toString());

                                        try {
                                            String name = object.getString("name");
                                            String id = object.getString("id");
                                            Intent i = new Intent(getBaseContext(), AfterFacebook.class);
                                            i.putExtra("name", name);
                                            i.putExtra("id", id);
                                            startActivity(i);
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                            Toast.makeText(getBaseContext(), "Failed to retrieve data from Facebook", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getBaseContext(), "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });

        setContentView(R.layout.register);
        connectfb = (LoginButton) findViewById(R.id.connectfb);
        connectfb.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

        fn = (EditText) findViewById (R.id.register_user_firstname);
        ln = (EditText) findViewById (R.id.register_user_lastname);
        em = (EditText) findViewById (R.id.register_user_email);
        mb = (EditText) findViewById (R.id.register_user_mobile);
        ps = (EditText) findViewById (R.id.register_user_password);
        done = (Button) findViewById(R.id.register_done);
        er = (TextView) findViewById (R.id.register_error_message);

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
                    SaveSharedPreference.setUserName(Register.this, name);
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

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
