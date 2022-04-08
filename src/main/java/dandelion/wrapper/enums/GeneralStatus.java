package dandelion.wrapper.enums;

import dandelion.wrapper.defines.GeneralExecuted;
import lombok.AllArgsConstructor;

/** @author Marcus */
@AllArgsConstructor
public enum GeneralStatus implements GeneralExecuted {

  /***/
  process_succeed("A0000000", "成功"),
  process_failure("A0000001", "失败"),
  process_errored("A0000002", "异常"),
  calling_timeout("A0000003", "请求调用超时"),
  params_errored("A0000004", "参数异常"),
  ;
  private final String code;
  private final String desc;

  @Override
  public String obtainCode() {
    return code;
  }

  @Override
  public String obtainDesc() {
    return desc;
  }

  @Override
  public String obtainNote() {
    return desc;
  }
}
