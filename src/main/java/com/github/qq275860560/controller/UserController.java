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
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class UserController {


 

	/*  curl -i -X POST "http://localhost:8080/api/github/qq275860560/user/pageUser?pageNum=1&pageSize=10" -H "Authorization:Bearer  eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 
	 *  curl -i -X POST "http://username1:password1@localhost:8080/api/github/qq275860560/user/pageUser?pageNum=1&pageSize=10" 
	*/
	@RequestMapping(value = "/api/github/qq275860560/user/pageUser")
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

	
	/* curl -i -X POST "http://localhost:8080/api/github/qq275860560/user/listUser" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 	 
	*/
	@RequestMapping(value = "/api/github/qq275860560/user/listUser")
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
	
	/*  curl -i -X POST "http://localhost:8080/api/github/qq275860560/user/getUser?id=1" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw"
	 *  
	 */
 	@RequestMapping(value = "/api/github/qq275860560/user/getUser")
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
	
	
	/* curl -i -X POST "http://localhost:8080/api/github/qq275860560/user/saveUser?username=admin2" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw"
	   
	*/
	@RequestMapping(value = "/api/github/qq275860560/user/saveUser")
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
	
	
	/* curl -i -X POST "http://localhost:8080/api/github/qq275860560/user/updateUser?username=admin2"  -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 
	   
	*/
	@RequestMapping(value = "/api/github/qq275860560/user/updateUser")
	public Map<String, Object> updateUser(
			@RequestParam(required=true) String username)  throws Exception{
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
	
	/* curl -i -X POST "http://localhost:8080/api/github/qq275860560/user/deleteUser?id=1" -H "Authorization:Bearer   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJ1c2VybmFtZTEiLCJleHAiOjE4NzQ1NDY2ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJkODMzMDMyNi03MWRkLTRiNTgtOTk4Yi04OGJlNThlMmQxNTUifQ.Osw9GC9SuQQ3ESfqEFSLm0TJlsYXcTOrs5KtmZd72O91NcGSFDaoBl8R3m4DkOWjtH7syM67A8RbID-CiI43jw" 
	   curl -i -X POST "http://username1:password1@localhost:8080/api/github/qq275860560/user/deleteUser?id=1"
	*/
 	@RequestMapping(value = "/api/github/qq275860560/user/deleteUser")
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
