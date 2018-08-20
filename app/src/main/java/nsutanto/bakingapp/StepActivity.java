package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

import nsutanto.bakingapp.fragment.IngredientFragment;
import nsutanto.bakingapp.fragment.MediaFragment;
import nsutanto.bakingapp.fragment.StepDetailFragment;
import nsutanto.bakingapp.fragment.StepFragment;
import nsutanto.bakingapp.listener.IStepFragmentListener;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class StepActivity extends AppCompatActivity implements IStepFragmentListener {

    private Recipe recipe;
    private List<Step> steps;

    private Boolean isMediaAdded = false;
    private Boolean isStepDetailAdded = false;
    private FragmentManager fm = getFragmentManager();
    private StepFragment stepFragment = new StepFragment();
    private IngredientFragment ingredientFragment = new IngredientFragment();
    private MediaFragment mediaFragment = new MediaFragment();
    private StepDetailFragment stepDetailFragment = new StepDetailFragment();


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

        stepFragment.setSteps(steps);
        fm.beginTransaction()
                .add(R.id.step_container, stepFragment)
                .commit();


        if (findViewById(R.id.tablet_step_layout) != null) {

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

            FragmentTransaction transaction = fm.beginTransaction();

            transaction.add(R.id.ingredient_container, ingredientFragment);
            transaction.remove(mediaFragment);
            transaction.remove(stepDetailFragment);
            transaction.commit();
            isMediaAdded = false;
            isStepDetailAdded = false;
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

            FragmentTransaction transaction = fm.beginTransaction();
            transaction.remove(ingredientFragment);

            if (!step.getVideoURL().equals("") || !step.getThumbnailURL().equals("")) {

                mediaFragment.setMediaURL(step.getVideoURL(), step.getThumbnailURL());

                if (!isMediaAdded) {
                    transaction.add(R.id.media_container, mediaFragment);
                    isMediaAdded = true;
                } else {
                    mediaFragment.resetMedia();
                }
            } else {
                transaction.remove(mediaFragment);
                isMediaAdded = false;
            }

            stepDetailFragment.setStepDetail(step.getShortDescription(), step.getDescription());
            if (!isStepDetailAdded) {
                transaction.add(R.id.step_detail_container, stepDetailFragment);
                isStepDetailAdded = true;
            } else {
                stepDetailFragment.resetStepDetail();
            }

            transaction.commit();
        }
    }
}