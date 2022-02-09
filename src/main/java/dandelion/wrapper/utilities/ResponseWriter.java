package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author Marcus */
@Slf4j
public final class ResponseWriter {

  public static <T> void write(final HttpServletResponse response, final T result) {
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json;charset=utf-8");
    try {
      response.getWriter().write(JacksonTools.jsonify(result));
    } catch (IOException e) {
      log.error("Response write errored [ {} ]", e.getMessage());
    }
  }
}
