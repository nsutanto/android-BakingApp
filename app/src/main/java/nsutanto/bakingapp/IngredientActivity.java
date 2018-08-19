package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nsutanto.bakingapp.fragment.IngredientFragment;
import nsutanto.bakingapp.fragment.StepFragment;
import nsutanto.bakingapp.listener.IStepFragmentListener;
import nsutanto.bakingapp.model.Ingredient;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class IngredientActivity extends AppCompatActivity {

    private List<Ingredient> ingredients;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        getBundle();
        setupFragment();
        setupUI();
    }

    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipe = (Recipe) bundle.getSerializable("recipe");
        ingredients = recipe.getIngredients();
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setIngredients(ingredients);

        fm.beginTransaction()
                .add(R.id.ingredient_container, ingredientFragment)
                .commit();
    }

    private void setupUI() {
        setTitle(recipe.getName() + " ingredients");
    }
}