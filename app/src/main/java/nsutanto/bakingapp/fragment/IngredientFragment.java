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

public class IngredientFragment extends Fragment {

    @BindView(R.id.rv_ingredient) RecyclerView rv_ingredient;
    IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredients;

    public IngredientFragment() {
    }

    public void setIngredients(List<Ingredient> ingredients) {

        this.ingredients = ingredients;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        ingredientAdapter = new IngredientAdapter();
        rv_ingredient.setNestedScrollingEnabled(false);

        // TODO : Set for Horizontal
        //if (view.findViewById(R.id.check_view) != null)
        //    recipeList.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        //else
        rv_ingredient.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        rv_ingredient.setHasFixedSize(true);
        rv_ingredient.setAdapter(ingredientAdapter);
        ingredientAdapter.setIngredients(ingredients);
    }
}