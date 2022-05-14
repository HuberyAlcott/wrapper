package dandelion.wrapper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Marcus
 */
@Getter
@AllArgsConstructor
public enum DeleteStatus {

  /***/
  unknown(0, "status unset", "status unset"),
  enabled(1, "record enable", "record enable"),
  deleted(2, "record deleted", "record deleted"),
  ;

  private final Integer flag;
  private final String desc;
  private final String note;

  public static DeleteStatus typeof(Integer state) {
    for (DeleteStatus value : DeleteStatus.values()) {
      if (Objects.equals(value.getFlag(), state)) {
        return value;
      }
    }
    return enabled;
  }
}
