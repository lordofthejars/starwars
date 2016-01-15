package org.starwars.service;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PlanetServiceTest {


    @Test
    public void shouldCalculateAverage() {

        final JsonObject planets = Json.createReader(PlanetServiceTest.class.getResourceAsStream("/planets.json")).readObject();
        PlanetService planetService = new PlanetService();
        final double average = planetService.calculateAverageOfRotationPeriod(planets.getJsonArray("results"));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        assertThat(decimalFormat.format(average), is("1699.42"));

    }

}
