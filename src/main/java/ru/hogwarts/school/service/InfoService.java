package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaceUniversity.InfoInterface;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoService implements InfoInterface {

    private ServerProperties serverProperties;

    @Autowired
    public InfoService(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    @Value("$(server.port)")
    public Integer getNewPort() {
        return serverProperties.getPort();
    }


}
