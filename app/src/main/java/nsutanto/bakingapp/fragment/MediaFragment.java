package nsutanto.bakingapp.fragment;

import android.app.Fragment;
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
import nsutanto.bakingapp.adapter.IngredientAdapter;
import nsutanto.bakingapp.model.Ingredient;

public class MediaFragment extends Fragment {

    String videoURL = "";
    String thumbnailURL = "";

    public MediaFragment() {
    }

    public void setMediaURL(String videoURL, String thumbnailURL) {

        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
