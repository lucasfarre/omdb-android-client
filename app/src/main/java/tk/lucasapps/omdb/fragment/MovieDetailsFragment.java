package tk.lucasapps.omdb.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import tk.lucasapps.omdb.R;
import tk.lucasapps.omdb.activity.SearchActivity;
import tk.lucasapps.omdb.model.DaoMaster;
import tk.lucasapps.omdb.model.DaoSession;
import tk.lucasapps.omdb.model.Movie;
import tk.lucasapps.omdb.model.MovieDao;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_movie_details)
public class MovieDetailsFragment extends Fragment {

    private static final String MOVIE_KEY = "MOVIE_KEY";

    @ViewById(R.id.details_title)
    TextView title;
    @ViewById(R.id.details_year)
    TextView year;
    @ViewById(R.id.details_directors)
    TextView directors;
    @ViewById(R.id.details_actors)
    TextView actors;
    @ViewById(R.id.details_plot)
    TextView plot;
    @ViewById(R.id.details_poster)
    ImageView poster;

    private Movie movie;

    // database connection / session related
    private SQLiteDatabase databaseConnection;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    // data access objects for our entities
    private MovieDao movieDao;

    public static MovieDetailsFragment newInstance(Movie movie) {
        MovieDetailsFragment f = new MovieDetailsFragment_();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_KEY, movie);
        f.setArguments(args);
        return f;
    }

    @Click(R.id.add_to_pending_button)
    void onAddToPendingButtonPressed() {
        Movie daoMovie = movieDao.queryBuilder().where(MovieDao.Properties.ImdbID.eq(movie.getImdbID())).unique();
        if(daoMovie == null) {
            movie.setPending(true);
            movieDao.save(movie);
            return;
        }
        daoMovie.setPending(!daoMovie.getPending());
        movieDao.update(daoMovie);

    }

    @AfterViews
    void getMovieDetails() {
        databaseConnection = new DaoMaster.DevOpenHelper(getContext(), SearchActivity.DATABASE_NAME, null)
                .getWritableDatabase();
        daoMaster = new DaoMaster(databaseConnection);
        daoSession = daoMaster.newSession(); // we can instantiate multiple sessions as well, sessions share the connection owned by the DaoMaster!
        movieDao = daoSession.getMovieDao();

        movie = (Movie) getArguments().getSerializable(MOVIE_KEY);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://www.omdbapi.com/?i=" + movie.getImdbID();
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

    private class SearchMoviesTask extends AsyncTask<String, Void, Movie> {
        protected Movie doInBackground(String... jsons) {
            try {
                String json = jsons[0];
                Movie movie = new Gson().fromJson(json, Movie.class);
                return movie;
            } catch (JsonSyntaxException e) {
                return null;
            }
        }

        protected void onPostExecute(Movie movie) {
            if(title != null) {
                title.setText(movie.getTitle());
                year.setText(movie.getYear());
                directors.setText(movie.getDirector());
                actors.setText(movie.getActors());
                plot.setText(movie.getPlot());
                Picasso.with(getContext()).load(movie.getPoster()).into(poster);
            }
        }
    }
}
