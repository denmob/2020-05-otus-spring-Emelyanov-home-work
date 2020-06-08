package ru.otus.hw02.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.core.reader.CsvReader;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvReaderTest {

  private DataReader dataReader;

  private static final String CVS_SPLIT_BY = ",";

  @BeforeEach
  void before() {
    dataReader = null;
  }

  @Test
  @DisplayName("create with questionsEmpty.csv")
  void createCsvReaderValid() {
    dataReader = new CsvReader(CVS_SPLIT_BY);
    assertThat(dataReader).isNotNull();
    assertThat(dataReader.getData("questions.csv")).isNotNull();
  }

  @Test
  @DisplayName("getData with null filename")
  void createCsvReaderGetNull() {
    assertThrows(IllegalArgumentException.class, () -> new CsvReader(CVS_SPLIT_BY).getData(null));
  }

  @Test
  @DisplayName("getData with empty filename")
  void createCsvReaderEmpty() {
    assertThrows(IllegalArgumentException.class, () -> new CsvReader(CVS_SPLIT_BY).getData(""));
  }

  @Test
  @DisplayName("getData with random fileName")
  void getDataRandomFileName() {
    assertThrows(IllegalStateException.class, () -> {
      new CsvReader(CVS_SPLIT_BY).getData("tytytry");
    });
  }

  @Test
  @DisplayName("getData with empty csv")
  void getDataEmpty() {
    dataReader = new CsvReader(CVS_SPLIT_BY);
    assertThat(dataReader.getData("questionsEmpty.csv")).isEmpty();
  }

  @Test
  @DisplayName("getData with 5 lines csv")
  void getDataCorrect() {
    Map<String, List<String>> data = new CsvReader(CVS_SPLIT_BY).getData("questions.csv");
    assertThat(data).isNotEmpty();
    assertThat(data).hasSize(5);
  }
}
