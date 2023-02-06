package com.everag.mobilemanifest.bootstrap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "dairy", ignoreUnknownFields = false)
public class DairyProperties {

    private final Logging logging = new Logging();

    @Getter
    @Setter
    public static class Logging {
        private final Logstash logstash = new Logstash();

        @Getter
        @Setter
        public static class Logstash {
            private boolean enabled = false;
            private String host = "localhost";
            private int port = 5000;
            private int queueSize = 512;
            private String token = "";
            private String logType = "";
        }
    }
}
