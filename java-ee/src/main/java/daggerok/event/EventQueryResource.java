package daggerok.event;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Stateless
@Path("api")
@Produces(APPLICATION_JSON)
public class EventQueryResource {

  //@Context UriInfo uriInfo;
  //@Context ResourceInfo resourceInfo;
  @Inject @EventStore Map<UUID, MyEvent> eventStore;

  @GET
  @Path("v1/events/{id}")
  public Response getEvent(@PathParam("id") final String id) {

    final UUID uuid = Try.of(() -> UUID.fromString(id))
                         .getOrElseGet(throwable -> UUID.randomUUID());

    return Response.ok(eventStore.getOrDefault(uuid, null))
                   .build();
  }

  @GET
  @Path("v1/events")
  public Response getEvents() {

    return Response.ok(eventStore.values())
                   .build();
  }
}
