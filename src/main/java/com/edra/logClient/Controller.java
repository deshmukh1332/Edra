package com.edra.logClient;

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/log")
@ServerEndpoint("/log")
public class Controller {

    private final String logFilePath = "log.txt";

    @GetMapping("/log")
    public String log() {
        return "Logging";
    }

    @PostMapping("")
    public String logPost() {
        return "Logging";
    }
    @GetMapping("/")
    public String chatPage() {
        return "chat.html";
    }

}
