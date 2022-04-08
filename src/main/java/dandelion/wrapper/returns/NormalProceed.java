package dandelion.wrapper.returns;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/** @author Marcus */
@Data
@EqualsAndHashCode(callSuper = true)
public class NormalProceed<A> extends ServerProceed {

  private Collection<A> back;
}
