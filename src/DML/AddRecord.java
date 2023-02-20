package DML;


import java.util.Date;

public class AddRecord {
    private String name;
    private String address;
    private Date birthday;
    private int age;

    private String status;

    public AddRecord(String name, String address, Date birthday, int age, String status) {
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.age = age;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
