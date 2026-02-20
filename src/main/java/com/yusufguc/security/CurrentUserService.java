package com.yusufguc.security;

import com.yusufguc.exception.base.BaseException;
import com.yusufguc.exception.message.ErrorMessage;
import com.yusufguc.exception.message.MessageType;
import com.yusufguc.model.User;
import com.yusufguc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND,username)));
    }
}