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

    public Integer getWholeSum() {
        Logger logger = (Logger) LoggerFactory.getLogger(InfoService.class);
        int sum;
        long slowTime = System.currentTimeMillis();
        sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        logger.info("Slow time is: " + slowTime);

        long fasterTime = System.currentTimeMillis();
        sum = IntStream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(1, Integer::sum);
        logger.info("better time is: " + (System.currentTimeMillis() - fasterTime));
        return sum;

    }

}
