package ruhogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ruhogwarts.school.service.InfoService;

@RestController
public class infoController {

    private final InfoService infoService;

    public infoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/port")
    public String getInfoPort() {
        return infoService.getInfoPort();
    }

}
