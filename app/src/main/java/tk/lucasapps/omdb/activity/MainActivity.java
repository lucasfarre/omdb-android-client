package tk.lucasapps.omdb.activity;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import tk.lucasapps.omdb.R;
import tk.lucasapps.omdb.fragment.SearchResultsFragment;
import tk.lucasapps.omdb.fragment.MovieDetailsFragment;
import tk.lucasapps.omdb.model.Movie;
import tk.lucasapps.omdb.model.Search;


public class MainActivity extends AppCompatActivity implements SearchResultsFragment.OnMovieSelected {

    public static final String EXTRA_SEARCH_RESULTS = "tk.lucasapps.omdb.activity.EXTRA_SEARCH_RESULTS";
    public static final String EXTRA_SEARCH_TEXT = "tk.lucasapps.omdb.activity.EXTRA_SEARCH_TEXT";

    private Search searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Results for: " + intent.getStringExtra(EXTRA_SEARCH_TEXT));
        setSupportActionBar(toolbar);

        searchResults = ((Search) intent.getSerializableExtra(EXTRA_SEARCH_RESULTS));

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            showSearchResultsFragment();
        }
    }


    private void showSearchResultsFragment() {
        // Create new fragment and transaction
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(searchResults.getSearch());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
    }

    public void showMovieDetailsFragment(Movie movie) {
        Fragment fragment = MovieDetailsFragment.newInstance(movie);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        // preguntar si ya hay alguno y si es el mismo no hacerlo.
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        showMovieDetailsFragment(movie);
    }
}
