package com.example.demo2.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.demo2.controller.ex.*;
import com.example.demo2.entity.User;
import com.example.demo2.service.IUserService;
import com.example.demo2.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 调用业务层，将得到的结果响应给客户端
 * 
 * @author soft01
 *
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
	
	public static final long FILE_MAX_SIZE = 1*1024*1024;
	public static final List<String> TYPES = new ArrayList<>();
	static {
		TYPES.add("image/png");
		TYPES.add("image/jpeg");
	}
	@Autowired
	private IUserService service;

	@RequestMapping("reg")
	public JsonResult<Void> reg(User user) {
		// 执行注册
		service.reg(user);
		// 操作成功
		return new JsonResult<Void>(SUCCESS);
	}

	@RequestMapping("login")
	public JsonResult<User> login(String username, String password, HttpSession session) {
		// 执行登陆,获取登陆的返回结果
		User user = service.login(username, password);
		// 将uid,username设置到session,登陆后需要使用
		session.setAttribute("uid", user.getUid());
		System.err.println("login");
		session.setAttribute("username", user.getUsername());
		session.setAttribute("avatar", user.getAvatar());
		return new JsonResult<>(SUCCESS, user);
	}

	@GetMapping("get_info")
	public JsonResult<User> getUserInfo(HttpSession session) {
		Integer uid = getUidFromSession(session);
		User user = service.getUserInfo(uid);
		return new JsonResult<>(SUCCESS,user);
	}

	@RequestMapping("change_info")
	public JsonResult<Void> changeInfo(User user, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		user.setUid(uid);
		user.setUsername(username);
		service.changeInfo(user);
		return new JsonResult<>(SUCCESS);
	}
	@RequestMapping("change_password")
	public JsonResult<Void> updatePassword(@RequestParam("old_password") String oldPassword,
			@RequestParam("new_password") String newPassword, HttpSession session) {
		// 执行修改信息业务
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		service.changePassword(uid, username, oldPassword, newPassword);
		return new JsonResult<>(SUCCESS);

	}
	
	@PostMapping("change_avatar")
	public JsonResult<String> updateAvatar(@RequestParam("file") MultipartFile file
			, HttpSession session) {
		Integer uid = Integer.parseInt(session.getAttribute("uid").toString()) ;
		String  username = session.getAttribute("username").toString();
		//获得上传图片的文件夹路径
		String dirPath = session.getServletContext().getRealPath("upload");
		System.err.println(dirPath);
		//创建一个文件目录存放上传的图片
		File dir = new File(dirPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		//判断上传图片是否为空
		if(file.isEmpty()) {
			throw new FileEmptyException("上传图片不能为空");
		}
		
		//设置上传图片的最大值
		if(file.getSize()>FILE_MAX_SIZE) {
			throw new FileSizeException("上传图片超过限制大小,请上传小于"+(FILE_MAX_SIZE/1024)+"MB的图片");
		}
		//设置允许上传的图片类型
		if(!TYPES.contains(file.getContentType())) {
			throw new FileTypeException("允许上传图片格式错误，请上传"+TYPES+"格式的图片");
		}
		//获得上传的图片名
		String getOriginalName = file.getOriginalFilename();
		
		//通过"."拆分扩展名，需注意没有"."的情况
		//得到最后一个“.”的下标
		int lastIndex = getOriginalName.lastIndexOf(".");
		String suffix = "";
			suffix = getOriginalName.substring(lastIndex);
		//得到文件名
		String fileName = UUID.randomUUID().toString()+suffix;
		System.err.println(fileName);
		//创建一个文件
		File dest = new File(dirPath,fileName);
		//将图片上传到服务器
			try {
				file.transferTo(dest);
			} catch (IllegalStateException e) {
				throw new FileStateException("文件上传状态异常");
			} catch (IOException e) {
				throw new FileIOException("文件读出失败");
			}
		String avatar = "/upload/"+fileName;
		System.err.println("avatar:"+avatar);
		service.changeAvatar(uid, username, avatar);
		return new JsonResult<>(SUCCESS,avatar);

	}
}
