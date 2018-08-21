package nsutanto.bakingapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import nsutanto.bakingapp.R;
import nsutanto.bakingapp.listener.INavigationFragmentListener;

public class NavigationFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.btn_next)
    Button btn_next;

    @BindView(R.id.btn_previous)
    Button btn_previous;

    private int currentStepID;
    private int maxStepID;
    private INavigationFragmentListener iNavigationFragmentListener;

    public NavigationFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            iNavigationFragmentListener = (INavigationFragmentListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement listener");
        }
    }

    public void setStep(int currentStepID, int maxStepID) {

        this.currentStepID = currentStepID;
        this.maxStepID = maxStepID;
    }

    public void resetCurrentStepID(int currentStepID) {
        this.currentStepID = currentStepID;
        setupUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        ButterKnife.bind(this, view);

        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);

        setupUI();
        return view;
    }

    private void setupUI() {
        if (currentStepID == 0) {
            btn_previous.setEnabled(false);
            btn_next.setEnabled(true);
        } else if (currentStepID == maxStepID - 1) {
            btn_previous.setEnabled(true);
            btn_next.setEnabled(false);
        } else {
            btn_previous.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                iNavigationFragmentListener.OnNextBtnClick();
                break;
            case R.id.btn_previous:
                iNavigationFragmentListener.OnPreviousBtnClick();
                break;
            default:
                break;
        }
    }
}
