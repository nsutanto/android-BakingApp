package nsutanto.bakingapp.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
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
import nsutanto.bakingapp.adapter.RecipeAdapter;
import nsutanto.bakingapp.model.Recipe;
import nsutanto.bakingapp.utils.NetworkUtil;

// TODO: Implement Async Task Loader
public class RecipeFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<String> {

    @BindView(R.id.rv_recipe) RecyclerView rv_recipe;
    RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;
    private static final String RECIPE_URL_EXTRA = "recipe";

    public RecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);



        loadRecipes();
        return view;
    }

    private void loadRecipes() {

    }


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
            //recipeListAdapter.setRecipeModelList(recipeModel);
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