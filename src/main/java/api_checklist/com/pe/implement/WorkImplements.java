package api_checklist.com.pe.implement;

import api_checklist.com.pe.entity.Works;
import api_checklist.com.pe.repository.WorkDao;
import api_checklist.com.pe.service.WorkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class WorkImplements implements WorkServices {

    @Autowired
    WorkDao repository;


    @Override
    public Works save(Works works) {
        repository.save(works);
        return works;
    }

    @Override
    public void update(Works works) {
        repository.save(works);
    }

    @Override
    public Works findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Works> findAll() {
        return (Collection<Works>) repository.findAll();
    }

    public List<Works> getWorksWithoutRecordsForToday() {
        LocalDate today = LocalDate.now();
        return repository.findWorksWithoutRecordForToday(today);
    }

    public List<Works> getPendingTasks() {
        LocalDate today = LocalDate.now();
        return repository.findTasksForToday(today);
    }
}
