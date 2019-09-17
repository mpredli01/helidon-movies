package org.redlich.movies;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(int id) {
        super("movieId " + id + " was not found!");
        }
    }
