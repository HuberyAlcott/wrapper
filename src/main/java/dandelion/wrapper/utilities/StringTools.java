package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/** @author Marcus */
@Slf4j
public final class StringTools {

  private StringTools() {}

  public static String generate(final boolean replace) {
    final var uuid = UUID.randomUUID().toString();
    if (replace) {
      return uuid.replace("-", "");
    }
    return uuid;
  }
}
