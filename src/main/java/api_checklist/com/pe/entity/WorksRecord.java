package api_checklist.com.pe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class WorksRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate fecha_record = LocalDate.now(); // Fecha específica para este registro.

    @ManyToOne
    @JoinColumn(nullable = false)
    Works works;

    @ManyToOne
    @JoinColumn(nullable = false )
    Users users;

    // Relación OneToMany con DetailRecord
    @OneToMany(mappedBy = "worksRecord", fetch = FetchType.LAZY)
    @JsonManagedReference  // Añadir esta anotación
    private Set<DetailRecord> detailRecords; // Asegúrate de que sea 'detailRecords' en plural

    public WorksRecord() {
    }

    public WorksRecord(Long id, LocalDate fecha_record, Works works, Users users, Set<DetailRecord> detailRecords) {
        this.id = id;
        this.fecha_record = fecha_record;
        this.works = works;
        this.users = users;
        this.detailRecords = detailRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha_record() {
        return fecha_record;
    }

    public void setFecha_record(LocalDate fecha_record) {
        this.fecha_record = fecha_record;
    }

    public Works getWorks() {
        return works;
    }

    public void setWorks(Works works) {
        this.works = works;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<DetailRecord> getDetailRecords() {
        return detailRecords;
    }

    public void setDetailRecords(Set<DetailRecord> detailRecords) {
        this.detailRecords = detailRecords;
    }
}
