package financialmanager.data;

public class Categories {
    Integer id;
    String name;
    Integer parentId;

    public Categories(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getParentId() {
        return parentId;
    }
}