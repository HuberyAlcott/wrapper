package dandelion.wrapper.defines;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** @author Marcus */
@Slf4j
@Getter
@EqualsAndHashCode(callSuper = true)
public class GeneralException extends RuntimeException {

  private GeneralExecuted state;

  public GeneralException(GeneralExecuted state) {
    super(state.obtainDesc());
    this.state = state;
  }

  public GeneralException(String message) {
    super(message);
  }

  public GeneralException(String message, Throwable cause) {
    super(message, cause);
  }

  public GeneralException(Throwable cause) {
    super(cause);
  }

  protected GeneralException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
