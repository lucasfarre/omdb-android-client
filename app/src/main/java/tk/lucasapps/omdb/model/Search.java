package tk.lucasapps.omdb.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lucas on 10/12/16.
 */
public class Search implements Serializable {

    private List<Movie> Search;

    private String totalResults;

    private String Response;

    public List<Movie> getSearch() {
        return Search;
    }

    public String getResponse() {
        return Response;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setSearch(List<Movie> search) {
        Search = search;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }
}
