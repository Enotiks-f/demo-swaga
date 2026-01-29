package ruhogwarts.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ruhogwarts.school.model.Avatar;
import ruhogwarts.school.repository.AvatarRepository;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarRepository avatarRepository;

    public AvatarController(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    // Получить аватарки постранично
    @GetMapping
    public Page<Avatar> getAvatars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by(sortBy).descending());
        return avatarRepository.findAll((org.springframework.data.domain.Pageable) pageable);
    }
}
