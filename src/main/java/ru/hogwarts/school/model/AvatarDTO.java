package ru.hogwarts.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

public class AvatarDTO {

    private String name;
    private String avatarUrl;

    public AvatarDTO() {
    }

    public AvatarDTO(String name, String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
    public static AvatarDTO fromAvatar(Avatar avatar) {
        String name = avatar.getStudent().getName();
        String avatarUrl = "http://localhost:8080/avatar/" + avatar.getId() + "/avatar/preview-avatar";

        return new AvatarDTO(name, avatarUrl);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDTO avatarDTO = (AvatarDTO) o;
        return Objects.equals(name, avatarDTO.name) && Objects.equals(avatarUrl, avatarDTO.avatarUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avatarUrl);
    }

    @Override
    public String toString() {
        return "AvatarDTO{" +
                "name='" + name + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
