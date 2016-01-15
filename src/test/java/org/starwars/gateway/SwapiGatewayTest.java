package org.starwars.gateway;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SwapiGatewayTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void shouldReturnAllPlanets() throws IOException {

        File configFile = new File(Thread.currentThread().getContextClassLoader()
                .getResource("planets.json").getFile());

        stubFor(get(urlEqualTo("/planets/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(Files.readAllBytes(configFile.toPath())))
        );

        SwapiGateway swapiGateway = new SwapiGateway();
        swapiGateway.swapiUrl = "http://localhost:8089";
        swapiGateway.initializeClient();

        final JsonObject allPlanets = swapiGateway.getAllPlanets();
        final JsonArray results = allPlanets.getJsonArray("results");
        assertThat(results.size(), is(7));

    }

}
