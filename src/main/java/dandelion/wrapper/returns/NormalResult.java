package dandelion.wrapper.returns;

import lombok.Data;
import lombok.EqualsAndHashCode;

/** @author Marcus */
@Data
@EqualsAndHashCode(callSuper = true)
public class NormalResult<A> extends ServerResult {

  private A back;
}
