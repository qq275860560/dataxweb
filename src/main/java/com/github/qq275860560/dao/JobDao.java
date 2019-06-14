package com.github.qq275860560.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangyuanlin@163.com
 *
 */
@RestController
@Slf4j
public class JobDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int countJob(String name) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" SELECT count(1) count from job where 1=1 ");
		sb.append(" and name = ? ");
		condition.add(name);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.queryForObject(sb.toString(), condition.toArray(), Integer.class);
	}

	public boolean checkJob(String id, String name) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" SELECT count(1) count  from job where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id != ? ");
			condition.add(id);
		}
		sb.append(" and name= ? ");
		condition.add(name);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		int count = jdbcTemplate.queryForObject(sb.toString(), condition.toArray(), Integer.class);
		if (count > 0)
			return false;
		else
			return true;
	}

	public int deleteJob(String id) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" delete  from job where 1=1 ");
		sb.append(" and id = ? ");
		condition.add(id);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.update(sb.toString(), condition.toArray());
	}

	public Map<String, Object> getJob(Object id) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,inputId,inputName,readerId,readerName,outputId,outputName,writerId,writerName,dataxJson,status,progress,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from job where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id = ? ");
			condition.add(id);
		}
		sb.append(" limit ? ,?  ");
		condition.add(0);
		condition.add(1);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		Map<String, Object> map = Collections.EMPTY_MAP;
		try {
			map = jdbcTemplate.queryForMap(sb.toString(), condition.toArray());
		} catch (Exception e) {
		}
		return map;
	}

	public Map<String, Object> getJobByKeyValue(String key, Object value) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,inputId,inputName,readerId,readerName,outputId,outputName,writerId,writerName,dataxJson,status,progress,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from job where 1=1 ");
		sb.append(" and " + key + " = ? ");
		condition.add(value);
		sb.append(" limit ? ,?  ");
		condition.add(0);
		condition.add(1);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		Map<String, Object> map = Collections.EMPTY_MAP;
		try {
			map = jdbcTemplate.queryForMap(sb.toString(), condition.toArray());
		} catch (Exception e) {
		}
		return map;
	}

	public int saveJob(Map<String, Object> map) throws Exception {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb1.append("id").append(",");
		sb2.append("?,");
		condition.add(map.get("id"));

		sb1.append("name").append(",");
		sb2.append("?,");
		condition.add(map.get("name"));

		sb1.append("inputId").append(",");
		sb2.append("?,");
		condition.add(map.get("inputId"));

		sb1.append("inputName").append(",");
		sb2.append("?,");
		condition.add(map.get("inputName"));

		sb1.append("readerId").append(",");
		sb2.append("?,");
		condition.add(map.get("readerId"));

		sb1.append("readerName").append(",");
		sb2.append("?,");
		condition.add(map.get("readerName"));

		sb1.append("outputId").append(",");
		sb2.append("?,");
		condition.add(map.get("outputId"));

		sb1.append("outputName").append(",");
		sb2.append("?,");
		condition.add(map.get("outputName"));

		sb1.append("writerId").append(",");
		sb2.append("?,");
		condition.add(map.get("writerId"));

		sb1.append("writerName").append(",");
		sb2.append("?,");
		condition.add(map.get("writerName"));

		sb1.append("dataxJson").append(",");
		sb2.append("?,");
		condition.add(map.get("dataxJson"));

		sb1.append("status").append(",");
		sb2.append("?,");
		condition.add(map.get("status"));

		sb1.append("progress").append(",");
		sb2.append("?,");
		condition.add(map.get("progress"));

		sb1.append("createUserId").append(",");
		sb2.append("?,");
		condition.add(map.get("createUserId"));

		sb1.append("createUserName").append(",");
		sb2.append("?,");
		condition.add(map.get("createUserName"));

		sb1.append("createTime").append(",");
		sb2.append("?,");
		condition.add(map.get("createTime"));

		if (sb1.length() > 0)
			sb1.deleteCharAt(sb1.length() - 1);
		if (sb2.length() > 0)
			sb2.deleteCharAt(sb2.length() - 1);
		String sql = "insert into job(" + sb1.toString() + ") values(" + sb2.toString() + ")";
		log.info("sql=" + sql);
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.update(sql, condition.toArray());

	}

	public int updateJob(Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" name = ? ,");
		condition.add(map.get("name"));

		sb.append(" inputId = ? ,");
		condition.add(map.get("inputId"));

		sb.append(" inputName = ? ,");
		condition.add(map.get("inputName"));

		sb.append(" readerId = ? ,");
		condition.add(map.get("readerId"));

		sb.append(" readerName = ? ,");
		condition.add(map.get("readerName"));

		sb.append(" outputId = ? ,");
		condition.add(map.get("outputId"));

		sb.append(" outputName = ? ,");
		condition.add(map.get("outputName"));

		sb.append(" writerId = ? ,");
		condition.add(map.get("writerId"));

		sb.append(" writerName = ? ,");
		condition.add(map.get("writerName"));

		sb.append(" dataxJson = ? ,");
		condition.add(map.get("dataxJson"));

		sb.append(" status = ? ,");
		condition.add(map.get("status"));

		sb.append(" progress = ? ,");
		condition.add(map.get("progress"));

		sb.append(" createUserId = ? ,");
		condition.add(map.get("createUserId"));

		sb.append(" createUserName = ? ,");
		condition.add(map.get("createUserName"));

		sb.append(" createTime = ? ,");
		condition.add(map.get("createTime"));

		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		String sql = "update job set " + sb.toString() + " where    id=?";
		condition.add(map.get("id"));
		log.info("sql=" + sql);
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.update(sql, condition.toArray());
	}

	public List<Map<String, Object>> listJob(String id, String name, String inputId, String inputName, String readerId,
			String readerName, String outputId, String outputName, String writerId, String writerName, String dataxJson,
			Integer status, Double progress, String createUserId, String createUserName, String startCreateTime,
			String endCreateTime) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,inputId,inputName,readerId,readerName,outputId,outputName,writerId,writerName,dataxJson,status,progress,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from job where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id like ? ");
			condition.add("%" + id + "%");
		}
		if (!StringUtils.isEmpty(name)) {
			sb.append(" and name like ? ");
			condition.add("%" + name + "%");
		}
		if (!StringUtils.isEmpty(inputId)) {
			sb.append(" and inputId like ? ");
			condition.add("%" + inputId + "%");
		}
		if (!StringUtils.isEmpty(inputName)) {
			sb.append(" and inputName like ? ");
			condition.add("%" + inputName + "%");
		}
		if (!StringUtils.isEmpty(readerId)) {
			sb.append(" and readerId like ? ");
			condition.add("%" + readerId + "%");
		}
		if (!StringUtils.isEmpty(readerName)) {
			sb.append(" and readerName like ? ");
			condition.add("%" + readerName + "%");
		}
		if (!StringUtils.isEmpty(outputId)) {
			sb.append(" and outputId like ? ");
			condition.add("%" + outputId + "%");
		}
		if (!StringUtils.isEmpty(outputName)) {
			sb.append(" and outputName like ? ");
			condition.add("%" + outputName + "%");
		}
		if (!StringUtils.isEmpty(writerId)) {
			sb.append(" and writerId like ? ");
			condition.add("%" + writerId + "%");
		}
		if (!StringUtils.isEmpty(writerName)) {
			sb.append(" and writerName like ? ");
			condition.add("%" + writerName + "%");
		}
		if (!StringUtils.isEmpty(dataxJson)) {
			sb.append(" and dataxJson like ? ");
			condition.add("%" + dataxJson + "%");
		}
		if (status != null) {
			sb.append(" and status = ? ");
			condition.add(status);
		}
		if (progress != null) {
			sb.append(" and progress = ? ");
			condition.add(progress);
		}
		if (!StringUtils.isEmpty(createUserId)) {
			sb.append(" and createUserId like ? ");
			condition.add("%" + createUserId + "%");
		}
		if (!StringUtils.isEmpty(createUserName)) {
			sb.append(" and createUserName like ? ");
			condition.add("%" + createUserName + "%");
		}
		if (!StringUtils.isEmpty(startCreateTime)) {
			sb.append(" and createTime >=  ?  ");
			condition.add(startCreateTime);
		}
		if (!StringUtils.isEmpty(endCreateTime)) {
			sb.append(" and createTime <=  ?  ");
			condition.add(endCreateTime);
		}
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.queryForList(sb.toString(), condition.toArray());

	}

	public Map<String, Object> pageJob(String id, String name, String inputId, String inputName, String readerId,
			String readerName, String outputId, String outputName, String writerId, String writerName, String dataxJson,
			Integer status, Double progress, String createUserId, String createUserName, String startCreateTime,
			String endCreateTime, Integer pageNum, Integer pageSize) throws Exception {
		if (pageNum == null)
			pageNum = 1;// 取名pageNum为了兼容mybatis-pageHelper中的page对象的pageNum,注意spring的PageRequest使用page表示页号,综合比较，感觉pageNum更加直观,不需要看上下文能猜出字段是页号
		if (pageSize == null)
			pageSize = 10;// 取名pageSize为了兼容mybatis-pageHelper中的page对象的pageSize,注意spring的PageRequest使用size表示页数量，综合比较，感觉pageSize会更加直观,不需要看上下文能猜出字段是分页时当前页的数量
		int from = (pageNum - 1) * pageSize;
		int size = pageSize;
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,inputId,inputName,readerId,readerName,outputId,outputName,writerId,writerName,dataxJson,status,progress,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from job where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id like ? ");
			condition.add("%" + id + "%");
		}
		if (!StringUtils.isEmpty(name)) {
			sb.append(" and name like ? ");
			condition.add("%" + name + "%");
		}
		if (!StringUtils.isEmpty(inputId)) {
			sb.append(" and inputId like ? ");
			condition.add("%" + inputId + "%");
		}
		if (!StringUtils.isEmpty(inputName)) {
			sb.append(" and inputName like ? ");
			condition.add("%" + inputName + "%");
		}
		if (!StringUtils.isEmpty(readerId)) {
			sb.append(" and readerId like ? ");
			condition.add("%" + readerId + "%");
		}
		if (!StringUtils.isEmpty(readerName)) {
			sb.append(" and readerName like ? ");
			condition.add("%" + readerName + "%");
		}
		if (!StringUtils.isEmpty(outputId)) {
			sb.append(" and outputId like ? ");
			condition.add("%" + outputId + "%");
		}
		if (!StringUtils.isEmpty(outputName)) {
			sb.append(" and outputName like ? ");
			condition.add("%" + outputName + "%");
		}
		if (!StringUtils.isEmpty(writerId)) {
			sb.append(" and writerId like ? ");
			condition.add("%" + writerId + "%");
		}
		if (!StringUtils.isEmpty(writerName)) {
			sb.append(" and writerName like ? ");
			condition.add("%" + writerName + "%");
		}
		if (!StringUtils.isEmpty(dataxJson)) {
			sb.append(" and dataxJson like ? ");
			condition.add("%" + dataxJson + "%");
		}
		if (status != null) {
			sb.append(" and status = ? ");
			condition.add(status);
		}
		if (progress != null) {
			sb.append(" and progress = ? ");
			condition.add(progress);
		}
		if (!StringUtils.isEmpty(createUserId)) {
			sb.append(" and createUserId like ? ");
			condition.add("%" + createUserId + "%");
		}
		if (!StringUtils.isEmpty(createUserName)) {
			sb.append(" and createUserName like ? ");
			condition.add("%" + createUserName + "%");
		}
		if (!StringUtils.isEmpty(startCreateTime)) {
			sb.append(" and createTime >=  ?  ");
			condition.add(startCreateTime);
		}
		if (!StringUtils.isEmpty(endCreateTime)) {
			sb.append(" and createTime <=  ?  ");
			condition.add(endCreateTime);
		}
		String countSql = "select count(1) count from ( " + sb.toString() + ") t";
		int count = jdbcTemplate.queryForObject(countSql, condition.toArray(), Integer.class);
		sb.append(" order by createTime desc ");
		sb.append(" limit ? ,?  ");
		condition.add(from);
		condition.add(size);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		List<Map<String, Object>> pageList = jdbcTemplate.queryForList(sb.toString(), condition.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", count);// 取名total为了兼容mybatis-pageHelper中的page对象的total,spring框架的PageImpl也使用total
		map.put("pageList", pageList);// 不同的框架取名不一样，可以把pageList改成list,array,rows,data,content,result等,spring框架使用的是content,mybatis因为page对象是继承ArrayList，字段命名乱七八糟，有时pageList，有时result，综上感觉pageList会更加直观和简洁,不需要看上下文能猜出字段是列表
		return map;

	}
}
