package com.example.demo2.controller;

import com.example.demo2.controller.ex.*;
import com.example.demo2.service.ex.*;
import com.example.demo2.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制器的基类
 *
 * @author soft01
 */
public abstract class BaseController {
    /**
     * 操作结果的“成功”状态
     */
    public static final Integer SUCCESS = 2000;

    /**
     * 通过session得到用户uid
     *
     * @param session
     * @return 返回用户uid
     */
    protected Integer getUidFromSession(HttpSession session) {
        return Integer.parseInt(session.getAttribute("uid").toString());
    }

    /**
     * 通过session得到用户名
     *
     * @param session
     * @return 返回用户名
     */
    protected String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("uid").toString();
    }

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> jr = new JsonResult<>();
        jr.setMessage(e.getMessage());
        if (e instanceof UsernameDuplicateException) {
            jr.setState(4000);
        } else if (e instanceof UserNotFoundException) {
            jr.setState(4001);
        } else if (e instanceof PasswordNotMatchException) {
            jr.setState(4002);
        } else if (e instanceof PasswordNotMatchException) {
            jr.setState(4003);
        } else if (e instanceof FileEmptyException) {
            jr.setState(4004);
        } else if (e instanceof FileSizeException) {
            jr.setState(4005);
        } else if (e instanceof FileTypeException) {
            jr.setState(5000);
        } else if (e instanceof UpdateException) {
            jr.setState(5001);
        } else if (e instanceof FileStateException) {
            jr.setState(6000);
        } else if (e instanceof FileIOException) {
            jr.setState(6001);
        }
        return jr;
    }
}
