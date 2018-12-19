package com.jslx.register.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jslx.register.generator.Table;

import java.util.HashMap;
import java.util.Map;

public final class _ApiModelKit {

	public static void init(String tableName, String primaryKey,
					Class<? extends Model<?>> modelClass, Map<String, Class<?>> attrTypeMapTypeMap) {
		Table table = new Table(tableName, primaryKey, modelClass);
		table.setColumnTypeMap(attrTypeMapTypeMap);
		TableMapping.me().putTable(table);
	}

	public static void initModel() {
		System.out.println("Starting mapping model...");
		Map<String, Class<?>> columnMap = new HashMap<String, Class<?>>();
		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("name", java.lang.String.class);
		columnMap.put("parent_id", java.lang.Integer.class);
		columnMap.put("is_del", java.lang.Boolean.class);
		columnMap.put("code", java.lang.String.class);
		columnMap.put("update_time", java.util.Date.class);
		columnMap.put("create_time", java.util.Date.class);
		init("base_area", "id", BaseArea.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("name", java.lang.String.class);
		columnMap.put("city", java.lang.String.class);
		columnMap.put("county", java.lang.String.class);
		columnMap.put("is_del", java.lang.Boolean.class);
		columnMap.put("create_time", java.util.Date.class);
		init("base_community", "id", BaseCommunity.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("name", java.lang.String.class);
		columnMap.put("type", java.lang.Boolean.class);
		columnMap.put("school_type", java.lang.Boolean.class);
		columnMap.put("city_id", java.lang.Integer.class);
		columnMap.put("county_id", java.lang.Integer.class);
		columnMap.put("create_time", java.util.Date.class);
		init("base_school", "id", BaseSchool.class, columnMap);

		columnMap.put("school_id", java.lang.Integer.class);
		columnMap.put("community_id", java.lang.Integer.class);
		columnMap.put("sort", java.lang.String.class);
		columnMap.put("create_time", java.util.Date.class);
		columnMap.put("update_time", java.util.Date.class);
		init("base_school_community", "community_id,school_id", BaseSchoolCommunity.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("name", java.lang.String.class);
		columnMap.put("idcard", java.lang.String.class);
		columnMap.put("sex", java.lang.Boolean.class);
		columnMap.put("hkb_jhr_pic", java.lang.String.class);
		columnMap.put("hkb_pic", java.lang.String.class);
		columnMap.put("idcard_pic", java.lang.String.class);
		columnMap.put("idcard_pic_back", java.lang.String.class);
		columnMap.put("create_time", java.util.Date.class);
		columnMap.put("update_time", java.util.Date.class);
		init("par_guardian", "id", ParGuardian.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("name", java.lang.String.class);
		columnMap.put("idcard", java.lang.String.class);
		columnMap.put("mobile", java.lang.String.class);
		columnMap.put("password", java.lang.String.class);
		columnMap.put("birth", java.lang.String.class);
		columnMap.put("sex", java.lang.Boolean.class);
		columnMap.put("grade", java.lang.String.class);
		columnMap.put("guardian_id", java.lang.Integer.class);
		columnMap.put("create_time", java.util.Date.class);
		init("par_user", "id", ParUser.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("has_house", java.lang.Boolean.class);
		columnMap.put("address", java.lang.String.class);
		columnMap.put("house_proof_pic", java.lang.String.class);
		columnMap.put("live_proof_pic", java.lang.String.class);
		columnMap.put("work_proof_pic", java.lang.String.class);
		columnMap.put("create_time", java.util.Date.class);
		columnMap.put("update_time", java.util.Date.class);
		init("par_user_address", "id", ParUserAddress.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("type", java.lang.String.class);
		columnMap.put("proof_pic", java.lang.String.class);
		columnMap.put("create_time", java.util.Date.class);
		init("par_user_type", "id", ParUserType.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("student_id", java.lang.Integer.class);
		columnMap.put("grade", java.lang.String.class);
		columnMap.put("school_id", java.lang.Integer.class);
		columnMap.put("wish_type", java.lang.Boolean.class);
		columnMap.put("create_time", java.util.Date.class);
		columnMap.put("update_time", java.util.Date.class);
		init("sch_student_apply", "id", SchStudentApply.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("exception", java.lang.String.class);
		columnMap.put("request_uri", java.lang.String.class);
		columnMap.put("source", java.lang.String.class);
		columnMap.put("package_name", java.lang.String.class);
		columnMap.put("request_para", java.lang.String.class);
		columnMap.put("create_time", java.util.Date.class);
		columnMap.put("resource", java.lang.String.class);
		init("sys_exception_log", "id", SysExceptionLog.class, columnMap);

		columnMap.put("id", java.lang.Integer.class);
		columnMap.put("mobile", java.lang.String.class);
		columnMap.put("code", java.lang.String.class);
		columnMap.put("is_checked", java.lang.Boolean.class);
		columnMap.put("code_type", java.lang.Integer.class);
		columnMap.put("source", java.lang.String.class);
		columnMap.put("status", java.lang.Integer.class);
		columnMap.put("result", java.lang.String.class);
		columnMap.put("create_time", java.util.Date.class);
		init("sys_verify_code", "id", SysVerifyCode.class, columnMap);

	}

}

