package org.redlich.movies;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class MovieRepository {

    static Map<Integer,Movie> data = new ConcurrentHashMap<>();

    static {
        Movie first = Movie.of(1,"Reservoir Dogs",1992);
        Movie second = Movie.of(2,"Pulp Fiction",1994);
        Movie third = Movie.of(3,"Jackie Brown",1997);
        Movie fourth = Movie.of(4,"Kill Bill, Volume 1",2003);
        Movie fifth = Movie.of(5,"Kill Bill, Volume 2",2004);
        Movie sixth = Movie.of(6,"Inglourious Basterds",2009);
        Movie seventh = Movie.of(7,"Django Unchained",2012);
        Movie eighth= Movie.of(8,"The Hateful Eight",2015);
        Movie ninth = Movie.of(9,"Once Unpon a Time...in Hollywood",2019);
        data.put(first.getId(),first);
        data.put(second.getId(),second);
        data.put(third.getId(),third);
        data.put(fourth.getId(),fourth);
        data.put(fifth.getId(),fifth);
        data.put(sixth.getId(),sixth);
        data.put(seventh.getId(),seventh);
        data.put(eighth.getId(),eighth);
        data.put(ninth.getId(),ninth);
        }

    public List<Movie> all() {
        return new ArrayList<>(data.values());
        }

    public Movie getById(int id) {
        return data.get(id);
        }

    public Movie save(Movie movie) {
        data.put(movie.getId(),movie);
        return movie;
        }

    public void deleteById(int id) {
        data.remove(id);
        }
    }
