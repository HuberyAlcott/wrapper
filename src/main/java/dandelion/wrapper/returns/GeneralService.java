package dandelion.wrapper.returns;

import dandelion.wrapper.builder.ResultBuilder;
import dandelion.wrapper.defines.GeneralExecuted;
import dandelion.wrapper.defines.Paged;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/** @author Marcus */
public interface GeneralService {

  /**
   * wrap normal attach
   *
   * @param attach attachment object
   * @param <A> type of attachment
   * @return {@link MethodResult <A>}
   */
  default <A> MethodResult<A> attach(final A attach) {
    return ResultBuilder.functional(attach);
  }

  /**
   * wrap pageable attach
   *
   * @param attach attachment object
   * @param <A> type of pageable attachment
   * @return {@link MethodResult <A>}
   */
  default <A> PagingResult<A> attach(final Paged<A> attach) {
    return ResultBuilder.temporary(attach);
  }

  /**
   * wrap pageable attach
   *
   * @param state processed status
   * @param attach attachment object
   * @param <A> type of pageable attachment
   * @return {@link MethodResult <A>}
   */
  default <A> MethodResult<A> attach(final GeneralExecuted state, final A attach) {
    return ResultBuilder.functional(state, attach);
  }

  /**
   * wrap pageable attach
   *
   * @param state processed status
   * @param attach attachment object
   * @param <A> type of pageable attachment
   * @return {@link MethodResult <A>}
   */
  default <A> PagingResult<A> attach(final GeneralExecuted state, final Paged<A> attach) {
    return ResultBuilder.temporary(state, attach);
  }

  /**
   * 判断通用返回值是否成功
   *
   * @param result 返回值
   * @param <B> 返回值类型
   * @return 是否成功
   */
  default <B> boolean judgement(MethodResult<B> result) {
    return !ObjectUtils.isEmpty(result) && judgement(result.getState());
  }

  /**
   * 判断通用返回值是否成功
   *
   * @param status 返回值
   * @return 是否成功
   */
  default boolean judgement(GeneralExecuted status) {
    return !ObjectUtils.isEmpty(status) && judgement(status.obtainCode());
  }

  /**
   * 判断通用返回值是否成功
   *
   * @param status 返回值
   * @return 是否成功
   */
  default boolean judgement(String status) {
    return !ObjectUtils.isEmpty(status) && process_succeed.obtainCode().equals(status);
  }

  /**
   * convert a list to other list
   *
   * @param source the source collection
   * @param method convert function method
   * @param <T> type of source object
   * @param <S> type of target object
   * @return {@link List<T>}
   */
  default <T, S> List<T> convertCollection(
      final Collection<S> source, final Function<S, T> method) {
    return source.parallelStream().map(method).collect(Collectors.toList());
  }
}
