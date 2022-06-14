package org.nira.construction.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class ReportInfo {
  private final Map<Integer, Integer> totalUniqueCustomerIdsPerContractId;
  private final Map<String, Integer> totalUniqueCustomerIdsPerGeozone;
  private final Map<String, Long> averageBuildDurationsPerGeozone;
  private final Map<String, Set<Integer>> uniqueCustomerIdsPerGeozone;
}
