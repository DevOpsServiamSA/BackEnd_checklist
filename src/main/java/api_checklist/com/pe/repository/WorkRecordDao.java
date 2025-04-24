package api_checklist.com.pe.repository;


import api_checklist.com.pe.entity.Works;
import api_checklist.com.pe.entity.WorksRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkRecordDao extends CrudRepository<WorksRecord, Long> {

    // Consulta para verificar si existe un registro con la misma fecha para el mismo work
    @Query("SELECT wr FROM WorksRecord wr WHERE wr.works.id = :worksId AND wr.fecha_record = :fecha_record")
    Optional<WorksRecord> findByWorksIdAndFecha(@Param("worksId") Long worksId, @Param("fecha_record") LocalDate fecha_record);

    @Query("SELECT wr FROM WorksRecord wr WHERE wr.fecha_record = :fecha_record AND wr.works = :works")
    Optional<WorksRecord> findRecordByFechaAndWorks(@Param("fecha_record") LocalDate fecha_record, @Param("works") Works works);

    @Query("SELECT wr FROM WorksRecord wr WHERE wr.fecha_record BETWEEN :startDate AND :endDate ORDER BY wr.fecha_record DESC")
    List<WorksRecord> findWorksRecordsInDateRange(LocalDate startDate, LocalDate endDate);

    @Query("SELECT wr FROM WorksRecord wr " +
            "JOIN FETCH wr.works w " +
            "JOIN FETCH wr.users u " +
            "LEFT JOIN FETCH wr.detailRecords d " + // Cambiado a LEFT JOIN FETCH
            "WHERE wr.id = :id")
    WorksRecord findByIdWithDetails(Long id);

}
