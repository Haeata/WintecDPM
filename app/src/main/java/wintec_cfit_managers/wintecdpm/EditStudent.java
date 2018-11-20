package wintec_cfit_managers.wintecdpm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class EditStudent extends AppCompatActivity {

    private static final String TAG = "AddStudent";
    private static final int REQUREST_IMAGE_CAPTURE = 1;
    private TextView studentEnrollmentDate, studentPathway;
    private EditText studentName, studentEmail, studentID;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private ImageView studentPhoto;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);


        studentID = findViewById(R.id.textStudentID);
        studentName = findViewById(R.id.textStudentName);
        studentEmail = findViewById(R.id.textStudentEmail);
        studentEnrollmentDate = findViewById(R.id.textEnrollmentDate);
        studentPathway = findViewById(R.id.textPathway);
        studentPhoto = findViewById(R.id.studentPhoto);

        int sID = getIntent().getIntExtra("studentID",0);
        String sName = getIntent().getStringExtra("studentName");
        String sEnrollmentDate = getIntent().getStringExtra("studentEnrollmentDate");
        String sEmail = getIntent().getStringExtra("studentEmail");
        String sPathway = getIntent().getStringExtra("studentPathway");
        byte[] sPhoto = getIntent().getByteArrayExtra("studentPhoto");


        studentID.setText(String.valueOf(sID));
        studentName.setText(sName);
        studentEnrollmentDate.setText(sEnrollmentDate);
        studentEmail.setText(sEmail);
        studentPathway.setText(sPathway);
        studentPhoto.setImageBitmap(convertToBitmap(sPhoto));

        //date picker for enrollment date
        datePicker();

        //edit student button
        editStudent();

        pathwayDialog();

        takeStudentPhoto();

        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditStudent.this,ManageStudent.class);
                startActivity(intent);
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

    //date picker
    private void datePicker() {
        studentEnrollmentDate = findViewById(R.id.textEnrollmentDate);
        studentEnrollmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        EditStudent.this,
                        R.style.DatePickerTheme,
                        datePickerListener,
                        year, month, day);
                dateDialog.show();
            }
        });

        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + dayOfMonth);

                month = month + 1;
                String d = dayOfMonth + "/" + month + "/" + year;
                studentEnrollmentDate.setText(d);
            }
        };
    }

    //edit button
    private void editStudent(){
        studentID = findViewById(R.id.textStudentID);
        studentName = findViewById(R.id.textStudentName);
        studentEmail = findViewById(R.id.textStudentEmail);
        studentEnrollmentDate = findViewById(R.id.textEnrollmentDate);
        studentPathway = findViewById(R.id.textPathway);
        studentPhoto = findViewById(R.id.studentPhoto);
        Button btnEdit = findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sID = Integer.parseInt(studentID.getText().toString());
                String sName = studentName.getText().toString();
                String sEmail = studentEmail.getText().toString();
                String sDate = studentEnrollmentDate.getText().toString();
                String sPathway = studentPathway.getText().toString();
                Bitmap bitmap = ((BitmapDrawable) studentPhoto.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] sPhoto = profileImage(bitmap);
                Student s = new Student(sName, sEmail, sDate, sPathway, sPhoto);

                DbHandler dbHandler = new DbHandler(EditStudent.this);
                dbHandler.updateStudent(s,sID);
                intent = new Intent(EditStudent.this,ManageStudent.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Student " + sName + " has been edited successfully.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void pathwayDialog() {

        studentPathway = findViewById(R.id.textPathway);
        studentPathway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog pDialog = new Dialog(EditStudent.this);
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.setContentView(R.layout.pathway_dialog);
                pDialog.show();

                LinearLayout webDevLayout = pDialog.findViewById(R.id.webDevLayout);
                LinearLayout networkingLayout = pDialog.findViewById(R.id.networkingLayout);
                LinearLayout softwareLayout = pDialog.findViewById(R.id.softwareLayout);
                LinearLayout databaseLayout = pDialog.findViewById(R.id.databaseLayout);

                final TextView webDev = pDialog.findViewById(R.id.webDevText);
                final TextView networking = pDialog.findViewById(R.id.networkingText);
                final TextView database = pDialog.findViewById(R.id.databaseText);
                final TextView software = pDialog.findViewById(R.id.softwareText);


                //When web development is clicked
                webDevLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        studentPathway.setText(webDev.getText().toString());
                        pDialog.cancel();
                    }
                });
                //When networking is clicked
                networkingLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        studentPathway.setText(networking.getText().toString());
                        pDialog.cancel();
                    }
                });
                //When software engineering is clicked
                softwareLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        studentPathway.setText(software.getText().toString());
                        pDialog.cancel();
                    }
                });
                //When databases is clicked
                databaseLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        studentPathway.setText(database.getText().toString());
                        pDialog.cancel();
                    }
                });
            }
        });
    }

    //convert bitmap to byte[]
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();

    }

    //convert byte[] to bitmap
    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    private void takeStudentPhoto(){
        studentPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Take a picture and pass results along to onActivityResult
                startActivityForResult(intent,REQUREST_IMAGE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUREST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if (data != null) {
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                studentPhoto.setImageBitmap(bitmap);
            }
        }
    }
}
