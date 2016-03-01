package org.starwars.features.get_planet_information;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.starwars.tasks.CalculateAverage;
import org.starwars.tasks.MakeRestCalls;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.starwars.tasks.CalculateAverage.theAverageOrbitalPeriod;

@Narrative(text={"In order to generate a report of Star Wars planets",
        "As a report engine",
        "I want to be able to get some important averages of planets"})
@WithTag(name="Reporting")
@RunWith(SerenityRunner.class)
public class PlanetsCalculations {

    private static final String LOCATION_PROPERTY = "starwars_planets_url";
    private static EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

    @Steps
    CalculateAverage calculateAverage;

    Actor reportEngine = Actor.named("Report Engine");

    @Before
    public void aggregator_engine_can_do_rest_calls_to_star_wars_service() {
        String location = environmentVariables.getValue(LOCATION_PROPERTY, environmentVariables.getProperty(LOCATION_PROPERTY));
        reportEngine.can(MakeRestCalls.with(location));
    }

    @Test
    public void should_be_able_to_get_average_orbital_pertiod_from_star_wars_planets() {
        reportEngine.should(seeThat(theAverageOrbitalPeriod(), is("1298.3")));
    }

    @Test
    @Pending
    public void should_be_able_to_get_average_diameter_from_start_wars_planets() {
    }
}
