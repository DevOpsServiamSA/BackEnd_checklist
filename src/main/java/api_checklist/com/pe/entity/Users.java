package api_checklist.com.pe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "No puede ir vacio")
    @Column(nullable = false, length = 40)
    private String name;

    @NotNull(message = "No puede ir vacio")
    @Column(nullable = false, length = 40)
    private String last_name;

    @NotNull(message = "No puede ir vacio")
    @Column(nullable = false, length = 30, unique = true)
    private String mail;

    @NotNull(message = "No puede ir vacio")
    @Column(nullable = false, length = 30, unique = true)
    private String password;

    @NotNull(message = "No puede ir vacio")
    @JoinColumn(nullable = false)
    private String rol;

    private int status = 1;

    public Users() {
    }

    public Users(Long id, String name, String last_name, String mail, String password, String rol, int status) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.mail = mail;
        this.password = password;
        this.rol = rol;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        if (this.status == 0) {
            this.status = 1; // Valor predeterminado
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "No puede ir vacio") String getLast_name() {
        return last_name;
    }

    public void setLast_name(@NotNull(message = "No puede ir vacio") String last_name) {
        this.last_name = last_name;
    }

    public @NotNull(message = "No puede ir vacio") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "No puede ir vacio") String name) {
        this.name = name;
    }

    public @NotNull(message = "No puede ir vacio") String getMail() {
        return mail;
    }

    public void setMail(@NotNull(message = "No puede ir vacio") String mail) {
        this.mail = mail;
    }

    public @NotNull(message = "No puede ir vacio") String getPassword() {
        return password;
    }

    public void setPasswor(@NotNull(message = "No puede ir vacio") String password) {
        this.password = password;
    }

    public void setPassword(@NotNull(message = "No puede ir vacio") String password) {
        this.password = password;
    }

    public @NotNull(message = "No puede ir vacio") String getRol() {
        return rol;
    }

    public void setRol(@NotNull(message = "No puede ir vacio") String rol) {
        this.rol = rol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
