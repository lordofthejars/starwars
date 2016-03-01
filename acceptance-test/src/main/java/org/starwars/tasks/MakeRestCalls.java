package org.starwars.tasks;

import com.google.common.eventbus.Subscribe;
import net.serenitybdd.core.eventbus.Broadcaster;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.events.ActorEndsPerformanceEvent;

import java.net.MalformedURLException;
import java.net.URL;

public class MakeRestCalls extends SerenityRest implements Ability {

    private Actor actor;
    URL rootUrl;

    protected MakeRestCalls(URL rootUrl) {
        this.rootUrl = rootUrl;
        registerForEventNotification();
    }

    private void registerForEventNotification() {
        Broadcaster.getEventBus().register(this);
    }

    public static MakeRestCalls with(String rootUrl) {
        try {
            return new MakeRestCalls(new URL(rootUrl));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static MakeRestCalls as(Actor actor) {
        return actor.abilityTo(MakeRestCalls.class).asActor(actor);
    }

    @Subscribe
    public void endPerformance(ActorEndsPerformanceEvent performanceEvent) {
        if (performanceEvent.getName().equals(actor.getName())) {
            clearQueryData();
        }
    }

    @Override
    public <T extends Ability> T asActor(Actor actor) {
        this.actor = actor;
        return (T) this;
    }
}
