package ruhogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ruhogwarts.school.exeption.StudentNotFoundException;
import ruhogwarts.school.model.Avatar;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.AvatarRepository;
import ruhogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${student.avatar.dir.path}")
    private String avatarDir;

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for uploading avatar with id {}", studentId);
        Student std = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

        Path path = Path.of(avatarDir, studentId + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try(InputStream is = file.getInputStream();
            OutputStream os = Files.newOutputStream(path, CREATE_NEW);
            BufferedInputStream bis = new BufferedInputStream(is, 1024);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ){
            bis.transferTo(bos);
        }

        Avatar avatar = findOrCreateAvatar(studentId);
        avatar.setStudent(std);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatarRepository.save(avatar);
    }

    public Avatar findOrCreateAvatar(Long studentId) {
        logger.info("Was invoked method for finding avatar with id {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        return avatarRepository.findByStudentId(studentId)
                .orElseGet(() -> {
                    Avatar newAvatar = new Avatar();
                    newAvatar.setStudent(student);
                    return newAvatar;
                });
    }
    public String getExtension(String fileName) {
        logger.info("Was invoked method for reading file with name {}", fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);}
}
