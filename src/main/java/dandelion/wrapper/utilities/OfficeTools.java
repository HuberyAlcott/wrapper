package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author marcus
 */
@Slf4j
public final class OfficeTools extends FilesTools {

  private static final long SPLIT_LENGTH = 1_0000;

  private OfficeTools() {}

  @Deprecated
  public static void writeExcel(
      final OutputStream target, final Map<String, String> header, final Collection<?> content) {
    try (final var workbook = new XSSFWorkbook()) {
      final var contSize = content.size();
      if (contSize > SPLIT_LENGTH) {
        final var times = (contSize / SPLIT_LENGTH) + 1;
        for (long time = 0; time < times; time++) {
          final var objects =
              content.parallelStream().skip((time * SPLIT_LENGTH)).limit(SPLIT_LENGTH).toList();
        }
      } else {

      }
    } catch (IOException error) {

    }
  }
}
