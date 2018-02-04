package daggerok.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MyEvent implements Serializable {

  private static final long serialVersionUID = -4121938416880827503L;

  UUID id;
  @NotNull String message;
}
