package api_checklist.com.pe.implement;

import api_checklist.com.pe.entity.Users;
import api_checklist.com.pe.repository.UserDao;
import api_checklist.com.pe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserImplements implements UserService {

    @Autowired
    UserDao repository;

    @Override
    public Collection<Users> findAll() {
        return repository.findAll();
    }

    @Override
    public void insert(Users users) {
        repository.save(users);
    }

    @Override
    public Optional<Users> login(String mail, String password) {
        // Buscar usuario por correo y validar contraseña
        return repository.findByMail(mail)
                .filter(user -> user.getPassword().equals(password));
    }

}
