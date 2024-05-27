package com.tfh.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tfh.blog.mapper.*;
import com.tfh.blog.pojo.*;
import com.tfh.blog.pojo.vo.ModifyPermissions;
import com.tfh.blog.pojo.vo.TreePermissions;
import com.tfh.blog.pojo.vo.UserAndPermissionTable;
import com.tfh.blog.service.UserService;
import com.tfh.blog.utils.result.Result;
import com.tfh.blog.utils.result.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author B
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2024-05-19 01:48:51
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Autowired
    private BlacklistAuthorityMapper blacklistAuthorityMapper;

    @Override
    public Result registered(User user) {

        User userBasedOnUserName = userMapper.getUserBasedOnUserName(user.getUserName());

        if (userBasedOnUserName != null) {
            return Result.error(ResultType.USER_ALREADY_EXISTS, null);
        }

        user.setUserPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getUserPassword()));
        userMapper.insert(user);
        userRoleMapper.initialRole(user.getUserId());
        userInfoMapper.initialUserInformation(user.getUserId());
        return Result.ok();
    }

    @Override
    public Result userAll() {
        List<User> users = userMapper.selectList(null);
        return Result.ok(users);
    }

    @Override
    public Result userInfo(Long tokenById) {
        UserInfo info = userInfoMapper.getUserInfoById(tokenById);

        return Result.ok(info);
    }

    @Override
    public Result getUserPermissions(Object searchInformation, Integer count, int current) {


        //获取权限
        List<Role> roleInformation = roleMapper.getRoleInformation();
        ArrayList<TreePermissions> treePermissionsList = new ArrayList<>();
        //权限信息封装
        roleInformation.forEach(role -> {
            List<Authority> permissionInformation = roleMapper.getPermissionInformation(role.getId());
            TreePermissions treePermissions = new TreePermissions();
            treePermissions.setKey(role.getRoleName());
            treePermissions.setLabel(role.getRoleName());
            treePermissions.setChildren(new ArrayList<>());
            permissionInformation.forEach(authority -> {
                TreePermissions treePermissionsChildren = new TreePermissions();
                treePermissionsChildren.setKey(authority.getPermissionName());
                treePermissionsChildren.setLabel(authority.getPermissionName());
                treePermissions.getChildren().add(treePermissionsChildren);
            });
            treePermissionsList.add(treePermissions);
        });

        //用户数据
        ArrayList<UserAndPermissionTable> userAndPermissionTablesList = new ArrayList<>();
        //分页查询
        Page<User> userPageList = userMapper.selectPage(new Page<>(current, 18), new LambdaQueryWrapper<>());
        List<User> users = userPageList.getRecords();
        long pages = userPageList.getPages();


        //用户数据封装
        users.forEach(user -> {
            UserAndPermissionTable userAndPermissionTable = new UserAndPermissionTable();
            userAndPermissionTable.setUserId(user.getUserId());
            userAndPermissionTable.setUserName(user.getUserName());
            userAndPermissionTable.setRoleId(roleMapper.getRoleBasedOnUserId(user.getUserId()));
            List<String> userAuthentication = userMapper.getUserAuthentication(user.getUserId());
            userAndPermissionTable.setGrantedPermission(userAuthentication);
            userAndPermissionTable.setCheckedKeys(userAuthentication);
            userAndPermissionTablesList.add(userAndPermissionTable);
        });


        //存到List数组返回数据
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(treePermissionsList);
        objects.add(userAndPermissionTablesList);
        objects.add(pages);
        return Result.ok(objects);

    }

    @Override
    public Result putUserPermissions(ModifyPermissions modifyPermissions) {

        if (modifyPermissions.getUserId() != null && modifyPermissions.getPermissions().size() != 0) {
            //过滤不必要的数据并获取当前需要的权限
            List<String> permissionsList = modifyPermissions.getPermissions().stream().filter(f -> f.contains(":")).toList();
            //拿出角色信息
            Set<String> strings = new HashSet<>();
            permissionsList.forEach(e -> strings.add(String.copyValueOf(e.toCharArray(), 0, e.indexOf(":"))));
            //删除用户的角色和黑名单权限
            userRoleMapper.deleteUserAllRole(modifyPermissions.getUserId());
            blacklistAuthorityMapper.deleteUserAllBlacklist(modifyPermissions.getUserId());

            //获取角色id
            LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            strings.forEach(s -> {
                roleLambdaQueryWrapper.eq(Role::getRoleName, s)
                        .or();
            });
            List<Role> roles = roleMapper.selectList(roleLambdaQueryWrapper);
            //添加角色信息
            userRoleMapper.addUserRole(modifyPermissions.getUserId(), roles);
            //获取所有权限信息
            List<Authority> authorities = authorityMapper.selectList(null);

            //根据所有权限进行减法操作，后填入黑名单
            for (int i = 0; i < permissionsList.size(); i++) {
                for (int i1 = 0; i1 < authorities.size(); i1++) {
                    if (permissionsList.get(i).equals(authorities.get(i1).getPermissionName())) {
                        authorities.remove(i1);
                    }
                }
            }


            if (authorities != null && authorities.size() != 0) {
                blacklistAuthorityMapper.addUserBlacklist(modifyPermissions.getUserId(), authorities);
            }

            return Result.ok();
        }


        return Result.error(ResultType.PERMISSION_DELETION_FAILED, null);
    }


}




