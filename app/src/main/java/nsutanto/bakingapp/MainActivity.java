package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nsutanto.bakingapp.fragment.RecipeFragment;
import nsutanto.bakingapp.listener.IRecipeFragmentListener;
import nsutanto.bakingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements IRecipeFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragment();
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        RecipeFragment recipeFragment = new RecipeFragment();

        fm.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }

    @Override
    public void OnRecipeClick(Recipe recipe) {

    }
}
