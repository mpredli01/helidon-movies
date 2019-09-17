package org.redlich.movies;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.*;

@Path("/movies")
@RequestScoped
public class MovieResource {
    private final static Logger LOGGER = Logger.getLogger(MovieResource.class.getName());

    private final MovieRepository movies;

    @Context
    ResourceContext resourceContext;

    @Context
    UriInfo uriInfo;

    @Inject
    public MovieResource(MovieRepository movies) {
        this.movies = movies;
        }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies() {
        return ok(this.movies.all()).build();
        }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response savePost(@Valid Movie movie) {
        Movie saved = this.movies.save(Movie.of(movie.getId(),movie.getTitle(),movie.getYear()));
        return created(
                uriInfo.getBaseUriBuilder()
                        .path("/movies/{id}")
                        .build(saved.getId())
        ).build();
        }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") final int id) {
        Movie movie = this.movies.getById(id);
        if (movie == null) {
            throw new MovieNotFoundException(id);
            }
        return ok(movie).build();
        }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") final int id, @Valid Movie movie) {
        Movie existed = this.movies.getById(id);
        existed.setTitle(movie.getTitle());
        existed.setYear(movie.getYear());

        Movie saved = this.movies.save(existed);
        return noContent().build();
        }

    @Path("{id}")
    @DELETE
    public Response deleteMovie(@PathParam("id") final int id) {
        this.movies.deleteById(id);
        return noContent().build();
        }
    }
