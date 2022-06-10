package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author Marcus
 */
@Slf4j
public final class StringTools extends StringUtils {

  private static final String REPLACE_CHAR_UNDERLINE = "_";

  private StringTools() {}

  public static String generate(final boolean replace) {
    final var uuid = UUID.randomUUID().toString();
    if (replace) {
      return uuid.replace("-", "");
    }
    return uuid;
  }

  public static String underHump(String para) {
    final StringBuilder builder = new StringBuilder();
    String[] a = para.split(REPLACE_CHAR_UNDERLINE);
    for (String s : a) {
      if (!para.contains(REPLACE_CHAR_UNDERLINE)) {
        builder.append(s);
        continue;
      }
      if (builder.length() == 0) {
        builder.append(s.toLowerCase());
      } else {
        builder.append(s.substring(0, 1).toUpperCase());
        builder.append(s.substring(1).toLowerCase());
      }
    }
    return builder.toString();
  }

  public static String humpUnder(String para) {
    StringBuilder builder = new StringBuilder(para);
    int temp = 0;
    if (!para.contains(REPLACE_CHAR_UNDERLINE)) {
      for (int i = 0; i < para.length(); i++) {
        if (Character.isUpperCase(para.charAt(i))) {
          builder.insert(i + temp, REPLACE_CHAR_UNDERLINE);
          temp += 1;
        }
      }
    }
    return builder.toString().toLowerCase();
  }

  public static String subString(final String source, final int length) {
    String fixedStr = null;
    if (ObjectUtils.isEmpty(source) && length > 0) {
      if (source.length() <= length) {
        fixedStr = source;
      } else {
        fixedStr = source.substring(0, length);
      }
    }
    return fixedStr;
  }
}
