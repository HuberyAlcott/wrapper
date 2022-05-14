package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Marcus
 */
@Slf4j
public class FilesTools {

  private static final String TMP_DIR = System.getProperty("user.dir");
  private static final Charset CHARSET_GBK = Charset.forName("GBK");
  private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

  public static Charset fileCharset(final Path path) {
    Charset charset = CHARSET_GBK;
    byte[] first3Bytes = new byte[3];
    try {
      boolean checked = false;
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path.toFile()));
      /*读者注： bis.mark(0);修改为 bis.mark(100);我用过这段代码，需要修改上面标出的地方。*/
      bis.mark(0);
      int read = bis.read(first3Bytes, 0, 3);
      if (read == -1) {
        bis.close();
        // 文件编码为 ANSI
        return CHARSET_GBK;
      } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
        // 文件编码为 Unicode
        charset = StandardCharsets.UTF_16LE;
        checked = true;
      } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
        // 文件编码为 Unicode big endian
        charset = StandardCharsets.UTF_16BE;
        checked = true;
      } else if (first3Bytes[0] == (byte) 0xEF
          && first3Bytes[1] == (byte) 0xBB
          && first3Bytes[2] == (byte) 0xBF) {
        // 文件编码为 UTF-8
        charset = StandardCharsets.UTF_8;
        checked = true;
      }
      bis.reset();
      if (!checked) {
        while ((read = bis.read()) != -1) {
          if (read >= 0xF0) {
            break;
          }
          if (0x80 <= read && read <= 0xBF)
          // 单独出现BF以下的，也算是GBK
          {
            break;
          }
          if (0xC0 <= read && read <= 0xDF) {
            read = bis.read();
            if (0x80 <= read && read <= 0xBF)
            // 双字节 (0xC0 - 0xDF)
            // (0x80 - 0xBF),也可能在GB编码内
            {
            } else {
              break;
            }
          } else if (0xE0 <= read) {
            read = bis.read();
            if (0x80 <= read && read <= 0xBF) {
              read = bis.read();
              if (0x80 <= read && read <= 0xBF) {
                charset = StandardCharsets.UTF_8;
                break;
              } else {
                break;
              }
            } else {
              break;
            }
          }
        }
      }
      bis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("File [ {} ] encoding is [ {} ]", path, charset);
    return charset;
  }

  public static Path saveFile(final MultipartFile source) {
    Assert.notNull(source, "The file is null");
    try {
      final var bytes = source.getBytes();
      final var name = obtainFilename(source);
      final var type = obtainFiletype(source);
      final var full = name + type;
      final var temp = Path.of(TMP_DIR, full);
      return Files.write(temp, bytes);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static String obtainFilename(final MultipartFile source) {
    return UUID.randomUUID().toString();
  }

  private static String obtainFiletype(final MultipartFile source) {
    Assert.notNull(source, "The file is null");
    final var origin = source.getOriginalFilename();
    if (!ObjectUtils.isEmpty(origin)) {
      final var suffix = origin.substring(origin.lastIndexOf('.') + 1);
    }
    return null;
  }
}
