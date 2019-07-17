package com.github.qq275860560.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.dao.FtpWriterDao;
import com.github.qq275860560.dao.HttpWriterDao;
import com.github.qq275860560.dao.MysqlWriterDao;
import com.github.qq275860560.dao.OutputDao;
import com.github.qq275860560.dao.TxtFileWriterDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangyuanlin@163.com
 * 
 * @apiDefine OutputController 输出流接口
 * @apiError {Object} data 返回数据
 * @apiError {String} msg 说明
 * @apiError {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 * @apiSuccess {Object} data 返回数据
 * @apiSuccess {String} msg 说明
 * @apiSuccess {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 */
@RestController
@Slf4j
public class OutputController {


  
	@Autowired
	private OutputDao outputDao;
	@Autowired
	private MysqlWriterDao mysqlWriterDao;
	@Autowired
	private TxtFileWriterDao txtFileWriterDao;
	@Autowired
	private HttpWriterDao httpWriterDao;
	@Autowired
	private FtpWriterDao ftpWriterDao;
 
 
	/**
	 * @api {POST} /api/output/checkOutput  校验唯一性
	 * @apiGroup OutputController
	 * @apiName /api/output/checkOutput
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>校验唯一性，成功code返回200 </p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-输出流管理-新建-校验合法性</font></li></p>
	 * <p><li><font color="red">数据交换组件-输出流管理-编辑-校验合法性</font></li></p>
	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} id ID,新建时为空，编辑时必填，
	 * @apiParam {String} name 要校验唯一性的名称，必填
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=2&name=name2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/output/checkOutput' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Boolean} data 校验结果{true:合法,false:不合法}		
	 * 
	 * @apiSuccessExample {json} 成功返回(校验成功时): 
	 * {"code":200,"msg":"名称有效","data":true}
	 * @apiSuccessExample {json} 成功返回(校验失败时):
	 * {"code":200,"msg":"名称已存在","data":false}	 
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"名称必填","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/output/checkOutput
	 *	 
	 */
	@RequestMapping(value = "/api/output/checkOutput")
	public Map<String, Object> checkOutput(@RequestParam Map<String, Object> requestMap) throws Exception {
		String id = (String) requestMap.get("id");
		String name = (String) requestMap.get("name");
		if (StringUtils.isEmpty(name)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "名称必填");
					put("data", null);
				}
			};
		}
		boolean data = outputDao.checkOutput(id, name);
		String msg = data == true ? "名称有效" : "名称已存在";
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", msg);
				put("data", data);
			}
		};

	}
	

	/**
	 * @api {POST} /api/output/pageOutput  分页搜索输出流
	 * @apiGroup OutputController
	 * @apiName /api/output/pageOutput
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>分页搜索输出流，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-输出流管理</font></li></p>
	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} name 名称
	 * @apiParam {Integer} pageNum 查询页码，从1开始计算
     * @apiParam {Integer} pageSize 每页展示的条数
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * pageNum=1&pageSize=10
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/output/pageOutput?pageNum=1&pageSize=10' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data 返回对象
	 * @apiSuccess (data对象字段数据:) {Integer} total 记录总数，前端可以根据此值和pageSize，pageNum计算其他分页参数
	 * @apiSuccess (data对象字段数据:) {Object[]} pageList 数组
     * @apiSuccess (pageList数组每个对象字段数据:) {String} id Id
	 * @apiSuccess (pageList数组每个对象字段数据:) {String} name 名称
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":{"total":100,"pageList":[{"id":"XXX","name":"XXX"}]}}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/output/pageOutput
	 *	 
	 */
	@RequestMapping(value = "/api/output/pageOutput")
	public Map<String, Object> pageOutput(
			@RequestParam Map<String, Object> requestMap
			)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);	
		
		String name=(String)requestMap.get("name");
		String type=(String)requestMap.get("type");
	
		String createUserName=(String)requestMap.get("createUserName");
		String startCreateTime=(String)requestMap.get("startCreateTime");
		String endCreateTime=(String)requestMap.get("endCreateTime");
		Integer pageNum =requestMap.get("pageNum")==null?1:Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize =requestMap.get("pageSize")==null?10:Integer.parseInt(requestMap.get("pageSize").toString());
		 
		Map<String, Object> data = outputDao.pageOutput(null,name,  type,  null, createUserName, startCreateTime, endCreateTime, pageNum, pageSize) ;
		return new HashMap<String, Object>() {
			{				 
				put("code", HttpStatus.OK.value());//此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");//此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);								
			}
		};
	}

	 
	

	/**
	 * @api {POST} /api/output/getOutput  获取输出流详情
	 * @apiGroup OutputController
	 * @apiName /api/output/getOutput
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>获取输出流详情，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-输出流管理-编辑</font></li></p>
	 * <p><li><font color="red">数据交换组件-输出流管理-详情</font></li></p>
	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} id ID
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=1
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/output/getOutput?id=1' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data 返回对象
	 * @apiSuccess (data对象字段数据:) {String} id Id
	 * @apiSuccess (data对象字段数据:) {String} name 名称
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":{"id":"XXX","name":"XXX"}}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/output/getOutput
	 *	 
	 */
 	@RequestMapping(value = "/api/output/getOutput")
	public Map<String, Object> getOutput(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id=(String)requestMap.get("id");
		Map<String, Object> outputMap=outputDao.getOutput(id);		
		
		String type = (String)outputMap.get("type");
		Map<String,Object> data = new HashMap<>();
		if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_MYSQLWRITER)) {
			data.putAll(mysqlWriterDao.getMysqlWriter(id));
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_TXTFILEWRITER)) {
			data.putAll(txtFileWriterDao.getTxtFileWriter(id));
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_HTTPWRITER)) {
			data.putAll(httpWriterDao.getHttpWriter(id));
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_FTPWRITER)) {
			data.putAll(ftpWriterDao.getFtpWriter(id));
		}
		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}
	
	
 
 	/**
	 * @api {POST} /api/ouput/saveOutput  保存输出流
	 * @apiGroup OutputController
	 * @apiName /api/ouput/saveOutput
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>保存输出流，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-输出流管理-新建-确定</font></li></p>

	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} name 名称
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * name=outputName1
	 * 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/output/saveOutput?name=outputName1' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data null
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/output/saveOutput
	 *	 
	 */
	@RequestMapping(value = "/api/output/saveOutput")
	public Map<String, Object> saveOutput(@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);		
	 
		String id=UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);
		
		String name = (String) requestMap.get("name");
		if (StringUtils.isEmpty(name)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "名称不能为空");
					put("data", null);
				}
			};
		}
		
		String type = (String) requestMap.get("type");
		if (StringUtils.isEmpty(type)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "输出流类型不能为空");
					put("data", null);
				}
			};
		}
				
		String createUserName=currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		
		if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_MYSQLWRITER)) {
			mysqlWriterDao.saveMysqlWriter(requestMap);
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_TXTFILEWRITER)) {
			txtFileWriterDao.saveTxtFileWriter(requestMap);
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_HTTPWRITER)) {
			httpWriterDao.saveHttpWriter(requestMap);
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_FTPWRITER)) {
			ftpWriterDao.saveFtpWriter(requestMap);
		}	
		outputDao.saveOutput(requestMap);
		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}
	


	
	/**
	 * @api {POST} /api/output/updateOutput  更新输出流
	 * @apiGroup OutputController
	 * @apiName /api/output/updateOutput
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>更新输出流，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-输出流管理-编辑-确定</font></li></p>

	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} id ID
	 * @apiParam {String} name 名称
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=2&name=outputName2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/output/updateOutput?id=2&name=outputName2' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data null
	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/output/updateOutput
	 *	 
	 */
	@RequestMapping(value = "/api/output/updateOutput")
	public Map<String, Object> updateOutput(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		// name,type不允许修改
		String id = (String) requestMap.get("id");	
		Map<String, Object> outputMap = outputDao.getOutput(id);
		String type = (String)outputMap.get("type");
		Map<String, Object> writerMap = null;
		if(type.equals(Constant.OUTPUT_TYPE_MYSQLWRITER)) {
			writerMap=mysqlWriterDao.getMysqlWriter(id);
			writerMap.putAll(requestMap);		
			mysqlWriterDao.updateMysqlWriter(writerMap);
		}else if(type.equals(Constant.OUTPUT_TYPE_TXTFILEWRITER)) {
			writerMap=txtFileWriterDao.getTxtFileWriter(id);
			writerMap.putAll(requestMap);		
			txtFileWriterDao.updateTxtFileWriter(writerMap);
		}else if(type.equals(Constant.OUTPUT_TYPE_HTTPWRITER)) {
			writerMap=httpWriterDao.getHttpWriter(id);
			writerMap.putAll(requestMap);		
			httpWriterDao.updateHttpWriter(writerMap);
		}else if(type.equals(Constant.OUTPUT_TYPE_FTPWRITER)) {
			writerMap=ftpWriterDao.getFtpWriter(id);
			writerMap.putAll(requestMap);		
			ftpWriterDao.updateFtpWriter(writerMap);
		}
		 
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}
	
	/**
	 * @api {POST} /api/output/deleteOutput  删除输出流
	 * @apiGroup OutputController
	 * @apiName /api/output/deleteOutput
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>删除输出流，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-输出流管理-删除</font></li></p>
		  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} id ID
	 * 
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=1
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/output/deleteOutput?id=1' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {Integer} code 状态码, {200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息
	 * @apiSuccess (返回结果:) {Object} data null

	 * 
	 * @apiSuccessExample {json} 成功返回: 
	 * {"code":200,"msg":"请求成功","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":400,"msg":"XXX参数不规范","data":null}	
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":401,"msg":"token已过期","data":null}
	 * @apiErrorExample {json} 失败返回: 
	 * {"code":403,"msg":"用户无权限访问该接口","data":null}
	 *  
	 * @apiSampleRequest /api/output/deleteOutput
	 *	 
	 */
 	@RequestMapping(value = "/api/output/deleteOutput")
	public Map<String, Object> deleteOutput(
			@RequestParam Map<String, Object> requestMap)  throws Exception{
		String currentLoginUsername=(String)SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id = (String) requestMap.get("id");
		Map<String, Object> outputMap = outputDao.getOutput(id);	
		
		String type = (String)outputMap.get("type");
		if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_MYSQLWRITER)) {
			mysqlWriterDao.deleteMysqlWriter(id);
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_TXTFILEWRITER)) {
			txtFileWriterDao.deleteTxtFileWriter(id);
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_HTTPWRITER)) {
			httpWriterDao.deleteHttpWriter(id);
		}else if(type.equalsIgnoreCase(Constant.OUTPUT_TYPE_FTPWRITER)) {
			ftpWriterDao.deleteFtpWriter(id);
		}
		 
		outputDao.deleteOutput(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}
	 
 	
}
