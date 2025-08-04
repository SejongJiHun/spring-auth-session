package me.lgh.auth_module.user.dto;

import lombok.Getter;
import me.lgh.auth_module.user.domain.User;

@Getter
public class UserResponseDto {
    private final String email;
    private final String nickname;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
