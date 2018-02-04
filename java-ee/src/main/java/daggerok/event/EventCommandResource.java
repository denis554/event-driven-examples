package daggerok.event;

import lombok.extern.slf4j.Slf4j;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Stateless
@Path("api")
@Produces(APPLICATION_JSON)
public class EventCommandResource {

  @Context UriInfo uriInfo;
  @Inject Event<MyEvent> events;

  @POST
  @Path("v1/events")
  public Response sendEvent(final MyEvent event) {

    final UUID uuid = UUID.randomUUID();

    events.fire(event.setId(uuid));

    return Response.created(uriInfo.getBaseUriBuilder()
                                   .path(EventQueryResource.class)
                                   .path(EventQueryResource.class, "getEvent")
                                   .build(uuid))
                   .build();
  }
}
