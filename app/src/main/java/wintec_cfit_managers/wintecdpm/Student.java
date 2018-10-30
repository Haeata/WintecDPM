package wintec_cfit_managers.wintecdpm;

public class Student {
    int StudentID;
    String Name;
    String Email;
    String EnrollmentDate;

    public Student(){

    }
    public Student(int studentID, String name, String email, String enrollmentDate) {
        this.StudentID = studentID;
        this.Name = name;
        this.Email = email;
        this.EnrollmentDate = enrollmentDate;
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
}
