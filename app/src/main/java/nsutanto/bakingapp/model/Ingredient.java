package nsutanto.bakingapp.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private int quantity;
    private String measure;
    private String ingredient;

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
