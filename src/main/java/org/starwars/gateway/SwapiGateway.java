package org.starwars.gateway;

import javax.annotation.PostConstruct;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

// tag::initial[]
public class SwapiGateway {

    String swapiUrl = "http://swapi.co/api/"; // <1>

    private Client client;

    @PostConstruct
    public void initializeClient() throws IOException {
        Properties properties = load();
        swapiUrl = properties.getProperty("url"); // <2>
        client = ClientBuilder.newClient();
    }

    private Properties load() throws IOException {
        Properties properties = new Properties();
        properties.load(SwapiGateway.class.getResourceAsStream("/swapi.properties"));
        return properties;
    }


    public JsonObject getAllPlanets() {
        WebTarget peopleTarget = client.target(swapiUrl).path("planets/"); // <3>
        Reader reader = peopleTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .get(Reader.class); // <4>
        return Json.createReader(reader).readObject(); // <5>
    }

}
// end::initial[]
