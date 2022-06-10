package dandelion.wrapper.builder;

import dandelion.wrapper.defines.GeneralException;
import dandelion.wrapper.defines.GeneralExecuted;
import dandelion.wrapper.defines.Paged;
import dandelion.wrapper.returns.NormalProceed;
import dandelion.wrapper.returns.PagingProceed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

import static dandelion.wrapper.enums.GeneralStatus.params_errored;
import static dandelion.wrapper.utilities.StringTools.generate;

/**
 * @author Marcus
 */
@Slf4j
public final class ProceedBuilder {

  private ProceedBuilder() {}

  public static <T> NormalProceed<T> normal(final GeneralExecuted state) {
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

  public static NormalProceed<String> errors(final GeneralException error) {
    final var state = error.getState();
    final var target = new NormalProceed<String>();
    target.setCode(state.obtainCode());
    target.setDesc(state.obtainDesc());
    target.setNote(state.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    target.setBack(buildErrors(error));
    return target;
  }

  public static NormalProceed<String> errors(final BindingResult error) {
    final var target = new NormalProceed<String>();
    target.setCode(params_errored.obtainCode());
    target.setDesc(params_errored.obtainDesc());
    target.setNote(params_errored.obtainNote());
    target.setMark(generate(true));
    target.setCost(0.1f);
    target.setZone(ZoneId.systemDefault().getId());
    target.setTime(System.currentTimeMillis());
    target.setBack(buildErrors(error));
    return target;
  }

  public static <T> NormalProceed<T> attach(
      final GeneralExecuted state, final Collection<T> backs) {
    final var target = new NormalProceed<T>();
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

  public static <A> PagingProceed<A> paging(final GeneralExecuted state, final Paged<A> paged) {
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

  private static List<String> buildErrors(final GeneralException errors) {
    final var state = errors.getState();
    final var desc = state.obtainDesc();
    final var message = desc + " >>> " + errors.getMessage();
    return List.of(message);
  }

  private static List<String> buildErrors(final BindingResult errors) {
    final var list = errors.getAllErrors();
    return list.parallelStream()
        .map(error -> error.getObjectName() + " : " + error.getDefaultMessage())
        .toList();
  }
}
