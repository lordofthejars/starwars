package org.starwars;

import org.starwars.gateway.SwapiGateway;
import org.starwars.service.PlanetService;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DecimalFormat;

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
    @Path("/rotation/average")
    @Produces(MediaType.TEXT_PLAIN)
    public Response calculateAverageOfRotation() {
        JsonObject planets = swapiGateway.getAllPlanets();
        final JsonArray results = planets.getJsonArray("results");
        double average = planetService.calculateAverageOfRotationPeriod(results);
        return Response.accepted(averageFormatter.format(average)).build();
    }

}
