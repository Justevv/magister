package financialmanager.data;

public class Users {
    Integer nId;
    String sSurname;
    String sName;
    String dtBirthday;
    String sSex;
    String sPhone;
    String sEmail;

    public Users(Integer nId, String sSurname, String sName, String dtBirthday, String sSex, String sPhone, String sEmail) {
        this.nId = nId;
        this.sSurname = sSurname;
        this.sName = sName;
        this.dtBirthday = dtBirthday;
        this.sSex = sSex;
        this.sPhone = sPhone;
        this.sEmail = sEmail;
    }

    public Integer getnId() {
        return nId;
    }

    public String getsSurname() {
        return sSurname;
    }

    public String getsName() {
        return sName;
    }

    public String getDtBirthday() {
        return dtBirthday;
    }

    public String getsSex() {
        return sSex;
    }

    public String getsPhone() {
        return sPhone;
    }

    public String getsEmail() {
        return sEmail;
    }
}