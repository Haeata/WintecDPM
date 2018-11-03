package wintec_cfit_managers.wintecdpm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class StudentListAdapter extends ArrayAdapter<Student> {

    private int studentListLayout;

    public class ViewHolder {
        TextView studentName;
        TextView studentPathway;
        TextView studentID;
        TextView studentEmail;
    }

    //constructor
    public StudentListAdapter(Context context,int layoutId, ArrayList<Student> students){
        super(context,layoutId, students);
        this.studentListLayout = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the student data for this position
        Student student = getItem(position);


        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(studentListLayout,parent,false);
            viewHolder.studentName =  convertView.findViewById(R.id.studentName);
            viewHolder.studentPathway =  convertView.findViewById(R.id.studentPathway);
            viewHolder.studentID =  convertView.findViewById(R.id.studentID);
            viewHolder.studentEmail =  convertView.findViewById(R.id.studentEmail);

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
            viewHolder.studentID.setText(student.getStudentID());
            viewHolder.studentEmail.setText(student.getEmail());
        }
        //return to the completed view to render on screen
        return convertView;
    }
}
