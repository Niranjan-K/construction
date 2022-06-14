package org.nira.construction.service.report.impl;

import org.nira.construction.model.ReportInfo;
import org.nira.construction.service.report.ReportPrinterService;

public class ConsoleReportPrinterServiceImpl implements ReportPrinterService {
  @Override
  public void print(ReportInfo reportInfo) {
    printUniqueCustomerIdsPerContractIds(reportInfo);
    printTotalUniqueCustomerIdsPerGeozone(reportInfo);
    printAverageBuildDurationsPerGeozone(reportInfo);
    printUniqueCustomerIdsPerGeozone(reportInfo);
  }

  private void printUniqueCustomerIdsPerContractIds(ReportInfo reportInfo) {
    System.out.println("=====The number of unique customerId for each contractId=====");
    reportInfo.getTotalUniqueCustomerIdsPerContractId()
        .forEach((contractId, totalUniqueCustomerIds) -> {
          System.out.println("Contract Id = \"" + contractId +
              "\", Total number unique Customer Ids = \"" + totalUniqueCustomerIds + "\"");
        });
    System.out.println("=============================================================");
    System.out.println("");
  }

  private void printTotalUniqueCustomerIdsPerGeozone(ReportInfo reportInfo) {
    System.out.println("=====The number of unique Customer Ids for each Geozone=====");
    reportInfo.getTotalUniqueCustomerIdsPerGeozone()
        .forEach((geozone, totalUniqueCustomerIds) -> {
          System.out.println("Geozone = \"" + geozone +
              "\", Total number unique Customer Ids = \"" + totalUniqueCustomerIds + "\"");
        });
    System.out.println("=============================================================");
    System.out.println("");
  }

  private void printAverageBuildDurationsPerGeozone(ReportInfo reportInfo) {
    System.out.println("=========The Average build duration for each Geozone=========");
    reportInfo.getAverageBuildDurationsPerGeozone()
        .forEach((geozone, averageBuildDurations) -> {
          System.out.println("Geozone = \"" + geozone +
              "\", Average Build Duration = \"" + averageBuildDurations + "s\"");
        });
    System.out.println("=============================================================");
    System.out.println("");
  }

  private void printUniqueCustomerIdsPerGeozone(ReportInfo reportInfo) {
    System.out.println("=======The list of unique Customer Ids for each Geozone=======");
    reportInfo.getUniqueCustomerIdsPerGeozone()
        .forEach((geozone, customerIds) -> {
          System.out.println("Unique Customer Ids for Geozone, \"" + geozone + "\", are: ");
          customerIds.forEach(customerId -> {
            System.out.println(" - " + customerId);
          });
        });
    System.out.println("==============================================================");
    System.out.println("");
  }
}
