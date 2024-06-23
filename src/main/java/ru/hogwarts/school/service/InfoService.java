package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaceUniversity.InfoInterface;

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

    public Integer getWholeSum() {
        // int sum = Stream.iterate(1, a -> a +1) .limit(1_000_000) .reduce(0, (a, b) -> a + b );

    }

}
