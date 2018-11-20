package wintec_cfit_managers.wintecdpm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentModule extends AppCompatActivity implements AdapterView.OnItemClickListener,
        NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemLongClickListener {

    //Widgets
    GridView moduleGrid;
    Button pathwayButton;
    Button yearButton;
    Button saveButton;
    moduleGridAdapter adapter;
    DbHandler handler = new DbHandler(this);

    //Variables for menu drawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public static final String PREFS_NAME = "SigninPrefs";

    //Variables
    boolean managerMode = false;
    int tempStudent = 000000000;
    String tempName = "???";
    String tempPathway = "Web Development";
    static String selectedPathway = "Web Development";
    static int selectedYear = 1; //1, 2, 3, 0 = all years

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_module);

        //Get manager/student mode
        managerMode = getIntent().getBooleanExtra("manager",false);
        if (!managerMode) {
            tempStudent = getIntent().getIntExtra("studentID",00000);
            tempName = getIntent().getStringExtra("studentName");
            tempPathway = getIntent().getStringExtra("studentPathway");
            selectedPathway = tempPathway;
            this.setTitle("Modules - " + tempName);
        }
        else {
            tempStudent = 000000000;
            selectedPathway = "Web Development";
        }
        selectedYear = 1;

        //Add all default modules if database doesn't exist
        handler.addDefaultModules();

        //navigation drawer if its manager side
        if(managerMode || (!managerMode && tempStudent != 999999999)){
                //Menu Drawer
                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_stmod);
                NavigationView navigationView = findViewById(R.id.navList);
                navigationView.setNavigationItemSelectedListener(this);
                mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
                mDrawerLayout.addDrawerListener(mDrawerToggle);
                mDrawerToggle.syncState();
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Hides menu toggle so back button should be visible
            }
            else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Buttons
        pathwayButton = (Button) findViewById(R.id.pathwayButton);
        yearButton = (Button) findViewById(R.id.yearButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        //Grid stuff
        moduleGrid = (GridView) findViewById(R.id.moduleGrid);
        adapter = new moduleGridAdapter(this,handler.getModules(selectedPathway,-1));
        moduleGrid.setAdapter(adapter);
        moduleGrid.setOnItemClickListener(this);
        moduleGrid.setOnItemLongClickListener(this);

        //Set the pathway and year
        changePathway(selectedPathway);
        changeYear(selectedYear);

        //Save/Add button text
        if (!managerMode)
            saveButton.setText("Save Selections");
        else
            saveButton.setText("Add Module");

        //Save/Add button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!managerMode){
                    if(tempStudent == 999999999)
                    {
                      startActivity(new Intent(StudentModule.this, MainMenu.class));
                    } else{
                        startActivity(new Intent(StudentModule.this, ManageStudent.class));
                    }
                    finish();
                }
                else{
                    Intent intent = new Intent(StudentModule.this,AddModule.class);
                    intent.putExtra("selectedPathway",selectedPathway);
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
        super.onBackPressed();
    }

    //Description dialog open on long click
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        ViewHolder holder = (ViewHolder) view.getTag();

        final Dialog module_desc = new Dialog(StudentModule.this);

        module_desc.requestWindowFeature(Window.FEATURE_NO_TITLE);
        module_desc.setContentView(R.layout.module_desc);
        module_desc.setTitle("Module Description");

        final TextView moduleCode = module_desc.findViewById(R.id.moduleCodeTv);
        final TextView moduleName = module_desc.findViewById(R.id.moduleNAmeTv);
        final TextView pathway = module_desc.findViewById(R.id.pathwayTv);
        final TextView level = module_desc.findViewById(R.id.levelTv);
        final TextView year = module_desc.findViewById(R.id.yearTv);
        final TextView credits = module_desc.findViewById(R.id.creditsTv);
        final TextView prerequisites = module_desc.findViewById(R.id.prereqTv);
        final TextView corequisites = module_desc.findViewById(R.id.coreqTv);
        final TextView description = module_desc.findViewById(R.id.descTv);
        Log.i("Dialog","Dialog");

        moduleCode.setText(holder.moduleCode.getText().toString());
        moduleName.setText(holder.moduleName.getText().toString());
        pathway.setText(holder.modulePath);
        level.setText(holder.moduleLevel.getText().toString().replace("Level: ",""));
        year.setText(holder.moduleYear);
        credits.setText(Integer.toString(holder.moduleCredits));
        prerequisites.setText(holder.moduleReq.getText().toString().replace("Req: ",""));
        corequisites.setText(holder.moduleCoReq);
        description.setText(holder.moduleDesc);

        module_desc.show();
        return (false);
    }

    //Handles Item clicks in navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(managerMode || (!managerMode && tempStudent != 999999999)){
            switch (item.getItemId()){
                case R.id.home:
                    Intent MainMenu=new Intent(getApplicationContext(), wintec_cfit_managers.wintecdpm.MainMenu.class);
                    startActivity(MainMenu);
                    finish();
                    break;
                case R.id.students_cap:
                    Intent ManageStudent=new Intent(getApplicationContext(),ManageStudent.class);
                    startActivity(ManageStudent);
                    finish();
                    break;
                case R.id.manage_module:
                    Intent ManageModule=new Intent(getApplicationContext(),StudentModule.class);
                    ManageModule.putExtra("manager",true);
                    startActivity(ManageModule);
                    finish();
                    break;
                case R.id.about:
                    Intent About=new Intent(getApplicationContext(),AboutUs.class);
                    startActivity(About);
                    finish();
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
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(managerMode || (!managerMode && tempStudent != 999999999)){
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

                    if(this.getClass() != StudentModule.class){
                        Intent intent = new Intent(this,StudentModule.class);
                        intent.putExtra("manager",true);
                        this.startActivity(intent);
                        return true;
                    }
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
            } else {
                NavUtils.navigateUpFromSameTask(this);
                finish();
            }
        return super.onOptionsItemSelected(item);
    }

    //When module is touched
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final DbHandler handler = new DbHandler(this);

        //Update module selections
        if (!managerMode) {
            if (holder.prereqsMet) {
                if (holder.moduleSelected) {
                    handler.updateSelections(tempStudent, holder.moduleCode.getText().toString(),true);
                    Toast.makeText(this,holder.moduleCode.getText().toString() + " removed!",Toast.LENGTH_SHORT).show();
                }
                else {
                    handler.updateSelections(tempStudent, holder.moduleCode.getText().toString(), false);
                    Toast.makeText(this,holder.moduleCode.getText().toString() + " selected!",Toast.LENGTH_SHORT).show();
                }
                refreshGrid();
            }
            else
                Toast.makeText(this,"You do not meet the prerequisites.",Toast.LENGTH_SHORT).show();
        }
        //Manager mode, edit module
        else {
        }
    }

    //Pathway is changed, // 1 - web, 2 - network, 3 - software, 4 - database
    public void changePathway(String p) {

        //Change the pathway
        selectedPathway = p;

        //Web dev
        if (p.equals("Web Development")) {
            pathwayButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorWebDevelopment));
            yearButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorWebDevelopment));
        }
        //Networking
        else if (p.equals("Networking")) {
            pathwayButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorNetworking));
            yearButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorNetworking));
            pathwayButton.setText(p);
        }
        //Software
        else if (p.equals("Software Engineering")) {
            pathwayButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorSoftwareEngineering));
            yearButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorSoftwareEngineering));
        }
        //Databases
        else if (p.equals("Databases")) {
            pathwayButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorDatabases));
            yearButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorDatabases));
        }
        //Error
        else {
            pathwayButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            yearButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
            pathwayButton.setText("???");
        }
        pathwayButton.setText(p);

        //Go to year
        changeYear(selectedYear);

        //Delay before refreshing
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Reset the adapter
                refreshGrid();
            }}, 1);

    }

    //Year is changed, (0, 1,2,3) = (All years, Year 1, Year 2, Year 3)
    public void changeYear(int y) {

        //Change the year
        selectedYear = y;

        //Year 1
        if (selectedYear == 1) {
            yearButton.setText("Year 1");
            moduleGrid.setSelection(0);
        }
        //Year 2
        else if (selectedYear == 2) {
            yearButton.setText("Year 2");
            moduleGrid.setSelection(handler.getModules(selectedPathway,1).size());
        }
        //Year 3
        else if (selectedYear == 3) {
            yearButton.setText("Year 3");
            moduleGrid.setSelection(handler.getModules(selectedPathway,1).size()+handler.getModules(selectedPathway,2).size());
        }
        //Year 3
        else {
            yearButton.setText("All Years");
        }

        //Delay before refreshing
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Reset the adapter
                refreshGrid();
            }}, 1);

    }

    //Pathway dialog
    public void pathwayDialog(View view) {
        final Dialog pDialog = new Dialog(StudentModule.this);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pDialog.setContentView(R.layout.pathway_dialog);
        pDialog.show();

        LinearLayout webDevLayout = pDialog.findViewById(R.id.webDevLayout);
        LinearLayout networkingLayout = pDialog.findViewById(R.id.networkingLayout);
        LinearLayout softwareLayout = pDialog.findViewById(R.id.softwareLayout);
        LinearLayout databaseLayout = pDialog.findViewById(R.id.databaseLayout);

        //When web development is clicked
        webDevLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePathway("Web Development");
                pDialog.cancel();
            }
        });
        //When networking is clicked
        networkingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePathway("Networking");
                pDialog.cancel();
            }
        });
        //When software engineering is clicked
        softwareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePathway("Software Engineering");
                pDialog.cancel();
            }
        });
        //When databases is clicked
        databaseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePathway("Databases");
                pDialog.cancel();
            }
        });
    }

    //Year dialog
    public void yearDialog(View view) {
        final Dialog yDialog = new Dialog(StudentModule.this);
        yDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        yDialog.setContentView(R.layout.year_dialog);
        yDialog.show();

        LinearLayout allYearsLayout = yDialog.findViewById(R.id.allYearsLayout);
        LinearLayout year1Layout = yDialog.findViewById(R.id.year1Layout);
        LinearLayout year2Layout = yDialog.findViewById(R.id.year2Layout);
        LinearLayout year3Layout = yDialog.findViewById(R.id.year3Layout);

        //When year 1 is clicked
        year1Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeYear(1);
                yDialog.cancel();
            }
        });
        //When year 2 is clicked
        year2Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeYear(2);
                yDialog.cancel();
            }
        });
        //When year 3 is clicked
        year3Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeYear(3);
                yDialog.cancel();
            }
        });
        //When all years is clicked
        allYearsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeYear(0);
                yDialog.cancel();
            }
        });
    }

    //View holder
    class ViewHolder{
        //Vars
        boolean moduleSelected = false;
        boolean prereqsMet = false;
        int moduleCredits;
        String moduleCoReq;
        String moduleDesc;
        String modulePath;
        String moduleYear;

        String[] s; //list of student selections
        String[] p; //Prereq list
        String[] pw; //Pathway list

        //Widgets
        RelativeLayout cardHighlightBG;
        TextView moduleCode;
        TextView moduleName;
        TextView moduleLevel;
        TextView moduleReq;
        CardView moduleCard;
        CardView lockedBG;
        ImageView checkBox;
    }

    //Module grid adapter
    class moduleGridAdapter extends BaseAdapter {

        ArrayList<Module> moduleList;
        LayoutInflater inflater;
        Context context;

        moduleGridAdapter(Context context, ArrayList<Module> modules) {
            this.context = context;
            moduleList = modules;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return moduleList.size();
        }

        @Override
        public Object getItem(int i) {
            return moduleList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        //The module display
        @Override
        public View getView(int i, View v, ViewGroup parent) {

            final ViewHolder holder;
            final Module module = moduleList.get(i);

            if (v==null) {

                v = inflater.inflate(R.layout.module_row,null);
                holder = new ViewHolder();

                //Widgets
                holder.cardHighlightBG = (RelativeLayout) v.findViewById(R.id.cardHighlightBG);
                holder.moduleCode = (TextView) v.findViewById(R.id.moduleCodeText);
                holder.moduleName = (TextView) v.findViewById(R.id.moduleNameText);
                holder.moduleLevel = (TextView) v.findViewById(R.id.moduleLevelText);
                holder.moduleReq = (TextView) v.findViewById(R.id.moduleReqText);
                holder.moduleCard = (CardView) v.findViewById(R.id.moduleCardHeader);
                holder.lockedBG = (CardView) v.findViewById(R.id.lockedBG);
                holder.checkBox = (ImageView) v.findViewById(R.id.checkBox);

                v.setTag(holder);
            }
            else {
                holder = (ViewHolder) v.getTag();
            }

            //Vars
            Module temp = moduleList.get(i);
            holder.moduleCode.setText(temp.ModuleCode);
            holder.moduleName.setText(temp.Name);
            holder.moduleLevel.setText("Level: " + temp.Level);
            holder.moduleReq.setText("Req: " + temp.Prereq);
            holder.moduleCredits = temp.Credits;
            holder.moduleCoReq = temp.Coreq;
            holder.moduleDesc = temp.Desc;
            holder.modulePath = temp.Pathway;
            holder.moduleYear = Integer.toString(temp.Year);
            holder.s = handler.getSelections(tempStudent); //List of selections
            holder.p = temp.Prereq.split(", "); //list of prereqs
            holder.pw = temp.Pathway.split(","); //List of pathways

            holder.checkBox.setTag(module);
            if(managerMode) holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final DbHandler handler = new DbHandler(getApplication());

                    //inflate popup menu when button clicked
                    PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                    MenuInflater menuInflater = popup.getMenuInflater();
                    menuInflater.inflate(R.menu.card_menu, popup.getMenu());
                    popup.show();

                    //action when Item in Popup Menu clicked
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            final Module module = (Module) v.getTag();
                            switch (item.getItemId()) {
                                case R.id.card_edit:
                                    Intent i = new Intent(StudentModule.this, EditModule.class);
                                    i.putExtra("moduleCode", module.ModuleCode);
                                    i.putExtra("moduleName", module.Name);
                                    i.putExtra("modulePath", module.Pathway);
                                    i.putExtra("moduleLevel", module.Level);
                                    i.putExtra("moduleCredits", module.Credits);
                                    i.putExtra("moduleYear", module.Year);
                                    i.putExtra("modulePrereq", module.Prereq);
                                    i.putExtra("moduleCoreq",module.Coreq);
                                    i.putExtra("moduleDesc",module.Desc);
                                    i.putExtra("selectedPathway",selectedPathway);

                                    startActivity(i);
                                    //Toast.makeText(getContext(),"This student name is " + student.getName(),Toast.LENGTH_LONG).show();
                                    return true;

                                case R.id.card_delete:
                                    final Dialog delete = new Dialog(context);

                                    //delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    delete.setContentView(R.layout.delete_dialog);
                                    delete.setTitle("Module Description");

                                    final Button deleteBtn = delete.findViewById(R.id.delete);
                                    final Button cancelBtn = delete.findViewById(R.id.cancel);

                                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //delete data in student table
                                            handler.deleteModule(holder.moduleCode.getText().toString());
                                            handler.close();

                                            //listview update
                                            refreshGrid();
                                            Toast.makeText(context,"Deleted the module "
                                                            + module.ModuleCode + " successfully.",
                                                    Toast.LENGTH_LONG).show();
                                            delete.cancel();
                                        }
                                    });

                                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            delete.cancel();

                                        }
                                    });
                                    delete.show();
                                    return true;
                                default:
                            }
                            return false;
                        }
                    });
                }
            });

            //Check if prereqs are met
            int prereqsMet = 0;
            if (!temp.Prereq.equals("none")) {
                for (int j = 0; j < holder.s.length; j++) {

                    //Check selected module with prereq
                    for (int k = 0; k < holder.p.length; k++) {
                        //Log.i("TAG","checking " + holder.s[j] + " with " + holder.p[k]);
                        if (holder.s[j].equals(holder.p[k])) {
                            prereqsMet++;
                            //Log.i("TAG","match (" + prereqsMet + " / " + holder.p.length + ")");
                        }
                    }

                    //If are met then exit
                    if (prereqsMet == holder.p.length) {
                        holder.prereqsMet = true;
                        //Log.i("TAG","PREREQS MET");
                        break;
                    } else {
                        holder.prereqsMet = false;
                    }

                }
            } else {
                holder.prereqsMet = true;
            }

            //Check if module is selected
            for (int j = 0; j < holder.s.length; j++) {
                if (holder.s[j].equals(temp.ModuleCode)) {
                    holder.moduleSelected = true;
                    break;
                } else
                    holder.moduleSelected = false;
            }

            //Module is selected but prereqs not met
            if (holder.moduleSelected && !holder.prereqsMet) {
                Log.i("TAG",temp.ModuleCode + " selected but prereqs not met, removing");
                handler.updateSelections(tempStudent, temp.ModuleCode, true);
                holder.moduleSelected = false;
            }

            //Display pencil/checkbox
            if (managerMode) {
                holder.checkBox.setImageResource(R.drawable.ic_module_menu);
            }
            else {
                //Change the checkbox
                if (holder.moduleSelected)
                    holder.checkBox.setImageResource(R.drawable.ic_check_box_black_24dp);
                else
                    holder.checkBox.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
            }


            //If core module
            if (temp.Pathway.equals("Core")) {
                holder.moduleCard.setCardBackgroundColor(getResources().getColor(R.color.colorCoreModule));
            }
            //Check if module pathway matches selected pathway
            else {
                for (int j = 0; j < holder.pw.length; j++) {
                    if (holder.pw[j].equals(selectedPathway)) {
                        //Set the card colour based on pathway (1 - web, 2 - network, 3 - software, 4 - database)
                        if (holder.pw[j].equals("Web Development"))
                            holder.moduleCard.setCardBackgroundColor(getResources().getColor(R.color.colorWebDevelopment));
                        else if (holder.pw[j].equals("Networking"))
                            holder.moduleCard.setCardBackgroundColor(getResources().getColor(R.color.colorNetworking));
                        else if (holder.pw[j].equals("Software Engineering"))
                            holder.moduleCard.setCardBackgroundColor(getResources().getColor(R.color.colorSoftwareEngineering));
                        else if (holder.pw[j].equals("Databases"))
                            holder.moduleCard.setCardBackgroundColor(getResources().getColor(R.color.colorDatabases));
                        else
                            holder.moduleCard.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
                        break;
                    }
                }
            }

            //Fade out other modules if year is not selected
            if (temp.Year == selectedYear || selectedYear == 0)
                holder.cardHighlightBG.setAlpha(1f);
            else
                holder.cardHighlightBG.setAlpha(0.35f);

            //Display the lock
            if (holder.prereqsMet || managerMode)
                holder.lockedBG.setVisibility(View.INVISIBLE);
            else
                holder.lockedBG.setVisibility(View.VISIBLE);

            return v;
        }
    }

    //Refresh the gridview
    public void refreshGrid() {
        Parcelable state = moduleGrid.onSaveInstanceState();
        adapter = new moduleGridAdapter(this, handler.getModules(selectedPathway,-1));
        moduleGrid.setAdapter(adapter);
        moduleGrid.onRestoreInstanceState(state);
    }
}
