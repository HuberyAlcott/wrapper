package dandelion.wrapper.defines;

import java.io.Serializable;

/** @author Marcus */
public interface CommonExecuted<C, D, N> extends Serializable {

  /**
   * define code
   *
   * @return code
   */
  C obtainCode();

  /**
   * define desc
   *
   * @return desc
   */
  D obtainDesc();

  /**
   * define note
   *
   * @return note
   */
  N obtainNote();
}
