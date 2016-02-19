package org.starwars;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.restassured.RestAssured;
import org.arquillian.cube.HostIp;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(Arquillian.class)
public class PlanetServiceAPIContainerTest {

    @HostIp
    private String ip;

    @Before
    public void setup() {
        WireMock.configureFor(ip, 80);
    }

    @After
    public void clean() {
        WireMock.reset();
    }

    @Test
    public void shouldReturnTheAverage() throws IOException {

        File configFile = new File(Thread.currentThread().getContextClassLoader()
                .getResource("planets.json").getFile());

        stubFor(get(urlEqualTo("/api/planets/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(Files.readAllBytes(configFile.toPath())))
        );

        URL url = new URL("http://" + ip + ":9090/starwars/");
        final String average = RestAssured.get(url.toExternalForm() + "rest/planet/rotation/average").asString();
        assertThat(average, is("1699.42"));
    }
}
