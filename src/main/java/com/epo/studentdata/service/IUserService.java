package com.epo.studentdata.service;

import com.epo.studentdata.model.User;
import com.epo.studentdata.model.Role;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    List<User> getUsersByRole(Role role);
    List<User> getActiveUsers();
    boolean authenticate(String email, String password);
    User activateUser(Long id);
    User deactivateUser(Long id);
}