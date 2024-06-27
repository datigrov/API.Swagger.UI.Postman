package ru.hogwarts.school.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class InfoController {

    private InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/port")
    public String getPort() {
        return "Server run on port: " + infoService.getNewPort();
    }

    @GetMapping("/wholeSum")
    public Integer wholeSum() {
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
