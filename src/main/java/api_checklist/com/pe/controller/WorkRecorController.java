package api_checklist.com.pe.controller;

import api_checklist.com.pe.entity.WorksRecord;
import api_checklist.com.pe.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/work-record")
@CrossOrigin(origins = {"*"})
public class WorkRecorController {

    @Autowired
    WorkRecordService service;

    // METHOD LIST ALL
    // LINK: http://localhost:9090/api/work-record/listAll-worksRecord
    // LINK: http://192.168.10.120:9090/api-checklist/work-record/listAll-worksRecord - production

    @GetMapping("/listAll-worksRecord")
    public ResponseEntity<?> listAll(){
        Collection<WorksRecord> listAll = service.findAll();
        return new ResponseEntity<>(listAll, HttpStatus.OK);
    }

    // METHOD LIST ALL
    // LINK: http://localhost:9090/api/work-record/search-work-record/
    // LINK: http://192.168.10.120:9090/api-checklist/work-record/search-work-record/{id} - production

    @GetMapping("/search-work-record/{id}")
    public ResponseEntity<WorksRecord> getWorksRecord(@PathVariable Long id) {
        WorksRecord record = service.getWorksRecordWithDetails(id);

        if (record.getDetailRecords() == null) {
            record.setDetailRecords(new HashSet<>()); // Evita null en el JSON
        }

        return ResponseEntity.ok(record);
    }

    // METHOD INSERT
    // LINK: http://localhost:9090/api/work-record/save-worksRecord
    // LINK: http://192.168.10.120:9090/api-checklist/work-record/save-worksRecord - production

    @PostMapping("/save-worksRecord")
    public ResponseEntity<WorksRecord> saveWorksRecord(@RequestBody WorksRecord worksRecord) {
        WorksRecord savedRecord = service.saveWorksRecord(worksRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }

    // METHOD INSERT
    // LINK: http://localhost:9090/api/work-record/last-five-days
    // LINK: http://192.168.10.120:9090/api-checklist/work-record/last-five-days - production

    @GetMapping("/last-five-days")
    public List<WorksRecord> getWorksRecordsLastFiveDays() {
        return service.getWorksRecordsLastFiveDays();
    }
}
