package org.starwars;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@RunWith(PactRunner.class)
@Provider("planets_provider")
@PactFolder("pacts")
public class PlanetProducerTest {

    private static URL location;

    @BeforeClass
    public static void setUpConnection() throws MalformedURLException {
        String url = Optional.ofNullable(System.getenv("starwars_planets_url"))
                .orElse(Optional.ofNullable(System.getProperty("starwars_planets_url"))
                .orElse("http://localhost:8080/starwars"));
        location = new URL(url);
    }

    @TestTarget
    public final Target target = new UrlHttpTarget(location);

}
