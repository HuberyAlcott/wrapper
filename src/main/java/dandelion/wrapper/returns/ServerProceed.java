package dandelion.wrapper.returns;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ServerProceed
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode
public class ServerProceed implements Serializable {

  private String code;

  private String desc;

  private String note;

  private String mark;

  private Float cost;

  private String zone;

  private Long time;
}
