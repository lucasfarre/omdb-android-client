package tk.lucasapps.omdb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import tk.lucasapps.omdb.R;
import tk.lucasapps.omdb.activity.MainActivity;
import tk.lucasapps.omdb.fragment.SearchResultsFragment;
import tk.lucasapps.omdb.model.Movie;

/**
 * Created by lucas on 10/13/16.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<Movie> movies;

    private SearchResultsFragment.OnMovieSelected onMovieSelected;

    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView year;
        public ImageView poster;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            year = (TextView) v.findViewById(R.id.year);
            poster = (ImageView) v.findViewById(R.id.poster);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SearchResultsAdapter(List<Movie> movies, SearchResultsFragment.OnMovieSelected onMovieSelected) {
        this.movies = movies;
        this.onMovieSelected = onMovieSelected;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_results, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(movies.get(position).getTitle());
        holder.year.setText(movies.get(position).getYear());
        Picasso.with(context).load(movies.get(position).getPoster()).into(holder.poster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMovieSelected.onMovieSelected(movies.get(position));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movies.size();
    }


}