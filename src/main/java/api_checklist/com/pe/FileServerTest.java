package api_checklist.com.pe;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;

@Component
public class FileServerTest {

    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void testFileAccess() {
        try {
            File testFile = new File(uploadPath, "test_springboot2.txt");

            if (testFile.createNewFile()) {
                System.out.println("✅ Archivo de prueba creado en: " + testFile.getAbsolutePath());
            } else {
                System.out.println("⚠️ No se pudo crear el archivo. Verifica permisos.");
            }
        } catch (IOException e) {
            System.err.println("❌ Error al escribir en la ruta UNC: " + e.getMessage());
        }
    }
}