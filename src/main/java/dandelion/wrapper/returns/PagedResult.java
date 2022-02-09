package dandelion.wrapper.returns;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/** @author Marcus */
@Data
@EqualsAndHashCode(callSuper = true)
public class PagedResult<A> extends ServerResult {

  private Long page;

  private Long size;

  private Long skip;

  private Long sums;

  private Collection<A> list;
}
