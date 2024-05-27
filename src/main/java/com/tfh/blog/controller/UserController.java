package com.tfh.blog.controller;


import com.tfh.blog.pojo.User;
import com.tfh.blog.pojo.vo.ModifyPermissions;
import com.tfh.blog.service.UserService;
import com.tfh.blog.utils.jwt.Token;
import com.tfh.blog.utils.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 13:13
 * TODO: 用户 Controller
 */

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private Token token;


    @PostMapping("registered")
    public Result registered(@RequestBody User user) {
        return userService.registered(user);
    }

    @GetMapping("test")
    public Result test() {
        return Result.ok();
    }


    @GetMapping("userAll")
    @PreAuthorize("hasAuthority('administrator:select')")
    public Result userAll() {
        return userService.userAll();
    }

    @GetMapping("userInfo")
    public Result userInfo(HttpServletRequest req) {
        return userService.userInfo(token.getTokenById(req));
    }

    @GetMapping("getUserPermissions")
    @PreAuthorize("hasAuthority('administrator:get_user_competence')")
    public Result getUserPermissions(@RequestParam(required = false) Object searchInformation, @RequestParam(required = false) Integer count, @RequestParam(defaultValue = "1") int current) {
        return userService.getUserPermissions(searchInformation, count, current);
}

    @PutMapping("putUserPermissions")
    @PreAuthorize("hasAuthority('administrator:put_user_permissions')")
    public Result putUserPermissions(@RequestBody ModifyPermissions modifyPermissions) {
        return userService.putUserPermissions(modifyPermissions);
    }
}
