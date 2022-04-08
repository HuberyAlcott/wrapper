package dandelion.wrapper.builder;

import dandelion.wrapper.defines.GeneralException;
import dandelion.wrapper.defines.GeneralExecuted;
import dandelion.wrapper.defines.Paged;
import dandelion.wrapper.returns.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static dandelion.wrapper.enums.GeneralStatus.params_errored;
import static dandelion.wrapper.enums.GeneralStatus.process_succeed;
import static dandelion.wrapper.utilities.StringTools.generate;

/** @author Marcus */
@Slf4j
public class ResultBuilder {

  protected ResultBuilder() {}

  /**
   * The result of method return as default status
   *
   * @param backs attach values
   * @param <A> class of attach value
   * @return wrapped attach value
   */
  public static <A> MethodResult<A> functional(final Collection<A> backs) {
    final var target = new MethodResult<A>();
    target.setState(process_succeed);
    target.setBacks(backs);
    return target;
  }

  /**
   * The result of method return as default status
   *
   * @param state self-defined status
   * @param backs attach values
   * @param <A> class of attach value
   * @return wrapped attach value
   */
  public static <A> MethodResult<A> functional(
      final GeneralExecuted state, final Collection<A> backs) {
    final var target = new MethodResult<A>();
    target.setState(state);
    target.setBacks(backs);
    return target;
  }

  /**
   * For build {@link PagingResult} without status
   *
   * @param paged {@link Paged} common paged value
   * @param <A> type of paged value
   * @return wrapped values
   */
  public static <A> PagingResult<A> temporary(final Paged<A> paged) {
    final var target = new PagingResult<A>();
    target.setState(process_succeed);
    target.setPaged(paged);
    return target;
  }

  /**
   * For build {@link PagingResult} with status
   *
   * @param state self-defined status
   * @param paged {@link Paged} common paged value
   * @param <A> type of paged value
   * @return wrapped values
   */
  public static <A> PagingResult<A> temporary(final GeneralExecuted state, final Paged<A> paged) {
    final var target = new PagingResult<A>();
    target.setState(state);
    target.setPaged(paged);
    return target;
  }

  /* -------- finally proceed result------- */

  /**
   * The result for no attachment with default status
   *
   * @return {@link ServerProceed} wrapped result
   */
  public static ServerProceed finalized() {
    return finalized(process_succeed);
  }

  /**
   * The result for no attachment with self-defined status
   *
   * @param state self-default status
   * @return {@link ServerProceed} wrapped result
   */
  public static ServerProceed finalized(final GeneralExecuted state) {
    final var target = new ServerProceed();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    return target;
  }

  /**
   * The result for attachment with default status
   *
   * @param result wrapped with {@link MethodResult}
   * @param <A> class of attachment
   * @return {@link NormalProceed} wrapped result
   */
  public static <A> NormalProceed<A> finalized(final MethodResult<A> result) {
    return finalized(result.getState(), result.getBacks());
  }

  /**
   * The result for attachment with self-defined status
   *
   * @param state self-defined status
   * @param backs attachment value
   * @param <A> class of attachment
   * @return {@link NormalProceed} wrapped result
   */
  public static <A> NormalProceed<A> finalized(
      final GeneralExecuted state, final Collection<A> backs) {
    final var target = new NormalProceed<A>();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    target.setBack(backs);
    return target;
  }

  /**
   * The result for paged attachment with default status
   *
   * @param result paged attachment value
   * @param <A> class of attachment
   * @return {@link PagingResult} wrapped result
   */
  public static <A> PagingProceed<A> pageable(final PagingResult<A> result) {
    return pageable(result.getState(), result.getPaged());
  }

  /**
   * The result for paged attachment with default status
   *
   * @param paged paged attachment value
   * @param <A> class of attachment
   * @return {@link PagingResult} wrapped result
   */
  public static <A> PagingProceed<A> pageable(final Paged<A> paged) {
    return pageable(process_succeed, paged);
  }

  /**
   * The result for paged attachment with self-defined status
   *
   * @param paged paged attachment value
   * @param <A> class of attachment
   * @return {@link PagingResult} wrapped result
   */
  public static <A> PagingProceed<A> pageable(final GeneralExecuted state, final Paged<A> paged) {
    final var target = new PagingProceed<A>();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    target.setPage(paged.getPage());
    target.setSize(paged.getSize());
    target.setSkip(paged.getSkip());
    target.setSums(paged.getSums());
    target.setList(paged.getList());
    return target;
  }

  public static NormalProceed<String> exceptions(final BindingResult errors) {
    final NormalProceed<String> target = normal(params_errored);
    final var collect =
        errors.getAllErrors().parallelStream()
            .map(ResultBuilder::buildErrors)
            .collect(Collectors.toList());
    target.setBack(collect);
    return target;
  }

  public static NormalProceed<String> exceptions(final GeneralException errors) {
    final var state = errors.getState();
    final NormalProceed<String> target = normal(state);
    final var build = buildErrors(errors);
    target.setBack(build);
    return target;
  }

  private static <T> NormalProceed<T> normal(final GeneralExecuted state) {
    final var target = new NormalProceed<T>();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    return target;
  }

  private static List<String> buildErrors(final GeneralException errors) {
    final var state = errors.getState();
    final var desc = state.obtainDesc();
    final var message = desc + " >>> " + errors.getMessage();
    return List.of(message);
  }

  private static String buildErrors(final ObjectError errors) {
    return errors.getObjectName() + " : " + errors.getDefaultMessage();
  }
}
