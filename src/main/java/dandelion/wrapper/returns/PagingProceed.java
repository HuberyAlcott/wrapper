package dandelion.wrapper.returns;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * PagingProceed
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PagingProceed<A> extends ServerProceed {

  private Long page;

  private Long size;

  private Long skip;

  private Long sums;

  private Collection<A> list;
}
