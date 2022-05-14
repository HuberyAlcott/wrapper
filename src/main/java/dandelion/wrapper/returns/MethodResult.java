package dandelion.wrapper.returns;

import dandelion.wrapper.defines.GeneralExecuted;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;

/**
 * MethodResult
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode
public class MethodResult<B> implements Serializable {

  private GeneralExecuted state;

  private Collection<B> backs;
}
