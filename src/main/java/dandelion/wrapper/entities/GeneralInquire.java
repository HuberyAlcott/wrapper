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
public class GeneralInquire<RS> implements Serializable {

  /** 当前页号 */
  private Integer currPage;

  /** 每页数量 */
  private Integer pageSize;

  /** 编号列表 */
  private List<RS> recordSerials = new ArrayList<>(0);

  /** 可用状态 */
  private Integer enableStatus;

  /** 删除状态 */
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
