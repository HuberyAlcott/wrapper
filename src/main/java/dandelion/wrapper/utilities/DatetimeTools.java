package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/** @author Marcus */
@Slf4j
public final class DatetimeTools {

  private DatetimeTools() {}

  public static String format(final long timestamp, final String format) {
    final SimpleDateFormat instance = new SimpleDateFormat(format);
    return instance.format(timestamp);
  }
}
