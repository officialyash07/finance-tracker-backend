package com.pathik.financetracker.mapper;

import com.pathik.financetracker.dto.auth.UserResponse;
import com.pathik.financetracker.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPreferredCurrency(),
                user.getCreatedAt()
        );
    }
}
