package com.github.qq275860560.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangyuanlin@163.com
 * 
 * @apiDefine UserController 用户接口
 * @apiError {Object} data 返回数据
 * @apiError {String} msg 说明
 * @apiError {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 * @apiSuccess {Object} data 返回数据
 * @apiSuccess {String} msg 说明
 * @apiSuccess {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 */
@RestController
@Slf4j
public class UserController {

	 
    /**
	 * @api {POST} /login  登录用户（获取令牌）
	 * @apiGroup UserController
	 * @apiName /login
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription  
	 * <p>登录用户（获取令牌），成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>
	 * <p><li><font color="red">XXX系统-XXX菜单-XXX页面-XXX按钮/链接</font></li></p>
 	 * 
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8"
	 * }
	 * 
	 * @apiParam {String} username 用户名称
	 * @apiParam {String} password 用户密码
	 * 	 
	 * @apiParamExample {String} 请求参数示例:
	 * username=admin&password=123456
	 * 	 
	 * @apiExample {curl} 命令行调用示例:  
	 * curl  -i -X  POST http://localhost:8045/login?username=admin&password=123456	  
	 
	 * @apiSuccess (返回结果:) {Integer} code 状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息，校验结果的提示信息
	 * @apiSuccess (返回结果:) {String} token_type 默认为bearer
	 * @apiSuccess (返回结果:) {Long} expires_in 过期时间
	 * @apiSuccess (返回结果:) {String} access_token 用户令牌 ,接下来所有访问该应用的请求都带上token，请在有效期内使用，如果即将过期，可以调用/refreshToken刷新token
	 *  
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":0,"msg":"请求成功","access_token":"XXX"}
	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX","data":null}
	 * @apiUse UserController
	 * @apiSampleRequest /login
	 * 
	 */	
	public void login() {}
 

	/*  curl -i -X POST "http://localhost:8045/api/user/pageUser?pageNum=1&pageSize=10" -H "Authorization:Bearer  eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 
	 *  curl -i -X POST "http://username1:password1@localhost:8045/api/user/pageUser?pageNum=1&pageSize=10" 
	*/
	@RequestMapping(value = "/api/user/pageUser")
	public Map<String, Object> pageUser(@RequestParam(required=false) Integer pageNum,@RequestParam(required=false) Integer pageSize)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		//模拟数据库执行
		Map<String, Object> data = new HashMap<String,Object>() {{			
			put("total", 2);
			put("pageList", Arrays.asList(new HashMap<String, Object>() {
				{
					put("userId", "1");
					put("username", "admin");
					put("roleNames", "ROLE_ADMIN");
				}
			}, new HashMap<String, Object>() {
				{
					put("userId", "2");
					put("username", "admin2");
					put("roleNames", "ROLE_ADMIN");
				}
			}));
		}};;
		return new HashMap<String, Object>() {
			{
				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	
	/* curl -i -X POST "http://localhost:8045/api/user/listUser" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 	 
	*/
	@RequestMapping(value = "/api/user/listUser")
	public Map<String, Object> listUser(@RequestParam(required=false) String username )  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		//模拟数据库执行
		List<Map<String, Object>> data = Arrays.asList(new HashMap<String, Object>() {
			{
				put("userId", "1");
				put("username", "admin");
				put("roleNames", "ROLE_ADMIN");
			}
		}, new HashMap<String, Object>() {
			{
				put("userId", "2");
				put("username", "admin2");
				put("roleNames", "ROLE_ADMIN");
			}
		});
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取列表成功");
				put("data", data);
			}
		};
	}
	
	/*  curl -i -X POST "http://localhost:8045/api/user/getUser?id=1" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw"
	 *  
	 */
 	@RequestMapping(value = "/api/user/getUser")
	public Map<String, Object> getUser(@RequestParam(required=true) String id)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		//模拟数据库执行
		Map<String, Object> data=new HashMap<String, Object>() {
			{
				put("userId", id);
				put("username", "username" + id);
				put("roleNames", "ROLE_ADMIN");
			}
		};
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
	/* curl -i -X POST "http://localhost:8045/api/user/saveUser?username=admin2" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw"
	   
	*/
	@RequestMapping(value = "/api/user/saveUser")
	public Map<String, Object> saveUser(@RequestParam(required=true) String username)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		//模拟数据库执行
		log.info("数据库执行");
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}
	
	
	/* curl -i -X POST "http://localhost:8045/api/user/updateUser?username=admin2"  -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 
	   
	*/
	@RequestMapping(value = "/api/user/updateUser")
	public Map<String, Object> updateUser(
			@RequestParam(required=false) String username)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		//模拟数据库执行
		log.info("数据库执行");
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	/* curl -i -X POST "http://localhost:8045/api/user/deleteUser?id=1" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 
	   curl -i -X POST "http://username1:password1@localhost:8045/api/user/deleteUser?id=1"
	*/
 	@RequestMapping(value = "/api/user/deleteUser")
	public Map<String, Object> deleteUser(
			@RequestParam(required=true) String id)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		//模拟数据库执行
		log.info("数据库执行");
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	
	
	
}
