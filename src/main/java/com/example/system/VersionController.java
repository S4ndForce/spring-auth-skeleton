package com.example.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RestController
public class VersionController {

    private final Properties versionProps = new Properties();

    public VersionController() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("version.txt")) {
            if (in != null) {
                versionProps.load(in);
            }
        } catch (IOException ignored) {}
    }

    @GetMapping("/version")
    public Object version() {
        return versionProps;
    }
}
