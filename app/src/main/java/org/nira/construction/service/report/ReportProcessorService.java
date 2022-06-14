package org.nira.construction.service.report;

import org.nira.construction.model.ConstructionInfo;

import java.util.List;

public interface ReportProcessorService {

  void process(String printType, List<ConstructionInfo> constructionInfoList);
}
