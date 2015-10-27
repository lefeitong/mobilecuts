package com.example.lefei.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Lefei on 10/23/2015.
 */
public class Login extends Activity {

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
        setContentView(R.layout.login);

        em = (EditText) findViewById (R.id.login_user_email);
        ps = (EditText) findViewById (R.id.login_user_password);
        done = (Button) findViewById(R.id.login_done);
        er = (TextView) findViewById (R.id.login_error_message);


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

                else if (password.length()==0){
                    er.setText("* Please enter password.");
                }
                else if (password.length()<6){
                    er.setText("* Password should be longer than 6 characters.");
                }
                else {
                    String name = fname + " " + lname;
                    SaveSharedPreference.setUserName(Login.this, name);
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }







}