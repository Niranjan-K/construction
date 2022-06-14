package org.nira.construction.service.report;

import org.nira.construction.exception.ConstructionException;
import org.nira.construction.model.ReportInfo;
import org.nira.construction.service.report.impl.ConsoleReportPrinterServiceImpl;


public class ReportPrinterFactory {

  public static void print(String printType, ReportInfo reportInfo) {
    ReportPrinterService reportPrinterService;
    switch (printType) {
      case "console":
        reportPrinterService = new ConsoleReportPrinterServiceImpl();
        break;
      default:
        throw new ConstructionException("unknown print type, " + printType);
    }
    reportPrinterService.print(reportInfo);
  }
}
