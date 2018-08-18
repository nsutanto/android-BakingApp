package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nsutanto.bakingapp.fragment.IngredientFragment;
import nsutanto.bakingapp.model.Ingredient;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    //private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        getBundle();
        setupFragment();
    }

    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Recipe recipe = (Recipe) bundle.getSerializable("recipe");
        //steps = recipe.steps();
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        IngredientFragment ingredientFragment = new IngredientFragment();
        //ingredientFragment.setIngredients(ingredients);

        fm.beginTransaction()
                .add(R.id.ingredient_container, ingredientFragment)
                .commit();
    }
}