package api_checklist.com.pe.dto;

public class UserDTO {

    private long Id;
    private String name;
    private String last_name;
    private String rol;
    private Integer status;

    public UserDTO() {
    }

    public UserDTO(long id, String name, String last_name, String rol, int status) {
        this.Id = id;
        this.name = name;
        this.last_name = last_name;
        this.rol = rol;
        this.status = status;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
