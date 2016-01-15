package org.starwars.gateway;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.Reader;

@ApplicationScoped
public class SwapiGateway {

    String swapiUrl = "http://swapi.co/api/";

    private Client client;
    private WebTarget peopleTarget;

    @PostConstruct
    public void initializeClient() {
        client = ClientBuilder.newClient();
        peopleTarget = client.target(swapiUrl).path("planets/");
    }

    public JsonObject getAllPlanets() {
        Reader reader = peopleTarget.request(MediaType.APPLICATION_JSON_TYPE).get(Reader.class);
        return Json.createReader(reader).readObject();
    }

}
