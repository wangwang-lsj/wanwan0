package com.wanwan.springboot.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanwan.springboot.common.Result;
import com.wanwan.springboot.entity.LeaveWord;
import com.wanwan.springboot.utils.IpUtil;
import org.springframework.web.bind.annotation.*;

import com.wanwan.springboot.service.ILeaveWordService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wanwan
 * @since 2024-03-19
 */
@RestController
@RequestMapping("/api/leavewords")
public class LeaveWordController {
    @Resource
    private ILeaveWordService leaveWordService;


    // @GetMapping("/page")
    // public Result page(@RequestParam Integer pageNum,
    //                    @RequestParam Integer pageSize) {
    //         Map<String, Object> map = new HashMap<>();
    //         map.put("records", leaveWordService.getByPage(new Page<>(pageNum, pageSize)));
    //         map.put("total", leaveWordService.count());
    //         return Result.success(map);
    // }
    @GetMapping("/page")
    public Result page(@RequestHeader(name = "Referer", required = false) String Referer,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize
    ) {
        System.out.println(Referer);
        if (Referer.contains("manage")) {
            QueryWrapper<LeaveWord> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("create_time");
            return Result.success(leaveWordService.page(new Page<>(pageNum, pageSize), queryWrapper));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("records", leaveWordService.getByPage(new Page<>(pageNum, pageSize)));
        map.put("total", leaveWordService.count());
        return Result.success(map);
    }

    @GetMapping
    public Result getAll() {
        return Result.success(leaveWordService.list());
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(leaveWordService.getById(id));
    }


    // 新增
    @PostMapping()
    public Result leaveMessage(@RequestBody LeaveWord leaveWord, HttpServletRequest request) {
        leaveWord.setIp(IpUtil.getIpAddr(request));
        System.out.println(leaveWord.getIp());
        return Result.success(leaveWordService.save(leaveWord));
    }

    @PatchMapping()
    public Result show(@RequestBody LeaveWord leaveWord) {
        UpdateWrapper<LeaveWord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", leaveWord.getId());
        updateWrapper.set("enable", leaveWord.getEnable());
        return Result.success(leaveWordService.update(updateWrapper));
    }

    @PutMapping("/reply")
    public Result reply(@RequestBody LeaveWord leaveWord) {
        leaveWord.setReplyTime(DateUtil.date());
        return Result.success(leaveWordService.saveOrUpdate(leaveWord));
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id) {
        leaveWordService.removeById(id);
        return Result.success();
    }

    @DeleteMapping()
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        leaveWordService.removeByIds(ids);
        return Result.success();
    }


}

