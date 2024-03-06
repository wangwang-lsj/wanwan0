package com.wanwan.springboot.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanwan.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @author：玩玩
 * @since：2024/1/23 18:27
 * @description:
 */

@Data
public class UserDTO {
    private String username;
    // @JsonIgnore
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
