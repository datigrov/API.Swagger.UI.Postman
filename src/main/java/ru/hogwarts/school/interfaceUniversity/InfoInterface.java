package ru.hogwarts.school.interfaceUniversity;

import org.springframework.beans.factory.annotation.Value;

public interface InfoInterface {
    @Value("$(server.port)")
    Integer getNewPort();
}
