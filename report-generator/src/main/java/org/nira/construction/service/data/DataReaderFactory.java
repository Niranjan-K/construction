package org.nira.construction.service.data;

import org.nira.construction.exception.ConstructionException;
import org.nira.construction.model.ConstructionInfo;
import org.nira.construction.service.data.impl.CsvFileDataReaderServiceImpl;

import java.util.List;

public class DataReaderFactory {

  public static List<ConstructionInfo> readFile(String fileType, String filename) {
    DataReaderService dataReaderService;
    switch (fileType) {
      case "csv":
        dataReaderService = new CsvFileDataReaderServiceImpl();
        break;
      default:
        throw new ConstructionException("unknown file type, " + fileType);
    }
    return dataReaderService.readFile(filename);
  }
}
