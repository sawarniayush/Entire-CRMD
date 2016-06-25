package com.example.android.splash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

    private TextView signup_text, forget_pass_text;
    private EditText Username, Pass;
    private Button login_button;
    private AlertDialog.Builder builder;
    private String imei = "";
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = tm.getDeviceId();

        signup_text = (TextView) findViewById(R.id.sign_up);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forget_pass_text = (TextView) findViewById(R.id.forget_pass);
        forget_pass_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
                backgroundTask.execute("forget_password", imei);
            }
        });

        Username = (EditText) findViewById(R.id.username_login);
        Pass = (EditText) findViewById(R.id.password_login);
        login_button = (Button) findViewById(R.id.login_button);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        /*
         * code to set up font for text views
         */
        TextView login_header = (TextView) findViewById(R.id.login_header);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");

        login_header.setTypeface(typeFace);

        if (saveLogin == true) {
            Username.setText(loginPreferences.getString("username", ""));
            Pass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Username.getText().toString().equals("") || Pass.getText().toString().equals("")) {
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Please fill both the fields");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);


                    String username = Username.getText().toString();
                    String password = Pass.getText().toString();
                    if (saveLoginCheckBox.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    /*
                     * AsyncTask has the form AsyncTask<Param, Progress, Return> with execute using the Param type.
                     * so here we are sending the @Params to the Background task with all the parameters as strings
                     */

                    backgroundTask.execute("login", Username.getText().toString(), Pass.getText().toString(), imei);
                    /*
                     * here login is arg[0], Username.getText().toString() is arg[1] and so on.
                     */

                }

            }
        });


        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable Location";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
//                                this.startActivity(new Intent(action));
                                Intent i = new Intent(action);
                                startActivity(i);
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();


//        }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//}
    }
}

