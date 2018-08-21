package nsutanto.bakingapp;

import android.app.FragmentManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import nsutanto.bakingapp.fragment.RecipeFragment;
import nsutanto.bakingapp.listener.IRecipeFragmentListener;
import nsutanto.bakingapp.model.Ingredient;
import nsutanto.bakingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements IRecipeFragmentListener {

    private int widgetID;
    boolean isWidget = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidget();
        setupFragment();
    }

    private void setupFragment() {
        FragmentManager fm = getFragmentManager();

        RecipeFragment recipeFragment = new RecipeFragment();

        fm.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }

    @Override
    public void OnRecipeClick(Recipe recipe) {
        if (isWidget) {
            setupWidget(recipe);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", recipe);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void initWidget() {
        if (getIntent().getExtras() != null) {
            if(getIntent().getExtras().containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                isWidget = true;
                widgetID = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            }
        }
    }

    private void setupWidget(Recipe recipe)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.tv_recipe, recipe.getName());

        String listIngredientStr = "";
        for (Ingredient ingredient : recipe.getIngredients())
        {
            listIngredientStr += ingredient.getIngredient() + "\n";
        }
        views.setTextViewText(R.id.tv_ingredients, listIngredientStr);
        appWidgetManager.updateAppWidget(widgetID, views);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        setResult(RESULT_OK, intent);
        finish();
    }
}
