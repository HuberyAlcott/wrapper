package dandelion.wrapper.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件后缀对应的文件类型
 *
 * @author Marcus
 */
@Getter
@AllArgsConstructor
public enum FilesType {
  /***/
  _texts("文本类型") {
    @Override
    public String[] suffix() {
      return new String[] {".txt"};
    }
  },
  _jsons("文本类型") {
    @Override
    public String[] suffix() {
      return new String[] {".json"};
    }
  },
  _image("图片类型") {
    @Override
    public String[] suffix() {
      return new String[] {};
    }
  },
  _music("音乐类型") {
    @Override
    public String[] suffix() {
      return new String[] {};
    }
  },
  _video("视频类型") {
    @Override
    public String[] suffix() {
      return new String[] {};
    }
  },
  _words("办公文件") {
    @Override
    public String[] suffix() {
      return new String[] {};
    }
  },
  ;

  private final String note;

  public abstract String[] suffix();
}
