package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

import static dandelion.wrapper.utilities.JacksonTools.jsonify;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Marcus
 */
@Slf4j
public final class ResponseWriter {

  public static <T> void writeJson(final HttpServletResponse response, final T result) {
    try {
      response.setCharacterEncoding(UTF_8.displayName());
      response.setContentType(APPLICATION_JSON_VALUE);
      response.getWriter().write(jsonify(result));
    } catch (IOException e) {
      log.error("Response write errored [ {} ]", e.getMessage());
    }
  }

  public static void writeExcel(final HttpServletResponse response, final Path path) {
    try {
      final var stream = response.getOutputStream();

      final var naming = URLEncoder.encode(path.getFileName().toString());

      response.setContentLengthLong(Files.size(path));
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Disposition", "attachment;filename=" + naming);
      final var bytes = Files.readAllBytes(path);
      stream.write(bytes, 0, bytes.length);
      stream.flush();
    } catch (IOException error) {
      log.error("Response write errored [ {} ]", error.getMessage());
    } finally {
      log.info("Response written file .");
    }
  }
}
