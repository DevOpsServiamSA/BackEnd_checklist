package api_checklist.com.pe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Works")
public class Works implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ir vacia")
    @Column (nullable = false, length = 200)
    private String title;

    @NotNull(message = "No puede ir vacia")
    @Column (nullable = false, length = 250)
    private String observacion;

    @Column (length = 25)
    private String frequency;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate startDate; // Desde cuándo debe mostrarse

    @Column(length = 50, nullable = true)
    private String specificDays; // Días específicos del mes en formato "5,10,15"

    @Column(nullable = true)
    private Integer status = 1;

    public enum TaskFrequency {
        DAILY, WEEKLY, MONTHLY, SPECIFIC_DAYS
    }

    // Método para obtener los días específicos como una lista de enteros
    public List<Integer> getSpecificDaysAsList() {
        if (this.specificDays == null || this.specificDays.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(this.specificDays.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public Works() {
        this.startDate = LocalDate.now().withDayOfMonth(1); // Primer día del mes actual
    }

    public Works(Long id, String title, String observacion, String frequency, LocalDate startDate, String specificDays, Integer status) {
        this.id = id;
        this.title = title;
        this.observacion = observacion;
        this.frequency = frequency;
        this.startDate = startDate;
        this.specificDays = specificDays;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "No puede ir vacia") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "No puede ir vacia") String title) {
        this.title = title;
    }

    public @NotNull(message = "No puede ir vacia") String getObservacion() {
        return observacion;
    }

    public void setObservacion(@NotNull(message = "No puede ir vacia") String observacion) {
        this.observacion = observacion;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getSpecificDays() {
        return specificDays;
    }

    public void setSpecificDays(String specificDays) {
        this.specificDays = specificDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
