package dandelion.wrapper.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author Marcus */
@Slf4j
public final class JacksonTools {

  private JacksonTools() {}

  public static <T> String jsonify(final T object) {
    final ObjectMapper mapper = new ObjectMapper();
    return jsonify(object, mapper);
  }

  private static <T> String jsonify(T object, ObjectMapper mapper) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("'{}'不能转换成JSON字符串", object.getClass().getName());
      return "{}";
    }
  }

  public static <T> String jsonify(final List<T> list) {
    final ObjectMapper mapper = new ObjectMapper();
    return jsonify(list, mapper);
  }

  public static <T> String jsonify(final List<T> object, final ObjectMapper mapper) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("目标数组不能转换成JSON字符串");
      return "[]";
    }
  }

  public static <T> String sorted(final T object) {
    final ObjectMapper mapper =
        new ObjectMapper().configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    return sorted(object, mapper);
  }

  public static <T> String sorted(final T object, final ObjectMapper mapper) {
    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "{}";
    }
  }

  public static <T> T parse(final String json, final Class<T> clazz) {
    final ObjectMapper mapper = new ObjectMapper();
    return parse(json, clazz, mapper);
  }

  public static <T> T parse(final String json, final Class<T> clazz, final ObjectMapper mapper) {
    try {
      return mapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      log.error("目标数组不能转换成JSON字符串");
      return null;
    }
  }

  public static <T> List<T> parse(final String json, final CollectionType type) {
    final ObjectMapper mapper = new ObjectMapper();
    return parse(json, type, mapper);
  }

  public static <T> List<T> parse(
      final String json, final CollectionType type, final ObjectMapper mapper) {
    try {
      return mapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      log.error("目标数组不能转换成JSON字符串");
      return new ArrayList<>();
    }
  }

  public static <O extends Collection<O>, I> List<I> parse(
      final String json, final Class<O> outer, final Class<I> inner) {
    final ObjectMapper mapper = new ObjectMapper();
    return parse(json, outer, inner, mapper);
  }

  public static <O extends Collection<O>, I> List<I> parse(
      final String json, final Class<O> outer, final Class<I> inner, final ObjectMapper mapper) {
    try {
      CollectionType type = mapper.getTypeFactory().constructCollectionType(outer, inner);
      return mapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      log.error("目标数组不能转换成JSON字符串");
      return new ArrayList<>();
    }
  }

  public static String property(final String json, final String prop) {
    final ObjectMapper mapper = new ObjectMapper();
    return property(json, prop, mapper);
  }

  public static String property(final String json, final String prop, final ObjectMapper mapper) {
    try {
      return mapper.readTree(json).get(prop).toString();
    } catch (JsonProcessingException e) {
      log.error("目标数组不能转换成JSON字符串");
      return "";
    }
  }
}
