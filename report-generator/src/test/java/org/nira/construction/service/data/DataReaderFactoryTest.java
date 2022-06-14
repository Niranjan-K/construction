package org.nira.construction.service.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.nira.construction.exception.ConstructionException;
import org.nira.construction.model.ConstructionInfo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataReaderFactoryTest {

  @Mock
  DataReaderService dataReaderService;

  String filename = "data.csv";

  @Test
  void testReadFile_csvFileType_doesNotThrowException() {
    List<ConstructionInfo> constructionInfoList = new ArrayList<>();
    when(dataReaderService.readFile(filename)).thenReturn(constructionInfoList);
    assertDoesNotThrow(() -> DataReaderFactory.readFile("csv",filename));
  }

  @Test
  void testReadFile_unknownFileType_throwsConstructionException() {
    ConstructionException exception = assertThrows(ConstructionException.class,
        () -> DataReaderFactory.readFile("aa", filename));
    assertEquals("unknown file type, aa", exception.getMessage());
  }
}
