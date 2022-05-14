package dandelion.wrapper.utilities;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Marcus
 */
@Slf4j
public class ZipTools extends FilesTools {

  private static final int BUFFER_BYTES_LENGTH = 2048;

  public static boolean doZipPathTree(final Path dirPath, final Path zipPath) {
    byte[] bytes = new byte[0];
    final var dirFile = dirPath.toFile();
    final var zipFile = zipPath.toFile();
    if (!dirFile.isDirectory()) {
      log.info("Path [ {} ] is not a directory", dirPath);
      return false;
    }
    /*if (!zipFile.exists()) {
      log.info("Path [ {} ] is not a file", zipPath);
      return false;
    }*/
    class FileZipper extends SimpleFileVisitor<Path> {

      private ZipOutputStream stream;

      private Path source;

      public boolean doZip(final Path pathname, final Path filename) {
        final var zipFile = filename.toFile();
        if (zipFile.isDirectory()) {
          log.error("Filepath[ {} ]is a directory", filename);
          return false;
        }
        try (final var fileStream = new FileOutputStream(filename.toFile());
            final var zipStream = new ZipOutputStream(fileStream); ) {
          this.stream = zipStream;
          this.source = pathname;
          log.debug("Start file walk ...");
          Files.walkFileTree(source, this);
          return true;
        } catch (IOException error) {
          log.error("Error when do zip cause {}", error.getMessage(), error);
          return false;
        } finally {
          log.info("Zip operate done");
        }
      }

      /**
       * 遍历文件时执行方法
       *
       * @param file 执行路径
       * @param attrs 路径属性
       * @return 访问结果
       * @throws IOException IO异常
       */
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        final var path = source.relativize(file);
        /*将文件加入ZIP文件流*/
        stream.putNextEntry(new ZipEntry(path.toString()));
        /*读取文件字节写入ZIP文件流*/
        final var fileBytes = Files.readAllBytes(file);
        stream.write(fileBytes, 0, fileBytes.length);
        stream.closeEntry();
        return FileVisitResult.CONTINUE;
      }
    }
    return new FileZipper().doZip(dirPath, zipPath);
  }

  public static boolean doZipFilesToFile(final Path target, final Path... source) {
    final var zipFile = target.toFile();
    final var paths =
        Arrays.stream(source)
            .map(Path::toFile)
            .filter(File::isFile)
            .map(File::toPath)
            .collect(Collectors.toSet())
            .toArray(new Path[] {});
    try (final var fileStream = new FileOutputStream(zipFile);
        final var zipStream = new ZipOutputStream(fileStream); ) {
      for (Path path : paths) {
        final var name = path.getFileName().toString();
        zipStream.putNextEntry(new ZipEntry(name));
        final var bytes = Files.readAllBytes(path);
        zipStream.write(bytes);
        zipStream.closeEntry();
      }
      return true;
    } catch (IOException error) {
      log.error("File[ {} ]zip error cause {}", target, error.getMessage(), error);
      return false;
    } finally {
      log.info("File zip done !");
    }
  }

  public static boolean doUnZipFileToPath(final Path source, final Path target) {
    final var zipFile = source.toFile();
    final var dirFile = target.toFile();
    if (!zipFile.isFile()) {
      log.info("Path[ {} ] is not a file", zipFile);
      return false;
    }
    if (!dirFile.exists()) {
      log.warn("Path[ {} ]not exist", dirFile);
      final var mkdir = dirFile.mkdirs();
      log.warn("Path[ {} ]created ? {}", dirFile, mkdir);
    }
    try (final var fileStream = new FileInputStream(zipFile);
        final var zipStream = new ZipInputStream(fileStream); ) {
      ZipEntry entry;
      while ((entry = zipStream.getNextEntry()) != null) {
        final var name = entry.getName();
        final var path = Paths.get(target.toString(), name);
        /*ZipEntry包含中文名,需要先创建路径*/
        if (name.contains(File.separator)) {
          final var prov = path.getParent();
          final var file = prov.toFile();
          final var mkdir = file.mkdirs();
          log.warn("Path[ {} ]created ? {}", prov, mkdir);
        }

        final var file = path.toFile();
        if (file.isDirectory()) {
          log.debug("Zip entry is directory");
        } else {
          byte[] bytes = new byte[BUFFER_BYTES_LENGTH];
          int read;
          int sum = 0;
          final var stream = new FileOutputStream(file);
          final var buffer = new BufferedOutputStream(stream);
          while ((read = zipStream.read(bytes)) > 0) {
            sum += read;
            buffer.write(bytes, 0, read);
          }
          buffer.close();
          stream.close();
          log.debug("File[ {} ]wrote size {}!", path, sum);
        }
        zipStream.closeEntry();
      }
      return true;
    } catch (IOException error) {
      log.error("Path[ {} ]zip error. Cause {}", target, error.getMessage(), error);
      return false;
    } finally {
      log.info("File unzip done !");
    }
  }
}
