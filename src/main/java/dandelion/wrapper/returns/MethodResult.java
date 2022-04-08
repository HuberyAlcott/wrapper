package dandelion.wrapper.returns;

import dandelion.wrapper.defines.GeneralExecuted;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/** @author Marcus */
@Data
@EqualsAndHashCode
public class MethodResult<B> {

  private GeneralExecuted state;

  private Collection<B> backs;
}
