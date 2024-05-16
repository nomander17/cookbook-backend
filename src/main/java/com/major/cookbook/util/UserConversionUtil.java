package com.major.cookbook.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.major.cookbook.dto.PublicUserDTO;
import com.major.cookbook.dto.UserDTOForAdmin;
import com.major.cookbook.model.User;

public class UserConversionUtil {
    private static Logger logger = LoggerFactory.getLogger(UserConversionUtil.class);
    public static PublicUserDTO convertToPublicUserDTO(User user) {
        PublicUserDTO publicUserDTO = new PublicUserDTO();
        publicUserDTO.setUserId(user.getUserId());
        publicUserDTO.setUsername(user.getUsername());
        publicUserDTO.setName(user.getName());
        publicUserDTO.setAvatar(user.getAvatar());
        // Set other fields if necessary
        return publicUserDTO;
    }
    public static UserDTOForAdmin convertToUserDTOForAdmin(User user) {
        UserDTOForAdmin userDTOForAdmin = new UserDTOForAdmin();
        userDTOForAdmin.setUserId(user.getUserId());
        userDTOForAdmin.setUsername(user.getUsername());
        userDTOForAdmin.setEmail(user.getEmail());
        userDTOForAdmin.setPassword(user.getPassword());
        userDTOForAdmin.setName(user.getName());
        userDTOForAdmin.setAvatar(user.getAvatar());
        userDTOForAdmin.setIsAdmin(Boolean.toString(user.getIsAdmin()));
        logger.debug(userDTOForAdmin.getIsAdmin());
        return userDTOForAdmin;
    }
}