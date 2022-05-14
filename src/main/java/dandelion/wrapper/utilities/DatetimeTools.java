package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Marcus
 */
@Slf4j
public final class DatetimeTools {

  private DatetimeTools() {}

  public static String format(final long timestamp, final String format) {
    final SimpleDateFormat instance = new SimpleDateFormat(format);
    return instance.format(timestamp);
  }

  public static Date parse(final String source, final String format) {
    Assert.isTrue(ObjectUtils.isEmpty(source), "The source string is null");
    Assert.isTrue(ObjectUtils.isEmpty(format), "The format string is null");
    final var pattern = DateTimeFormatter.ofPattern(format);
    final var parsed = pattern.parse(source);
    final var instant = LocalDateTime.from(parsed).toInstant(ZoneOffset.UTC);
    return Date.from(instant);
  }
}
