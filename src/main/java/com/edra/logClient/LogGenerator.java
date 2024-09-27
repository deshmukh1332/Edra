package com.edra.logClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

import static com.edra.logClient.Utils.LOG_LINES;
import static com.edra.logClient.Utils.logQueue;

@Slf4j
@Component
public class LogGenerator {
    private static final String LOG_FILE_PATH = "src/main/resources/static/log.txt";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_STRING_LENGTH = 30;

    @Scheduled(fixedRate = 1000)
    public void chatPage() {
        File file = new File(LOG_FILE_PATH);
        // add random line of 30 characters in the file
        addRandomLineToFile(file);
    }

    private static void addRandomLineToFile(File file) {
        String randomLine = generateRandomString();
        try (FileWriter writer = new FileWriter(file, true)) {
            String line = System.currentTimeMillis() + " - " + randomLine + System.lineSeparator();
            writer.write(line);
            if (logQueue.size() >= LOG_LINES) {
                logQueue.poll();
            }
            logQueue.add(line);
            LogWebSocketHandler.sendLogToAllSessions();
            System.out.println("Added line: " + randomLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomString() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LogGenerator.RANDOM_STRING_LENGTH);
        for (int i = 0; i < LogGenerator.RANDOM_STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}