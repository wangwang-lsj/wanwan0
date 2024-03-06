package com.wanwan.springboot.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author：玩玩
 * @since：2024/2/28 13:01
 * @description:
 */
@Data
public class UserPasswordDTO {

    private String username;
    private String password;
    private String newPassword;
}
