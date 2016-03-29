package org.starwars


import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.util.Properties

class AverageOrbitalPeriodSimulation extends Simulation {

  val LOCATION_PROPERTY = "starwars_planets_url";
  val location = Properties.envOrElse(LOCATION_PROPERTY, Properties.propOrElse(LOCATION_PROPERTY, "http://localhost:8080/"))

  val conf = http.baseURL(location)

  val scn = scenario("calculate average orbital period")
    .exec(http("get average orbital period")
      .get("rest/planet/orbital/average"))
    .pause(1)

  setUp(scn.inject(rampUsers(10).over(3 seconds)))
    .protocols(conf)
    .assertions(global.successfulRequests.percent.is(100), global.responseTime.max.lessThan(3000))
}