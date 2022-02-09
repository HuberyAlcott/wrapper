package dandelion.wrapper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Marcus
 */
@Getter
@AllArgsConstructor
public enum EnableStatus {

  /***/
  unknown(0, "status unset", "status unset"),
  enabled(1, "record enable", "record enable"),
  disable(2, "record disable", "record disable"),
  ;

  private final Integer flag;
  private final String desc;
  private final String note;

  public static EnableStatus typeof(Integer code) {
    for (EnableStatus value : EnableStatus.values()) {
      if (value.getFlag().equals(code)) {
        return value;
      }
    }
    return enabled;
  }
}