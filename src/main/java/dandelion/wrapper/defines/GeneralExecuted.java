package dandelion.wrapper.defines;

/** @author Marcus */
public interface GeneralExecuted extends CommonExecuted<String, String, String> {

  /**
   * define returned code
   *
   * @return code
   */
  @Override
  String obtainCode();

  /**
   * define returned desc
   *
   * @return desc
   */
  @Override
  String obtainDesc();

  /**
   * define returned note
   *
   * @return note
   */
  @Override
  String obtainNote();
}
