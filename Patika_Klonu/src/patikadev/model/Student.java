package patikadev.model;

public class Student extends User {

    public Student() {
    }

    public Student(int id, String name, String uname, String password, String type) {
        super(id, name, uname, password, type);
    }
}
