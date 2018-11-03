package wintec_cfit_managers.wintecdpm;

public class Student {
    int StudentID;
    String Name;
    String Email;
    String EnrollmentDate;
    String Pathway;

    public Student(){

    }
    public Student(int studentID, String name, String email, String enrollmentDate, String pathway) {
        this.StudentID = studentID;
        this.Name = name;
        this.Email = email;
        this.EnrollmentDate = enrollmentDate;
        this.Pathway = pathway;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int studentID) {
        StudentID = studentID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEnrollmentDate() {
        return EnrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        EnrollmentDate = enrollmentDate;
    }

    public String getPathway() {
        return Pathway;
    }

    public void setPathway(String pathway) {
        Pathway = pathway;
    }
}
