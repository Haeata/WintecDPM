package wintec_cfit_managers.wintecdpm;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class ManageStudent extends AppCompatActivity {

    private static final String TAG = "ManageStudent";
    private ListView studentListView;
    private ArrayList<Student> studentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadListView();

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

    //Loading the list of student using custom adapter
    protected void loadListView() {
        //clear the list
        studentList.clear();

        //getStudent from the database
        DbHandler db = new DbHandler(this);
        final ArrayList<Student> s = db.getStudent();
        if (s.size() > 0) {
            for (int i = 0; i < s.size(); i++) {
                studentList.add(s.get(i));
            }
        }

        StudentListAdapter studentListAdapter = new StudentListAdapter(this,
                R.layout.list_row_student,studentList);
        studentListView = findViewById(R.id.student_list);
        studentListView.setAdapter(studentListAdapter);
    }
}
