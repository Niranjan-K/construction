package org.nira.construction.service.data;

import org.nira.construction.model.ConstructionInfo;

import java.util.List;

public interface DataReaderService {
  List<ConstructionInfo> readFile(String filename);
}
