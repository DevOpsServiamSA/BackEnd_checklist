package api_checklist.com.pe.implement;

import api_checklist.com.pe.entity.WorksRecord;
import api_checklist.com.pe.repository.WorkRecordDao;
import api_checklist.com.pe.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkRecordImplements implements WorkRecordService {

    @Autowired
    WorkRecordDao repository;

    @Override
    public void sava(WorksRecord worksRecord) {
        repository.save(worksRecord);
    }

    @Override
    public void update(WorksRecord worksRecord) {
        repository.save(worksRecord);
    }

    @Override
    public WorksRecord findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<WorksRecord> findAll() {
        return (Collection<WorksRecord>) repository.findAll();
    }

    // Método para guardar el registro validando duplicados
    public WorksRecord saveWorksRecord(WorksRecord worksRecord) {
        Long worksId = worksRecord.getWorks().getId();
        LocalDate fecha = worksRecord.getFecha_record();

        // Buscar si ya existe un registro
        Optional<WorksRecord> existingRecord = repository.findByWorksIdAndFecha(worksId, fecha);

        if (existingRecord.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La tarea ya fue resuelta el día de hoy por: " +
                            existingRecord.get().getUsers().getName() + " " + existingRecord.get().getUsers().getLast_name());
        }

        // Si no existe, guardar y devolver el objeto
        return repository.save(worksRecord);
    }

    public List<WorksRecord> getWorksRecordsLastFiveDays() {
        LocalDate endDate = LocalDate.now(); // Fecha actual
        LocalDate startDate = endDate.minusDays(5); // Fecha 5 días antes

        return repository.findWorksRecordsInDateRange(startDate, endDate);
    }

    public WorksRecord getWorksRecordWithDetails(Long id) {
        return repository.findByIdWithDetails(id);
    }

}
