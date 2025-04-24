package api_checklist.com.pe.controller;

import api_checklist.com.pe.entity.DetailRecord;
import api_checklist.com.pe.service.DetailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/record_details")
@CrossOrigin(origins = {"*"})
public class DetailsRecordController {

    @Autowired
    private DetailRecordService detailsRecordService;

    @Value("${upload.path}")
    private String uploadPath; // Ruta donde se guardan los archivos

    // METHOD CREATE DETAILS RECORD
    // LINK: http://localhost:9090/api/record_details/ver/{id}/file
    // LINK: http://192.168.10.120:9090/api-checklist/record_details/ver/{id}/file - production

    @GetMapping("/ver/{id}/file")
    public ResponseEntity<org.springframework.core.io.Resource> getFile(@PathVariable Long id) {
        Optional<DetailRecord> recordOpt = detailsRecordService.getRecordById(id);

        if (recordOpt.isEmpty() || recordOpt.get().getDocuments() == null) {
            return ResponseEntity.notFound().build();
        }

        String fileName = recordOpt.get().getDocuments();
        Path filePath = Paths.get(uploadPath).resolve(fileName).normalize(); // Normalizar ruta

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        try {
            org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Tipo de archivo genérico
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // METHOD CREATE DETAILS RECORD
    // LINK: http://localhost:9090/api/record_details/create
    // LINK: http://192.168.10.120:9090/api-checklist/record_details/create - production

    @PostMapping("/create")
    public ResponseEntity<DetailRecord> createRecord(
            @RequestParam("detail") String detail,
            @RequestParam(value = "documents", required = false) MultipartFile documents,
            @RequestParam("works_record_id") Long works_record_id) {
        try {
            // Imprimir los parámetros recibidos
            System.out.println("Detail: " + detail);  // Imprime el contenido del parámetro "detail"
            if (documents != null) {
                System.out.println("Documents file name: " + documents.getOriginalFilename()); // Nombre del archivo
            } else {
                System.out.println("No documents were uploaded.");
            }
            System.out.println("Works record ID: " + works_record_id); // Imprime el ID del works_record_id

            // Aquí puedes realizar la lógica adicional que necesites
            DetailRecord record = detailsRecordService.saveRecord(detail, documents, works_record_id);
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir stack trace en caso de error
            return ResponseEntity.internalServerError().build();
        }
    }

    // METHOD UPDATE RECORD DETAILS
    // LINK: http://localhost:9090/api/record_details/update/{id}
    // LINK: http://192.168.10.120:9090/api-checklist/record_details/update/{id} - production

    @PutMapping("/update/{id}")
    public ResponseEntity<DetailRecord> updateRecord(
            @PathVariable Long id,
            @RequestParam("detail") String detail,
            @RequestParam(value = "documents", required = false) MultipartFile documents, // Cambio aquí
            @RequestParam("worksRecordId") Long worksRecordId) {
        try {
            DetailRecord record = detailsRecordService.updateRecord(id, detail, documents, worksRecordId); // Cambio aquí
            return ResponseEntity.ok(record);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // METHOD LIST ALL DETAILS RECORD
    // LINK: http://localhost:9090/api/record_details/list_record_details
    // LINK: http://192.168.10.120:9090/api-checklist/record_details/list_record_details - production

    @GetMapping("/list_record_details")
    public ResponseEntity<List<DetailRecord>> getAllRecords() {
        return ResponseEntity.ok(detailsRecordService.getAllRecords());
    }

    // METHOD SEARCH DETAILS RECORD ID
    // LINK: http://192.168.10.120:9090/api-checklist/record_details/details_id/{id} - production
    // LINK: http://localhost:9090/api/record_details/details_id/{id}

    @GetMapping("/details_id/{id}")
    public ResponseEntity<Optional<DetailRecord>> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(detailsRecordService.getRecordById(id));
    }

    // METHOD DELETE DETAILS RECORD
    // LINK: http://localhost:9090/api/record_details/delete/{id}
    // LINK: http://192.168.10.120:9090/api-checklist/record_details/delete/{id}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        try {
            detailsRecordService.deleteRecord(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
