package dandelion.wrapper.builder;

import dandelion.wrapper.defines.GeneralException;
import dandelion.wrapper.defines.GeneralExecuted;
import dandelion.wrapper.defines.Paged;
import dandelion.wrapper.returns.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static dandelion.wrapper.enums.GeneralStatus.params_errored;
import static dandelion.wrapper.enums.GeneralStatus.process_succeed;
import static dandelion.wrapper.utilities.StringTools.generate;

/** @author Marcus */
@Slf4j
public class ResultBuilder {

  protected ResultBuilder() {}

  public static <A> MethodResult<A> functional(final A backs) {
    final var target = new MethodResult<A>();
    target.setState(process_succeed);
    target.setBacks(backs);
    return target;
  }

  public static <A> MethodResult<A> functional(final GeneralExecuted state, final A backs) {
    final var target = new MethodResult<A>();
    target.setState(state);
    target.setBacks(backs);
    return target;
  }

  public static <A> PagingResult<A> temporary(final Paged<A> paged) {
    final var target = new PagingResult<A>();
    target.setState(process_succeed);
    target.setPaged(paged);
    return target;
  }

  public static <A> PagingResult<A> temporary(final GeneralExecuted state, final Paged<A> paged) {
    final var target = new PagingResult<A>();
    target.setState(state);
    target.setPaged(paged);
    return target;
  }

  public static ServerResult finalized() {
    return finalized(process_succeed);
  }

  public static ServerResult finalized(final GeneralExecuted state) {
    final var target = new ServerResult();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    return target;
  }

  public static <A> NormalResult<A> finalized(final MethodResult<A> result) {
    return finalized(result.getState(), result.getBacks());
  }

  public static <A> NormalResult<A> finalized(final GeneralExecuted state, final A backs) {
    final var target = new NormalResult<A>();
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

  public static <A> PagedResult<A> pageable(final PagingResult<A> result) {
    return pageable(result.getState(), result.getPaged());
  }

  public static <A> PagedResult<A> pageable(final Paged<A> paged) {
    return pageable(process_succeed, paged);
  }

  public static <A> PagedResult<A> pageable(final GeneralExecuted state, final Paged<A> paged) {
    final var target = new PagedResult<A>();
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

  public static NormalResult<List<String>> exceptions(final BindingResult errors) {
    final var target = new NormalResult<List<String>>();
    target.setCode(params_errored.obtainCode());
    target.setDesc(params_errored.obtainDesc());
    target.setNote(params_errored.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    final var collect =
        errors.getAllErrors().parallelStream()
            .map(ResultBuilder::buildErrors)
            .collect(Collectors.toList());
    target.setBack(collect);
    return target;
  }

  public static NormalResult<List<String>> exceptions(final GeneralException errors) {
    final var state = errors.getState();
    final var target = new NormalResult<List<String>>();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    final var build = buildErrors(errors);
    target.setBack(build);
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
