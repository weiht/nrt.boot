package nrt.boot.org;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("org_departments")
public class Department {
	@Name @Column("d_id") @ColDefine(notNull=true, width=64, update=false)
	private String id;
	@Column("d_code") @ColDefine(notNull=true, width=10, update=false)
	private String code;
	@Column("d_path") @ColDefine(notNull=true, width=200, update=false)
	private String path;
	@Column("display_name") @ColDefine(notNull=true, width=200)
	private String name;
	@Column("alternate_name") @ColDefine(notNull=true, width=200)
	private String altName;
	@Column("d_description") @ColDefine(type=ColType.TEXT)
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
