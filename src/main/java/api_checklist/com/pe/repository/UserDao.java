package api_checklist.com.pe.repository;

import api_checklist.com.pe.entity.Users;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;

public interface UserDao extends ListCrudRepository<Users, Long> {

    Optional<Users> findByMail(String mail);
}
