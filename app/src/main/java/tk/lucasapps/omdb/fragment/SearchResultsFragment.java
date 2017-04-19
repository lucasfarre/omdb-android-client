package tk.lucasapps.omdb.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.Serializable;
import java.util.List;

import tk.lucasapps.omdb.R;
import tk.lucasapps.omdb.adapter.SearchResultsAdapter;
import tk.lucasapps.omdb.model.Movie;
import tk.lucasapps.omdb.model.Search;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(value = R.layout.fragment_search_results)
public class SearchResultsFragment extends Fragment {

    @ViewById(R.id.search_results_recycler_view)
    RecyclerView searchResultsRecyclerView;
    private LinearLayoutManager layoutManager;
    private SearchResultsAdapter searchResultsAdapter;

    private static final String SEARCH_RESULTS_KEY = "SEARCH_RESULTS_KEY";

    private List<Movie> movies;
    private OnMovieSelected onMovieSelected;

    public static SearchResultsFragment newInstance(List<Movie> searchResults) {
        SearchResultsFragment f = new SearchResultsFragment_();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable(SEARCH_RESULTS_KEY, (Serializable) searchResults);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) { // usar broadcastreceiver
        super.onAttach(context);
        try {
            onMovieSelected = (OnMovieSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnMovieSelected");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = (List<Movie>) getArguments().getSerializable(SEARCH_RESULTS_KEY);
    }

    @AfterViews
    void initializeRecyclerView() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        searchResultsRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        searchResultsRecyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        searchResultsAdapter = new SearchResultsAdapter(movies, onMovieSelected);
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);
    }

    public interface OnMovieSelected {
        void onMovieSelected(Movie movie);
    }

}
