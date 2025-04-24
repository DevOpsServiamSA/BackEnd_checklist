package api_checklist.com.pe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class FileTestController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/write")
    public ResponseEntity<String> testWriteFile() {
        try {
            File testFile = new File(uploadPath, "test_produccion.txt");

            if (testFile.createNewFile()) {
                return ResponseEntity.ok("✅ Archivo creado con éxito en " + testFile.getAbsolutePath());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("⚠️ No se pudo crear el archivo.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error: " + e.getMessage());
        }
    }
}
