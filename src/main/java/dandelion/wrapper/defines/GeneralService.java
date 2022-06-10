package dandelion.wrapper.defines;

import dandelion.wrapper.builder.ResultBuilder;
import dandelion.wrapper.returns.PagingResult;
import dandelion.wrapper.returns.PlentyResult;
import dandelion.wrapper.returns.SingleResult;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/**
 * GeneralService
 *
 * @author Marcus
 */
public interface GeneralService {

  /**
   * 封装对象类型返回值结果,默认状态
   *
   * @param attach 返回值结果
   * @param <A> 返回值类型
   * @return {@link SingleResult }
   */
  default <A> SingleResult<A> attach(final A attach) {
    return ResultBuilder.functional(attach);
  }

  /**
   * 封装对象类型返回值,指定状态
   *
   * @param state 状态值
   * @param attach 返回值
   * @param <A> 返回值类型
   * @return {@link SingleResult }
   */
  default <A> SingleResult<A> attach(final GeneralExecuted state, final A attach) {
    return ResultBuilder.functional(state, attach);
  }

  /**
   * 封装集合类型返回值,默认状态
   *
   * @param attach 返回值
   * @param <A> 返回值类型
   * @return {@link PlentyResult}
   */
  default <A> PlentyResult<A> attach(final Collection<A> attach) {
    return ResultBuilder.functional(attach);
  }

  /**
   * 封装集合类型返回值,指定状态
   *
   * @param state 状态值
   * @param attach 返回值
   * @param <A> 返回值类型
   * @return {@link PlentyResult }
   */
  default <A> PlentyResult<A> attach(final GeneralExecuted state, final Collection<A> attach) {
    return ResultBuilder.functional(state, attach);
  }

  /**
   * 封装分页类型返回值,默认状态
   *
   * @param attach 返回值
   * @param <A> 返回值类型
   * @return {@link PagingResult }
   */
  default <A> PagingResult<A> paging(final Paged<A> attach) {
    return ResultBuilder.temporary(attach);
  }

  /**
   * 封装分页类型返回值,指定状态
   *
   * @param state 状态值
   * @param attach 返回值
   * @param <A> 返回值类型
   * @return {@link PagingResult }
   */
  default <A> PagingResult<A> paging(final GeneralExecuted state, final Paged<A> attach) {
    return ResultBuilder.temporary(state, attach);
  }

  /**
   * 判断通用返回值是否成功
   *
   * @param result 返回值
   * @param <B> 返回值类型
   * @return 是否成功
   */
  default <B> boolean judgement(SingleResult<B> result) {
    return !ObjectUtils.isEmpty(result) && judgement(result.getState());
  }

  /**
   * 判断通用返回值是否成功
   *
   * @param result 返回值
   * @param <B> 返回值类型
   * @return 是否成功
   */
  default <B> boolean judgement(PlentyResult<B> result) {
    return !ObjectUtils.isEmpty(result) && judgement(result.getState());
  }

  /**
   * 判断通用返回值是否成功
   *
   * @param result 返回值
   * @param <B> 返回值类型
   * @return 是否成功
   */
  default <B> boolean judgement(PagingResult<B> result) {
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
   * 将指定集合转换成另一个集合
   *
   * @param source 原始集合
   * @param method 转换方法
   * @param <T> 原始类型
   * @param <S> 转换类型
   * @return {@link List<T>}
   */
  default <T, S> List<T> collector(final Collection<S> source, final Function<S, T> method) {
    return source.parallelStream().map(method).collect(Collectors.toList());
  }
}
