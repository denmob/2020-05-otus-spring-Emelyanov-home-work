package ru.otus.hw02.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.core.reader.CsvReader;

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
    dataReader = new CsvReader("questions.csv",CVS_SPLIT_BY);
    assertThat(dataReader).isNotNull();
    assertThat(dataReader.getData()).isNotNull();
  }

  @Test
  @DisplayName("create with null")
  void createCsvReaderNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      dataReader = new CsvReader(null,CVS_SPLIT_BY);
    });
  }

  @Test
  @DisplayName("create with empty")
  void createCsvReaderEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      dataReader = new CsvReader("",CVS_SPLIT_BY);
    });
  }

  @Test
  @DisplayName("create with random")
  void createCsvReaderRandom() {
    assertThrows(IllegalStateException.class, () -> {
      dataReader = new CsvReader("tytry",CVS_SPLIT_BY);
      dataReader.getData();
    });
  }

  @Test
  @DisplayName("getData with empty csv")
  void getDataEmpty() {
    dataReader = new CsvReader("questionsEmpty.csv",CVS_SPLIT_BY);
    assertThat(dataReader.getData()).isEmpty();
  }

  @Test
  @DisplayName("getData with 5 lines csv")
  void getDataCorrect() {
    dataReader = new CsvReader("questions.csv",CVS_SPLIT_BY);
    assertThat(dataReader.getData()).isNotEmpty();
    assertThat(dataReader.getData()).hasSize(5);
  }
}
