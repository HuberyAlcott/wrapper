package dandelion.wrapper.utilities;

import dandelion.wrapper.enums.VerifyType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author marcus
 */
@Slf4j
public final class RandomTools {

  private RandomTools() {}

  private static final String BASE_NUMS = "123456789";
  private static final String SPEC_NUMS = "0";
  private static final String BASE_CHARS = "abcdefghjkmnpqrstuvwxyz";
  private static final String SPEC_CHARS = "iol";

  public static String text(final VerifyType type, final int length) {
    final var target = new StringBuilder();
    final var chars = concat(type);
    final var based = chars.toCharArray();
    final var range = based.length;
    final var random = ThreadLocalRandom.current();
    for (int i = 0; i < length; i++) {
      final var next = random.nextInt(range);
      target.append(based[next]);
    }
    return target.toString();
  }

  private static String concat(VerifyType type) {
    String baseChar = BASE_NUMS + BASE_CHARS;
    switch (type) {
      case TYPE_NUM_ONLY:
        baseChar = SPEC_NUMS + BASE_NUMS;
        break;
      case TYPE_LETTER_ONLY:
        baseChar =
            BASE_CHARS.toLowerCase()
                + SPEC_CHARS.toLowerCase()
                + BASE_CHARS.toUpperCase(Locale.ROOT)
                + SPEC_CHARS.toUpperCase();
        break;
      case TYPE_ALL_MIXED:
        baseChar = BASE_NUMS + BASE_CHARS.toLowerCase() + BASE_CHARS.toUpperCase();
        break;
      case TYPE_NUM_UPPER:
        baseChar = BASE_NUMS + BASE_CHARS.toUpperCase();
        break;
      case TYPE_NUM_LOWER:
        baseChar = BASE_NUMS + BASE_CHARS.toLowerCase();
        break;
      case TYPE_UPPER_ONLY:
        baseChar = (BASE_CHARS + SPEC_CHARS).toUpperCase();
        break;
      case TYPE_LOWER_ONLY:
        baseChar = (BASE_CHARS + SPEC_CHARS).toLowerCase();
        break;
      default:
    }
    return baseChar;
  }

  public static String uuid() {
    return UUID.randomUUID().toString().substring(0, 8);
  }

  public static void main(String[] args) {
    Arrays.stream(VerifyType.values())
        .forEach(
            type -> {
              final var text = text(type, 6);
              System.out.println(text + "\n");
            });
  }
}
