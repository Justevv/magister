package financialmanager.businesslogic;

import java.util.ArrayList;

public class Categories {
    public static ArrayList<financialmanager.data.Categories> categories = new ArrayList<>();
    ;

    public void removeList() {
        categories.removeAll(categories);
    }
}
