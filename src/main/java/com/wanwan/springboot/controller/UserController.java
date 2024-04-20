package com.wanwan.springboot.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanwan.springboot.common.Constants;
import com.wanwan.springboot.common.Result;
import com.wanwan.springboot.common.enums.ResultCodeEnum;
import com.wanwan.springboot.entity.User;
import com.wanwan.springboot.entity.dto.UserDTO;
import com.wanwan.springboot.entity.dto.UserPasswordDTO;
import com.wanwan.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-01-22
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Resource
    private IUserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户注册
     * @param userDto
     * @return user
     */
    @PostMapping("/users/register")
    public Result register(@RequestBody UserDTO userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        User dto = userService.register(userDto);
        return Result.success(dto);
    }

    /**
     * 用户登录
     * @param userDto
     * @return userDTO
     */
    @PostMapping("/users/login")
    public Result login(@RequestBody UserDTO userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
        UserDTO dto = userService.login(userDto);
        return Result.success(dto);
    }

    /**
     * 按条件分页查询
     * @param pageNum
     * @param pageSize
     * @param username
     * @param nickname
     * @param address
     * @param phone
     * @param email
     * @return Map<String,Object>
     */
    @GetMapping("/users/page")
    public Result page(@RequestParam Integer pageNum,
                             @RequestParam Integer pageSize,
                             @RequestParam(defaultValue = "") String username,
                             @RequestParam(defaultValue = "") String nickname,
                             @RequestParam(defaultValue = "") String address,
                             @RequestParam(defaultValue = "") String phone,
                             @RequestParam(defaultValue = "") String email
    ) {
        return Result.success(userService.findByPageOrSearch(pageNum, pageSize, username, nickname, address, phone, email));
    }

    /**
     * 保存或更新用户
     * @param user
     * @return Boolean
     */
    @PostMapping("/users")
    public Result saveOrUpdate(@RequestBody User user) {
        stringRedisTemplate.delete(Constants.USER_KEY);
        return Result.success(userService.saveOrUpdate(user));
    }

    /**
     * 通过id删除
     * @param id
     * @return Boolean
     */
    @DeleteMapping("/users/{id}")
    public Result deleteById(@PathVariable Integer id) {
        stringRedisTemplate.delete(Constants.USER_KEY);
        return Result.success(userService.removeById(id));
    }

    /**
     * 通过ids批量删除
     * @param ids
     * @return Boolean
     */
    @DeleteMapping("/users")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        stringRedisTemplate.delete(Constants.USER_KEY);
        return Result.success(userService.removeBatchByIds(ids));
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return 异常返回数据
     */
    @PutMapping("/users")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

    /**
     * 通过用户名查找用户
     * @param username
     * @return user
     */
    @GetMapping("/users/{username}")
    public Result getByName(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User one = userService.getOne(queryWrapper);
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(one, userDTO, true);
        return Result.success(one);
    }


    /**
     * 查询一个角色的所有的用户
     * @param role
     * @return List<User>
     */
    // @GetMapping("/user/role/{role}")
    // public Result selectUserByRole(@PathVariable String role) {
    //     QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    //     queryWrapper.eq("role", role);
    //     return Result.success(userService.list(queryWrapper));
    // }

    /**
     * 重置redis缓存
     * @return Boolean
     */
    @DeleteMapping("/users/reset")
    public Result reset() {
        return Result.success(stringRedisTemplate.delete(Constants.USER_KEY));
    }

    /**
     * 导出用户表为excel
     * @param response
     * @throws Exception
     */
    @GetMapping("/users/export")
    public void exportExcel(HttpServletResponse response) throws Exception {
        List<User> list = userService.list();
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(true);

        //自定义标题别名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("phone", "电话");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("avatarUrl", "头像");
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        IoUtil.close(out);
        writer.close();
    }

    /**
     * 将excel中用户导入
     * @param file
     * @return Boolean
     * @throws Exception
     */
    @PostMapping("/users/import")
    public Result importExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.read();
        // 要求excel表头英文
        // List<User> list = reader.readAll(User.class);
        // 表头中文
        List<List<Object>> lists = reader.read(1);
        List<User> userList = new ArrayList<>();
        for (List<Object> list : lists) {
            User user = new User();
            user.setUsername(list.get(0).toString());
            user.setPassword(list.get(1).toString());
            user.setNickname(list.get(2).toString());
            user.setEmail(list.get(3).toString());
            user.setPhone(list.get(4).toString());
            user.setAddress(list.get(5).toString());
            user.setAvatarUrl(list.get(6).toString());
            userList.add(user);
        }
        stringRedisTemplate.delete(Constants.USER_KEY);
        return Result.success(userService.saveBatch(userList));
    }


}

