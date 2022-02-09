package dandelion.wrapper.returns;

import dandelion.wrapper.builder.ResultBuilder;
import dandelion.wrapper.defines.GeneralException;
import dandelion.wrapper.defines.GeneralExecuted;
import org.springframework.validation.BindingResult;

import java.util.List;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/** @author Marcus */
public interface GeneralReturns {

  /**
   * wrap no args result
   *
   * @return {@link ServerResult}
   */
  default ServerResult wrapper() {
    return ResultBuilder.finalized();
  }

  /**
   * wrap no args result
   *
   * @param state status for return
   * @return {@link ServerResult}
   */
  default ServerResult wrapper(final GeneralExecuted state) {
    return ResultBuilder.finalized(state);
  }

  /**
   * wrap no args result
   *
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerResult}
   */
  default <A> NormalResult<A> wrapper(final A attach) {
    return ResultBuilder.finalized(process_succeed, attach);
  }

  /**
   * wrap no args result
   *
   * @param state response status
   * @param attach response attachment
   * @param <A> type of attachment
   * @return {@link ServerResult}
   */
  default <A> NormalResult<A> wrapper(final GeneralExecuted state, final A attach) {
    return ResultBuilder.finalized(state, attach);
  }

  /**
   * wrap normalize result
   *
   * @param result {@link MethodResult <A>} defined wrapper
   * @param <A> type of attachment
   * @return {@link NormalResult<A>}
   */
  default <A> NormalResult<A> wrapper(final MethodResult<A> result) {
    return ResultBuilder.finalized(result);
  }

  /**
   * wrap pageable attach
   *
   * @param attach attachment object
   * @param <A> type of pageable attachment
   * @return {@link MethodResult <A>}
   */
  default <A> PagedResult<A> wrapper(final PagingResult<A> attach) {
    return ResultBuilder.pageable(attach);
  }

  /**
   * wrap ProjectException
   *
   * @param errors {@link GeneralException} the exception
   * @return {@link NormalResult<List<String>>}
   */
  default NormalResult<List<String>> wrapper(final GeneralException errors) {
    return ResultBuilder.exceptions(errors);
  }

  /**
   * wrap ProjectException
   *
   * @param errors {@link GeneralException} the exception
   * @return {@link NormalResult<List<String>>}
   */
  default NormalResult<List<String>> wrapper(final BindingResult errors) {
    return ResultBuilder.exceptions(errors);
  }
}
