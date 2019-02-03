package financialmanager.data;

public class Users {
    Integer id;
    String surname;
    String name;
    String birthday;
    String sex;
    String phone;
    String email;

    public Users(Integer id, String surname, String name, String birthday, String sex, String phone, String email) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}