package api_checklist.com.pe.service;

import api_checklist.com.pe.entity.DetailRecord;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;


public interface DetailRecordService {

        DetailRecord saveRecord(String detail, MultipartFile documents, Long works_record_id) throws Exception;
        DetailRecord updateRecord(Long id, String detail, MultipartFile documents, Long works_record_id) throws Exception;
        List<DetailRecord> getAllRecords();
        Optional<DetailRecord> getRecordById(Long id);
        void deleteRecord(Long id) throws Exception;
}


