package ru.otus.hw17.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
@Configuration
public class DiskSpaceHealthIndicator extends AbstractHealthIndicator {

  private final FileStore fileStore;
  private final long thresholdBytes;

  @Autowired
  public DiskSpaceHealthIndicator(@Value("${health.filestore.path:${user.dir}}") String path,
                                  @Value("${health.filestore.threshold.bytes:10485760}") long thresholdBytes) throws IOException {
    fileStore = Files.getFileStore(Paths.get(path));
    this.thresholdBytes = thresholdBytes;
  }

  @Override
  protected void doHealthCheck(Health.Builder builder) throws Exception {
    long diskFreeInBytes = fileStore.getUnallocatedSpace();
    long totalSpaceInBytes = fileStore.getTotalSpace();
    Map<String, Long> stringLongMap = new HashMap<>();
    stringLongMap.put("diskFreeInBytes", diskFreeInBytes);
    stringLongMap.put("thresholdBytes", thresholdBytes);
    stringLongMap.put("totalSpaceInBytes", totalSpaceInBytes);
    if (diskFreeInBytes >= thresholdBytes) {
      builder.up().withDetails(stringLongMap);
    } else {
      builder.down().withDetails(stringLongMap);
    }
  }
}
