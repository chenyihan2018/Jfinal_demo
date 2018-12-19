package com.jslx.register.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseParUserAddress<M extends BaseParUserAddress<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setHasHouse(java.lang.Boolean hasHouse) {
		set("has_house", hasHouse);
		return (M)this;
	}
	
	public java.lang.Boolean getHasHouse() {
		return get("has_house");
	}

	public M setAddress(java.lang.String address) {
		set("address", address);
		return (M)this;
	}
	
	public java.lang.String getAddress() {
		return getStr("address");
	}

	public M setHouseProofPic(java.lang.String houseProofPic) {
		set("house_proof_pic", houseProofPic);
		return (M)this;
	}
	
	public java.lang.String getHouseProofPic() {
		return getStr("house_proof_pic");
	}

	public M setLiveProofPic(java.lang.String liveProofPic) {
		set("live_proof_pic", liveProofPic);
		return (M)this;
	}
	
	public java.lang.String getLiveProofPic() {
		return getStr("live_proof_pic");
	}

	public M setWorkProofPic(java.lang.String workProofPic) {
		set("work_proof_pic", workProofPic);
		return (M)this;
	}
	
	public java.lang.String getWorkProofPic() {
		return getStr("work_proof_pic");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}
	
	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}