package wintec_cfit_managers.wintecdpm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {

    //Logcat tag
    private static final String TAG = "DbHandler";

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "WintecDpm.db";

    //Students Table & Column Name
    private static final String TABLE_STUDENTS = "Students";
    private static final String COLUMN_STUDENT_ID = "StudentID";
    private static final String COLUMN_STUDENT_NAME = "Name";
    private static final String COLUMN_STUDENT_EMAIL = "Email";
    private static final String COLUMN_STUDENT_ENROLLMENT_DATE = "EnrollmentDate";
    private static final String COLUMN_STUDENT_PATHWAYNAME = "Pathway";

    //Pathway Table & Column Name
    private static final String TABLE_PATHWAYS = "Pathways";
    private static final String COLUMN_PATHWAY_ID = "PathwayID";
    private static final String COLUMN_PATHWAY_NAME = "PathwayName";

    //CREATE TABLE statements

    //Students Table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + " ( "
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_STUDENT_NAME + " TEXT, "
            + COLUMN_STUDENT_EMAIL + " TEXT, "
            + COLUMN_STUDENT_ENROLLMENT_DATE + " DATETIME, "
            + COLUMN_STUDENT_PATHWAYNAME + " TEXT )";

    //Pathways Table create statement
    private static final String CREATE_TALBE_PATHWAYS = "CREATE TABLE "
            + TABLE_PATHWAYS + " ( "
            + COLUMN_PATHWAY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_PATHWAY_NAME + " TEXT )";

    //constructor
    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TALBE_PATHWAYS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATHWAYS);

        //create new tables
        onCreate(db);
    }

    /*
        CRUD (CREATE, READ, UPDATE AND DELETE) OPERATIONS FOR EACH TABLE
     */

    /*
       Students Table CRUD
     */

    //Creating a student
    public void addStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, student.getStudentID());
        values.put(COLUMN_STUDENT_NAME, student.getName());
        values.put(COLUMN_STUDENT_EMAIL, student.getEmail());
        values.put(COLUMN_STUDENT_ENROLLMENT_DATE, student.getEnrollmentDate());
        values.put(COLUMN_STUDENT_PATHWAYNAME, student.getPathway());

        //insert values into table
        db.insert(TABLE_STUDENTS,null,values);

        //Log check insert
        Log.i(TAG,"Student ID: " + String.valueOf(student.getStudentID())
                + " inserted successfully");

        db.close();
    }

    //READ Student
    public ArrayList<Student> getStudent(){
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS
                + " ORDER BY " + COLUMN_STUDENT_ID + " ASC";


        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            Student s = new Student();
            s.setStudentID(Integer.parseInt(cursor.getString(
                    cursor.getColumnIndex(COLUMN_STUDENT_ID))));
            s.setName(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME)));
            s.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)));
            s.setEnrollmentDate(cursor.getString(
                    cursor.getColumnIndex(COLUMN_STUDENT_ENROLLMENT_DATE)));
            s.setPathway(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PATHWAYNAME)));
            studentList.add(s);
        }
        cursor.close();
        db.close();
        return studentList;
    }

    //DELETE Student
    public void deleteStudent (int studentID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS,COLUMN_STUDENT_ID + "= ?",
                new String[]{String.valueOf(studentID)});
        db.close();
    }
}
