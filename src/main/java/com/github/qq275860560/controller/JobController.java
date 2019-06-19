package com.github.qq275860560.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.qq275860560.common.util.CommandUtil;
import com.github.qq275860560.constant.Constant;
import com.github.qq275860560.dao.InputDao;
import com.github.qq275860560.dao.JobDao;
import com.github.qq275860560.dao.OutputDao;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class JobController {

	@Autowired
	private JobDao jobDao;
	@Autowired
	private InputDao inputDao;
	@Autowired
	private OutputDao outputDao;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * @api {POST} /api/github/qq275860560/job/checkJob  校验唯一性
	 * @apiGroup JobController
	 * @apiName checkJob
	 * @apiVersion 1.0.0
	 * @apiPermission user
	 * @apiDescription   <p>校验唯一性，成功code返回200并且data返回true </p>
	 * <p><font color="red">适用具体场景：</font></p>	
	 * <p><li><font color="red">数据交换组件-任务管理管理-新建-校验合法性</font></li></p>
	 * <p><li><font color="red">数据交换组件-任务管理管理-编辑-校验合法性</font></li></p>
	  
	 * @apiHeader {String} ContentType=application/x-www-form-urlencoded  请求类型,确保参数urlencode之后才发送
	 * @apiHeader {String} Accept=application/json;charset=UTF-8 响应类型
	 * @apiHeader {String} Authorization "Bearer "串接调用/login接口获取的令牌
	
	 * @apiHeaderExample {json} 请求头部示例: 
	 * { 
	 * 		"Content-Type":"application/x-www-form-urlencoded", 
	 *      "Accept":"application/json;charset=UTF-8",
	 *      "Authorization":"Bearer XXX" 
	 * }
	 * 
	 * @apiParam {String} id 任务,新建时为空，编辑时必填，
	 * @apiParam {String} name 要校验唯一性的名称，必填
	 * 
	 * @apiParamExample {String} 请求参数示例:
	 * id=2&name=name2
	 
	 * @apiExample {curl} 命令行调用示例: 	
	 * curl -X POST 'http://127.0.0.1:8080/api/github/qq275860560/job/checkJob' -H "Authorization:Bearer admin_token" 
	
	 * @apiSuccess (返回结果:) {int} code 状态码:{200:成功,400:参数错误(比如参数格式不符合文档要求),401:认证失败(比如token已过期),403:授权失败(比如用户无权限访问该接口)}
	 * @apiSuccess (返回结果:) {String} msg 提示信息，校验结果的提示信息
	 * @apiSuccess (返回结果:) {Boolean} data 返回对象,code为200时此字段有效{true:合法,false:不合法}		
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
	 * @apiSampleRequest /api/github/qq275860560/job/checkJob
	 *	 
	 */
	@RequestMapping(value = "/checkJob")
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

	/*
	 * curl -i -X POST "http://admin:123456@localhost:8080/api/github/qq275860560/job/pageJob?pageNum=1&pageSize=10"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/pageJob")
	public Map<String, Object> pageJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String name = (String) requestMap.get("name");
		String inputName = (String) requestMap.get("inputName");
		String readerName = (String) requestMap.get("readerName");
		String outputName = (String) requestMap.get("outputName");
		String writerName = (String) requestMap.get("writerName");
		Integer status = requestMap.get("status") == null ? null
				: Integer.parseInt(requestMap.get("status").toString());
		Double progress = requestMap.get("progress") == null ? null
				: Double.parseDouble(requestMap.get("progress").toString());
		String createUserName = (String) requestMap.get("createUserName");
		String startCreateTime = (String) requestMap.get("startCreateTime");
		String endCreateTime = (String) requestMap.get("endCreateTime");
		log.info("" + requestMap.get("pageNum"));
		Integer pageNum = requestMap.get("pageNum") == null ? 1
				: Integer.parseInt(requestMap.get("pageNum").toString());
		Integer pageSize = requestMap.get("pageSize") == null ? 10
				: Integer.parseInt(requestMap.get("pageSize").toString());

		Map<String, Object> data = jobDao.pageJob(null, name, null, inputName, null, outputName, null, readerName, null,
				writerName, null, status,null,null,null,null, progress, null, createUserName, startCreateTime, endCreateTime, pageNum,
				pageSize);

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());// 此字段可以省略，这里仿照蚂蚁金服的接口返回字段code，增加状态码说明
				put("msg", "分页搜索成功");// 此字段可以省略，这里仿照蚂蚁金服的接口返回字段msg，增加说明
				put("data", data);
			}
		};
	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/getJob?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/getJob")
	public Map<String, Object> getJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

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

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/saveJob?name=jobName1&inputId=&inputName=inputName1&readerId=&readerName=mysqlreader&outputId=1&outputName=outputName1&writerId=&writerName=mysqlwriter"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/saveJob")
	public Map<String, Object> saveJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = UUID.randomUUID().toString().replace("-", "");
		requestMap.put("id", id);

		String inputId = (String) requestMap.get("inputId");
		Map<String, Object> inputMap = inputDao.getInput(inputId);
		requestMap.put("inputName", inputMap.get("name"));
		requestMap.put("readerId", inputMap.get("readerId"));
		requestMap.put("readerName", inputMap.get("readerName"));
		requestMap.put("readerParameterUsername", inputMap.get("readerParameterUsername"));
		requestMap.put("readerParameterPassword", inputMap.get("readerParameterPassword"));
		requestMap.put("readerParameterColumn", inputMap.get("readerParameterColumn"));
		requestMap.put("readerParameterWhere", inputMap.get("readerParameterWhere"));
		requestMap.put("readerParameterConnectionJdbcUrl", inputMap.get("readerParameterConnectionJdbcUrl"));
		requestMap.put("readerParameterConnectionTable", inputMap.get("readerParameterConnectionTable"));

		String outputId = (String) requestMap.get("outputId");
		Map<String, Object> outputMap = outputDao.getOutput(outputId);
		requestMap.put("outputName", outputMap.get("name"));
		requestMap.put("writerId", outputMap.get("writerId"));
		requestMap.put("writerName", outputMap.get("writerName"));
		requestMap.put("writerParameterUsername", outputMap.get("writerParameterUsername"));
		requestMap.put("writerParameterPassword", outputMap.get("writerParameterPassword"));
		requestMap.put("writerParameterWriteMode", outputMap.get("writerParameterWriteMode"));
		requestMap.put("writerParameterColumn", outputMap.get("writerParameterColumn"));
		requestMap.put("writerParameterPreSql", outputMap.get("writerParameterPreSql"));
		requestMap.put("writerParameterConnectionJdbcUrl", outputMap.get("writerParameterConnectionJdbcUrl"));
		requestMap.put("writerParameterConnectionTable", outputMap.get("writerParameterConnectionTable"));

		String dataxJson = objectMapper.writeValueAsString(generateDataxMap(requestMap));
		requestMap.put("dataxJson", dataxJson);

		Integer status = 1;
		requestMap.put("status", status);
		Double progress = 0.0;
		requestMap.put("progress", progress);

		String createUserName = currentLoginUsername;
		requestMap.put("createUserName", createUserName);
		String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		requestMap.put("createTime", createTime);
		jobDao.saveJob(requestMap);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "保存成功");
				put("data", null);
			}
		};
	}

	public static Map<String, Object> generateDataxMap(Map<String, Object> requestMap)
			throws IOException, JsonParseException, JsonMappingException {
		Map<String, Object> readerMap = generateReaderMap(requestMap);
		Map<String, Object> writerMap = generateWriterMap(requestMap);
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
		String name = (String) requestMap.get("readerName");
		if (name.equals("mysqlreader")) {
			return new HashMap<String, Object>() {
				{
					put("name", name);
					put("parameter", new HashMap<String, Object>() {
						{
							String username = (String) requestMap.get("readerParameterUsername");
							put("username", username);
							String password = (String) requestMap.get("readerParameterPassword");
							put("password", password);

							String[] column = ((String) requestMap.get("readerParameterColumn")).split(",");
							put("column", column);

							String where = (String) requestMap.get("readerParameterWhere");
							put("where", where);

							put("connection", new ArrayList<HashMap<String, Object>>() {
								{
									add(new HashMap<String, Object>() {
										{
											String[] jdbcUrl = ((String) requestMap
													.get("readerParameterConnectionJdbcUrl")).split(",");
											put("jdbcUrl", jdbcUrl);
											String[] table = ((String) requestMap.get("readerParameterConnectionTable"))
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
		String name = (String) requestMap.get("writerName");

		if (name.equals("mysqlwriter")) {
			return new HashMap<String, Object>() {
				{
					put("name", name);
					put("parameter", new HashMap<String, Object>() {
						{
							String username = (String) requestMap.get("writerParameterUsername");
							put("username", username);
							String password = (String) requestMap.get("writerParameterPassword");
							put("password", password);

							String writeMode = (String) requestMap.get("writerParameterWriteMode");
							put("writeMode", writeMode);

							String[] column = ((String) requestMap.get("writerParameterColumn")).split(",");
							put("column", column);

							String[] preSql = ((String) requestMap.get("writerParameterPreSql")).split(",");
							put("preSql", preSql);

							put("connection", new ArrayList<HashMap<String, Object>>() {
								{
									add(new HashMap<String, Object>() {
										{
											String jdbcUrl = (String) requestMap
													.get("writerParameterConnectionJdbcUrl");
											put("jdbcUrl", jdbcUrl);

											String[] table = ((String) requestMap.get("writerParameterConnectionTable"))
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

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/updateJob?id=2&name=jobname2"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/updateJob")
	public Map<String, Object> updateJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		map.putAll(requestMap);
		jobDao.updateJob(map);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "更新成功");
				put("data", null);
			}
		};
	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/deleteJob?id=2"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/deleteJob")
	public Map<String, Object> deleteJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		jobDao.deleteJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "删除成功");
				put("data", null);
			}
		};
	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/runJob?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/runJob")
	public Map<String, Object> runJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");

		Integer status = (Integer) jobDao.getJob(id).get("status");
		if (status == 0) {
			return new HashMap<String, Object>() {
				{
					put("code", HttpStatus.BAD_REQUEST.value());
					put("msg", "构建失败,任务禁用中");
					put("data", null);
				}
			};
		}
		String result = runJob(id);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "构建成功");
				put("data", result);
			}
		};
	}

	private String runJob(String id) throws Exception {
		Map<String, Object> map = jobDao.getJob(id);
		String dataxJson = (String) map.get("dataxJson");

		String name = (String) map.get("name");
		File file = new File(Constant.DATAX_HOME + File.separator + "job" + File.separator + name + ".json");
		FileUtils.writeStringToFile(file, dataxJson, "UTF-8");

		log.info(file.getAbsolutePath());
		// 工具类实现参考https://github.com/qq275860560/common/blob/master/src/main/java/com/github/qq275860560/common/util/CommandUtil.java
		return CommandUtil.runComand("python " + Constant.DATAX_HOME + File.separator + "bin" + File.separator
				+ "datax.py " + file.getAbsolutePath());

	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/getJobProgress?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/getJobProgress")
	public Map<String, Object> getJobProgress(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Double data = (Double) jobDao.getJob(id).get("progress");
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "获取进度成功");
				put("data", data);
			}
		};
	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/stopJob?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/stopJob")
	public Map<String, Object> stopJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "停止成功");
				put("data", null);
			}
		};
	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/enableJob?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/enableJob")
	public Map<String, Object> enableJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		map.put("status", 1);
		jobDao.updateJob(map);
		return new HashMap<String, Object>() {
			{
				put("code", HttpStatus.OK.value());
				put("msg", "启用成功");
				put("data", null);
			}
		};
	}

	/*
	 * curl -i -X POST
	 * "http://admin:123456@localhost:8080/api/github/qq275860560/job/disableJob?id=1"
	 */
	@RequestMapping(value = "/api/github/qq275860560/job/disableJob")
	public Map<String, Object> disableJob(@RequestParam Map<String, Object> requestMap) throws Exception {
		String currentLoginUsername = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("当前登录用户=" + currentLoginUsername);

		String id = (String) requestMap.get("id");
		Map<String, Object> map = jobDao.getJob(id);
		map.put("status", 0);
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
