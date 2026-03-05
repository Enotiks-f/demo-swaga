package ruhogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ruhogwarts.school.controller.infoController;

@Service
public class InfoService {

    Logger logger = LoggerFactory.getLogger(infoController.class);


    @Value("${server.port}")
    private String info;

    public String getInfoPort() {
        logger.info("Was invoked method for getting info port");
        return info.trim();
    };

}
