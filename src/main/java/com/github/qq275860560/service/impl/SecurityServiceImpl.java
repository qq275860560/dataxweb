package com.github.qq275860560.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.github.qq275860560.service.SecurityService;

/**
 * @author jiangyuanlin@163.com
 *
 */
@Component
public class SecurityServiceImpl extends SecurityService {

	

	private Map<String, Map<String,Object>> user_cache = new HashMap<String, Map<String,Object>>() {
		{
			put("username1", 					
					new HashMap<String,Object>(){{
						put("password",new BCryptPasswordEncoder().encode("123456"));
			            put("roleNames","ROLE_USER");			         
					}}
			);
			put("admin", 					
					new HashMap<String,Object>(){{
						put("password",new BCryptPasswordEncoder().encode("123456"));
			            put("roleNames","ROLE_ADMIN,ROLE_USER");			        
					}});
		}
	};

	/**根据登录用户查询密码
	 * 在登录阶段时，要调用此接口获取到用户密码，之后跟加密后的登录密码比较
	 * 根据登录账号查询密码，此密码非明文密码，而是PasswordEncoder对明文加密后的密码，因为
	 * spring security框架中数据库默认保存的是PasswordEncoder对明文加密后的密码
	 * 用户发送的密码加密后会跟这个函数返回的密码相匹配，如果成功，则认证成功，并保存到session中，程序任何地方可以通过以下代码获取当前的username
	 * String username=(String)SecurityContextHolder.getContext().getAuthentication().getName();  
	 * 再根据用户名称查询数据库获得其他个人信息
	 * @param username 登录用户名称
	 * @return 返回加密后的用户密码字符串
	 */
	@Override
	public String getPasswordByUserName(String username) {
		// 从缓存或数据库中查找
		return (String)user_cache.get(username).get("password");
	}
	/**
	 * 根据登录用户查询对应的角色名称集合
	 * 在认证阶段时，要调用此接口初始化用户权限
	 * 如果返回null或空集合，代表该用户没有权限，这类用户其实跟匿名用户没有什么区别
	 * 如果username隶属于某高层次的角色或组织，应当把高层次的角色或组织对应的角色也返回，比如username的角色为ROLE_1, ROLE_1继承ROLE_2角色，并且username属于A部门，A部门拥有角色ROLE_3；所以应当返回ROLE_1,ROLE_2,ROLE_3
	 * @param username 登录用户名称
	 * @return 角色名称集合
	 */
	@Override
	public Set<String> getRoleNamesByUsername(String username) {// ROLE_开头
		// 从缓存或数据库中查找
		return new HashSet<>(Arrays.asList(((String)user_cache.get(username).get("roleNames")).split(",")));
	}
	
	private Map<String, Map<String, Object>> url_cache = new HashMap<String, Map<String, Object>>() {
		{

			put("/api/github/qq275860560/user/**", new HashMap<String, Object>() {//请注意正则表达式的写法，是两个*号
				{
					put("roleNames", "ROLE_ADMIN");// 只需此角色即可访问
				}
			});
			put("/api/github/qq275860560/user/pageUser", new HashMap<String, Object>() {
				{
					put("roleNames", "ROLE_USER");// 只需此权限即可访问
				}
			});

			put("/api/github/qq275860560/user/listUser", new HashMap<String, Object>() {
				{
					put("roleNames", "ROLE_USER");// 只需此权限即可访问
				}
			});
			put("/api/github/qq275860560/user/getUser", new HashMap<String, Object>() {
				{
					put("roleNames", "ROLE_USER");// 只需此权限即可访问
				}
			});
			put("/api/github/qq275860560/user/saveUser", new HashMap<String, Object>() {
				{
					put("roleNames", "");
				}
			});
			put("/api/github/qq275860560/user/deleteUser", new HashMap<String, Object>() {
				{
					put("roleNames", "");
				}
			});
			put("/api/github/qq275860560/user/updateUser", new HashMap<String, Object>() {
				{
					put("roleNames", "");
				}
			});			

		}
	};

	/**
	 * 根据请求路径查询对应的角色名称集合
	 * 在授权阶段时，要调用此接口获取权限，之后跟登录用户的权限比较
	 * 登录用户至少拥有一个角色，才能访问
	 * 如果返回null或空集合或包含ROLE_ANONYMOUS，代表该url不需要权限控制，任何用户(包括匿名)用户都可以访问
	 * 如果url符合某个正则表达式，应当把正则表达式的角色也返回，比如/api/a的角色为ROLE_1,ROLE_2, 而数据库中还存在/api/**的角色为ROLE_3,ROLE_4；由于/api/a属于正则表达式/api/*,所以应当返回ROLE_1,ROLE_2,ROLE_3,ROLE_4
	 * @param url 请求路径（ip端口之后的路径）
	 * @return 权限集合
	 */
	@Override
	public Set<String> getRoleNamesByUrI(String url) {// ROLE_开头
		// 从缓存或数据库中查找
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		Set<String> set = new HashSet<>();
		for (String pattern : url_cache.keySet()) {
			if (antPathMatcher.match(pattern, url)) {
				Map<String, Object> map = (Map<String, Object>) url_cache.get(pattern);
				if (map == null)
					continue;
				String attributesString = (String) map.get("roleNames");
				if (StringUtils.isEmpty(attributesString))
					continue;
				set.addAll(Arrays.asList(attributesString.split(",")));
			}
		}
		return set;
	}

	 

	

	
}
