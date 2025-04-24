package api_checklist.com.pe.repository;

import api_checklist.com.pe.entity.Works;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface WorkDao extends CrudRepository<Works, Long> {

    @Query("SELECT w FROM Works w WHERE w.id NOT IN (SELECT wr.works.id FROM WorksRecord wr WHERE wr.fecha_record = :currentDate)")
    List<Works> findWorksWithoutRecordForToday(@Param("currentDate") LocalDate currentDate);

    @Query(value = "SELECT * FROM Works w WHERE w.start_date <= :today AND (" +
            "w.frequency = 'DAILY' OR " +
            "(w.frequency = 'WEEKLY' AND DATEPART(WEEKDAY, :today) = DATEPART(WEEKDAY, w.start_date)) OR " +
            "(w.frequency = 'MONTHLY' AND DAY(:today) = DAY(w.start_date)) OR " +
            "(w.frequency = 'SPECIFIC_DAYS' AND (" +
            "CHARINDEX(',' + CAST(DAY(:today) AS VARCHAR) + ',', ',' + w.specific_days + ',') > 0 " +
            "OR (CHARINDEX(',31,', ',' + w.specific_days + ',') > 0 AND DAY(EOMONTH(:today)) = DAY(:today)))))",
            nativeQuery = true)
    List<Works> findTasksForToday(@Param("today") LocalDate today);

}
