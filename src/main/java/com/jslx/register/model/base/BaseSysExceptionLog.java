package com.jslx.register.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysExceptionLog<M extends BaseSysExceptionLog<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setException(java.lang.String exception) {
		set("exception", exception);
		return (M)this;
	}
	
	public java.lang.String getException() {
		return getStr("exception");
	}

	public M setRequestUri(java.lang.String requestUri) {
		set("request_uri", requestUri);
		return (M)this;
	}
	
	public java.lang.String getRequestUri() {
		return getStr("request_uri");
	}

	public M setSource(java.lang.String source) {
		set("source", source);
		return (M)this;
	}
	
	public java.lang.String getSource() {
		return getStr("source");
	}

	public M setPackageName(java.lang.String packageName) {
		set("package_name", packageName);
		return (M)this;
	}
	
	public java.lang.String getPackageName() {
		return getStr("package_name");
	}

	public M setRequestPara(java.lang.String requestPara) {
		set("request_para", requestPara);
		return (M)this;
	}
	
	public java.lang.String getRequestPara() {
		return getStr("request_para");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setResource(java.lang.String resource) {
		set("resource", resource);
		return (M)this;
	}
	
	public java.lang.String getResource() {
		return getStr("resource");
	}

}
