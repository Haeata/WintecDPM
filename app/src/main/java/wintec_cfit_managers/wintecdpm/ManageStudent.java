package wintec_cfit_managers.wintecdpm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;

public class ManageStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ManageStudent";
    private ArrayList<Student> studentList = new ArrayList<>();
    private Intent intent;
    private StudentListAdapter studentListAdapter;

    //Variables for menu drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public static final String PREFS_NAME = "SigninPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);
        Log.i(TAG,"onCreate");
        //load listview
        loadListView();

        //floating button
        floatingButton();

        //Menu Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_mngst);
        NavigationView navigationView = findViewById(R.id.navList);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Hides menu toggle so back button should be visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item =menu.findItem(R.id.appBarSearch);
        SearchView searchview = (SearchView) item.getActionView();
        searchview.setQueryHint("Search student name or ID number");
        searchview.setMaxWidth(Integer.MAX_VALUE);
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                studentListAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    //Loading the list of student using custom adapter
    protected void loadListView() {
        Log.i(TAG,"ListView Loaded");
        //clear the list
        studentList.clear();

        //getStudent from the database
        DbHandler db = new DbHandler(this);
        final ArrayList<Student> s = db.getStudent();
        if (s.size() > 0) {
            studentList.addAll(s);

        }

        ListView studentListView = findViewById(R.id.student_list);
        studentListAdapter = new StudentListAdapter(studentList);
        studentListView.setAdapter(studentListAdapter);
    }

    //floating button
    protected void floatingButton(){
        Log.i(TAG,"floatingButton created");
        FloatingActionButton fab = findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(TAG,"Intent to Add student");
                intent = new Intent (ManageStudent.this,AddStudent.class);
                startActivity(intent);
            }
        });
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

        return super.onOptionsItemSelected(item);
    }


}
