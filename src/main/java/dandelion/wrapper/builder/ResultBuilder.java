package dandelion.wrapper.builder;

import dandelion.wrapper.defines.GeneralExecuted;
import dandelion.wrapper.defines.Paged;
import dandelion.wrapper.returns.PagingResult;
import dandelion.wrapper.returns.PlentyResult;
import dandelion.wrapper.returns.SingleResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static dandelion.wrapper.enums.GeneralStatus.process_succeed;

/**
 * @author Marcus
 */
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
  public static <A> SingleResult<A> functional(final A backs) {
    final var target = new SingleResult<A>();
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
  public static <A> SingleResult<A> functional(final GeneralExecuted state, final A backs) {
    final var target = new SingleResult<A>();
    target.setState(state);
    target.setBacks(backs);
    return target;
  }

  /**
   * The result of method return as default status
   *
   * @param backs attach values
   * @param <A> class of attach value
   * @return wrapped attach value
   */
  public static <A> PlentyResult<A> functional(final Collection<A> backs) {
    final var target = new PlentyResult<A>();
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
  public static <A> PlentyResult<A> functional(
      final GeneralExecuted state, final Collection<A> backs) {
    final var target = new PlentyResult<A>();
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
}
