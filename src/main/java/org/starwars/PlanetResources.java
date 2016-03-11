package org.starwars;

import org.starwars.gateway.SwapiGateway;
import org.starwars.service.PlanetService;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
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
            final Response averageResponse = Response.accepted(averageFormatter.format(average)).build();
            response.resume(averageResponse);

        } catch(Throwable e) {
            response.resume(e);
        }
    }

}
