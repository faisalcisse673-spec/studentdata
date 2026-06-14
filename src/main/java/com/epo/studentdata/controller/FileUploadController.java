package com.epo.studentdata.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.epo.studentdata.model.LogTelechargement;
import com.epo.studentdata.model.Ressource;
import com.epo.studentdata.model.User;
import com.epo.studentdata.repository.LogTelechargementRepository;
import com.epo.studentdata.repository.RessourceRepository;
import com.epo.studentdata.repository.UserRepository;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Value("${file.upload-dir:./uploads/}")
    private String uploadDir;

    private final LogTelechargementRepository logTelechargementRepository;
    private final UserRepository userRepository;
    private final RessourceRepository ressourceRepository;

    public FileUploadController(LogTelechargementRepository logTelechargementRepository,
                                UserRepository userRepository,
                                RessourceRepository ressourceRepository) {
        this.logTelechargementRepository = logTelechargementRepository;
        this.userRepository = userRepository;
        this.ressourceRepository = ressourceRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Fichier vide");
            }

            // Créer le dossier si nécessaire
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Générer un nom unique
            String originalFileName = file.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;

            // Sauvegarder le fichier
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erreur lors de l'upload");
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 @RequestParam(required = false) Long userId,
                                                 @RequestParam(required = false) Long ressourceId) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // Log download
            if (userId != null && ressourceId != null) {
                userRepository.findById(userId).ifPresent(user -> {
                    ressourceRepository.findById(ressourceId).ifPresent(ressource -> {
                        logTelechargementRepository.save(new LogTelechargement(user, ressource));
                    });
                });
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}