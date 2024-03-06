package com.wanwan.springboot.controller;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanwan.springboot.common.Constants;
import com.wanwan.springboot.common.Result;
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
 *  前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-01-22
 */
@RestController
@RequestMapping("/user")
public class UserController {
        @Resource
        private IUserService userService;
        @Autowired
        private StringRedisTemplate stringRedisTemplate;

        @PostMapping("/register")
        public Result register(@RequestBody UserDTO userDto){
                String username = userDto.getUsername();
                String password = userDto.getPassword();
                if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
                        return Result.error(Constants.CODE_400,"参数错误");
                }
                User dto = userService.register(userDto);
                return Result.success(dto);
        }

        @PostMapping("/login")
        public Result login(@RequestBody UserDTO userDto){
                String username = userDto.getUsername();
                String password = userDto.getPassword();
                if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
                        return Result.error(Constants.CODE_400,"参数错误");
                }
                UserDTO dto = userService.login(userDto);
                return Result.success(dto);
        }
        @PostMapping
        public Result save(@RequestBody User user) {
                stringRedisTemplate.delete(Constants.USER_KEY);
                return Result.success(userService.saveOrUpdate(user));
        }

        @DeleteMapping("/{id}")
        public Result delete(@PathVariable Integer id) {
                stringRedisTemplate.delete(Constants.USER_KEY);
                return Result.success(userService.removeById(id));
        }
        @DeleteMapping("/del/batch")
        public Result deleteBatch(@RequestBody List<Integer> ids){
                stringRedisTemplate.delete(Constants.USER_KEY);
                return Result.success(userService.removeBatchByIds(ids));
        }
        /**
         * 修改密码
         * @param userPasswordDTO
         * @return
         */
        @PostMapping("/password")
        public Result password(@RequestBody UserPasswordDTO userPasswordDTO){
                userService.updatePassword(userPasswordDTO);
                return  Result.success();
        }


        @GetMapping("/username/{username}")
        public Result findOne(@PathVariable String username) {
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username",username);
                User one = userService.getOne(queryWrapper);
                return Result.success(one);
        }
        @GetMapping("/page")
        public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam(defaultValue = "") String nickname,
                                    @RequestParam(defaultValue = "") String address,
                                    @RequestParam(defaultValue = "") String phone,
                                    @RequestParam(defaultValue = "") String email
                                    ) {
                return  Result.success(userService.findByPageOrSearch(pageNum,pageSize,username,nickname,address,phone,email));
        }

        @GetMapping("/role/{role}")
        public Result findUsersByRole(@PathVariable String role){
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("role",role);
                return Result.success(userService.list(queryWrapper));
        }
        @PostMapping("/reset")
        public Result reset(){
                stringRedisTemplate.delete(Constants.USER_KEY);
                return Result.success();
        }
        @GetMapping("/export")
        public void export(HttpServletResponse response) throws Exception{
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
                String fileName = URLEncoder.encode("用户信息","UTF-8");
                response.setHeader("Content-Disposition","attachment"+fileName+".xlsx");
                ServletOutputStream out = response.getOutputStream();
                writer.flush(out, true);
                IoUtil.close(out);
                writer.close();
        }
        @PostMapping("/import")
        public Result imp(MultipartFile file) throws Exception{
                InputStream inputStream = file.getInputStream();
                ExcelReader reader = ExcelUtil.getReader(inputStream);
                reader.read();
                // 要求excel表头英文
                // List<User> list = reader.readAll(User.class);
                // 表头中文
                List<List<Object>> lists = reader.read(1);
                List<User> userList = new ArrayList<>();
                for(List<Object> list: lists){
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

