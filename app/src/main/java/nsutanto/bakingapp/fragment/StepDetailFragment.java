package nsutanto.bakingapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nsutanto.bakingapp.R;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.tv_short_description)
    TextView tv_short_description;

    String shortDescription = "";
    String description = "";


    public StepDetailFragment() {
    }

    public void setStepDetail(String shortDescription, String description) {

        this.shortDescription = shortDescription;
        this.description = description;
    }

    public void resetStepDetail() {
        tv_description.setText(description);
        tv_short_description.setText(shortDescription);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);

        setupUI();
        return view;
    }

    private void setupUI() {
        if (!description.equals(shortDescription)) {
            tv_description.setText(description);
            tv_short_description.setText(shortDescription);
        }
    }
}
