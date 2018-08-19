package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nsutanto.bakingapp.fragment.IngredientFragment;
import nsutanto.bakingapp.fragment.MediaFragment;
import nsutanto.bakingapp.fragment.StepDetailFragment;
import nsutanto.bakingapp.model.Ingredient;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class StepDetailActivity extends AppCompatActivity {

    private Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        getBundle();
        setupFragment();
        setupUI();
    }

    private void getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        step = (Step) bundle.getSerializable("step");
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setStepDetail(step.getShortDescription(), step.getDescription());

        fm.beginTransaction()
                .add(R.id.step_detail_container, stepDetailFragment)
                .commit();

        if (!step.getVideoURL().equals("") || !step.getThumbnailURL().equals("")) {
            MediaFragment mediaFragment = new MediaFragment();
            mediaFragment.setMediaURL(step.getVideoURL(), step.getThumbnailURL());

            fm.beginTransaction()
                    .add(R.id.media_container, mediaFragment)
                    .commit();
        }
    }

    private void setupUI() {
        setTitle(step.getShortDescription());
    }
}