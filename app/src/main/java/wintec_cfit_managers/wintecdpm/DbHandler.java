package wintec_cfit_managers.wintecdpm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {

    Context context;

    // --------------------------------------- DEFAULT MODULE DATA ---------------------------------------------------------------------- //

    /*
    Module  = new Module("", "", , , "none", "none", 1, 0, "");
    */

    //Year 1 modules
    Module COMP501 = new Module("COMP501","Information Technology Operations", 5, 15, "none", "none", 1, "Core", "To enable students to apply fundamental IT technical support concepts and practice, and configure and administer systems and applications to meet organisational requirements.");
    Module COMP502 = new Module("COMP502","Fundamentals of Programming and Problem Solving", 5, 15, "none", "none", 1, "Core", "To enable students to apply the principles of software development to create simple working applications and use problem-solving and decision-making techniques to provide innovative and timely Information Technology outcomes.");
    Module INFO501 = new Module("INFO501","Professional Practice", 5, 15, "none", "none", 1, "Core", "To enable students to apply professional, legal, and ethical principles and practices in a socially responsible manner as an emerging IT professional, and apply communication, personal and interpersonal skills to enhance effectiveness in an IT role.");
    Module INFO502 = new Module("INFO502","Business Systems Analysis and Design", 5, 15, "none", "none", 1, "Core", "The student will be able to apply the fundamentals of information systems concepts and practice to support and enhance organisational processes and systems; and apply the fundamentals of interaction design concepts and practice to enhance interface design.");
    Module COMP503 = new Module("COMP503","Introduction to Networks", 5, 15, "none", "none", 1, "Core", "To enable students to apply a broad operational knowledge of networking, and associated services and technologies to meet typical organisational requirements.");
    Module COMP504 = new Module("COMP504","Operating Systems and Systems Support", 5, 15, "none", "none", 1, "Core", "To enable students to select, install, and configure IT hardware and systems software and use graphical (GUI) and command line interfaces (CLI) to meet organisational requirements.");
    Module INFO503 = new Module("INFO503","Database Principles", 5, 15, "none", "none", 1, "Core", "To enable students to apply a broad operational knowledge of database administration to meet typical organisational data storage and retrieval requirements, and apply conceptual knowledge of cloud services and virtualisation.");
    Module INFO504 = new Module("INFO504","Technical Support", 5, 15, "none", "none", 1, "Core", "To enable students to demonstrate an operational knowledge and understanding of IT service management, identify common issues related to IT security, and troubleshoot and resolve a range of common system problems.");

    //Year 2 modules (core)
    Module COMP601 = new Module("COMP601", "Object Oriented Programming",6,15, "COMP502", "none", 2, "Core", "To enable students to gain the skills to develop software applications using Object Oriented Programming techniques. Students will enrich their programming and problem solving skills by applying classes, objects, constructors, methods and properties, inheritance and polymorphism to develop programming applications.");
    Module INFO601 = new Module("INFO601", "Database Modelling and SQL", 6, 15, "INFO503", "none", 2, "Core", "To enable students to apply an in depth understanding of database modelling, database design and SQL concepts.");
    Module MATH601 = new Module("MATH601", "Mathematics for Information Technology", 6, 15, "none", "none", 2, "Core", "To enable students to gain mathematical skills and acquire in-depth understanding of mathematics as applied to Information Technology.");
    Module COMP602 = new Module("COMP602", "Web Development", 6, 15, "none", "INFO502, COMP502", 2, "Core", "To enable students to gain the in depth knowledge and skills required to be able to write programs in web programming languages that solve various web programming tasks.");
    Module INFO602 = new Module("INFO602", "Business, Interpersonal Communications & Technical Writing", 6, 15, "none", "none", 2, "Core", "Systems analysis and design is a common thread throughout all areas within the IT industry. This module will equip the student with the necessary skills to be more effective regardless of their area of specialisation.");
    //web
    Module COMP603 = new Module("COMP603", "The Web Environment", 6, 15, "COMP602", "none", 2, "Web Development", "To enable students to apply a broad operational knowledge of data centres and associated technologies to meet typical organisational requirements.");
    Module COMP607 = new Module("COMP607", "Visual Effects and Animation", 6, 15, "COMP602", "none", 2, "Web Development", "To enable students to develop the knowledge and skills required to design and develop effective graphics and animation, and to apply various visual effects for static graphics as well as create 3D models and produce 2D and 3D animation.");
    //networking
    Module COMP615 = new Module("COMP615", "Data Centre Infrastructure",6,15, "none", "none", 2, "Networking", "To enable students to apply a broad operational knowledge of data centres and associated technologies to meet typical organisational requirements.");
    Module INFO603 = new Module("INFO603", "Systems Administration", 6, 15, "none", "none", 2, "Networking", "To enable students to gain the skills and knowledge required to effectively plan, install and administer a Microsoft Windows Server.");
    Module COMP604 = new Module("COMP604", "Routing and Switching Essentials", 6, 15, "COMP503", "none", 2, "Networking", "To enable students to configure, troubleshoot and understand the operation of Routers, Routing Protocols, Switches and VLANs in a networking environment, and complete the Cisco Certified Network Associate 2 (CCNA2) Curriculum.");
    //software
    Module COMP609 = new Module("COMP609", "Applications Development",6,15, "COMP601, MATH601", "none", 2, "Software Engineering", "Students will gain in-depth programming and problem solving skills. They will be able to use a modern development environment and a programming language to implement a working solution. This includes rigorous programming and effective use of built-in data structures and other useful features of the development environment.");
    Module MATH602 = new Module("MATH602", "Mathematics for Programming", 6, 15, "MATH601", "none", 2, "Software Engineering", "To enable students to obtain the mathematical skills to facilitate an in-depth understanding of advanced programming techniques. Students will be able to solve problems in recurrence functions, asymptotic functions, algorithmic puzzles, combinatorics and graph theory and advanced topics in probability and statistics.");
    //database
    Module INFO604 = new Module("INFO604", "Database Systems", 6, 15, "INFO503", "INFO601", 2, "Databases", "To enable students to understand and discuss database systems, concepts, modelling, design and administration, and to apply theoretical and practical administrative tasks in a database administrator's role.");
    //web/database
    Module COMP606 = new Module("COMP606", "Web Programming", 6, 15, "COMP602", "none", 2, "Web Development,Databases", "To enable students to gain the in depth knowledge and skills required to be able to write programs in web programming languages that solve various web programming tasks.");
    //software/database
    Module COMP605 = new Module("COMP605", "Data Structures and Algorithms", 6, 15, "none", "none", 2, "Software Engineering,Databases", "To enable students to apply programming and analytical skills to implement and analyze common data structures for computer programs. Students will cover a wide range of computer programming algorithms.");

    //Year 3 modules(Core)
    Module BIZM701 = new Module("BIZM701", "Business Essentials for IT Professionals",7,15, "INFO602", "none", 3, "Core", "To enable students to develop an understanding of the common principles of business practice whilst focusing on the theoretical and practical concepts of accounting, marketing and management in order to understand the business context within which Information Technology solutions are developed.");
    //Year 3 modules(Web)
    Module INFO709 = new Module("INFO709", "Human Computer Interaction",7,15, "none", "none", 3, "Web Development", "To enable students to understand the supporting theories and principles of user interface design with respect to human computer interaction. They will investigate applications in human computer interaction and apply usability best practices and processes.");
    Module INFO702 = new Module("INFO702", "Cyber-Security",7,15, "COMP504, COMP601", "none", 3, "Web Development,Networking", "To enable students to investigate computer system attacks and vulnerabilities in relation to operating systems (OS), applications, networking and websites, and investigate the security techniques for protecting a computer system from such attacks.");
    Module COMP709 = new Module("COMP709", "Mobile Applications Development",7,15, "none", "none", 3, "Web Development,Software Engineering,Databases", "To enable students to design, develop and implement mobile applications on a given platform.");
    Module COMP710 = new Module("COMP710", "Web Applications Development",7,15, "none", "none", 3, "Web Development", "To enable students to apply practical knowledge of Model View Controller (MVC) frameworks to plan, design and implement web applications. The core focus will be on architecture design and implementation of a web application that will meet a set of functional requirements and user interface requirements, and address business models.");
    Module INFO708 = new Module("INFO708", "Data Visualisation",7,15, "COMP606, COMP607", "none", 3, "Web Development", "To enable students to study and apply visual techniques that transform data into a format efficient for human perception, cognition, and communication, thus allowing for extraction of meaningful information and insight. Students will investigate data visualisation techniques, human visual systems and cognitive perception, and design, construct, and evaluate data visualisations.");
    Module COMP713 = new Module("COMP713 / INFO710 / DFNZ701", "Web Application Project / Internship / Design Factory",7,30, "COMP606, COMP710", "none", 3, "Web Development", "To enable students to further develop their knowledge of Web Applications by analysing, designing and implementing a web solution. This module is the Web Application Capstone Project.");
    //Year 3 Modules(Networking)
    Module COMP701 = new Module("COMP701", "Advanced Networking",7,15, "none", "none", 3, "Networking", "To enable students to investigate and configure advanced system administration tools, advanced network virtualisation and wireless networking technologies. Students will also research emerging networking technologies.");
    Module COMP704 = new Module("COMP704", "Network Security",7,15, "COMP604", "none", 3, "Networking", "To enable students to understand and configure the components, and operation of Virtual Private Networks, firewalls and network security, which will enable them to complete the Cisco Certified Network Associate Security curriculum.");
    Module COMP702 = new Module("COMP702", "Scaling Networks",7,15, "COMP604", "none", 3, "Networking", "To enable students to gain an understanding of the architecture, components, and operations of routers and switches in larger and more complex networks.");
    Module COMP705 = new Module("COMP705", "Connecting Networks",7,15, "COMP702", "none", 3, "Networking", "To enable students to gain an understanding of Wide Area Network (WAN) technologies and network services required by converged applications in a complex network.");
    Module COMP714 = new Module("COMP714 / INFO710 / DFNZ701", "Network Engineering Project / Internship / Design Factory", 7,30,"COMP701, COMP702", "none", 3, "Networking", "To enable students to further develop their knowledge of Web Applications by analysing, designing and implementing a web solution. This module is the Web Application Capstone Project.");
    //Year 3 Modules(Software Engineering)
    Module INFO704 = new Module("INFO704", "Data Warehousing and Business Intelligence",7,15, "INFO601", "none", 3, "Software Engineering,Databases", "To enable students to examine the main components of data warehousing and apply it to business intelligence applications, enabling them to provide solutions which incorporate extracting data from different sources, storing data in a data warehouse and developing applications for business decision-making.");
    Module COMP707 = new Module("COMP707", "Principles of software Testing",7,15, "COMP605", "none", 3, "Software Engineering", "Students will gain comprehensive knowledge of software testing methodologies and software testing tools used in industry and apply fundamental aspects of software testing incorporating system requirements, quality assurance, testing processes, automation, testing types and testing levels. This forms the third part of the Software Engineering Capstone Project.");
    Module COMP706 = new Module("COMP706", "Game Development",7,15, "COMP601, COMP605, MATH602", "none", 3, "Software Engineering", "To enable students to understand supporting theories and principles of game design and apply these to the art and science of game design, development and programming.");
    Module INFO703 = new Module("INFO703", "Big Data and Analytics",7,15, "COMP605, MATH602", "none", 3, "Software Engineering,Databases", "To enable students to gain the practical knowledge and skills required to store, manage and analyse large amounts of data, using appropriate algorithms.");
    Module COMP715 = new Module("COMP715 / INFO710 / DFNZ701", "Software Engineering Project / Internship / Design Factory",7,30, "COMP707", "none", 3, "Software Engineering", "Students will gain advanced software development skills. They will be able to provide an in depth analysis of prototyping, performance, correctness, software reusability, scalability and quality and maintenance and appropriate documentation. This module is the Software Engineering capstone project.");
    //Year 3 Modules(Databases)
    Module INFO706 = new Module("INFO706", "Database Front-End Applications",7,15, "INFO601, INFO604", "COMP709", 3, "Databases", "To enable students to understand and apply various front end applications and their interfaces with supporting databases.");
    Module INFO707 = new Module("INFO707", "Cloud Server Databases",7,15, "INFO601, INFO604", "none", 3, "Databases", "To enable students to gain an in-depth knowledge of cloud server database concepts, fundamentals and essentials. They will apply practical skills to install, setup, configure, manage and maintain a cloud server database and deploy cloud database services to support database applications.");
    Module INFO712 = new Module("INFO712 / INFO710 / DFNZ701", "Database Architecture Project / Internship / Design Factory",7,30, "INFO707, INFO704", "none", 3, "Databases", "To enable students to further develop their knowledge of Database Architecture by analysing, designing and implementing a database solution. This module is the Database Architecture Capstone Project.");


    // --------------------------------------- END OF DEFAULT MODULE DATA ---------------------------------------------------------------------- //


    //Logcat tag
    private static final String TAG = "DbHandler";

    //Database Version
    private static final int DATABASE_VERSION = 2;

    //Database Name
    private static final String DATABASE_NAME = "WintecDpm.db";

    //Students Table & Column Name
    private static final String TABLE_STUDENTS = "Students";
    private static final String COLUMN_STUDENT_ID = "StudentID";
    private static final String COLUMN_STUDENT_NAME = "Name";
    private static final String COLUMN_STUDENT_EMAIL = "Email";
    private static final String COLUMN_STUDENT_ENROLLMENT_DATE = "EnrollmentDate";
    private static final String COLUMN_STUDENT_PATHWAY = "Pathway";
    private static final String COLUMN_STUDENT_PHOTO = "Photo";
    private static final String COLUMN_STUDENT_SELECTIONS = "StudentSelections";

    //Pathway Table & Column Name
    private static final String TABLE_PATHWAYS = "Pathways";
    private static final String COLUMN_PATHWAY_ID = "PathwayID";
    private static final String COLUMN_PATHWAY_NAME = "PathwayName";

    //Module Table & Column Name
    private static final String TABLE_MODULES = "_Modules";
    private static final String COLUMN_MODULE_CODE = "ModuleCode";
    private static final String COLUMN_MODULE_NAME = "ModuleName";
    private static final String COLUMN_MODULE_LEVEL = "ModuleLevel";
    private static final String COLUMN_MODULE_CREDITS = "ModuleCredits";
    private static final String COLUMN_MODULE_PREREQ = "ModulePrereq";
    private static final String COLUMN_MODULE_COREQ = "ModuleCoreq";
    private static final String COLUMN_MODULE_YEAR = "ModuleYear";
    private static final String COLUMN_MODULE_PATHWAY = "ModulePathway";
    private static final String COLUMN_MODULE_DESC = "ModuleDesc";

    //CREATE TABLE statements

    //Students Table create statement
    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_STUDENTS + " ( "
            + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_STUDENT_NAME + " TEXT, "
            + COLUMN_STUDENT_EMAIL + " TEXT, "
            + COLUMN_STUDENT_ENROLLMENT_DATE + " DATETIME, "
            + COLUMN_STUDENT_PATHWAY + " INTEGER, "
            + COLUMN_STUDENT_PHOTO + " BLOB, "
            + COLUMN_STUDENT_SELECTIONS + " TEXT ) ";

    //Pathways Table create statement
    private static final String CREATE_TABLE_PATHWAYS = "CREATE TABLE "
            + TABLE_PATHWAYS + " ( "
            + COLUMN_PATHWAY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_PATHWAY_NAME + " TEXT ) ";

    //Module Table create statement
    private static final String CREATE_TABLE_MODULES = "CREATE TABLE "
            + TABLE_MODULES + " ( "
            + COLUMN_MODULE_CODE + " TEXT PRIMARY KEY, "
            + COLUMN_MODULE_NAME + " TEXT, "
            + COLUMN_MODULE_LEVEL + " INTEGER, "
            + COLUMN_MODULE_CREDITS + " INTEGER, "
            + COLUMN_MODULE_PREREQ + " TEXT, "
            + COLUMN_MODULE_COREQ + " TEXT, "
            + COLUMN_MODULE_YEAR + " INTEGER, "
            + COLUMN_MODULE_PATHWAY + " STRING, "
            + COLUMN_MODULE_DESC + " TEXT ) ";

    //constructor
    DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating the tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_PATHWAYS);
        db.execSQL(CREATE_TABLE_MODULES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onUpgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATHWAYS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULES);

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
    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_ID, student.getStudentID());
        values.put(COLUMN_STUDENT_NAME, student.getName());
        values.put(COLUMN_STUDENT_EMAIL, student.getEmail());
        values.put(COLUMN_STUDENT_ENROLLMENT_DATE, student.getEnrollmentDate());
        values.put(COLUMN_STUDENT_PATHWAY, student.getPathway());
        values.put(COLUMN_STUDENT_PHOTO, student.getPhoto());
        values.put(COLUMN_STUDENT_SELECTIONS, "");

        //insert values into table
        db.insert(TABLE_STUDENTS, null, values);

        //Log check insert
        Log.i(TAG, "Student ID: " + String.valueOf(student.getStudentID())
                + " inserted successfully");

        db.close();
    }

    //READ Student
    public ArrayList<Student> getStudent() {
        ArrayList<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENT_ID + " NOT LIKE 999999999"
                + " ORDER BY " + COLUMN_STUDENT_ID + " DESC";


        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Student s = new Student();
            s.setStudentID(Integer.parseInt(cursor.getString(
                    cursor.getColumnIndex(COLUMN_STUDENT_ID))));
            s.setName(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME)));
            s.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)));
            s.setEnrollmentDate(cursor.getString(
                    cursor.getColumnIndex(COLUMN_STUDENT_ENROLLMENT_DATE)));
            s.setPathway(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PATHWAY)));
            s.setPhoto(cursor.getBlob(cursor.getColumnIndex(COLUMN_STUDENT_PHOTO)));
            studentList.add(s);
        }
        cursor.close();
        db.close();
        return studentList;
    }

    //DELETE Student
    public void deleteStudent(int studentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, COLUMN_STUDENT_ID + "= ?",
                new String[]{String.valueOf(studentID)});
        db.close();
    }

    //UPDATE Student
    public void updateStudent(Student student, int id) {
        Log.i(TAG, "updateStudent with id" + id);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NAME, student.getName());
        values.put(COLUMN_STUDENT_EMAIL, student.getEmail());
        values.put(COLUMN_STUDENT_ENROLLMENT_DATE, student.getEnrollmentDate());
        values.put(COLUMN_STUDENT_PATHWAY, student.getPathway());
        values.put(COLUMN_STUDENT_PHOTO, student.getPhoto());

        //update statement executed
        db.update(TABLE_STUDENTS, values,
                COLUMN_STUDENT_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //Check if database is empty
    boolean checkDatabaseEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_MODULES;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if (icount > 0)
            return false;
        else
            return true;
    }

    //Add a module
    public void addModule(Module module) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MODULE_CODE, module.getModuleCode());
        values.put(COLUMN_MODULE_NAME, module.getModuleName());
        values.put(COLUMN_MODULE_LEVEL, module.getModuleLevel());
        values.put(COLUMN_MODULE_CREDITS, module.getModuleCredits());
        values.put(COLUMN_MODULE_PREREQ, module.getModulePrereq());
        values.put(COLUMN_MODULE_COREQ, module.getModuleCoreq());
        values.put(COLUMN_MODULE_YEAR, module.getModuleYear());
        values.put(COLUMN_MODULE_PATHWAY, module.getModulePathway());
        values.put(COLUMN_MODULE_DESC, module.getModuleDesc());

        //insert values into table
        db.insert(TABLE_MODULES, null, values);

        db.close();
    }

    //UPDATE Module
    public void updateModule(Module module, String mCode) {
        Log.i(TAG, "updateModule with id" + mCode);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MODULE_CODE, module.getModuleCode());
        values.put(COLUMN_MODULE_NAME, module.getModuleName());
        values.put(COLUMN_MODULE_LEVEL, module.getModuleLevel());
        values.put(COLUMN_MODULE_PATHWAY, module.getModulePathway());
        values.put(COLUMN_MODULE_CREDITS, module.getModuleCredits());
        values.put(COLUMN_MODULE_PREREQ, module.getModulePrereq());
        values.put(COLUMN_MODULE_COREQ, module.getModuleCoreq());
        values.put(COLUMN_MODULE_DESC, module.getModuleDesc());

        //update statement executed
        db.update(TABLE_MODULES, values,
                COLUMN_MODULE_CODE + " = ?", new String[]{mCode});
    }

    //DELETE module
    public void deleteModule(String mCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MODULES, COLUMN_MODULE_CODE + "= ?",
                new String[]{mCode});
        db.close();
    }

    //Update selections
    public void updateSelections(int studentID, String module, boolean remove) {
        String[] currentSelections = getSelections(studentID);
        String selections = "";

        //If module is selected, remove it
        if (remove) {
            selections = arrayToString(currentSelections);
            selections = selections.replace(module + ",", "");
            Log.i("TAG", "removed " + module + " from selections");
        } else
            //If module isn't selected, add it
            if (!remove) {
                //Add new module
                selections = arrayToString(currentSelections);
                selections += module + ",";
                Log.i("TAG", "added " + module + " to selections for '" + studentID + "'");
            }
        Log.i("TAG", selections);

        //Update table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_SELECTIONS, "");

        String query = "UPDATE " + TABLE_STUDENTS + " SET " + COLUMN_STUDENT_SELECTIONS + " = '" + selections
                + "' WHERE " + COLUMN_STUDENT_ID + " = '" + studentID + "'";
        db.execSQL(query);
        db.close();
    }

    //Convert array to string
    public String arrayToString(String[] array) {
        String string = "";

        for (int i = 0; i < array.length; i++) {
            string += array[i] + ",";
        }

        return string;
    }

    //Read modules
    public ArrayList<Module> getModules(String pathway, int year) {
        ArrayList<Module> moduleList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        //Select the modules from pathway
        if (year == -1) {
            query = "SELECT * FROM " + TABLE_MODULES
                    + " WHERE " + COLUMN_MODULE_PATHWAY + " LIKE '%" + pathway
                    + "%' OR " + COLUMN_MODULE_PATHWAY + " = 'Core'";
        } else {
            query = "SELECT * FROM " + TABLE_MODULES
                    + " WHERE (" + COLUMN_MODULE_PATHWAY + " LIKE '%" + pathway
                    + "%' OR " + COLUMN_MODULE_PATHWAY + " = 'Core')"
                    + " AND " + COLUMN_MODULE_YEAR + " = " + year;
        }

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {

            //Create module entry using data from database
            Module m = new Module(
                    cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_CODE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_NAME)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_LEVEL))),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_CREDITS))),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_PREREQ)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_COREQ)),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_YEAR))),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_PATHWAY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MODULE_DESC)));
            moduleList.add(m);
        }
        cursor.close();
        db.close();
        return moduleList;
    }

    //Get selections
    public String[] getSelections(int studentID) {
        String[] selectionList;
        String s = "";
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        //Select the modules from pathway
        query = "SELECT * FROM " + TABLE_STUDENTS
                + " WHERE " + COLUMN_STUDENT_ID + " = '" + studentID + "'";

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            //Get selections string
            s = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_SELECTIONS));
        }
        //Convert string to list of modules
        selectionList = s.split(",");
        cursor.close();
        db.close();
        return selectionList;
    }

    //Add the default modules, temporary student
    public void addDefaultModules() {

        //clearModules();
        //Add the modules if database doesn't exist
        if (checkDatabaseEmpty()) {
            //Default student
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_person_black);
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
            byte[] sPhoto = profileImage(bitmap);


            addStudent(new Student(999999999,"Information", "", "","Web Development",sPhoto));
            //Year 1
            addModule(COMP501);
            addModule(COMP502);
            addModule(INFO501);
            addModule(INFO502);
            addModule(COMP503);
            addModule(COMP504);
            addModule(INFO503);
            addModule(INFO504);
            //Year 2
            addModule(COMP601);
            addModule(INFO601);
            addModule(MATH601);
            addModule(COMP602);
            addModule(INFO602);
            addModule(COMP615);
            addModule(COMP605);
            addModule(COMP603);
            addModule(INFO603);
            addModule(COMP609);
            addModule(COMP606);
            addModule(COMP604);
            addModule(MATH602);
            addModule(INFO604);
            addModule(COMP607);
            //Year 3
            addModule(INFO709);
            addModule(INFO702);
            addModule(COMP709);
            addModule(COMP710);
            addModule(BIZM701); //core
            addModule(INFO708);
            addModule(COMP713);
            addModule(COMP701);
            addModule(COMP704);
            addModule(COMP702);
            addModule(COMP705);
            addModule(COMP714);
            addModule(INFO704);
            addModule(COMP707);
            addModule(COMP706);
            addModule(INFO703);
            addModule(COMP715);
            addModule(INFO706);
            addModule(INFO707);
            addModule(INFO712);
        }
    }

    //Clear modules
    public void clearModules() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MODULES);
        db.execSQL(CREATE_TABLE_MODULES);

        db.close();
    }

    //convert bitmap to byte[]
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();

    }
}
