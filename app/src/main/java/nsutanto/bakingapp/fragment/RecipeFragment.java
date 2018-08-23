package nsutanto.bakingapp.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nsutanto.bakingapp.R;
import nsutanto.bakingapp.RecipeIdlingResource;
import nsutanto.bakingapp.adapter.RecipeAdapter;
import nsutanto.bakingapp.listener.IRecipeFragmentListener;
import nsutanto.bakingapp.listener.IRecipeListener;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.utils.NetworkUtil;

// TODO: Implement Async Task Loader
public class RecipeFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, IRecipeListener {

    @BindView(R.id.rv_recipe) RecyclerView rv_recipe;
    RecipeAdapter recipeAdapter;
    private IRecipeFragmentListener iRecipeFragmentListener;
    private List<Recipe> recipes;
    private static final String RECIPE_URL_EXTRA = "recipe";
    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int RECIPE_LOADER_ID = 1;

    @Nullable
    private RecipeIdlingResource recipeIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (recipeIdlingResource == null) {
            recipeIdlingResource = new RecipeIdlingResource();
        }
        return recipeIdlingResource;
    }


    public RecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);

        if (recipeIdlingResource != null)
            recipeIdlingResource.setIdleState(false);

        setupRecyclerView(view);
        setupLoader(savedInstanceState);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            iRecipeFragmentListener = (IRecipeFragmentListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement listener");
        }
    }

    private void setupRecyclerView(View view) {
        recipeAdapter = new RecipeAdapter(this);
        rv_recipe.setNestedScrollingEnabled(false);

        if (view.findViewById(R.id.fl_recipe_tablet) != null) {
            rv_recipe.setLayoutManager(new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false));
        }
        else {
            rv_recipe.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        }

        rv_recipe.setHasFixedSize(true);
        rv_recipe.setAdapter(recipeAdapter);
    }

    private void setupLoader(Bundle savedInstanceState) {
        LoaderManager loaderManager = getActivity().getLoaderManager();
        if (savedInstanceState == null) {

            if (recipeIdlingResource != null)
                recipeIdlingResource.setIdleState(true);

            Bundle recipeBundle = new Bundle();
            recipeBundle.putString(RECIPE_URL_EXTRA, RECIPE_URL);
            Loader<String> recipeLoader = loaderManager.getLoader(RECIPE_LOADER_ID);


            if (recipeLoader == null)
                loaderManager.initLoader(RECIPE_LOADER_ID, recipeBundle, this);
            else
                loaderManager.restartLoader(RECIPE_LOADER_ID, recipeBundle, this);

        } else {
            loaderManager.initLoader(RECIPE_LOADER_ID, null, this);
        }
    }

    @Override
    public void OnRecipeClick(Recipe recipe) {
        iRecipeFragmentListener.OnRecipeClick(recipe);
    }

    // ----- LOADER ----- //
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(getContext()) {

            String recipeJson;

            @Override
            protected void onStartLoading() {

                if (args == null) {
                    return;
                }

                if (recipeJson != null) {
                    deliverResult(recipeJson);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {

                String recipeURLStr = args.getString(RECIPE_URL_EXTRA);

                try {
                    URL recipeURL = new URL(recipeURLStr);
                    return NetworkUtil.getHttpResponse(recipeURL.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String recipeJson) {
                this.recipeJson = recipeJson;
                super.deliverResult(recipeJson);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if (data != null) {
            Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
            recipes = new Gson().fromJson(data, recipeListType);
            recipeAdapter.setRecipes(recipes);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         */
    }
}