package api_checklist.com.pe.implement;

import api_checklist.com.pe.entity.DetailRecord;
import api_checklist.com.pe.entity.WorksRecord;
import api_checklist.com.pe.repository.DetailsRecordDao;
import api_checklist.com.pe.repository.WorkRecordDao;
import api_checklist.com.pe.service.DetailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DetailsRecordImplements implements DetailRecordService {

    @Value("${upload.path}")
    private String uploadPath; // Se obtiene de application.properties

    @Autowired
    private DetailsRecordDao detailsRecordRepository;

    @Autowired
    private WorkRecordDao worksRecordRepository; // Repositorio de WorksRecord

    @Override
    public DetailRecord saveRecord(String detail, MultipartFile documents, Long works_record_id) throws Exception {
        WorksRecord worksRecord = worksRecordRepository.findById(works_record_id)
                .orElseThrow(() -> new RuntimeException("WorksRecord no encontrado"));

        String fileName = (documents != null && !documents.isEmpty()) ? saveFile(documents) : null;

        DetailRecord record = new DetailRecord();
        record.setDetail(detail);
        record.setDocuments(fileName);
        record.setWorksRecord(worksRecord);

        return detailsRecordRepository.save(record);
    }

    @Override
    public DetailRecord updateRecord(Long id, String detail, MultipartFile documents, Long work_record_id) throws Exception {
        DetailRecord record = detailsRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        WorksRecord worksRecord = worksRecordRepository.findById(work_record_id)
                .orElseThrow(() -> new RuntimeException("WorksRecord no encontrado"));

        if (documents != null && !documents.isEmpty()) {
            if (record.getDocuments() != null) {
                deleteFile(record.getDocuments());
            }
            record.setDocuments(saveFile(documents));
        }

        record.setDetail(detail);
        record.setWorksRecord(worksRecord);

        return detailsRecordRepository.save(record);
    }

    @Override
    public List<DetailRecord> getAllRecords() {
        return detailsRecordRepository.findAll();
    }

    @Override
    public Optional<DetailRecord> getRecordById(Long id) {
        return detailsRecordRepository.findById(id);
    }

    @Override
    public void deleteRecord(Long id) throws Exception {
        DetailRecord record = detailsRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        if (record.getDocuments() != null) {
            deleteFile(record.getDocuments());
        }

        detailsRecordRepository.deleteById(id);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File destFile = new File(uploadDir, fileName);
        file.transferTo(destFile);
        return fileName;
    }

    private void deleteFile(String fileName) throws IOException {
        File file = new File(uploadPath, fileName);
        if (file.exists()) {
            Files.delete(Paths.get(file.getAbsolutePath()));
        }
    }
}
