package wintec_cfit_managers.wintecdpm;

public class Student {
    private int StudentID;
    private String Name;
    private String Email;
    private String EnrollmentDate;
    private String Pathway;
    private byte[] Photo;


    //empty constructor
    Student(){
    }

    //constructor for default

    //constructor for insert
    Student(int studentID, String name, String email, String enrollmentDate, String pathway, byte[] Photo) {
        this.StudentID = studentID;
        this.Name = name;
        this.Email = email;
        this.EnrollmentDate = enrollmentDate;
        this.Pathway = pathway;
        this.Photo = Photo;
    }

    //constructor for update
    Student(String name, String email, String enrollmentDate, String pathway, byte[] Photo) {
        this.Name = name;
        this.Email = email;
        this.EnrollmentDate = enrollmentDate;
        this.Pathway = pathway;
        this.Photo = Photo;

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

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

}
