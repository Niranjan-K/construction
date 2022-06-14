package org.nira.construction.model;

import lombok.Builder;
import lombok.Getter;
import org.nira.construction.exception.ConstructionException;

import java.util.Objects;

@Getter
public class ConstructionInfo {
  private final Integer customerId;
  private final Integer contractId;
  private final String geoZone;
  private final String teamCode;
  private final String projectCode;
  private final Integer buildDuration;

  private static final String BUILD_DURATION_REGEX = "^[0-9]+[s]$";
  private static final String ERROR_MESSAGE = "invalid data for %s";

  public ConstructionInfo(final String customerId, final String contractId, final String geoZone, final String teamCode,
                          final String projectCode, final String buildDuration) {
    this.customerId = getIntegerValue(customerId, "Customer Id");
    this.contractId = getIntegerValue(contractId, "Contract Id");
    this.geoZone = getStringValue(geoZone, "Geozone");
    this.teamCode = getStringValue(teamCode, "Team Code");
    this.projectCode = getStringValue(projectCode, "Project Code");
    this.buildDuration = getBuildDuration(buildDuration);
  }

  private Integer getIntegerValue(final String data, final String fieldName) {
    try {
      Integer value = Integer.parseInt(data.trim());
      if (value == 0) {
        throw new ConstructionException(String.format(ERROR_MESSAGE, fieldName));
      }
      return value;
    } catch (NumberFormatException e) {
      throw new ConstructionException(String.format(ERROR_MESSAGE, fieldName));
    }
  }

  private String getStringValue(final String data, final String fieldName) {
    if (data == null || data.trim().isEmpty()) {
      throw new ConstructionException(String.format(ERROR_MESSAGE, fieldName));
    }
    return data.trim();
  }

  private Integer getBuildDuration(final String data) {
    final String errorMessage = String.format(ERROR_MESSAGE, "Build Duration");
    if (data != null && data.matches(BUILD_DURATION_REGEX)) {
      return Integer.parseInt(data.substring(0, data.length() - 1));
    }
    throw new ConstructionException(errorMessage);
  }
}
