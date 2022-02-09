package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/** @author Marcus */
@Slf4j
public final class ConvertTools {

  private ConvertTools() {}

  public static <T, S> List<T> convertCollection(
      final Collection<S> source, final Function<S, T> method) {
    return source.parallelStream().map(method).collect(Collectors.toList());
  }
}
