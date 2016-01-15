package org.starwars.service;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.OptionalDouble;

@ApplicationScoped
public class PlanetService {

    public double calculateAverageOfRotationPeriod(final JsonArray data) {

        final OptionalDouble orbital_period = data.stream().mapToInt(p -> Integer.parseInt(((JsonObject) p).getString("orbital_period"))).average();
        return orbital_period.orElse(0);
    }

}
