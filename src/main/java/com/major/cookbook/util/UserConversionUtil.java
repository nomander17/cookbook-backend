package com.major.cookbook.util;

import com.major.cookbook.dto.PublicUserDTO;
import com.major.cookbook.model.User;

public class UserConversionUtil {
    public static PublicUserDTO convertToPublicUserDTO(User user) {
        PublicUserDTO publicUserDTO = new PublicUserDTO();
        publicUserDTO.setUserId(user.getUserId());
        publicUserDTO.setUsername(user.getUsername());
        publicUserDTO.setName(user.getName());
        publicUserDTO.setAvatar(user.getAvatar());
        // Set other fields if necessary
        return publicUserDTO;
    }
}