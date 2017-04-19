package tk.lucasapps.omdb.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lucas on 10/12/16.
 */
@Entity
public class Movie implements Serializable {

    public static final long serialVersionUID = 536871008;

    @Id
    private Long id;

    @Transient
    private String Released;

    @Transient
    private String Type;

    @Transient
    private String imdbVotes;

    @Transient
    private String Runtime;

    @Transient
    private String Response;

    private String Poster;

    @Index(unique = true)
    private String imdbID;

    @Transient
    private String Country;

    private String Title;

    @Transient
    private String imdbRating;

    private String Year;

    @Transient
    private String Rated;

    @Transient
    private String Actors;

    @Transient
    private String Plot;

    @Transient
    private String Metascore;

    @Transient
    private String Writer;

    @Transient
    private String Genre;

    @Transient
    private String Language;

    @Transient
    private String Awards;

    @Transient
    private String Director;

    @Index
    private boolean watched;

    @Index
    private boolean pending;


    @Generated(hash = 2016790444)
    public Movie(Long id, String Poster, String imdbID, String Title, String Year,
            boolean watched, boolean pending) {
        this.id = id;
        this.Poster = Poster;
        this.imdbID = imdbID;
        this.Title = Title;
        this.Year = Year;
        this.watched = watched;
        this.pending = pending;
    }

    @Generated(hash = 1263461133)
    public Movie() {
    }


    public boolean isPending() {
        return pending;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReleased() {
        return Released;
    }

    public void setReleased(String Released) {
        this.Released = Released;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String Runtime) {
        this.Runtime = Runtime;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String Response) {
        this.Response = Response;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getRated() {
        return Rated;
    }

    public void setRated(String Rated) {
        this.Rated = Rated;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String Actors) {
        this.Actors = Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String Plot) {
        this.Plot = Plot;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setMetascore(String Metascore) {
        this.Metascore = Metascore;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String Writer) {
        this.Writer = Writer;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getAwards() {
        return Awards;
    }

    public void setAwards(String Awards) {
        this.Awards = Awards;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String Director) {
        this.Director = Director;
    }

    public boolean getPending() {
        return this.pending;
    }

    public boolean getWatched() {
        return this.watched;
    }

}

