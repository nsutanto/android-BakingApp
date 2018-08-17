package nsutanto.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nsutanto.bakingapp.model.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private List<Step> steps = new ArrayList<>();
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView tv_content;
        //private final TextView tv_author;

        public ViewHolder(View v) {
            super(v);
            //tv_author = v.findViewById(R.id.tv_author);
            //tv_content = v.findViewById(R.id.tv_content);
        }

        public void bind(Step step) {
            //tv_author.setText(review.getAuthor());
            //tv_content.setText(review.getContent());
        }
    }


    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.review_item, parent, false);
        //return new ViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setReviews(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }
}
