package daggerok.health.hateoas;

import lombok.Builder;
import lombok.Value;

import javax.ws.rs.core.UriInfo;

@Value
@Builder
public class UriBuilder {

  final UriInfo uriInfo;
  final Class resource;
  final String method;

  public String uri() {
    return uriInfo.getBaseUriBuilder()
                  .path(resource)
                  .path(resource, method)
                  .build()
                  .toString();
  }
}
