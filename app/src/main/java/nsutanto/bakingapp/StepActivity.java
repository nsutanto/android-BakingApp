package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nsutanto.bakingapp.fragment.RecipeFragment;
import nsutanto.bakingapp.fragment.StepFragment;
import nsutanto.bakingapp.listener.IStepFragmentListener;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class StepActivity extends AppCompatActivity implements IStepFragmentListener {

    private List<Step> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        getBundle();
        setupFragment();
    }

    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Recipe recipe = (Recipe) bundle.getSerializable("recipe");
        steps = recipe.getSteps();
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        StepFragment stepFragment = new StepFragment();
        stepFragment.setSteps(steps);

        fm.beginTransaction()
                .add(R.id.step_container, stepFragment)
                .commit();
    }

    @Override
    public void OnStepClick(Step step) {

    }
}