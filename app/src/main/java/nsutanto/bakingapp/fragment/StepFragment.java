package nsutanto.bakingapp.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nsutanto.bakingapp.R;
import nsutanto.bakingapp.adapter.RecipeAdapter;
import nsutanto.bakingapp.adapter.StepAdapter;
import nsutanto.bakingapp.listener.IRecipeFragmentListener;
import nsutanto.bakingapp.listener.IStepFragmentListener;
import nsutanto.bakingapp.listener.IStepListener;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.model.Step;

public class StepFragment extends Fragment implements IStepListener {

    @BindView(R.id.rv_step) RecyclerView rv_step;
    StepAdapter stepAdapter;
    private IStepFragmentListener iStepFragmentListener;
    private List<Step> steps;

    public StepFragment() {
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            iStepFragmentListener = (IStepFragmentListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement listener");
        }
    }

    private void setupRecyclerView(View view) {
        stepAdapter = new StepAdapter(this);
        rv_step.setNestedScrollingEnabled(false);

        // TODO : Set for Horizontal
        //if (view.findViewById(R.id.check_view) != null)
        //    recipeList.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        //else
        rv_step.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        rv_step.setHasFixedSize(true);
        rv_step.setAdapter(stepAdapter);
        stepAdapter.setSteps(steps);
    }

    @Override
    public void OnStepClick(Step step) {

        iStepFragmentListener.OnStepClick(step);
    }

}