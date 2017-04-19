package tk.lucasapps.omdb.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import tk.lucasapps.omdb.R;
import tk.lucasapps.omdb.fragment.SearchResultsFragment;
import tk.lucasapps.omdb.model.DaoMaster;
import tk.lucasapps.omdb.model.DaoSession;
import tk.lucasapps.omdb.model.Movie;
import tk.lucasapps.omdb.model.MovieDao;
import tk.lucasapps.omdb.model.Search;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity implements SearchResultsFragment.OnMovieSelected {

    public static final String DATABASE_NAME = "movie";

    @ViewById(R.id.search_text)
    EditText searchText;

    private String lastSearchText;

    // database connection / session related
    private SQLiteDatabase databaseConnection;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    // data access objects for our entities
    private MovieDao movieDao;


    @Click(R.id.search_button)
    void onSearchButtonPressed() {
        searchMovie(searchText.getText().toString());
    }

    private void searchMovie(String searchText) {
        lastSearchText = searchText;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.omdbapi.com/?s=" + searchText;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new SearchMoviesTask().execute(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API RESPONSE", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private class SearchMoviesTask extends AsyncTask<String, Void, Search> {
        protected Search doInBackground(String... jsons) {
            try {
                String json = jsons[0];
                Search searchResults = new Gson().fromJson(json, Search.class);
                return searchResults;
            } catch (JsonSyntaxException e) {
                return null;
            }
        }

        protected void onPostExecute(Search searchResults) {
            if(searchResults != null && searchResults.getSearch() != null && !searchResults.getSearch().isEmpty()) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_SEARCH_RESULTS, searchResults);
                intent.putExtra(MainActivity.EXTRA_SEARCH_TEXT, lastSearchText);
                startActivity(intent);
            } else {
                Toast.makeText(SearchActivity.this, SearchActivity.this.getString(R.string.no_results), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        refreshFragment();
    }

    @AfterViews
    void viewInitialization() {
        databaseConnection = new DaoMaster.DevOpenHelper(this, DATABASE_NAME, null)
                .getWritableDatabase();
        daoMaster = new DaoMaster(databaseConnection);
        daoSession = daoMaster.newSession(); // we can instantiate multiple sessions as well, sessions share the connection owned by the DaoMaster!
        movieDao = daoSession.getMovieDao();

        refreshFragment();
    }

    private void refreshFragment() {
        // Create new fragment and transaction
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(movieDao.queryBuilder().where(MovieDao.Properties.Pending.eq(true)).list());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pending_fragment_container, fragment).commit();
    }

    @Override
    public void onMovieSelected(Movie movie) {

    }

}
