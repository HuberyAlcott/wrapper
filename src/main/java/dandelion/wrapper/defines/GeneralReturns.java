package dandelion.wrapper.defines;

import dandelion.wrapper.builder.ProceedBuilder;
import dandelion.wrapper.returns.*;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/**
 * GeneralReturns
 *
 * @author Marcus
 */
public interface GeneralReturns {

  /**
   * wrap no args result
   *
   * @return {@link ServerProceed}
   */
  default ServerProceed wrapper() {
    return ProceedBuilder.normal(process_succeed);
  }

  /**
   * wrap no args result
   *
   * @param state status for return
   * @return {@link ServerProceed}
   */
  default ServerProceed wrapper(final GeneralExecuted state) {
    return ProceedBuilder.normal(state);
  }

  /**
   * wrap no args result
   *
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerProceed}
   */
  default <A> NormalProceed<A> wrapper(final Collection<A> attach) {
    return ProceedBuilder.attach(process_succeed, attach);
  }
  /**
   * wrap no args result
   *
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerProceed}
   */
  default <A> NormalProceed<A> wrapper(final A attach) {
    return ProceedBuilder.attach(process_succeed, Collections.singletonList(attach));
  }

  /**
   * wrap no args result
   *
   * @param state response status
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerProceed}
   */
  default <A> NormalProceed<A> wrapper(final GeneralExecuted state, final A attach) {
    return ProceedBuilder.attach(state, Collections.singletonList(attach));
  }

  /**
   * wrap no args result
   *
   * @param state response status
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link NormalProceed}
   */
  default <A> NormalProceed<A> wrapper(final GeneralExecuted state, final Collection<A> attach) {
    return ProceedBuilder.attach(state, attach);
  }

  /**
   * wrap normalize result
   *
   * @param result {@link SingleResult <A>} defined wrapper
   * @param <A> type of attachment
   * @return {@link NormalProceed}
   */
  default <A> NormalProceed<A> wrapper(final SingleResult<A> result) {
    return ProceedBuilder.attach(result.getState(), List.of(result.getBacks()));
  }

  /**
   * wrap normalize result
   *
   * @param result {@link SingleResult <A>} defined wrapper
   * @param <A> type of attachment
   * @return {@link NormalProceed}
   */
  default <A> NormalProceed<A> wrapper(final PlentyResult<A> result) {
    return ProceedBuilder.attach(result.getState(), result.getBacks());
  }

  /**
   * wrap pageable attach
   *
   * @param attach attachment object
   * @param <A> type of pageable attachment
   * @return {@link PagingProceed <A>}
   */
  default <A> PagingProceed<A> wrapper(final PagingResult<A> attach) {
    return ProceedBuilder.paging(attach.getState(), attach.getPaged());
  }

  /**
   * wrap ProjectException
   *
   * @param errors {@link GeneralException} the exception
   * @return {@link NormalProceed <List<String>>}
   */
  default NormalProceed<String> wrapper(final GeneralException errors) {
    return ProceedBuilder.errors(errors);
  }

  /**
   * wrap ProjectException
   *
   * @param errors {@link GeneralException} the exception
   * @return {@link NormalProceed <List<String>>}
   */
  default NormalProceed<String> wrapper(final BindingResult errors) {
    return ProceedBuilder.errors(errors);
  }
}
