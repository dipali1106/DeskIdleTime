package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="SystemScreenshots")
@Entity(name="SystemScreenshots")
public class SystemScreenshots {


	@Id
	@Column(name="Id")
	int Id;
	@Column(name="SystemDayId")
	int SystemDayId=0;
	@Column(name="Screenshot")
	String Screenshot="";	
	@Column(name="Status")
	int Status=1;
	
	
	public SystemScreenshots(int systemDayId, String screenshot) {
		super();
		SystemDayId = systemDayId;
		Screenshot = screenshot;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getSystemDayId() {
		return SystemDayId;
	}
	public void setSystemDayId(int systemDayId) {
		SystemDayId = systemDayId;
	}
	public String getScreenshot() {
		return Screenshot;
	}
	public void setScreenshot(String screenshot) {
		Screenshot = screenshot;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	
	
	
}
