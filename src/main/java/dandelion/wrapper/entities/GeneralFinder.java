package dandelion.wrapper.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/** @author Marcus */
@Data
@EqualsAndHashCode
public class GeneralFinder implements Serializable {

  private long currPage = 1;

  private long pageSize = 20;
}
