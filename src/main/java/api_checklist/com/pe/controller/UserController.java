package api_checklist.com.pe.controller;

import api_checklist.com.pe.entity.Users;
import api_checklist.com.pe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"*"})
public class UserController {

    @Autowired
    UserService service;


    // METHOD LIST ALL USERS
    // LINK: http://localhost:9090/api/user/list-users
    // LINK: http://192.168.10.120:9090/api-checklist/user/list-users - production

    @GetMapping("/list-users")
    public ResponseEntity<?> list() {
        Collection<Users> listAll = service.findAll();

        if (listAll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay usuarios disponibles.");
        }
        return ResponseEntity.ok(listAll); // Status 200 OK
    }

    // METHOD CREATE NEW USER
    // LINK: http://localhost:9090/api/user/create
    // LINK: http://192.168.10.120:9090/api-checklist/user/create - production

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody Users users) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.insert(users);
            response.put("User created:", users);
            response.put("message", "Usuario creado correctamente.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201 Created
        } catch (Exception e) {
            response.put("error", "Error al crear el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Status 500 Error
        }
    }


    // METHOD: UPDATE USER
    // LINK: http://localhost:9090/api/user/update/{id}
    // LINK: http://192.168.10.120:9090/api-checklist/user/create - production

    /*
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Users users) {
        Map<String, Object> response = new HashMap<>();

        if (!service.existsById(id)) {
            response.put("message", "Usuario no encontrado con ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Status 404 Not Found
        }

        try {
            users.setId(id);
            service.update(users);
            response.put("user", users);
            response.put("message", "Usuario actualizado correctamente.");
            return ResponseEntity.ok(response); // Status 200 OK
        } catch (Exception e) {
            response.put("error", "Error al actualizar el usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // Status 500 Error
        }
    }
    */

    // METHOD AUTHENTICATED
    // LINK: http://localhost:9090/api/user/auth
    // LINK: http://192.168.10.120:9091/api-checklist/user/auth - production

    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String mail = body.get("mail");
        String password = body.get("password");

        return service.login(mail, password)
                .map(user -> {
                    if (user.getStatus() == 1) { // VALID USER ACTIVE
                        return ResponseEntity.ok(user); // Status 200 OK
                    } else {
                        Map<String, String> response = new HashMap<>();
                        response.put("message", "No autorizado: El usuario " + user.getName() + " está inactivo.");
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // Status 403 Forbidden
                    }
                }).orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Credenciales incorrectas.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // Status 401 Unauthorized
                });
    }
}
