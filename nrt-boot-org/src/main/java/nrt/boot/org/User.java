package nrt.boot.org;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("org_users")
public class User {
	@Name @Column("u_id") @ColDefine(notNull=true, width=64, update=false)
	private String id;
	@Column("login_name") @ColDefine(notNull=true, width=10, update=false)
	private String loginName;
	@Column("display_name") @ColDefine(notNull=true, width=200)
	private String fullName;
	@Column("family_name") @ColDefine(notNull=true, width=100)
	private String familyName;
	@Column("personal_name") @ColDefine(notNull=true, width=100)
	private String personalName;
	@Column("alternate_name") @ColDefine(notNull=true, width=40)
	private String altName;
	@Column("u_description") @ColDefine(type=ColType.TEXT)
	private String description;
	@Column("display_order") @ColDefine(notNull=true)
	private Integer displayOrder;
	@Column("create_time") @ColDefine(notNull=true)
	private Long createTime;
	@Column("updateTime")
	private Long updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getAltName() {
		return altName;
	}

	public void setAltName(String altName) {
		this.altName = altName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

}
