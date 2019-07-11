package com.github.qq275860560.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.common.util.JenkinsUtil;
import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.dao.BuildDao;
import com.github.qq275860560.dao.InputDao;
import com.github.qq275860560.dao.JobDao;
import com.github.qq275860560.dao.MysqlReaderDao;
import com.github.qq275860560.dao.MysqlWriterDao;
import com.github.qq275860560.dao.OutputDao;
import com.github.qq275860560.dao.TransformerDao;
import com.github.qq275860560.dao.TxtFileWriterDao;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jiangyuanlin@163.com
 * 
 * @apiDefine JobController 计划任务接口
 * @apiError {Object} data 返回数据
 * @apiError {String} msg 说明
 * @apiError {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 * @apiSuccess {Object} data 返回数据
 * @apiSuccess {String} msg 说明
 * @apiSuccess {Integer} code 返回状态码,{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
 */
@RestController
@Slf4j
public class JobController {

	@Value("${jenkinsUrl}")
	private String jenkinsUrl;
	@Autowired
	private RestTemplate restTemplate  ;
	@Autowired
	private JobDao jobDao;
	@Autowired
	private BuildDao buildDao;
	@Autowired
	private InputDao inputDao;
	@Autowired
	private MysqlReaderDao mysqlReaderDao;
	@Autowired
	private OutputDao outputDao;
	@Autowired
	private MysqlWriterDao mysqlWriterDao;
	@Autowired
	private TxtFileWriterDao txtFileWriterDao;
	@Autowired
	private TransformerDao transformerDao;
	
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * @api {POST} /api/job/checkJob  校验唯一性
	 * @apiGroup JobController
	 * @apiName /api/job/checkJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>校验唯一性，成功code返回200 </p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-新建-校验合法性</font></li></p>
	 * <p><li><font color="red">数据交换组件-计划任务管理-编辑-校验合法性</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/checkJob' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/checkJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/checkJob")
	public Map<String, Object> checkJob(@RequestParam Map<String, Object> requestMap) throws Exception {
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
		boolean data = jobDao.checkJob(id, name);
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
	 * @api {POST} /api/job/pageJob  分页搜索计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/pageJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>分页搜索计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/pageJob?pageNum=1&pageSize=10' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/pageJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/pageJob")
	public Map<String, Object> pageJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String name = (String) requestMap.get("name");
		String inputName = (String) requestMap.get("inputName"); 
		String inputType = (String) requestMap.get("inputType"); 
		String outputName = (String) requestMap.get("outputName");
		String outputType = (String) requestMap.get("outputType");
		
		String transformerName = (String) requestMap.get("transformerName");
		String transformerType = (String) requestMap.get("transformerType");
 
		Integer status = requestMap.get("status") == null ? null
				: Integer.parseInt(requestMap.get("status").toString());
		Double lastBuildProgress = requestMap.get("lastBuildProgress") == null ? null
				: Double.parseDouble(requestMap.get("lastBuildProgress").toString());
		String createUserName = (String) requestMap.get("createUserName");
		String startCreateTime = (String) requestMap.get("startCreateTime");
		String endCreateTime = (String) requestMap.get("endCreateTime");
		log.info("" + requestMap.get("pageNum"));
		Integer pageNum = requestMap.get("pageNum") == null ? 1
				: Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize = requestMap.get("pageSize") == null ? 10
				: Integer.parseInt(requestMap.get("pageSize").toString());

		Map<String, Object> data = jobDao.pageJob(
				null, name, null, inputName, 
				inputType,null,outputName,outputType,
				null,transformerName,transformerType,
				null, status,
				null,null,null,	null,null, 
				null,null,null,lastBuildProgress,  
				null,createUserName, startCreateTime, endCreateTime, pageNum,pageSize);

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());// 此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");// 此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);
			}
		};
	}

	/**
	 * @api {POST} /api/job/getJob  获取计划任务详情
	 * @apiGroup JobController
	 * @apiName /api/job/getJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>获取计划任务详情，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-编辑</font></li></p>
	 * <p><li><font color="red">数据交换组件-计划任务管理-详情</font></li></p>
	  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/getJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/getJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/getJob")
	public Map<String, Object> getJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		//TODO 把具体的输入流输出流，转换清洗规则也加载出来
		String id = (String) requestMap.get("id");
		Map<String, Object> data = jobDao.getJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取对象成功");
				put("data", data);
			}
		};
	}

	
	
	/**
	 * @api {POST} /api/job/saveJob  保存计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/saveJob 
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>保存计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-新建-确定</font></li></p>

	  
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
	 * name=jobName1&inputId=&inputName=inputName1&readerId=&type=mysqlreader&outputId=1&outputName=outputName1&writerId=&type=mysqlwriter
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/job/saveJob?name=jobName1&inputId=&inputName=inputName1&readerId=&type=mysqlreader&outputId=1&outputName=outputName1&writerId=&type=mysqlwriter' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/saveJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/saveJob")
	public Map<String, Object> saveJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = UUID.randomUUID().toString().replace("-", "");
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

		//生成datax格式的reader
		Map<String, Object> readerMap = null;
		String inputId = (String) requestMap.get("inputId");
		if (StringUtils.isEmpty(inputId)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "输入流不能为空");
					put("data", null);
				}
			};
		}		
		Map<String, Object> inputMap = inputDao.getInput(inputId);
		requestMap.put("inputName", inputMap.get("name"));
		requestMap.put("inputType", inputMap.get("type"));
		String inputType = (String)inputMap.get("type");
		if(inputType.equalsIgnoreCase(Constant.INPUT_TYPE_MYSQLREADER)) {
			readerMap = generateReaderMap(mysqlReaderDao.getMysqlReader(inputId));		
		}

		//生成datax格式的writer
		Map<String, Object> writerMap = null;
		String outputId = (String) requestMap.get("outputId");
		if (StringUtils.isEmpty(inputId)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "输出流不能为空");
					put("data", null);
				}
			};
		}		
		Map<String, Object> outputMap = outputDao.getOutput(outputId);
		requestMap.put("outputName", outputMap.get("name"));
		requestMap.put("outputType", outputMap.get("type"));
		String outputType = (String)outputMap.get("type");
		if(outputType.equalsIgnoreCase(Constant.OUTPUT_TYPE_MYSQLWRITER)) {
			writerMap = generateWriterMap(mysqlWriterDao.getMysqlWriter(outputId));	
		}else if(outputType.equalsIgnoreCase(Constant.OUTPUT_TYPE_TXTFILEWRITER)) {
			writerMap = generateWriterMap(txtFileWriterDao.getTxtFileWriter(outputId));		
		}
		
		//生成datax格式的transformer
		List<HashMap<String, Object>> transformerList = new ArrayList<>();
		String transformerId = (String) requestMap.get("transformerId");
		Map<String, Object> transformerMap = transformerDao.getTransformer(transformerId);
		requestMap.put("transformerName", transformerMap.get("name"));
		requestMap.put("transformerType", transformerMap.get("type"));
		if(transformerId!=null) {
			transformerList = generateTransformerList(transformerMap);		 
		}		
		String dataxJson = objectMapper.writeValueAsString(generateDataxMap(readerMap,writerMap,transformerList));
		requestMap.put("dataxJson", dataxJson);

		
		requestMap.put("status", Constant.JOB_STATUS_ENABLE);
		requestMap.put("nextBuildNumber", "1");
		Double progress = 0.0;
		requestMap.put("progress", progress);

		String createUserName = currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		jobDao.saveJob(requestMap);
		
	    //TODO 这部分使用异步处理	
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, dataxJson, "UTF-8");

		String command = "python " + Constant.DATAX_HOME + File.separator + "bin" + File.separator + "datax.py "
				+ file.getAbsolutePath() ;		
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/createItem?name=%s", jenkinsUrl, name),
				HttpMethod.POST, new HttpEntity<>(JenkinsUtil.generateJenkinsJobXml(command), new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		//		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}

	public static Map<String, Object> generateDataxMap(Map<String, Object> readerMap ,
			Map<String, Object> writerMap ,
			List<HashMap<String, Object>> transformerList 
			
			)
			throws IOException, JsonParseException, JsonMappingException {
		
		Map<String, Object> dataxMap = new HashMap<String, Object>() {
			{
				put("job", new HashMap<String, Object>() {
					{
						put("setting", new HashMap<String, Object>() {
							{
								put("speed", new HashMap<String, Object>() {
									{
										put("channel", 3);
									}
								});
								put("errorLimit", new HashMap<String, Object>() {
									{
										put("record", 0);
										put("percentage", 0.02);
									}
								});

							}
						});
						put("content", new ArrayList<Map<String, Object>>() {
							{
								add(new HashMap<String, Object>() {
									{
										put("reader", readerMap);
										put("writer", writerMap);
										if(!transformerList.isEmpty()) {
											put("transformer", transformerList);
										}

									}
								});
							}
						});

					}
				});
			}
		};
		return dataxMap;
	}

	public static Map<String, Object> generateReaderMap(Map<String, Object> requestMap) {
		String type = (String) requestMap.get("type");
		if (Constant.INPUT_TYPE_MYSQLREADER.equals(type)) {
			return new HashMap<String, Object>() {
				{
					put("name", type);
					put("parameter", new HashMap<String, Object>() {
						{
							String username = (String) requestMap.get("parameterUsername");
							put("username", username);
							String password = (String) requestMap.get("parameterPassword");
							put("password", password);

							String[] column = ((String) requestMap.get("parameterColumn")).split(",");
							put("column", column);

							String where = (String) requestMap.get("parameterWhere");
							put("where", where);

							put("connection", new ArrayList<HashMap<String, Object>>() {
								{
									add(new HashMap<String, Object>() {
										{
											String[] jdbcUrl = ((String) requestMap
													.get("parameterConnectionJdbcUrl")).split(",");
											put("jdbcUrl", jdbcUrl);
											String[] table = ((String) requestMap.get("parameterConnectionTable"))
													.split(",");
											put("table", table);
										}
									});

								}
							});

						}
					});

				}
			};
		}
		return null;
	}

	public static Map<String, Object> generateWriterMap(Map<String, Object> requestMap)
			throws IOException, JsonParseException, JsonMappingException {
		String type = (String) requestMap.get("type");

		if (Constant.OUTPUT_TYPE_MYSQLWRITER.equals(type)) {
			return new HashMap<String, Object>() {
				{
					put("name", type);
					put("parameter", new HashMap<String, Object>() {
						{
							String username = (String) requestMap.get("parameterUsername");
							put("username", username);
							String password = (String) requestMap.get("parameterPassword");
							put("password", password);

							String writeMode = (String) requestMap.get("parameterWriteMode");
							put("writeMode", writeMode);

							String[] column = ((String) requestMap.get("parameterColumn")).split(",");
							put("column", column);

							String[] preSql = ((String) requestMap.get("parameterPreSql")).split(",");
							put("preSql", preSql);

							put("connection", new ArrayList<HashMap<String, Object>>() {
								{
									add(new HashMap<String, Object>() {
										{
											String jdbcUrl = (String) requestMap
													.get("parameterConnectionJdbcUrl");
											put("jdbcUrl", jdbcUrl);

											String[] table = ((String) requestMap.get("parameterConnectionTable"))
													.split(",");
											put("table", table);
										}
									});

								}
							});

						}
					});

				}
			};
		}else if (Constant.OUTPUT_TYPE_TXTFILEWRITER.equals(type)) {
			return new HashMap<String, Object>() {
				{
					put("name", type);
					put("parameter", new HashMap<String, Object>() {
						{
							String path = (String) requestMap.get("parameterPath");
							put("path", path);
							String fileName = (String) requestMap.get("parameterFileName");
							put("fileName", fileName);
							String writeMode = (String) requestMap.get("parameterWriteMode");
							put("writeMode", writeMode);
						}
					});

				}
			};
		}
		return null;

	}

	
	
	public static List<HashMap<String, Object>> generateTransformerList(Map<String, Object> requestMap)
			throws IOException, JsonParseException, JsonMappingException {
		String type = (String) requestMap.get("type");
		
		if (  Constant.TRANSFORMER_TYPE_GROOVY.equals(type) ) {			
			return new ArrayList<HashMap<String, Object>>() {
				{
					add(new HashMap<String, Object>() {
						{
							put("name", type);
							put("parameter", new HashMap<String, Object>() {
								{
									String parameterCode = (String) requestMap.get("parameterCode");
									put("code", parameterCode);
								 

									String[] column = ((String) requestMap.get("parameterExtraPackage")).split("\n");
									put("extraPackage", column);

									 

								}
							});
						}
					});

				}
			};
		}
		return Collections.EMPTY_LIST;
					
				 
	}

	
	/**
	 * @api {POST} /api/job/updateJob  更新计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/updateJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>更新计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-编辑-确定</font></li></p>

	  
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
	 * id=2&name=jobName2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -i -X POST 'http://localhost:8045/api/job/updateJob?id=2&name=jobName2' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/updateJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/updateJob")
	public Map<String, Object> updateJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		// name不允许修改
		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
				
		//生成datax格式的reader
		Map<String, Object> readerMap = null;
		String inputId = (String) requestMap.get("inputId");
		if (StringUtils.isEmpty(inputId)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "输入流不能为空");
					put("data", null);
				}
			};
		}		
		Map<String, Object> inputMap = inputDao.getInput(inputId);
		requestMap.put("inputName", inputMap.get("name"));
		requestMap.put("inputType", inputMap.get("type"));
		String inputType = (String)inputMap.get("type");
		if(inputType.equalsIgnoreCase(Constant.INPUT_TYPE_MYSQLREADER)) {
			readerMap = generateReaderMap(mysqlReaderDao.getMysqlReader(inputId));		
		}

		//生成datax格式的writer
		Map<String, Object> writerMap = null;
		String outputId = (String) requestMap.get("outputId");
		if (StringUtils.isEmpty(inputId)) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "输出流不能为空");
					put("data", null);
				}
			};
		}		
		Map<String, Object> outputMap = outputDao.getOutput(outputId);
		requestMap.put("outputName", outputMap.get("name"));
		requestMap.put("outputType", outputMap.get("type"));
		String outputType = (String)outputMap.get("type");
		if(outputType.equalsIgnoreCase(Constant.OUTPUT_TYPE_MYSQLWRITER)) {
			writerMap = generateWriterMap(mysqlWriterDao.getMysqlWriter(outputId));	
		}else if(outputType.equalsIgnoreCase(Constant.OUTPUT_TYPE_TXTFILEWRITER)) {
			writerMap = generateWriterMap(txtFileWriterDao.getTxtFileWriter(outputId));		
		}
		
		//生成datax格式的transformer
		List<HashMap<String, Object>> transformerList = new ArrayList<>();
		String transformerId = (String) requestMap.get("transformerId");
		Map<String, Object> transformerMap = transformerDao.getTransformer(transformerId);
		requestMap.put("transformerName", transformerMap.get("name"));
		requestMap.put("transformerType", transformerMap.get("type"));
		if(transformerId!=null) {
			transformerList = generateTransformerList(transformerMap);		 
		}		
		String dataxJson = objectMapper.writeValueAsString(generateDataxMap(readerMap,writerMap,transformerList));
		requestMap.put("dataxJson", dataxJson);
		
		map.putAll(requestMap);
		jobDao.updateJob(map);
		
		//TODO 这部分使用异步处理
		 //不能对任务名称继续更新		
		String name=(String)map.get("name");
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, dataxJson, "UTF-8");
		//
		
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/job/deleteJob  删除计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/deleteJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>删除计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-删除</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/deleteJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/deleteJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/deleteJob")
	public Map<String, Object> deleteJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		//
		try {//TODO 这部分使用异步处理
		Map<String, Object> map = jobDao.getJob(id);
		String name=(String)map.get("name");
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/job/%s/doDelete", jenkinsUrl, name), HttpMethod.POST,
				new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		log.info(response.getBody());
		}catch (Exception e) {
			log.error("",e);
		}
		//
		jobDao.deleteJob(id);		
		buildDao.deleteBuildByJobId(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/job/runJob  运行计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/runJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>运行计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-运行</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/runJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/runJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/runJob")
	public Map<String, Object> runJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> jobMap = jobDao.getJob(id);
		String name = (String) jobMap.get("name");

		Integer status = (Integer) jobMap.get("status");
		if (status == Constant.JOB_STATUS_DISABLE) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "构建失败,任务停用中");
					put("data", null);
				}
			};
		}
		if (status == Constant.JOB_STATUS_RUNNING) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "构建失败,任务正在构建中");
					put("data", null);
				}
			};
		}
 
	   //向jenkins发送构建请求
		ResponseEntity<String> response = restTemplate.exchange(String.format("%s/job/%s/build", jenkinsUrl, name),
				HttpMethod.POST, new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);
		
		//写入构建日志
		String currentBuildNumber = (String) jobMap.get("nextBuildNumber");
		Map<String, Object> buildMap = new HashMap<>();
		buildMap.put("id", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		buildMap.put("name", currentBuildNumber);
		buildMap.put("jobId", jobMap.get("id"));
		buildMap.put("jobName", jobMap.get("name"));
		buildMap.put("number", currentBuildNumber);		
		buildMap.put("status", Constant.BUILD_STATUS_RUNNING);	
		buildMap.put("progress", 0.0);
		String createUserName = currentLoginUsername;
		buildMap.put("createUserName", createUserName);
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		buildMap.put("createTime", createTime);		
		buildDao.saveBuild(buildMap);
		
		//更新计划任务
		jobMap.put("lastBuildCreateTime", createTime);
		jobMap.put("lastBuildProgress",0.0);
		jobMap.put("lastBuildId", buildMap.get("id"));
		jobMap.put("lastBuildNumber", currentBuildNumber);
		jobMap.put("nextBuildNumber", Integer.parseInt(currentBuildNumber)+1+"");
		jobMap.put("status", Constant.JOB_STATUS_RUNNING);
		jobDao.updateJob(jobMap);
		
		//异步更新计划任务和构建日志START
		String jobId=(String)jobMap.get("id");
		String jobName=(String)jobMap.get("name");	 
		 new Thread(){
			 public void run() {
				 updateJobAndBuild( jobId, jobName, currentBuildNumber);			 }
		 }.start();
		//异步更新计划任务和构建日志END
		 
		 
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "构建成功");
				put("data", null);
			}
		};
	}

	
	public void updateJobAndBuild(String jobId,String jobName,String currentBuildNumber) {
		// 不断的更新mysql
				while (true) {
					try {

						

						// getBuild-1请求
						ResponseEntity<Map> response3 = restTemplate.exchange(
								String.format("%s/job/%s/%s/api/json?pretty=true", jenkinsUrl, jobName, currentBuildNumber),
								HttpMethod.GET, new HttpEntity<>(new HttpHeaders() {
									{
										set("Content-Type", "text/xml; charset=UTF-8");

									}
								}), Map.class);
						Map<String, Object> responseMap = (Map<String, Object>) response3.getBody();
						Map<String, Object> buildMap = buildDao.getBuildByJobNameAndNumber(jobName, currentBuildNumber);
						buildMap.put("jobId", jobId);
						buildMap.put("jobName", jobName);
						buildMap.put("number", currentBuildNumber);
						// 是否构建中
						boolean building = (boolean) responseMap.get("building");
						buildMap.put("status", building == false ? Constant.BUILD_STATUS_EXIT : Constant.BUILD_STATUS_RUNNING);
						// 预期构建时长
						int estimatedDuration=(Integer) responseMap.get("estimatedDuration");
						buildMap.put("estimatedDuration", estimatedDuration);
						
												
						// 实际构建时长
						buildMap.put("duration", (Integer) responseMap.get("duration"));
						String result = (String) responseMap.get("result");
						if (result == null) {
							buildMap.put("result", null);
						} else if (result.equals("SUCCESS")) {
							buildMap.put("result", Constant.BUILD_RESULT_SUCCESS);
						} else if (result.equals("FAILURE")) {
							buildMap.put("result", Constant.BUILD_RESULT_FAILURE);
						} else if (result.equals("ABORTED")) {
							buildMap.put("result", Constant.BUILD_RESULT_ABORTED);
						}
						
						// 更新任务执行进度
						double progress = 0.0;
						if (building == false && result != null) {
							progress=1.0;// 已经构建完毕
						}else {
							DecimalFormat df=new DecimalFormat("0.00");
							long duration = System.currentTimeMillis()-new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)buildMap.get("createTime")).getTime();
							progress=
									Double.parseDouble(df.format(
											 (float)duration/estimatedDuration
											));	
							if(progress>1) progress=0.99;
						}						
						buildMap.put("progress", progress);
						
					
						// getBuild-2请求
						ResponseEntity<String> response4 = restTemplate.exchange(
								String.format("%s/job/%s/%s/consoleText", jenkinsUrl, jobName, currentBuildNumber), HttpMethod.GET,
								new HttpEntity<>(new HttpHeaders() {
									{
										set("Content-Type", "text/xml; charset=UTF-8");

									}
								}), String.class);
						//控制台日志
						String consoleText = response4.getBody();	
						buildMap.put("consoleText", consoleText);
						buildDao.updateBuild(buildMap);

						// 获取job的最后一次构建状态
						 response3 = restTemplate.exchange(
								String.format("%s/job/%s/api/json?pretty=true", jenkinsUrl, jobName), HttpMethod.GET,
								new HttpEntity<>(new HttpHeaders() {
									{
										set("Content-Type", "text/xml; charset=UTF-8");

									}
								}), Map.class);

						responseMap = (Map<String, Object>) response3.getBody();
						
						Integer lastBuildNumber = (Integer) ((Map<String, Object>) responseMap.get("lastBuild")).get("number");

						Map<String, Object> jobMap = jobDao.getJob(jobId);
						//最后一次构建编号
						jobMap.put("lastBuildNumber", lastBuildNumber);
						//最后一次成功构建编号
						jobMap.put("lastSuccessfulBuildNumber", responseMap.get("lastSuccessfulBuildNumber") == null ? null
								: ((Map<String, Object>) responseMap.get("lastSuccessfulBuildNumber")).get("number"));
						//最后一次失败构建编号
						jobMap.put("lastUnsuccessfulBuildNumber", responseMap.get("lastUnsuccessfulBuildNumber") == null ? null
								: ((Map<String, Object>) responseMap.get("lastUnsuccessfulBuildNumber")).get("number"));
						//下一次构建编号
						jobMap.put("nextBuildNumber", responseMap.get("nextBuildNumber"));
						jobMap.put("status", building == false ? Constant.JOB_STATUS_ENABLE : Constant.JOB_STATUS_RUNNING);
						jobMap.put("lastBuildEstimatedDuration", estimatedDuration);
						//更新任务执行进度
						jobMap.put("lastBuildProgress", progress);
						
						boolean end = false;
						if (building == false && result != null) {
							if (result.equals("SUCCESS")) {
								jobMap.put("lastSuccessfulBuildNumber", lastBuildNumber);
							} else if (result.equals("FAILURE")) {
								jobMap.put("lastUnsuccessfulBuildNumber", lastBuildNumber);
							}
							end = true;
						}
						jobDao.updateJob(jobMap);//TODO 增加字段，最后一次任务的执行结果lastBuildResult
						
						if (building == false && result != null) {
							break;// 已经构建完毕
						}
						
						try {
							Thread.sleep(5000);
						} catch (Exception e) {
							 
						}
					} catch (Exception e) {
						log.error("", e);
						try {
							Thread.sleep(5000);
						} catch (Exception e2) {
							 
						}
						continue;		
						
					}
					
				}
	}

  

	
	/**
	 * @api {POST} /api/job/stopJob  停止计划任务的当前构建
	 * @apiGroup JobController
	 * @apiName /api/job/stopJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>停止计划任务的当前构建，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-停止</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/stopJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/stopJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/stopJob")
	public Map<String, Object> stopJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);
		
		String id = (String) requestMap.get("id");
		
		Map<String, Object> map = jobDao.getJob(id);
		String name = (String) map.get("name");
		String lastBuildNumber = (String)map.get("lastBuildNumber");

		Integer status = (Integer) map.get("status");
		if (status == Constant.JOB_STATUS_DISABLE) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "停止失败,任务停用中");
					put("data", null);
				}
			};
		}
		 
		if(status==Constant.JOB_STATUS_ENABLE) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "任务已经构建完毕，无法停止");
					put("data", null);
				}
			};
		}
		//TODO取消队列	
		//停止
		ResponseEntity<String> response  =  restTemplate.exchange(String.format("%s/job/%s/%s/stop", jenkinsUrl, name,lastBuildNumber),
				HttpMethod.POST, new HttpEntity<>(new HttpHeaders() {
					{
						set("Content-Type", "text/xml; charset=UTF-8");

					}
				}), String.class);	
		log.info(""+response.getStatusCode());
		
		//更新job
	    map.put("status", Constant.JOB_STATUS_ENABLE);
	    jobDao.updateJob(map);

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "停止成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/job/enableJob  启用计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/enableJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>启用计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-启用</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/enableJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/enableJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/enableJob")
	public Map<String, Object> enableJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		
		Integer status = (Integer) map.get("status");
		if (status == Constant.JOB_STATUS_ENABLE) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "启用失败,任务启用中");
					put("data", null);
				}
			};
		}
		
		map.put("status", Constant.JOB_STATUS_ENABLE);
		jobDao.updateJob(map);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "启用成功");
				put("data", null);
			}
		};
	}

	/**
	 * @api {POST} /api/job/disableJob  停用计划任务
	 * @apiGroup JobController
	 * @apiName /api/job/disableJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>停用计划任务，成功code返回200</p>
	 * <p><font color="red">适用场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-计划任务管理-停用</font></li></p>
		  
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
	 * curl -i -X POST 'http://localhost:8045/api/job/disableJob?id=1' -H "Authorization:Bearer admin_token" 
	
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
	 * @apiSampleRequest /api/job/disableJob
	 *	 
	 */
	@RequestMapping(value = "/api/job/disableJob")
	public Map<String, Object> disableJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		
		Integer status = (Integer) map.get("status");
		if (status == Constant.JOB_STATUS_DISABLE) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "禁用失败,任务停用中");
					put("data", null);
				}
			};
		}
		
		map.put("status", Constant.JOB_STATUS_DISABLE);
		jobDao.updateJob(map);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "停用成功");
				put("data", null);
			}
		};
	}

}
