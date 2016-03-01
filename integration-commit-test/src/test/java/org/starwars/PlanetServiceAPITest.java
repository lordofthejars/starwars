package org.starwars;


import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.starwars.gateway.SwapiGateway;
import org.starwars.service.PlanetService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class PlanetServiceAPITest {

    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class).addClasses(SwapiGateway.class, PlanetService.class, AverageFormatter.class,
                AverageFormatterProducer.class, PlanetResources.class, StarWarsApplication.class)
                .addAsResource("swapitest.properties", "swapi.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @ArquillianResource
    URL url;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Before
    public void configureStub() throws IOException {
        File configFile = new File(Thread.currentThread().getContextClassLoader()
                .getResource("planets.json").getFile());

        stubFor(get(urlEqualTo("/planets/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(Files.readAllBytes(configFile.toPath())))
        );
    }

    @Test
    public void shouldReturnTheAverage() {
        final String average = RestAssured.get(url.toExternalForm() + "rest/planet/orbital/average").asString();
        assertThat(average, Matchers.is("1699.42"));
    }

}
