package wintec_cfit_managers.wintecdpm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Locale;


public class StudentListAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = "studentList";
    //initialize the stduent arrayList to save all the student for listView
    private ArrayList<Student> studentList;

    //filtered listView saved on this ArrayList
    private ArrayList<Student> filteredStudentList;

    private Filter listFilter;

    public class ViewHolder {
        CardView studentCard;
        TextView studentName;
        TextView studentPathway;
        TextView studentID;
        TextView studentEmail;
        TextView enrollmentDate;
        ImageView studentPhoto;
        ImageButton btnStudentMenu;
    }

    //constructor
    StudentListAdapter(ArrayList<Student> students){
        this.studentList = students;
        this.filteredStudentList = studentList;
    }

    @Override
    public int getCount() {
        return filteredStudentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return (filteredStudentList.get(position));
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the student data for this position
        final Context context = parent.getContext();

        //gain data from the position of students List
        final Student student = filteredStudentList.get(position);

        //viewholder to fill the view
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_row_student,parent,false);

            //reference from the widgets
            viewHolder.studentCard = convertView.findViewById(R.id.studentCard);
            viewHolder.studentPhoto = convertView.findViewById(R.id.studentPhoto);
            viewHolder.studentName =  convertView.findViewById(R.id.studentName);
            viewHolder.studentPathway =  convertView.findViewById(R.id.studentPathway);
            viewHolder.studentID =  convertView.findViewById(R.id.studentID);
            viewHolder.studentEmail =  convertView.findViewById(R.id.studentEmail);
            viewHolder.enrollmentDate = convertView.findViewById(R.id.enrollmentDate);
            viewHolder.btnStudentMenu = convertView.findViewById(R.id.btnStudentMenu);

            //view lookup cache stored in tag
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //populate data into the template view using the data object
        if (student != null) {
            viewHolder.studentName.setText(student.getName());
            viewHolder.studentPathway.setText(student.getPathway());
            viewHolder.studentID.setText(String.valueOf(student.getStudentID()));
            viewHolder.studentEmail.setText(student.getEmail());
            viewHolder.studentPhoto.setImageBitmap(convertToBitmap(student.getPhoto()));
        }
        viewHolder.btnStudentMenu.setTag(student);
        //Click the student to edit their selections
        viewHolder.studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i=new Intent(context.getApplicationContext(),StudentModule.class);
                i.putExtra("manager", false);
                i.putExtra("studentID",student.getStudentID());
                i.putExtra("studentName",student.getName());
                i.putExtra("studentPathway",student.getPathway());
                context.startActivity(i);

            }
        });

        viewHolder.btnStudentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.i(TAG,"popup Menu clicked");
                //inflate popup menu when button clicked
                PopupMenu popup = new PopupMenu(context,v);
                MenuInflater menuInflater = popup.getMenuInflater();
                menuInflater.inflate(R.menu.card_menu,popup.getMenu());
                popup.show();

                //action when Item in Popup Menu clicked
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        final Student student = (Student) v.getTag();
                        switch(item.getItemId()){
                            case R.id.card_edit:
                                Log.i(TAG,"Edit menu clicked successfully");

                                Intent i = new Intent(context, EditStudent.class);
                                i.putExtra("studentID",student.getStudentID());
                                i.putExtra("studentName",student.getName());
                                i.putExtra("studentEnrollmentDate",student.getEnrollmentDate());
                                i.putExtra("studentEmail",student.getEmail());
                                i.putExtra("studentPathway",student.getPathway());
                                i.putExtra("studentPhoto",student.getPhoto());

                                context.startActivity(i);

                                //Toast.makeText(getContext(),"This student name is " + student.getName(),Toast.LENGTH_LONG).show();
                                return true;

                            case R.id.card_delete:
                                Log.i(TAG,"Delete menu clicked successfully");
                                final Dialog delete = new Dialog(context);

                                delete.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                delete.setContentView(R.layout.delete_dialog);
                                delete.setTitle("Module Description");

                                final Button deleteBtn = delete.findViewById(R.id.delete);
                                final Button cancelBtn = delete.findViewById(R.id.cancel);

                                deleteBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //delete data in student table
                                        DbHandler dbHandler = new DbHandler(context);
                                        dbHandler.deleteStudent(student.getStudentID());
                                        dbHandler.close();

                                        //listview update
                                        filteredStudentList.remove(student);
                                        notifyDataSetChanged();
                                        Toast.makeText(context,"Deleted the student "
                                                        + student.getName() + " record successfully.",
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
        //return to the completed view to render on screen
        return convertView;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0){
                results.values = studentList;
                results.count = studentList.size();
            } else {
                ArrayList<Student> sList = new ArrayList<Student>();

                for(Student student: studentList) {
                    if(student.getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || (String.valueOf(student.getStudentID())).toLowerCase()
                            .contains(constraint.toString().toLowerCase())){
                        sList.add(student);

                    }
                }

                results.values = sList;
                results.count = sList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //update listview by filtered student list.
            filteredStudentList = (ArrayList<Student>) results.values;

            //notify
            if (results.count > 0){
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}