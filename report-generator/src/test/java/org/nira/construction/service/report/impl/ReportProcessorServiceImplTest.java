package org.nira.construction.service.report.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nira.construction.model.ConstructionInfo;
import org.nira.construction.service.report.ReportProcessorService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ReportProcessorServiceImplTest {

  ReportProcessorService reportProcessorService;
  private final PrintStream systemOut = System.out;
  private ByteArrayOutputStream byteArrayOutputStream;

  @BeforeEach
  public void setup() throws Exception {
    reportProcessorService = new ReportProcessorServiceImpl();
    byteArrayOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(byteArrayOutputStream));
  }

  @AfterEach
  public void reset() throws Exception {
    System.setOut(systemOut);
  }

  @Test
  void testProcess_validConstructionInfo_success() {
    reportProcessorService.process("console", createConstructionInfoList());

    assertTrue(byteArrayOutputStream.toString().contains("=====The number of unique customerId for each contractId====="));
    assertTrue(byteArrayOutputStream.toString().contains("Contract Id = \"2345\", Total number unique Customer Ids = \"3\""));
    assertTrue(byteArrayOutputStream.toString().contains("Contract Id = \"2346\", Total number unique Customer Ids = \"2\""));

    assertTrue(byteArrayOutputStream.toString().contains("=====The number of unique Customer Ids for each Geozone====="));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"eu_west\", Total number unique Customer Ids = \"2\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_west\", Total number unique Customer Ids = \"2\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_east\", Total number unique Customer Ids = \"3\""));

    assertTrue(byteArrayOutputStream.toString().contains("=========The Average build duration for each Geozone========="));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"eu_west\", Average Build Duration = \"4255s\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_west\", Average Build Duration = \"2216s\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_east\", Average Build Duration = \"3148s\""));

    assertTrue(byteArrayOutputStream.toString().contains("=======The list of unique Customer Ids for each Geozone======="));
    assertTrue(byteArrayOutputStream.toString().contains("Unique Customer Ids for Geozone, \"eu_west\", are:"));
    assertTrue(byteArrayOutputStream.toString().contains("- 3244132"));
    assertTrue(byteArrayOutputStream.toString().contains("- 3244332"));
    assertTrue(byteArrayOutputStream.toString().contains("Unique Customer Ids for Geozone, \"us_west\", are:"));
    assertTrue(byteArrayOutputStream.toString().contains("- 1223456"));
    assertTrue(byteArrayOutputStream.toString().contains("- 1233456"));
    assertTrue(byteArrayOutputStream.toString().contains("Unique Customer Ids for Geozone, \"us_east\", are:"));
    assertTrue(byteArrayOutputStream.toString().contains("- 1223456"));
    assertTrue(byteArrayOutputStream.toString().contains("- 2343225"));
    assertTrue(byteArrayOutputStream.toString().contains("- 3244332"));
  }

  private List<ConstructionInfo> createConstructionInfoList() {
    List<ConstructionInfo> constructionInfoList = new ArrayList<>();
    constructionInfoList.add(new ConstructionInfo("2343225", "2345", "us_east", "RedTeam", "ProjectApple", "3445s"));
    constructionInfoList.add(new ConstructionInfo("1223456", "2345", "us_west", "BlueTeam", "ProjectBanana", "2211s"));
    constructionInfoList.add(new ConstructionInfo("3244332", "2346", "eu_west", "YellowTeam3", "ProjectCarrot", "4322s"));
    constructionInfoList.add(new ConstructionInfo("1233456", "2345", "us_west", "BlueTeam", "ProjectDate", "2221s"));
    constructionInfoList.add(new ConstructionInfo("3244132", "2346", "eu_west", "YellowTeam3", "ProjectEgg", "4122s"));
    constructionInfoList.add(new ConstructionInfo("3244332", "2346", "us_east", "PurpleTeam", "ProjectOrange", "2500s"));
    constructionInfoList.add(new ConstructionInfo("1223456", "2345", "us_east", "OrangeTeam", "ProjectBerry", "3500s"));
    constructionInfoList.add(new ConstructionInfo("3244332", "2346", "eu_west", "YellowTeam3", "ProjectCarrot", "4321s"));
    return constructionInfoList;
  }
}
