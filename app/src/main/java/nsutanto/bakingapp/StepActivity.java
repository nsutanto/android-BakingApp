package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import nsutanto.bakingapp.fragment.IngredientFragment;
import nsutanto.bakingapp.fragment.MediaFragment;
import nsutanto.bakingapp.fragment.RecipeFragment;
import nsutanto.bakingapp.fragment.StepDetailFragment;
import nsutanto.bakingapp.fragment.StepFragment;
import nsutanto.bakingapp.listener.IStepFragmentListener;
import nsutanto.bakingapp.model.Ingredient;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class StepActivity extends AppCompatActivity implements IStepFragmentListener {

    private Recipe recipe;
    private List<Step> steps;
    private Step lastStepDetail;
    private Step lastStepMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        getBundle();
        setupFragment();
        setupUI();
    }

    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipe = (Recipe) bundle.getSerializable("recipe");
        steps = recipe.getSteps();
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        StepFragment stepFragment = new StepFragment();
        stepFragment.setSteps(steps);

        fm.beginTransaction()
                .add(R.id.step_container, stepFragment)
                .commit();


        if (findViewById(R.id.tablet_step_layout) != null) {

            IngredientFragment ingredientFragment = new IngredientFragment();
            ingredientFragment.setIngredients(recipe.getIngredients());

            fm.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();
        }
    }

    private void setupUI() {
        setTitle(recipe.getName());
    }

    public void onIngredientClicked(View v) {
        if (findViewById(R.id.tablet_step_layout) == null) {
            Intent intent = new Intent(this, IngredientActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", recipe);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            FrameLayout ingredient_layout = findViewById(R.id.ingredient_container);
            ingredient_layout.setVisibility(View.VISIBLE);

            FrameLayout media_layout = findViewById(R.id.media_container);
            media_layout.setVisibility(View.GONE);

            FrameLayout step_detail_layout = findViewById(R.id.step_detail_container);
            step_detail_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnStepClick(Step step) {

        if (findViewById(R.id.tablet_step_layout) == null) {
            Intent intent = new Intent(this, StepDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("step", step);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            FrameLayout ingredient_layout = findViewById(R.id.ingredient_container);
            ingredient_layout.setVisibility(View.GONE);

            FrameLayout media_layout = findViewById(R.id.media_container);
            media_layout.setVisibility(View.VISIBLE);

            FrameLayout step_detail_layout = findViewById(R.id.step_detail_container);
            step_detail_layout.setVisibility(View.VISIBLE);
            FragmentManager fm = getFragmentManager();
            if (!step.getVideoURL().equals("") || !step.getThumbnailURL().equals("")) {

                MediaFragment mediaFragment = new MediaFragment();
                mediaFragment.setMediaURL(step.getVideoURL(), step.getThumbnailURL());

                if (lastStepMedia == null) {
                    lastStepMedia = step;
                    fm.beginTransaction()
                        .add(R.id.media_container, mediaFragment)
                        .commit();
                } else {
                    lastStepMedia = step;
                    fm.beginTransaction()
                            .replace(R.id.media_container, mediaFragment)
                            .commit();
                }
            }
            else {
                //lastStepMedia = null;
            }

            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepDetail(step.getShortDescription(), step.getDescription());

            if (lastStepDetail == null) {
                lastStepDetail = step;
                fm.beginTransaction()
                        .add(R.id.step_detail_container, stepDetailFragment)
                        .commit();
            } else {
                lastStepDetail = step;
                fm.beginTransaction()
                        .replace(R.id.step_detail_container, stepDetailFragment)
                        .commit();
            }

        }
    }
}