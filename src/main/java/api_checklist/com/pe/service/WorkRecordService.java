package api_checklist.com.pe.service;

import api_checklist.com.pe.entity.WorksRecord;
import java.util.Collection;
import java.util.List;

public interface WorkRecordService {

    public abstract void sava(WorksRecord worksRecord);
    public abstract void update(WorksRecord worksRecord);
    public WorksRecord findById (Long id);
    public Collection<WorksRecord> findAll();
    WorksRecord saveWorksRecord(WorksRecord worksRecord);
    List<WorksRecord> getWorksRecordsLastFiveDays();
    WorksRecord getWorksRecordWithDetails(Long id);
}
