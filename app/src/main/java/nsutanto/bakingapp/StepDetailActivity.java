package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.List;

import nsutanto.bakingapp.fragment.MediaFragment;
import nsutanto.bakingapp.fragment.NavigationFragment;
import nsutanto.bakingapp.fragment.StepDetailFragment;
import nsutanto.bakingapp.listener.INavigationFragmentListener;
import nsutanto.bakingapp.model.Step;

public class StepDetailActivity extends AppCompatActivity implements INavigationFragmentListener{

    private Step step;
    private List<Step> steps;
    private int currentStepID;
    private Boolean isMediaAdded = false;
    private FragmentManager fm = getFragmentManager();
    private StepDetailFragment stepDetailFragment = new StepDetailFragment();
    private MediaFragment mediaFragment = new MediaFragment();
    private NavigationFragment navigationFragment = new NavigationFragment();

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
        currentStepID = step.getId();
        steps = (List<Step>) bundle.getSerializable("steps");
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        stepDetailFragment.setStepDetail(step.getShortDescription(), step.getDescription());
        fm.beginTransaction()
                .add(R.id.step_detail_container, stepDetailFragment)
                .commit();

        if (!step.getVideoURL().equals("") || !step.getThumbnailURL().equals("")) {
            mediaFragment.setMediaURL(step.getVideoURL(), step.getThumbnailURL());

            fm.beginTransaction()
                    .add(R.id.media_container, mediaFragment)
                    .commit();

            isMediaAdded = true;
        }

        navigationFragment.setStep(currentStepID, steps.size());
        fm.beginTransaction()
                .add(R.id.navigation_container, navigationFragment)
                .commit();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fm.beginTransaction()
                    .remove(stepDetailFragment)
                    .commit();
            fm.beginTransaction()
                    .remove(navigationFragment)
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            fm.beginTransaction()
                    .add(R.id.step_detail_container, stepDetailFragment)
                    .commit();
            fm.beginTransaction()
                    .add(R.id.navigation_container, navigationFragment)
                    .commit();
        }
    }

    private void setupUI() {
        setTitle(step.getShortDescription());
    }

    public void OnPreviousBtnClick() {
        currentStepID--;

        updateFragments(currentStepID);
    }
    public void OnNextBtnClick() {
        currentStepID++;
        updateFragments(currentStepID);
    }

    private void updateFragments(int currentStepID) {
        step = steps.get(currentStepID);

        FragmentTransaction transaction = fm.beginTransaction();

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
        stepDetailFragment.resetStepDetail();

        navigationFragment.resetCurrentStepID(currentStepID);

        transaction.commit();
    }
}