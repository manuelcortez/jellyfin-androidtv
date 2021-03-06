package org.jellyfin.androidtv.livetv;

import org.jellyfin.androidtv.TvApp;
import org.jellyfin.androidtv.preferences.SystemPreferences;
import org.jellyfin.androidtv.util.Utils;
import org.jellyfin.apiclient.model.dto.BaseItemDto;

/**
 * Created by Eric on 5/13/2015.
 */
public class GuideFilters {
    private SystemPreferences prefs;
    private boolean movies;
    private boolean news;
    private boolean series;
    private boolean kids;
    private boolean sports;
    private boolean premiere;

    public GuideFilters() {
        load();
    }

    public void load() {
        prefs = TvApp.getApplication().getSystemPreferences();

        movies = prefs.getLiveTvGuideFilterMovies();
        news = prefs.getLiveTvGuideFilterNews();
        series = prefs.getLiveTvGuideFilterSeries();
        kids = prefs.getLiveTvGuideFilterKids();
        sports = prefs.getLiveTvGuideFilterSports();
        premiere = prefs.getLiveTvGuideFilterPremiere();
    }

    public boolean any() { return movies || news || series || kids || sports || premiere; }

    public boolean passesFilter(BaseItemDto program) {
        if (!any()) return true;

        if (movies && Utils.isTrue(program.getIsMovie())) return !premiere || Utils.isTrue(program.getIsPremiere());
        if (news && Utils.isTrue(program.getIsNews())) return !premiere || Utils.isTrue(program.getIsPremiere()) || Utils.isTrue(program.getIsLive()) || !Utils.isTrue(program.getIsRepeat());
        if (series && Utils.isTrue(program.getIsSeries())) return !premiere || Utils.isTrue(program.getIsPremiere()) || !Utils.isTrue(program.getIsRepeat());
        if (kids && Utils.isTrue(program.getIsKids())) return !premiere || Utils.isTrue(program.getIsPremiere());
        if (sports && Utils.isTrue(program.getIsSports())) return !premiere || Utils.isTrue(program.getIsPremiere()) || Utils.isTrue(program.getIsLive());
        if (!movies && !news && !series && !kids && !sports) return (premiere && (Utils.isTrue(program.getIsPremiere()) || (Utils.isTrue(program.getIsSeries()) && !Utils.isTrue(program.getIsRepeat())) || (Utils.isTrue(program.getIsSports()) && Utils.isTrue(program.getIsLive()))));

        return false;

    }

    public void clear() {
        setNews(false);
        setSeries(false);
        setSports(false);
        setKids(false);
        setMovies(false);
        setPremiere(false);
    }

    @Override
    public String toString() {
        return any() ? "Content filtered. Showing channels with " + getFilterString() : "Showing all programs ";
    }

    private String getFilterString() {
        String filterString = "";
        if (movies) filterString += "movies";
        if (news) filterString += getSeparator(filterString) + "news";
        if (sports) filterString += getSeparator(filterString) + "sports";
        if (series) filterString += getSeparator(filterString) + "series";
        if (kids) filterString += getSeparator(filterString) + "kids";
        if (premiere) filterString += getSeparator(filterString) + "ONLY new";

        return filterString;
    }

    private String getSeparator(String original) {return (original.length()) > 0 ? ", " : "";}

    public boolean isMovies() {
        return movies;
    }

    public void setMovies(boolean movies) {
        this.movies = movies;
        prefs.setLiveTvGuideFilterMovies(movies);
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
        prefs.setLiveTvGuideFilterNews(news);
    }

    public boolean isSeries() {
        return series;
    }

    public void setSeries(boolean series) {
        this.series = series;
        prefs.setLiveTvGuideFilterSeries(series);
    }

    public boolean isKids() {
        return kids;
    }

    public void setKids(boolean kids) {
        this.kids = kids;
        prefs.setLiveTvGuideFilterKids(kids);
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
        prefs.setLiveTvGuideFilterSports(sports);
    }

    public boolean isPremiere() {
        return premiere;
    }

    public void setPremiere(boolean premiere) {
        this.premiere = premiere;
        prefs.setLiveTvGuideFilterPremiere(premiere);
    }

}
