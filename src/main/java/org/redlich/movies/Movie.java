package org.redlich.movies;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private int year;

    public static Movie of(int id, String title, int year) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setYear(year);

        return movie;
        }

    public int getId() {
        return id;
        }

    public void setId(int id) {
        this.id = id;
        }

    public String getTitle() {
        return title;
        }

    public void setTitle(String title) {
        this.title = title;
        }

    public int getYear() {
        return year;
        }

    public void setYear(int year) {
        this.year = year;
        }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
        }
    }
