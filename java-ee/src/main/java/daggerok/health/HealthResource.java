package daggerok.health;

import daggerok.health.hateoas.UriBuilder;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("")
@Stateless
@Produces(APPLICATION_JSON)
public class HealthResource {

  @GET
  @Path("health")
  public Response health(@Context final UriInfo uriInfo) {
    return Response.ok(Json.createObjectBuilder()
                           .add("status", "UP")
                           .add("_self", UriBuilder.builder()
                                                   .uriInfo(uriInfo)
                                                   .resource(HealthResource.class)
                                                   .method("health")
                                                   .build()
                                                   .uri())
                           .build())
                   .build();
  }
}
