package dandelion.wrapper.returns;

import dandelion.wrapper.builder.ResultBuilder;
import dandelion.wrapper.defines.GeneralException;
import dandelion.wrapper.defines.GeneralExecuted;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Collections;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/** @author Marcus */
public interface GeneralReturns {

  /**
   * wrap no args result
   *
   * @return {@link ServerProceed}
   */
  default ServerProceed wrapper() {
    return ResultBuilder.finalized();
  }

  /**
   * wrap no args result
   *
   * @param state status for return
   * @return {@link ServerProceed}
   */
  default ServerProceed wrapper(final GeneralExecuted state) {
    return ResultBuilder.finalized(state);
  }

  /**
   * wrap no args result
   *
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerProceed}
   */
  default <A> NormalProceed<A> wrapper(final Collection<A> attach) {
    return ResultBuilder.finalized(process_succeed, attach);
  }
  /**
   * wrap no args result
   *
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerProceed}
   */
  default <A> NormalProceed<A> wrapper(final A attach) {
    return ResultBuilder.finalized(process_succeed, Collections.singletonList(attach));
  }

  /**
   * wrap no args result
   *
   * @param state response status
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerProceed}
   */
  default <A> NormalProceed<A> wrapper(final GeneralExecuted state, final Collection<A> attach) {
    return ResultBuilder.finalized(state, attach);
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
    return ResultBuilder.finalized(state, Collections.singletonList(attach));
  }

  /**
   * wrap normalize result
   *
   * @param result {@link MethodResult <A>} defined wrapper
   * @param <A> type of attachment
   * @return {@link NormalProceed <A>}
   */
  default <A> NormalProceed<A> wrapper(final MethodResult<A> result) {
    return ResultBuilder.finalized(result);
  }

  /**
   * wrap pageable attach
   *
   * @param attach attachment object
   * @param <A> type of pageable attachment
   * @return {@link MethodResult <A>}
   */
  default <A> PagingProceed<A> wrapper(final PagingResult<A> attach) {
    return ResultBuilder.pageable(attach);
  }

  /**
   * wrap ProjectException
   *
   * @param errors {@link GeneralException} the exception
   * @return {@link NormalProceed <List<String>>}
   */
  default NormalProceed<String> wrapper(final GeneralException errors) {
    return ResultBuilder.exceptions(errors);
  }

  /**
   * wrap ProjectException
   *
   * @param errors {@link GeneralException} the exception
   * @return {@link NormalProceed <List<String>>}
   */
  default NormalProceed<String> wrapper(final BindingResult errors) {
    return ResultBuilder.exceptions(errors);
  }
}
