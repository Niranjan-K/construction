package org.nira.construction.service.data.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nira.construction.exception.ConstructionException;
import org.nira.construction.model.ConstructionInfo;
import org.nira.construction.service.data.DataReaderService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CsvFileDataReaderServiceImplTest {

  DataReaderService dataReaderService;

  @BeforeEach
  public void setup() throws IOException {
    dataReaderService = new CsvFileDataReaderServiceImpl();
  }

  @Test
  void testReadFile_withValidCsvFile_success() {
    List<ConstructionInfo> constructionInfoList = dataReaderService.readFile("data.csv");
    assertEquals(5, constructionInfoList.size());
  }

  @ParameterizedTest
  @CsvSource(value = {
      "invalid_csv_file.csv, invalid csv file structure",
      "abc.csv, error reading file",
      "invalid_contract_id.csv, invalid data for Contract Id",
      "invalid_customer_id.csv, invalid data for Customer Id",
      "invalid_build_duration_without_s.csv, invalid data for Build Duration",
      "invalid_number_build_duration.csv, invalid data for Build Duration",
      "empty_geozone.csv, invalid data for Geozone",
      "empty_team_code.csv, invalid data for Team Code",
      "empty_project_code.csv, invalid data for Project Code"
  })
  void testReadFile_withInvalidCsvFile_throwConstructionException(String filename, String errorMessage) {
    ConstructionException exception = assertThrows(ConstructionException.class,
        () -> dataReaderService.readFile(filename));
    assertEquals(errorMessage, exception.getMessage());
  }
}
