package org.nira.construction.service.report.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nira.construction.model.ReportInfo;
import org.nira.construction.service.report.ReportPrinterService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ConsoleReportPrinterServiceImplTest {

  ReportPrinterService reportPrinterService;
  private final PrintStream systemOut = System.out;
  private ByteArrayOutputStream byteArrayOutputStream;

  @BeforeEach
  public void setup() throws IOException {
    reportPrinterService = new ConsoleReportPrinterServiceImpl();
    byteArrayOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(byteArrayOutputStream));
  }

  @AfterEach
  public void reset() throws Exception {
    System.setOut(systemOut);
  }

  @Test
  void testPrint_withValidReportInfo_success() {
    reportPrinterService.print(createReportInfo());
    assertTrue(byteArrayOutputStream.toString().contains("=====The number of unique customerId for each contractId====="));
    assertTrue(byteArrayOutputStream.toString().contains("Contract Id = \"2345\", Total number unique Customer Ids = \"3\""));
    assertTrue(byteArrayOutputStream.toString().contains("Contract Id = \"2346\", Total number unique Customer Ids = \"2\""));

    assertTrue(byteArrayOutputStream.toString().contains("=====The number of unique Customer Ids for each Geozone====="));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"eu_west\", Total number unique Customer Ids = \"2\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_west\", Total number unique Customer Ids = \"2\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_east\", Total number unique Customer Ids = \"1\""));

    assertTrue(byteArrayOutputStream.toString().contains("=========The Average build duration for each Geozone========="));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"eu_west\", Average Build Duration = \"4222s\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_west\", Average Build Duration = \"2216s\""));
    assertTrue(byteArrayOutputStream.toString().contains("Geozone = \"us_east\", Average Build Duration = \"3445s\""));

    assertTrue(byteArrayOutputStream.toString().contains("=======The list of unique Customer Ids for each Geozone======="));
    assertTrue(byteArrayOutputStream.toString().contains("Unique Customer Ids for Geozone, \"eu_west\", are:"));
    assertTrue(byteArrayOutputStream.toString().contains("- 3244132"));
    assertTrue(byteArrayOutputStream.toString().contains("- 3244332"));
    assertTrue(byteArrayOutputStream.toString().contains("Unique Customer Ids for Geozone, \"us_west\", are:"));
    assertTrue(byteArrayOutputStream.toString().contains("- 1223456"));
    assertTrue(byteArrayOutputStream.toString().contains("- 1233456"));
    assertTrue(byteArrayOutputStream.toString().contains("Unique Customer Ids for Geozone, \"us_east\", are:"));
    assertTrue(byteArrayOutputStream.toString().contains("- 2343225"));
  }

  private ReportInfo createReportInfo() {
    Map<Integer, Integer> totalUniqueCustomerIdsPerContractId = new HashMap<>();
    totalUniqueCustomerIdsPerContractId.put(2345, 3);
    totalUniqueCustomerIdsPerContractId.put(2346, 2);

    Map<String, Set<Integer>> uniqueCustomerIdsPerGeozone = new HashMap<>();
    uniqueCustomerIdsPerGeozone.put("eu_west", new HashSet<>(Arrays.asList(3244132, 3244332)));
    uniqueCustomerIdsPerGeozone.put("us_west", new HashSet<>(Arrays.asList(1223456, 1233456)));
    uniqueCustomerIdsPerGeozone.put("us_east", new HashSet<>(Collections.singletonList(2343225)));

    Map<String, Integer> totalUniqueCustomerIdsPerGeozone = new HashMap<>();
    uniqueCustomerIdsPerGeozone.forEach((geozone, customerIds) -> {
      totalUniqueCustomerIdsPerGeozone.put(geozone, customerIds.size());
    });

    Map<String, Long> averageBuildDurationsPerGeozone = new HashMap<>();
    averageBuildDurationsPerGeozone.put("eu_west", Long.parseLong("4222"));
    averageBuildDurationsPerGeozone.put("us_west", Long.parseLong("2216"));
    averageBuildDurationsPerGeozone.put("us_east", Long.parseLong("3445"));

    return ReportInfo.builder()
        .totalUniqueCustomerIdsPerContractId(totalUniqueCustomerIdsPerContractId)
        .totalUniqueCustomerIdsPerGeozone(totalUniqueCustomerIdsPerGeozone)
        .averageBuildDurationsPerGeozone(averageBuildDurationsPerGeozone)
        .uniqueCustomerIdsPerGeozone(uniqueCustomerIdsPerGeozone)
        .build();
  }
}
