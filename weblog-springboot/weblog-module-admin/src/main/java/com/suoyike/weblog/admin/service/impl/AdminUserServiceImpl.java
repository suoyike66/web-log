package com.suoyike.weblog.admin.service.impl;

import com.suoyike.weblog.admin.model.vo.FindUserInfoRspVO;
import com.suoyike.weblog.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.suoyike.weblog.admin.service.AdminUserService;
import com.suoyike.weblog.common.domain.mapper.UserMapper;
import com.suoyike.weblog.common.enums.ResponseCodeEnum;
import com.suoyike.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2026-01-10 18:27
 * @description: TODO
 **/
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 修改密码
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        // 获取当前登录的用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // 拿到请求中的用户名、密码
        String requestUsername = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();

        // 验证当前用户是否有权限修改指定用户的密码
        // 在当前实现中，只允许用户修改自己的密码
        if (!currentUsername.equals(requestUsername)) {
            return Response.fail("没有权限修改其他用户的密码");
        }

        // 加密密码
        String encodePassword = passwordEncoder.encode(password);

        // 更新到数据库
        int count = userMapper.updatePasswordByUsername(requestUsername, encodePassword);

        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Override
    public Response findUserInfo() {
        // 获取存储在 ThreadLocal 中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 拿到用户名
        String username = authentication.getName();

        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }
}
