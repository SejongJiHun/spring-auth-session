package me.lgh.auth_module.user.service;

import lombok.RequiredArgsConstructor;
import me.lgh.auth_module.exception.CustomException;
import me.lgh.auth_module.exception.ErrorCode;
import me.lgh.auth_module.user.domain.User;
import me.lgh.auth_module.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
