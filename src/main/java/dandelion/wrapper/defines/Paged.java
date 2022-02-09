package dandelion.wrapper.defines;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/** @author Marcus */
@Data
@EqualsAndHashCode
public class Paged<E> implements Serializable {

  private Long page;

  private Long size;

  private Long skip;

  private Long sums;

  private Collection<E> list;

  public static <E> Paged<E> empty() {
    final Paged<E> target = new Paged<>();
    target.page = 1L;
    target.size = 20L;
    target.skip = 0L;
    target.sums = 0L;
    target.list = new LinkedList<>();
    return target;
  }
}
