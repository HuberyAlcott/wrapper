package dandelion.wrapper.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口入参查询对象
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode
public class GeneralInquire implements Serializable {

  private List<Long> recordSerials = new ArrayList<>(0);

  private Integer enableStatus;

  private Integer deleteStatus;

  /** 创建开始时间 */
  private LocalDateTime createStart;

  /** 创建开始时间 */
  private LocalDateTime createFinal;

  /** 更新开始时间 */
  private LocalDateTime updateStart;

  /** 更新开始时间 */
  private LocalDateTime updateFinal;
}
