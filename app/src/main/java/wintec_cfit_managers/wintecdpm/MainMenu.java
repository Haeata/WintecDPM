package wintec_cfit_managers.wintecdpm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    public static final String PREFS_NAME = "SigninPrefs";

    String msg = new String("The DPM App is a Prototype version of a degree planner only and NOT an official study plan." +
            "It is intended that students shall use this to get an understanding of the pathways offered and then discuss study" +
            "plans with a Wintec representative.Pathways may change accordingly making older versions redundant" +
            " – Wintec takes no responsibility of outdated pathways or information displayed on the app.");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        CardView web_development = findViewById(R.id.webdev_card);
        web_development.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentModule.class);
                intent.putExtra("manager", false);
                intent.putExtra("studentPathway","Web Development");
                intent.putExtra("studentID",999999999);
                intent.putExtra("studentName","Information");

                startActivity(intent);
            }
        });

        CardView networking = findViewById(R.id.network_card);
        networking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentModule.class);
                intent.putExtra("manager", false);
                intent.putExtra("studentPathway","Networking");
                intent.putExtra("studentID",999999999);
                intent.putExtra("studentName","Information");
                startActivity(intent);
            }
        });

        CardView software_eng = findViewById(R.id.software_card);
        software_eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentModule.class);
                intent.putExtra("manager", false);
                intent.putExtra("studentPathway","Software Engineering");
                intent.putExtra("studentID",999999999);
                intent.putExtra("studentName","Information");
                startActivity(intent);
            }
        });

        CardView databases = findViewById(R.id.database_card);
        databases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentModule.class);
                intent.putExtra("manager", false);
                intent.putExtra("studentPathway","Databases");
                intent.putExtra("studentID",999999999);
                intent.putExtra("studentName","Information");
                startActivity(intent);
            }
        });

        CardView manager = findViewById(R.id.manager_card);
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * Check if we successfully logged in before.
                 * If we did, redirect to Manager page
                 */
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                if (settings.getString("logged", "").toString().equals("logged")) {
                    Intent intent = new Intent(MainMenu.this, ManagerMenu.class);
                    startActivity(intent);
                } else {

                    //Open Sign in
                    final Dialog login = new Dialog(MainMenu.this);
                    // Set GUI of login screen
                    login.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    login.setContentView(R.layout.login_gui);
                    login.setTitle("Sign In");

                    // Init button of login GUI
                    Button btnSignIn = login.findViewById(R.id.btnSignIn);
                    Button btnCancel = login.findViewById(R.id.btnCancel);

                    final EditText txtPassword = login.findViewById(R.id.txtPassword);


                    // Attached listener for login GUI button
                    btnSignIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (txtPassword.getText().toString().trim().equals("WinITDMP01")) {
                                // Validate Your login credential here than display message
                                //make SharedPreferences object
                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("logged", "logged");
                                editor.commit();

                                Toast.makeText(MainMenu.this,
                                        "Sign In Successful", Toast.LENGTH_LONG).show();

                                // Redirect to dashboard / home screen.
                                login.dismiss();
                                Intent intent = new Intent(getApplicationContext(), ManagerMenu.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainMenu.this,
                                        "Please enter valid Password", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            login.dismiss();
                        }
                    });

                    // Make dialog box visible.
                    login.show();


                }
            }
        });

        //Displays Disclaimer through sharedpreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean agreed = sharedPreferences.getBoolean("agreed", false);
        if (!agreed) {
            new AlertDialog.Builder(this)
                    .setTitle("Disclaimer")
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("agreed", true);
                            editor.commit();
                        }
                    })
                    .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })

                    .setMessage(msg)
                    .show();
        }


    }




}
