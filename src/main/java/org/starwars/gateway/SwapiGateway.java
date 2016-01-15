package org.starwars.gateway;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class SwapiGateway {

    String swapiUrl = "http://swapi.co/api/";

    private Client client;

    @PostConstruct
    public void initializeClient() throws IOException {
        Properties properties = load();
        swapiUrl = properties.getProperty("url");
        client = ClientBuilder.newClient();
    }

    private Properties load() throws IOException {
        Properties properties = new Properties();
        properties.load(SwapiGateway.class.getResourceAsStream("/swapi.properties"));
        return properties;
    }


    public JsonObject getAllPlanets() {
        WebTarget peopleTarget = client.target(swapiUrl).path("planets/");
        Reader reader = peopleTarget.request(MediaType.APPLICATION_JSON_TYPE).get(Reader.class);
        return Json.createReader(reader).readObject();
    }

}
