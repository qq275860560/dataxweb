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
public class OutputDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int countOutput(String name) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" SELECT count(1) count from output where 1=1 ");
		sb.append(" and name = ? ");
		condition.add(name);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.queryForObject(sb.toString(), condition.toArray(), Integer.class);
	}

	public boolean checkOutput(String id, String name) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" SELECT count(1) count  from output where 1=1 ");
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

	public int deleteOutput(String id) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" delete  from output where 1=1 ");
		sb.append(" and id = ? ");
		condition.add(id);
		log.info("sql=" + sb.toString());
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.update(sb.toString(), condition.toArray());
	}

	public Map<String, Object> getOutput(Object id) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,writerId,writerName,writerParameterUsername,writerParameterPassword,writerParameterWriteMode,writerParameterColumn,writerParameterPreSql,writerParameterConnectionJdbcUrl,writerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from output where 1=1 ");
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

	public Map<String, Object> getOutputByKeyValue(String key, Object value) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,writerId,writerName,writerParameterUsername,writerParameterPassword,writerParameterWriteMode,writerParameterColumn,writerParameterPreSql,writerParameterConnectionJdbcUrl,writerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from output where 1=1 ");
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

	public int saveOutput(Map<String, Object> map) throws Exception {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb1.append("id").append(",");
		sb2.append("?,");
		condition.add(map.get("id"));

		sb1.append("name").append(",");
		sb2.append("?,");
		condition.add(map.get("name"));

		sb1.append("writerId").append(",");
		sb2.append("?,");
		condition.add(map.get("writerId"));

		sb1.append("writerName").append(",");
		sb2.append("?,");
		condition.add(map.get("writerName"));

		sb1.append("writerParameterUsername").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterUsername"));

		sb1.append("writerParameterPassword").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterPassword"));

		sb1.append("writerParameterWriteMode").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterWriteMode"));

		sb1.append("writerParameterColumn").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterColumn"));

		sb1.append("writerParameterPreSql").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterPreSql"));

		sb1.append("writerParameterConnectionJdbcUrl").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterConnectionJdbcUrl"));

		sb1.append("writerParameterConnectionTable").append(",");
		sb2.append("?,");
		condition.add(map.get("writerParameterConnectionTable"));

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
		String sql = "insert into output(" + sb1.toString() + ") values(" + sb2.toString() + ")";
		log.info("sql=" + sql);
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.update(sql, condition.toArray());

	}

	public int updateOutput(Map<String, Object> map) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(" name = ? ,");
		condition.add(map.get("name"));

		sb.append(" writerId = ? ,");
		condition.add(map.get("writerId"));

		sb.append(" writerName = ? ,");
		condition.add(map.get("writerName"));

		sb.append(" writerParameterUsername = ? ,");
		condition.add(map.get("writerParameterUsername"));

		sb.append(" writerParameterPassword = ? ,");
		condition.add(map.get("writerParameterPassword"));

		sb.append(" writerParameterWriteMode = ? ,");
		condition.add(map.get("writerParameterWriteMode"));

		sb.append(" writerParameterColumn = ? ,");
		condition.add(map.get("writerParameterColumn"));

		sb.append(" writerParameterPreSql = ? ,");
		condition.add(map.get("writerParameterPreSql"));

		sb.append(" writerParameterConnectionJdbcUrl = ? ,");
		condition.add(map.get("writerParameterConnectionJdbcUrl"));

		sb.append(" writerParameterConnectionTable = ? ,");
		condition.add(map.get("writerParameterConnectionTable"));

		sb.append(" createUserId = ? ,");
		condition.add(map.get("createUserId"));

		sb.append(" createUserName = ? ,");
		condition.add(map.get("createUserName"));

		sb.append(" createTime = ? ,");
		condition.add(map.get("createTime"));

		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		String sql = "update output set " + sb.toString() + " where    id=?";
		condition.add(map.get("id"));
		log.info("sql=" + sql);
		log.info("condition=" + Arrays.deepToString(condition.toArray()));
		return jdbcTemplate.update(sql, condition.toArray());
	}

	public List<Map<String, Object>> listOutput(String id, String name, String writerId, String writerName,
			String writerParameterUsername, String writerParameterPassword, String writerParameterWriteMode,
			String writerParameterColumn, String writerParameterPreSql, String writerParameterConnectionJdbcUrl,
			String writerParameterConnectionTable, String createUserId, String createUserName, String startCreateTime,
			String endCreateTime) throws Exception {
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(
				" SELECT id,name,writerId,writerName,writerParameterUsername,writerParameterPassword,writerParameterWriteMode,writerParameterColumn,writerParameterPreSql,writerParameterConnectionJdbcUrl,writerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from output where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id like ? ");
			condition.add("%" + id + "%");
		}
		if (!StringUtils.isEmpty(name)) {
			sb.append(" and name like ? ");
			condition.add("%" + name + "%");
		}
		if (!StringUtils.isEmpty(writerId)) {
			sb.append(" and writerId like ? ");
			condition.add("%" + writerId + "%");
		}
		if (!StringUtils.isEmpty(writerName)) {
			sb.append(" and writerName like ? ");
			condition.add("%" + writerName + "%");
		}
		if (!StringUtils.isEmpty(writerParameterUsername)) {
			sb.append(" and writerParameterUsername like ? ");
			condition.add("%" + writerParameterUsername + "%");
		}
		if (!StringUtils.isEmpty(writerParameterPassword)) {
			sb.append(" and writerParameterPassword like ? ");
			condition.add("%" + writerParameterPassword + "%");
		}
		if (!StringUtils.isEmpty(writerParameterWriteMode)) {
			sb.append(" and writerParameterWriteMode like ? ");
			condition.add("%" + writerParameterWriteMode + "%");
		}
		if (!StringUtils.isEmpty(writerParameterColumn)) {
			sb.append(" and writerParameterColumn like ? ");
			condition.add("%" + writerParameterColumn + "%");
		}
		if (!StringUtils.isEmpty(writerParameterPreSql)) {
			sb.append(" and writerParameterPreSql like ? ");
			condition.add("%" + writerParameterPreSql + "%");
		}
		if (!StringUtils.isEmpty(writerParameterConnectionJdbcUrl)) {
			sb.append(" and writerParameterConnectionJdbcUrl like ? ");
			condition.add("%" + writerParameterConnectionJdbcUrl + "%");
		}
		if (!StringUtils.isEmpty(writerParameterConnectionTable)) {
			sb.append(" and writerParameterConnectionTable like ? ");
			condition.add("%" + writerParameterConnectionTable + "%");
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

	public Map<String, Object> pageOutput(String id, String name, String writerId, String writerName,
			String writerParameterUsername, String writerParameterPassword, String writerParameterWriteMode,
			String writerParameterColumn, String writerParameterPreSql, String writerParameterConnectionJdbcUrl,
			String writerParameterConnectionTable, String createUserId, String createUserName, String startCreateTime,
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
				" SELECT id,name,writerId,writerName,writerParameterUsername,writerParameterPassword,writerParameterWriteMode,writerParameterColumn,writerParameterPreSql,writerParameterConnectionJdbcUrl,writerParameterConnectionTable,createUserId,createUserName,date_format(createTime,	'%Y-%m-%d %H:%i:%s') createTime from output where 1=1 ");
		if (!StringUtils.isEmpty(id)) {
			sb.append(" and id like ? ");
			condition.add("%" + id + "%");
		}
		if (!StringUtils.isEmpty(name)) {
			sb.append(" and name like ? ");
			condition.add("%" + name + "%");
		}
		if (!StringUtils.isEmpty(writerId)) {
			sb.append(" and writerId like ? ");
			condition.add("%" + writerId + "%");
		}
		if (!StringUtils.isEmpty(writerName)) {
			sb.append(" and writerName like ? ");
			condition.add("%" + writerName + "%");
		}
		if (!StringUtils.isEmpty(writerParameterUsername)) {
			sb.append(" and writerParameterUsername like ? ");
			condition.add("%" + writerParameterUsername + "%");
		}
		if (!StringUtils.isEmpty(writerParameterPassword)) {
			sb.append(" and writerParameterPassword like ? ");
			condition.add("%" + writerParameterPassword + "%");
		}
		if (!StringUtils.isEmpty(writerParameterWriteMode)) {
			sb.append(" and writerParameterWriteMode like ? ");
			condition.add("%" + writerParameterWriteMode + "%");
		}
		if (!StringUtils.isEmpty(writerParameterColumn)) {
			sb.append(" and writerParameterColumn like ? ");
			condition.add("%" + writerParameterColumn + "%");
		}
		if (!StringUtils.isEmpty(writerParameterPreSql)) {
			sb.append(" and writerParameterPreSql like ? ");
			condition.add("%" + writerParameterPreSql + "%");
		}
		if (!StringUtils.isEmpty(writerParameterConnectionJdbcUrl)) {
			sb.append(" and writerParameterConnectionJdbcUrl like ? ");
			condition.add("%" + writerParameterConnectionJdbcUrl + "%");
		}
		if (!StringUtils.isEmpty(writerParameterConnectionTable)) {
			sb.append(" and writerParameterConnectionTable like ? ");
			condition.add("%" + writerParameterConnectionTable + "%");
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
