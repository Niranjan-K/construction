package org.nira.construction.service.report;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.nira.construction.exception.ConstructionException;
import org.nira.construction.model.ReportInfo;
import org.nira.construction.service.data.DataReaderFactory;
import org.nira.construction.service.report.impl.ReportProcessorServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReportPrinterFactoryTest {

  @Mock
  ReportPrinterService reportPrinterService;

  @Mock
  ReportInfo reportInfo;

  @Test
  void print_consolePrintType_doesNotThrowException() {
    doNothing().when(reportPrinterService).print(reportInfo);
    assertDoesNotThrow(() -> ReportPrinterFactory.print("console", reportInfo));
  }

  @Test
  void print_unknownPrintType_throwsConstructionException() {
    ConstructionException exception = assertThrows(ConstructionException.class,
        () -> ReportPrinterFactory.print("aa", reportInfo));
    assertEquals("unknown print type, aa", exception.getMessage());
  }
}
