package org.nira.construction.service.report.impl;

import org.nira.construction.model.ConstructionInfo;
import org.nira.construction.model.ReportInfo;
import org.nira.construction.service.report.ReportPrinterFactory;
import org.nira.construction.service.report.ReportProcessorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReportProcessorServiceImpl implements ReportProcessorService {

  private final Map<Integer, Set<Integer>> uniqueCustomerIdsPerContractId = new HashMap<>();
  private final Map<Integer, Integer> totalUniqueCustomerIdsPerContractId = new HashMap<>();
  private final Map<String, Set<Integer>> uniqueCustomerIdsPerGeozone = new HashMap<>();
  private final Map<String, Integer> totalUniqueCustomerIdsPerGeozone = new HashMap<>();
  private final Map<String, List<Integer>> buildDurationsPerGeozone = new HashMap<>();
  private final Map<String, Long> averageBuildDurationsPerGeozone = new HashMap<>();

  @Override
  public void process(String printType, List<ConstructionInfo> constructionInfoList) {

    constructionInfoList.forEach(constructionInfo -> {
      updateTotalUniqueCustomerIdsPerContractId(constructionInfo);
      updateUniqueCustomerIdsPerGeozone(constructionInfo);
      addBuildDurationPerGeozone(constructionInfo);
    });

    buildDurationsPerGeozone.forEach((geozone, buildDurations) ->
        averageBuildDurationsPerGeozone.put(
            geozone, buildDurations.stream().mapToLong(Integer::longValue).sum() / (long) buildDurations.size()));

    ReportPrinterFactory.print(printType, ReportInfo.builder()
        .totalUniqueCustomerIdsPerContractId(totalUniqueCustomerIdsPerContractId)
        .uniqueCustomerIdsPerGeozone(uniqueCustomerIdsPerGeozone)
        .totalUniqueCustomerIdsPerGeozone(totalUniqueCustomerIdsPerGeozone)
        .averageBuildDurationsPerGeozone(averageBuildDurationsPerGeozone)
        .build());
  }

  private void updateTotalUniqueCustomerIdsPerContractId(ConstructionInfo constructionInfo) {
    Integer contractId = constructionInfo.getContractId();
    if (uniqueCustomerIdsPerContractId.containsKey(contractId)) {
      if (!uniqueCustomerIdsPerContractId.get(contractId).contains(constructionInfo.getCustomerId())) {
        uniqueCustomerIdsPerContractId.get(contractId).add(constructionInfo.getCustomerId());
        totalUniqueCustomerIdsPerContractId.put(contractId, totalUniqueCustomerIdsPerContractId.get(contractId) + 1);
      }
    } else {
      totalUniqueCustomerIdsPerContractId.put(contractId, 1);
      Set<Integer> customerIds = new HashSet<>();
      customerIds.add(constructionInfo.getCustomerId());
      uniqueCustomerIdsPerContractId.put(contractId, customerIds);
    }
  }

  private void updateUniqueCustomerIdsPerGeozone(ConstructionInfo constructionInfo) {
    String geozone = constructionInfo.getGeoZone();
    if (uniqueCustomerIdsPerGeozone.containsKey(geozone)) {
      if (!uniqueCustomerIdsPerGeozone.get(geozone).contains(constructionInfo.getCustomerId())) {
        uniqueCustomerIdsPerGeozone.get(geozone).add(constructionInfo.getCustomerId());
        totalUniqueCustomerIdsPerGeozone.put(geozone, totalUniqueCustomerIdsPerGeozone.get(geozone) + 1);
      }
    } else {
      totalUniqueCustomerIdsPerGeozone.put(geozone, 1);
      Set<Integer> customerIds = new HashSet<>();
      customerIds.add(constructionInfo.getCustomerId());
      uniqueCustomerIdsPerGeozone.put(geozone, customerIds);
    }
  }

  private void addBuildDurationPerGeozone(ConstructionInfo constructionInfo) {
    if (buildDurationsPerGeozone.containsKey(constructionInfo.getGeoZone())) {
      buildDurationsPerGeozone.get(constructionInfo.getGeoZone()).add(constructionInfo.getBuildDuration());
    } else {
      List<Integer> buildDurations = new ArrayList<>();
      buildDurations.add(constructionInfo.getBuildDuration());
      buildDurationsPerGeozone.put(constructionInfo.getGeoZone(), buildDurations);
    }
  }
}
