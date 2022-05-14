package dandelion.wrapper.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口出入参对象
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode
public class GeneralConduit implements Serializable {

  /** 记录编号 */
  private Long recordSerial;

  /** 可用状态 */
  private Integer enableStatus;

  /** 删除状态 */
  private Integer deleteStatus;

  /** 创建时间 */
  private Long createTiming;

  /** 更新时间 */
  private Long updateTiming;
}
