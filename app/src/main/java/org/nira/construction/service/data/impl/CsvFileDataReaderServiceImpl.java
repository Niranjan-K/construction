package org.nira.construction.service.data.impl;

import org.nira.construction.exception.ConstructionException;
import org.nira.construction.model.ConstructionInfo;
import org.nira.construction.service.data.DataReaderService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CsvFileDataReaderServiceImpl implements DataReaderService {

  private static final String COMMA = ",";

  @Override
  public List<ConstructionInfo> readFile(String filename) {
    Pattern pattern = Pattern.compile(COMMA);
    List<ConstructionInfo> constructionInfoList = new ArrayList<>();
    try (Stream<String> constructionInfoStream = Files.lines((Paths.get(ClassLoader.getSystemResource(filename).toURI())))) {
      constructionInfoStream.forEach(constructionInfoString ->
          constructionInfoList.add(getConstructionInfo(pattern.split(constructionInfoString))));
      return constructionInfoList;
    } catch (NullPointerException | IOException e) {
      throw new ConstructionException("error reading file");
    } catch (Exception e) {
      throw new ConstructionException(e.getMessage());
    }
  }

  private ConstructionInfo getConstructionInfo(final String[] constructionInfoArray) {
    if (constructionInfoArray.length != 6) {
      throw new ConstructionException("invalid csv file structure");
    }
    return new ConstructionInfo(constructionInfoArray[0], constructionInfoArray[1], constructionInfoArray[2],
        constructionInfoArray[3], constructionInfoArray[4], constructionInfoArray[5]);
  }
}
