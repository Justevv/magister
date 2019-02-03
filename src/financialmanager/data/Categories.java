package financialmanager.data;

public class Categories {
    Integer Id;
    String Name;
    Integer ParentId;

    public Categories(Integer Id, String Name, Integer ParentId) {
        this.Id = Id;
        this.Name = Name;
        this.ParentId = ParentId;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Integer getParentId() {
        return ParentId;
    }
}