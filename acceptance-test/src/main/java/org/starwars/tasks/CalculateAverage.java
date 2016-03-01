package org.starwars.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.thucydides.core.annotations.Step;

import java.net.URL;

public class CalculateAverage implements Question<String> {

    public static CalculateAverage theAverageOrbitalPeriod() {
        return new CalculateAverage();
    }

    @Step("{0} wants to know the average rotation time of Star Wars Planets")
    @Override
    public String answeredBy(Actor actor) {
        final MakeRestCalls restCalls = MakeRestCalls.as(actor);
        URL url = restCalls.rootUrl;
        return restCalls.rest().get(url.toExternalForm() + "rest/planet/orbital/average").asString();
    }
}
