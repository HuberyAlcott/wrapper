package dandelion.wrapper.returns;

import dandelion.wrapper.defines.GeneralExecuted;
import dandelion.wrapper.defines.Paged;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/** @author Marcus */
@Data
@EqualsAndHashCode
public class PagingResult<B> {

  private GeneralExecuted state;

  private Paged<B> paged;

  public PagingResult() {
    this.state = process_succeed;
    this.paged = Paged.empty();
  }
}
