package com.epo.studentdata.service;

import com.epo.studentdata.model.User;
import com.epo.studentdata.model.Role;
import com.epo.studentdata.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email déjà utilisé: " + user.getEmail());
        }
        if (user.getMatricule() != null && userRepository.existsByMatricule(user.getMatricule())) {
            throw new RuntimeException("Matricule déjà utilisé: " + user.getMatricule());
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            String tempPassword = UUID.randomUUID().toString().substring(0, 8);
            user.setPassword(tempPassword);
            System.out.println("====== NOUVEAU COMPTE CREE ======");
            System.out.println("Email: " + user.getEmail());
            System.out.println("Mot de passe temporaire: " + tempPassword);
            System.out.println("=================================");
        }
        user.setDateInscription(LocalDateTime.now());
        user.setActif(true);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        existing.setNom(user.getNom());
        existing.setPrenom(user.getPrenom());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.findByActifTrue();
    }

    @Override
    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getActif() && user.get().getPassword().equals(password);
    }

    @Override
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setActif(true);
        return userRepository.save(user);
    }

    @Override
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setActif(false);
        return userRepository.save(user);
    }
}