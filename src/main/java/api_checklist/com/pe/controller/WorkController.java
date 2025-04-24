package api_checklist.com.pe.controller;

import api_checklist.com.pe.entity.Works;
import api_checklist.com.pe.service.WorkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work")
@CrossOrigin(origins = {"*"})
public class WorkController {

    @Autowired
    WorkServices service;

    // METHOD LIST ALL WORKS
    // LINK: http://localhost:9090/api/work/listAll-works
    // LINK: http://192.168.10.120:9090/api-checklist/work/listAll-works - production

    @GetMapping("/listAll-works")
    public ResponseEntity<?> listAll(){
        Collection<Works> listAll = service.findAll();
        if (listAll.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay usuarios disponibles.");
        }
        return ResponseEntity.ok(listAll); // Status 200 OK
    }

    // METHOD LIST ALL WORKS OF DAY PRESENT
    // LINK: http://localhost:9090/api/work/list-work-today
    // LINK: http://192.168.10.120:9091/api-checklist/work/list-work-today - production

    @GetMapping("/list-work-today")
    public ResponseEntity<List<Works>> getWorksWithoutRecordsForToday() {
        List<Works> works = service.getWorksWithoutRecordsForToday();
        return ResponseEntity.ok(works);
    }

    // METHOD LIST INSERT RECORD
    // LINK: http://localhost:9090/api/work/insert
    // LINK: http://192.168.10.120:9090/api-checklist/work/insert - production

    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> agregar(@RequestBody Works works) {
        Map<String, Object> response = new HashMap<>();
        service.save(works);
        response.put("Task: ", works);
        response.put("Message: ", "Task create succesfull");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // METHOD UPDATE WORK -
    // LINK: http://localhost:9090/api/work/update
    // LINK: http://192.168.10.120:9090/api-checklist/work/update/{id} - production

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Works newWorks) {
        Map<String, Object> response = new HashMap<>();
        Works works = service.findById(id);
        if (works != null) {
            works.setTitle(newWorks.getTitle());
            works.setObservacion(newWorks.getObservacion());
            service.update(works);
            response.put("Mensaje: ", "Task updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error: ", "No se encuentra la tarea con el ID: " +id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // METHOD UPDATE WORK -
    // LINK: http://localhost:9090/api/work/actualizar
    // LINK: http://192.168.10.120:9090/api-checklist/work/actualizar/{id} - production

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> Editar(@PathVariable Long id, @RequestBody Works newWorks) {
        Map<String, Object> response = new HashMap<>();
        Works works = service.findById(id); // Buscar la tarea por ID
        System.out.println("DATA RECIBED: "
                + " " + "ID: " + "  " +works.getId()
                + " " + "titulo " + " : " +works.getTitle()
                + " " + "observacion"+ " : " + works.getObservacion()
                + " " + "Frecuencia"+ " : " + works.getFrequency()
                + " " + "estado " + " : " + works.getStatus()); // Log para revisar datos

        // Verifica si la tarea existe
        if (works != null) {
            // Actualiza solo los campos no nulos enviados por el cliente
            if (newWorks.getTitle() != null) {
                works.setTitle(newWorks.getTitle());
            }
            if (newWorks.getObservacion() != null) {
                works.setObservacion(newWorks.getObservacion());
            }
            if (works.getStatus() == null) {
                works.setStatus(1); // Asigna el valor predeterminado
            }
            // Guarda la actualización
            service.update(works);
            // Respuesta exitosa
            response.put("Message: ", "Task updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Respuesta cuando no se encuentra el ID
            response.put("Error", "Not found ID invalid: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // METHOD UPDATE WORK -
    // LINK: http://localhost:9090/api/work/task_pending
    // LINK: http://192.168.10.120:9090/api-checklist/work/task_pending - production

    @GetMapping("/task_pending")
    public ResponseEntity<List<Works>> getTasksForToday() {
        List<Works> tasks = service.getPendingTasks();
        return ResponseEntity.ok(tasks);
    }
}
