package dandelion.wrapper.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Marcus
 */
public interface GeneralConverter {

  /**
   * Timestamp to LocalDateTime
   *
   * @param source Timestamp
   * @return LocalDateTime
   */
  default LocalDateTime timeToDateTime(final Timestamp source) {
    return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
  }

  /**
   * LocalDateTime to Timestamp
   *
   * @param source LocalDateTime
   * @return Timestamp
   */
  default Timestamp dateTimeToTime(final LocalDateTime source) {
    return Timestamp.valueOf(source);
  }
}
