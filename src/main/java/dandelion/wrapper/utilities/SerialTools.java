package dandelion.wrapper.utilities;

/** @author Marcus */
public final class SerialTools {

  public static class Snow {

    /** 开始时间截 (2015-01-01) */
    private static final long START_TIMESTAMP = 1420041600000L;

    /** 机器id所占的位数 Machine placeholder */
    private static final long MACHINE_SERIAL_HOLDER = 5L;

    /** 数据标识id所占的位数 */
    private static final long DATA_SERIAL_HOLDER = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private static final long MAX_WORKER_ID = ~(-1L << MACHINE_SERIAL_HOLDER);

    /** 支持的最大数据标识id，结果是31 */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_SERIAL_HOLDER);

    /** 序列在id中占的位数 */
    private static final long SERIAL_ID_HOLDER = 12L;

    /** 机器ID向左移12位 */
    private static final long WORKER_ID_SHIFT = SERIAL_ID_HOLDER;

    /** 数据标识id向左移17位(12+5) */
    private static final long DATA_CENTER_ID_SHIFT = SERIAL_ID_HOLDER + MACHINE_SERIAL_HOLDER;

    /** 时间截向左移22位(5+5+12) */
    private static final long TIMESTAMP_LEFT_SHIFT =
        SERIAL_ID_HOLDER + MACHINE_SERIAL_HOLDER + DATA_SERIAL_HOLDER;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private static final long SEQUENCE_MASK = ~(-1L << SERIAL_ID_HOLDER);

    /** 工作机器ID(0~31) */
    private static long workerSerial;

    /** 数据中心ID(0~31) */
    private static long dataCenterSerial;

    /** 毫秒内序列(0~4095) */
    private static long sequence = 0L;

    /** 上次生成ID的时间截 */
    private static long lastTimestamp = -1L;

    /*==============================Constructors=====================================*/

    /**
     * 构造函数
     *
     * @param worker 工作ID (0~31)
     * @param center 数据中心ID (0~31)
     */
    public Snow(long worker, long center) {
      if (worker > MAX_WORKER_ID || worker < 0) {
        throw new IllegalArgumentException(
            String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
      }
      if (center > MAX_DATA_CENTER_ID || center < 0) {
        throw new IllegalArgumentException(
            String.format(
                "d center Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
      }
      workerSerial = worker;
      dataCenterSerial = center;
    }

    public Snow() {
      new Snow(MAX_WORKER_ID, MAX_DATA_CENTER_ID);
    }

    /*==============================Methods==========================================*/

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public static synchronized long next() {
      long timestamp = timeGen();

      /*如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常*/
      if (timestamp < lastTimestamp) {
        throw new RuntimeException(
            String.format(
                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                lastTimestamp - timestamp));
      }

      /*如果是同一时间生成的，则进行毫秒内序列*/
      if (lastTimestamp == timestamp) {
        sequence = (sequence + 1) & SEQUENCE_MASK;
        /*毫秒内序列溢出*/
        if (sequence == 0) {
          /*阻塞到下一个毫秒,获得新的时间戳*/
          timestamp = tilNextMillis(lastTimestamp);
        }
      } else {
        /*时间戳改变，毫秒内序列重置*/
        sequence = 0L;
      }

      /*上次生成ID的时间截*/
      lastTimestamp = timestamp;

      /*移位并通过或运算拼到一起组成64位的ID*/
      return ((timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT_SHIFT)
          | (dataCenterSerial << DATA_CENTER_ID_SHIFT)
          | (workerSerial << WORKER_ID_SHIFT)
          | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long tilNextMillis(long lastTimestamp) {
      long timestamp = timeGen();
      while (timestamp <= lastTimestamp) {
        timestamp = timeGen();
      }
      return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private static long timeGen() {
      return System.currentTimeMillis();
    }
  }

  public static class Time{}
}
