package api_checklist.com.pe.service;

import api_checklist.com.pe.entity.Users;
import java.util.Collection;
import java.util.Optional;

public interface UserService {

    public abstract Collection<Users> findAll();
    public abstract void insert(Users users);
    Optional<Users> login(String mail, String password);
}
