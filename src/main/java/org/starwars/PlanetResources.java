package org.starwars;

import org.starwars.gateway.SwapiGateway;
import org.starwars.service.PlanetService;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DecimalFormat;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Path("/planet")
@Singleton
@Lock(LockType.READ)
public class PlanetResources {

    @Inject
    SwapiGateway swapiGateway;

    @Inject
    PlanetService planetService;

    @Inject
    @AverageFormatter
    DecimalFormat averageFormatter;

    @GET
    @Path("/orbital/average")
    @Produces(MediaType.TEXT_PLAIN)
    @Asynchronous
    public void calculateAverageOfOrbitalPeriod(@Suspended final AsyncResponse response) {

        // Timeout control
        response.setTimeoutHandler(asyncResponse -> asyncResponse.resume(Response.status
                (Response.Status.SERVICE_UNAVAILABLE)
                .entity("TIME OUT !").build()));
        response.setTimeout(30, TimeUnit.SECONDS);

        try {
            
            JsonObject planets = swapiGateway.getAllPlanets();
            final JsonArray results = planets.getJsonArray("results");
            double average = planetService.calculateAverageOfOrbitalPeriod(results);
            final Response averageResponse = Response.ok(averageFormatter.format(average)).build();
            response.resume(averageResponse);

        } catch(Throwable e) {
            response.resume(e);
        }
    }

    @GET
    @Path("/orbital/biggest")
    @Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public void getThreePlanetsWithBiggestOrbitalPeriod(@Suspended final AsyncResponse response) {

        // Timeout control
        response.setTimeoutHandler(asyncResponse -> asyncResponse.resume(Response.status
                (Response.Status.SERVICE_UNAVAILABLE)
                .entity("TIME OUT !").build()));
        response.setTimeout(30, TimeUnit.SECONDS);

        try {

            JsonObject planets = swapiGateway.getAllPlanets();
            final JsonArray results = planets.getJsonArray("results");
            Set<String> planetsWithBiggestOrbitalTimes = planetService.getThreePlanetsWithBiggestOrbitalPeriod(results);
            final JsonArrayBuilder planetsJsonArray = Json.createArrayBuilder();

            planetsWithBiggestOrbitalTimes.forEach(planetsJsonArray::add);
            final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("planets", planetsJsonArray);
            final Response averageResponse = Response.ok(objectBuilder.build()).build();
            response.resume(averageResponse);

        } catch(Throwable e) {
            response.resume(e);
        }
    }

}
