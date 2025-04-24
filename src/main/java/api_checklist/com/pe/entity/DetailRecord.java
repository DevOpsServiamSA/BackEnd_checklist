package api_checklist.com.pe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "detail_record")
public class DetailRecord implements Serializable {

    @Serial
    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String detail;

    @Column(length = 900)
    private String documents;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    @Column(nullable = false)
    private LocalDateTime fecha_details = LocalDateTime.now(); // Fecha específica para este registro.


    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference  // Añadir esta anotación
    WorksRecord worksRecord;

    public DetailRecord() {
    }

    public DetailRecord(Long id, String detail, String documents, LocalDateTime fecha_details, WorksRecord worksRecord) {
        this.id = id;
        this.detail = detail;
        this.documents = documents;
        this.fecha_details = fecha_details;
        this.worksRecord = worksRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public LocalDateTime getFecha_details() {
        return fecha_details;
    }

    public void setFecha_details(LocalDateTime fecha_details) {
        this.fecha_details = fecha_details;
    }

    public WorksRecord getWorksRecord() {
        return worksRecord;
    }

    public void setWorksRecord(WorksRecord worksRecord) {
        this.worksRecord = worksRecord;
    }
}
