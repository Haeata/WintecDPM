package wintec_cfit_managers.wintecdpm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AddModule extends AppCompatActivity {

    private static final String TAG = "AddModule";
    private String result;
    private EditText moduleName, moduleCode, moduleLevel, moduleCredits, modulePrereq, moduleCoreq, moduleDesc, moduleYear;
    private CheckBox webDev, networking, databases, software, core;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        //assign the variable with the id of fields.
        moduleName = findViewById(R.id.textModuleName);
        moduleCode = findViewById(R.id.textModuleCode);
        moduleLevel = findViewById(R.id.textLevel);
        moduleYear = findViewById(R.id.textYear);
        moduleCredits = findViewById(R.id.textCredits);
        modulePrereq = findViewById(R.id.textPreRequisite);
        moduleCoreq = findViewById(R.id.textCoRequisite);
        moduleDesc = findViewById(R.id.textDescription);

        core = findViewById(R.id.coreText);
        webDev = findViewById(R.id.webDevText);
        networking = findViewById(R.id.networkingText);
        databases = findViewById(R.id.databasesText);
        software = findViewById(R.id.softwareText);

        core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(core.isChecked()){
                    webDev.setChecked(false);
                    networking.setChecked(false);
                    databases.setChecked(false);
                    software.setChecked(false);

                    webDev.setEnabled(false);
                    networking.setEnabled(false);
                    databases.setEnabled(false);
                    software.setEnabled(false);
                }
                if(!core.isChecked()){
                    webDev.setEnabled(true);
                    networking.setEnabled(true);
                    databases.setEnabled(true);
                    software.setEnabled(true);
                }
            }
        });

        //create module button action
        createModule();

        //Cancel button and back button confirmation
        Button btnCancel = findViewById(R.id.btnCancelModule);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddModule.this,StudentModule.class);
                startActivity(intent);
                intent.putExtra("manager",true);
                intent.putExtra("selectedPathway",getIntent().getStringExtra("selectedPathway"));
                finish();
            }
        });
    }

    //select back to go homescreen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //create button
    private void createModule() {

        Button btnCreateModule = findViewById(R.id.btnCreateModule);

        //btnCreate click to create new module in database
        btnCreateModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = "";
                if(core.isChecked()){
                    result = "Core";
                }
                if(webDev.isChecked()){
                    result += "Web Development,";
                }
                if(networking.isChecked()){
                    result += "Networking,";
                }
                if(databases.isChecked()){
                    result += "Databases,";
                }
                if(software.isChecked()){
                    result += "Software Engineering,";
                }
                Log.i(TAG,result);
                String mCode = moduleCode.getText().toString();
                String mName = moduleName.getText().toString();
                String mPathway = result;
                int mLevel = Integer.parseInt(moduleLevel.getText().toString());
                int mCredits = Integer.parseInt(moduleCredits.getText().toString());
                String mPrereq = modulePrereq.getText().toString();
                String mCoreq = moduleCoreq.getText().toString();
                String mDesc = moduleDesc.getText().toString();
                int mYear = Integer.parseInt(moduleYear.getText().toString());

                Module m = new Module(mCode,mName,mLevel,mCredits,mPrereq,mCoreq,mYear,mPathway,mDesc);

                DbHandler dbHandler = new DbHandler(AddModule.this);

                dbHandler.addModule(m);
                intent = new Intent (AddModule.this,StudentModule.class);
                intent.putExtra("manager",true);
                intent.putExtra("studentPathway",getIntent().getStringExtra("selectedPathway"));
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Module was successfully created",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str="";
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.coreText:
                str = checked?"Core selected":"Core Deselected";
                break;
            case R.id.webDevText:
                str = checked?"Web Development Selected":"Web Development Deselected";
                break;
            case R.id.networkingText:
                str = checked?"Networking Selected":"Networking Deselected";
                break;
            case R.id.databasesText:
                str = checked?"Software Engineering Selected":"Software Engineering Deselected";
                break;
            case R.id.softwareText:
                str = checked?"Databases Selected":"Databases Deselected";
                break;
        }
        //Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}


