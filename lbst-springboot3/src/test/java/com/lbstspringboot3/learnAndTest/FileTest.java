package com.lbstspringboot3.learnAndTest;

import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class FileTest {

  @Test
  public void fileReaderTest() {
    Path f1 = Path.of("C:\\Users\\i64coder\\Desktop\\Test\\data", "img.jpg");
    Path f2 =
        Path.of("C:\\Users\\i64coder\\Desktop\\Test\\data", "img-copy.jpg");

    byte[] data = FileUtil.readBytes(f1.toFile());
    FileUtil.writeBytes(data, f2.toFile());
  }
}
