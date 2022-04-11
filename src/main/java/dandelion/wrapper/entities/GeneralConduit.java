package dandelion.wrapper.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 接口出入参对象
 *
 * @author Marcus
 */
@Data
@EqualsAndHashCode
public class GeneralConduit implements Serializable {

  private Long recordSerial;

  private Integer enableStatus;

  private Integer deleteStatus;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private LocalDateTime createTiming;

  @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
  private LocalDateTime updateTiming;

}
