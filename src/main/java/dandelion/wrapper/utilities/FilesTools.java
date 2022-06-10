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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.*;

/**
 * @author Marcus
 */
@Slf4j
public class FilesTools {

  protected FilesTools() {}

  private static final String TMP_DIR = System.getProperty("java.io.tmpdir");
  private static final Charset CHARSET_GBK = Charset.forName("GBK");

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
        /*文件编码为 ANSI*/
        return CHARSET_GBK;
      } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
        /*文件编码为 Unicode*/
        charset = UTF_16LE;
        checked = true;
      } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
        /*文件编码为 Unicode big endian*/
        charset = UTF_16BE;
        checked = true;
      } else if (first3Bytes[0] == (byte) 0xEF
          && first3Bytes[1] == (byte) 0xBB
          && first3Bytes[2] == (byte) 0xBF) {
        /*文件编码为 UTF-8*/
        charset = UTF_8;
        checked = true;
      }
      bis.reset();
      if (!checked) {
        while ((read = bis.read()) != -1) {
          if (read >= 0xF0) {
            break;
          }
          if (0x80 <= read && read <= 0xBF)
          /*单独出现BF以下的，也算是GBK*/
          {
            break;
          }
          if (0xC0 <= read && read <= 0xDF) {
            read = bis.read();
            if (0x80 <= read && read <= 0xBF)
            /*双字节 (0xC0 - 0xDF)(0x80 - 0xBF),也可能在GB编码内*/
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
              }
              break;
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
    return saveFile(Path.of(TMP_DIR), source);
  }

  public static Path saveFile(final Path target, final MultipartFile source) {
    Assert.notNull(source, "The file is null");
    final var file = target.toFile();
    if (file.isDirectory()) {
      if (!file.exists()) {
        final var mkdir = file.mkdirs();
        log.warn("The path[ {} ]created ? {}", file, mkdir);
      }
    } else {
      log.error("Target path is not a directory !");
      return target;
    }
    final var full = obtainFullName(source);
    final var temp = Path.of(target.toString(), full);
    try {
      final var bytes = source.getBytes();
      final var write = Files.write(temp, bytes);
      log.warn("File[ {} ]wrote done", write);
      return write;
    } catch (IOException error) {
      log.error("Error write file [ {} ] ,cause {}", temp, error.getMessage(), error);
      return target;
    }
  }

  public static List<Path> saveFiles(final MultipartFile... source) {
    final var path = Path.of(TMP_DIR);
    return Arrays.stream(source)
        .map(file -> saveFile(path, file))
        .filter(val -> !val.equals(path))
        .toList();
  }

  public static List<Path> saveFiles(final Path target, final MultipartFile... source) {
    Assert.notNull(target, "The save path is null");
    return Arrays.stream(source)
        .map(file -> saveFile(target, file))
        .filter(val -> !val.equals(target))
        .toList();
  }

  private static String obtainFullName(final MultipartFile source) {
    Assert.notNull(source, "The source file is null");
    final var name = source.getOriginalFilename();
    if (!ObjectUtils.isEmpty(name)) {
      return name;
    }
    log.warn("The source file name is null, return UUID !");
    return UUID.randomUUID().toString().replace("-", "") + ".txt";
  }

  private static String obtainFileSubs(final MultipartFile source) {
    Assert.notNull(source, "The file is null");
    final var origin = source.getOriginalFilename();
    if (!ObjectUtils.isEmpty(origin)) {
      if (origin.contains(".")) {
        return origin.substring(origin.lastIndexOf('.') + 1);
      } else {
        return origin;
      }
    }
    return origin;
  }
}
