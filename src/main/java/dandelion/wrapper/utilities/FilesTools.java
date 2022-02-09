package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/** @author Marcus */
@Slf4j
public class FilesTools {

  private static final String TMP_DIR = System.getProperty("user.dir");

  public static Optional<Path> saveFile(final MultipartFile source) {
    if (ObjectUtils.isEmpty(source)) {
      return Optional.empty();
    }
    try {
      final var bytes = source.getBytes();
      final var name = obtainFilename(source);
      final var type = obtainFiletype(source);
      final var full = name + type;
      final var temp = Path.of(TMP_DIR, full);
      final var write = Files.write(temp, bytes);
      return Optional.of(write);
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private static String obtainFilename(final MultipartFile source) {
    return UUID.randomUUID().toString();
  }

  private static String obtainFiletype(final MultipartFile source) {
    if (ObjectUtils.isEmpty(source)) {
      return null;
    }
    final var origin = source.getOriginalFilename();
    if (!ObjectUtils.isEmpty(origin)) {
      final var suffix = origin.substring(origin.lastIndexOf('.') + 1);
    }
    return null;
  }
}
