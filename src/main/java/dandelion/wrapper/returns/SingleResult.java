package dandelion.wrapper.returns;

import dandelion.wrapper.defines.GeneralExecuted;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * MethodResult
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode
public class SingleResult<B> implements Serializable {

  private GeneralExecuted state;

  private B backs;
}
