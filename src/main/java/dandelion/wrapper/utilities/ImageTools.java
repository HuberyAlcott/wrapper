package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Marcus
 */
@Slf4j
public final class ImageTools extends FilesTools {

  private ImageTools() {}

  /** 生成随机颜色 */
  private static Color colors() {
    final var random = ThreadLocalRandom.current();
    return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
  }

  public static BufferedImage image(final String code, final int width, final int height) {
    final var length = code.length();
    final var target = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    /*获取图形上下文*/
    final var graphics = target.getGraphics();
    /*画背景图*/
    graphics.setColor(colors());
    graphics.fillRect(0, 0, width, height);
    /*画干扰线*/
    final var random = ThreadLocalRandom.current();
    if (length > 0) {
      int x0 = 0, y0, y1;
      for (int i = 0; i < length; i++) {
        graphics.setColor(colors());
        y0 = random.nextInt(height);
        y1 = random.nextInt(height);
        graphics.drawLine(x0, y0, width, y1);
      }
    }
    /*字体大小为图片高度的80%*/
    int fSize = (int) (height * 0.8);
    int fx = height - fSize;
    int fy;
    /*设定字体*/
    graphics.setFont(new Font("Default", Font.PLAIN, fSize));
    /*写验证码字符*/
    final var chars = code.toCharArray();
    for (char curr : chars) {
      fy = Double.valueOf((Math.random() * 0.3 + 0.6) * height).intValue();
      graphics.setColor(colors());
      /*将验证码字符显示到图象中*/
      graphics.drawString(String.valueOf(curr), fx, fy);
      fx += fSize * 0.9;
    }

    graphics.dispose();
    return target;
  }

  /**
   * 已有验证码,生成验证码图片
   *
   * @param textCode 文本验证码
   * @param width 图片宽度(注意此宽度若过小,容易造成验证码文本显示不全,如4个字符的文本可使用85到90的宽度)
   * @param height 图片高度
   * @param interLine 图片中干扰线的条数
   * @param randomLocation 每个字符的高低位置是否随机
   * @param backColor 图片颜色,若为null则表示采用随机颜色
   * @param foreColor 字体颜色,若为null则表示采用随机颜色
   * @param lineColor 干扰线颜色,若为null则表示采用随机颜色
   * @return 图片缓存对象
   */
  private static BufferedImage generateImageCode(
      String textCode,
      int width,
      int height,
      int interLine,
      boolean randomLocation,
      Color backColor,
      Color foreColor,
      Color lineColor) {
    /*创建内存图像*/
    final var bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    /*获取图形上下文*/
    final var graphics = bufferedImage.getGraphics();
    /*画背景图*/
    graphics.setColor(null == backColor ? colors() : backColor);
    graphics.fillRect(0, 0, width, height);
    /*画干扰线*/
    final var random = ThreadLocalRandom.current();
    if (interLine > 0) {
      int x = 0, y, y1;
      for (int i = 0; i < interLine; i++) {
        graphics.setColor(null == lineColor ? colors() : lineColor);
        y = random.nextInt(height);
        y1 = random.nextInt(height);
        graphics.drawLine(x, y, width, y1);
      }
    }
    /*字体大小为图片高度的80%*/
    int fsize = (int) (height * 0.8);
    int fx = height - fsize;
    int fy = fsize;
    /*设定字体*/
    graphics.setFont(new Font("Default", Font.PLAIN, fsize));
    /*写验证码字符*/
    for (int i = 0; i < textCode.length(); i++) {
      fy = randomLocation ? (int) ((Math.random() * 0.3 + 0.6) * height) : fy;
      graphics.setColor(null == foreColor ? colors() : foreColor);
      /*将验证码字符显示到图象中*/
      graphics.drawString(textCode.charAt(i) + "", fx, fy);
      fx += fsize * 0.9;
    }
    graphics.dispose();
    return bufferedImage;
  }
}
