package financialmanager.data;

import java.util.ArrayList;

public class Categories {
    Integer id;
    String name;
    Integer parentId;

    public static ArrayList<Categories> categories = new ArrayList<>();
    public Categories() {
    }

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

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}