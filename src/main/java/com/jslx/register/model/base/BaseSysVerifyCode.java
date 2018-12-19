package com.jslx.register.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysVerifyCode<M extends BaseSysVerifyCode<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMobile(java.lang.String mobile) {
		set("mobile", mobile);
		return (M)this;
	}
	
	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public M setCode(java.lang.String code) {
		set("code", code);
		return (M)this;
	}
	
	public java.lang.String getCode() {
		return getStr("code");
	}

	public M setIsChecked(java.lang.Boolean isChecked) {
		set("is_checked", isChecked);
		return (M)this;
	}
	
	public java.lang.Boolean getIsChecked() {
		return get("is_checked");
	}

	public M setCodeType(java.lang.Integer codeType) {
		set("code_type", codeType);
		return (M)this;
	}
	
	public java.lang.Integer getCodeType() {
		return getInt("code_type");
	}

	public M setSource(java.lang.String source) {
		set("source", source);
		return (M)this;
	}
	
	public java.lang.String getSource() {
		return getStr("source");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setResult(java.lang.String result) {
		set("result", result);
		return (M)this;
	}
	
	public java.lang.String getResult() {
		return getStr("result");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}