package com.example.lefei.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Lefei on 10/16/2015.
 */
public class Start extends Activity {

    ImageButton signin;
    ImageButton register;
    LinearLayout layout;
    ImageView test;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starting_screen);

        if(SaveSharedPreference.getUserName(Start.this).length() != 0) {

            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity (intent);
            this.finishActivity (0);

        }

        layout = (LinearLayout)findViewById (R.id.start_layout);

        TransitionDrawable transition = (TransitionDrawable) layout.getBackground();
        //transition.startTransition(1200);

            signin = (ImageButton) findViewById(R.id.sign_in);
            register = (ImageButton) findViewById(R.id.register);

            signin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(getBaseContext(), Register.class);
                    startActivity(i);
                }
            });


    }


}
