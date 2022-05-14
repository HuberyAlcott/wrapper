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
}
