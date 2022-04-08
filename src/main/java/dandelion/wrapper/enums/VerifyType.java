package dandelion.wrapper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author marcus
 */
@Getter
@AllArgsConstructor
public enum VerifyType {
  /** */
  TYPE_NUM_ONLY(0, "纯数字"),
  TYPE_LETTER_ONLY(1, "仅字母(大小写混合)"),
  TYPE_ALL_MIXED(2, "数字及大小写混合"),
  TYPE_NUM_UPPER(3, "数字及大写字母混合"),
  TYPE_NUM_LOWER(4, "数字及小写字母混合"),
  TYPE_UPPER_ONLY(5, "纯大写字母"),
  TYPE_LOWER_ONLY(6, "纯小写字母"),
  ;

  private final int code;
  private final String type;
}
