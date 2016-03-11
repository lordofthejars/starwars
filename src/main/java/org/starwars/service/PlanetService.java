package org.starwars.service;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlanetService {

    public double calculateAverageOfOrbitalPeriod(final JsonArray data) {

        final OptionalDouble orbital_period = data.stream().mapToInt(p -> Integer.parseInt(((JsonObject) p).getString("orbital_period"))).average();
        return orbital_period.orElse(0);
    }

    public Set<String> getThreePlanetsWithBiggestOrbitalPeriod(final JsonArray data) {

        return data.stream().sorted((p1, p2) -> {
                                    final int orbital_period_1 = Integer.parseInt(((JsonObject) p1).getString("orbital_period"));
                                    final int orbital_period_2 = Integer.parseInt(((JsonObject) p2).getString("orbital_period"));
                                    return orbital_period_2 - orbital_period_1;
                            })
                            .limit(3)
                            .map(p -> ((JsonObject) p).getString("name"))
                            .collect(Collectors.toSet());
    }

}
