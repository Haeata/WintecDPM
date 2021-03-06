package wintec_cfit_managers.wintecdpm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

public class ManagerMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public static final String PREFS_NAME = "SigninPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navList);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView manage_student=findViewById(R.id.manageStudents_card);
        manage_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ManageStudent.class);
                startActivity(intent);
            }
        });

        CardView manage_module=findViewById(R.id.manageModule_card);
        manage_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),StudentModule.class);
                intent.putExtra("manager",true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
        super.onBackPressed();
    }

    //Handles Item clicks in navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent MainMenu=new Intent(getApplicationContext(), wintec_cfit_managers.wintecdpm.MainMenu.class);
                finish();
                startActivity(MainMenu);
                break;
            case R.id.students_cap:
                Intent ManageStudent=new Intent(getApplicationContext(),ManageStudent.class);
                startActivity(ManageStudent);
                break;
            case R.id.manage_module:
                Intent ManageModule=new Intent(getApplicationContext(),StudentModule.class);
                ManageModule.putExtra("manager",true);
                startActivity(ManageModule);
                break;
            case R.id.about:
                Intent About=new Intent(getApplicationContext(),AboutUs.class);
                startActivity(About);
                break;
            case R.id.signout:
                if (item.getItemId() == R.id.signout) {
                    //Clears sign in for shared preferences
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.clear();
                    editor.commit();
                    Intent signout = new Intent(getApplicationContext(),MainMenu.class);
                    startActivity(signout);
                    finish();
                }
                return super.onOptionsItemSelected(item);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item)){
            return (true);

        }

        if (id == R.id.home) {

            Intent intent = new Intent(this,MainMenu.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.students_cap) {

            Intent intent = new Intent(this,ManageStudent.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.manage_module) {

            Intent intent = new Intent(this,StudentModule.class);
            intent.putExtra("manager",true);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.about) {

            Intent intent = new Intent(this,AboutUs.class);
            this.startActivity(intent);
            return true;
        }
            if (item.getItemId() == R.id.signout) {

            Intent intent = new Intent(this,MainMenu.class);
            this.startActivity(intent);
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
