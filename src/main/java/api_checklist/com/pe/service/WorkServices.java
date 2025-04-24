package api_checklist.com.pe.service;

import api_checklist.com.pe.entity.Works;
import java.util.Collection;
import java.util.List;

public interface WorkServices {

    public abstract Works save(Works works);
    public abstract void update(Works works);
    public abstract Works findById(Long id);
    public abstract Collection<Works> findAll();
    List<Works> getWorksWithoutRecordsForToday();
    List<Works> getPendingTasks();
}
