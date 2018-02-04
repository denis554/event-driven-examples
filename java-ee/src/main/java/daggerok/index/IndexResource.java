package daggerok.index;

import daggerok.event.EventCommandResource;
import daggerok.event.EventQueryResource;
import daggerok.health.HealthResource;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URLEncoder;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
@Stateless
@Produces(APPLICATION_JSON)
public class IndexResource {

  @GET
  @Path("")
  public Response index(@Context final UriInfo uriInfo) {
    return Response.ok(Json.createObjectBuilder()
                           .add("POST events", uriInfo.getBaseUriBuilder()
                                                      .path(EventCommandResource.class)
                                                      .path(EventCommandResource.class, "sendEvent")
                                                      .build()
                                                      .toString())
                           .add("GET events", uriInfo.getBaseUriBuilder()
                                                     .path(EventQueryResource.class)
                                                     .path(EventQueryResource.class, "getEvents")
                                                     .build()
                                                     .toString())
                           .add("GET event by id", uriInfo.getBaseUriBuilder()
                                                          .path(EventQueryResource.class)
                                                          .path(EventQueryResource.class, "getEvent")
                                                          .build("$id")
                                                          .toASCIIString())
                           .add("GET health", uriInfo.getBaseUriBuilder()
                                                     .path(HealthResource.class)
                                                     .path(HealthResource.class, "health")
                                                     .build()
                                                     .toString())
                           .add("_self", uriInfo.getBaseUriBuilder()
                                                .path(IndexResource.class)
                                                .path(IndexResource.class, "index")
                                                .build()
                                                .toString())
                           .build())
                   .build();
  }
}
