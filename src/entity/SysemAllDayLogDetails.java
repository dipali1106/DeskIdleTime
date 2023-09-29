package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="SysemAllDayLogDetails")
@Entity(name="SysemAllDayLogDetails")
public class SysemAllDayLogDetails {

	@Id
	@Column(name="Id")
	int Id;
	@Column(name="SystemDayId")
	int SystemDayId=0;
	@Column(name="Time")
	String Time="";
	@Column(name="OffDuration")
	long OffDuration=0;
	@Column(name="Status")
	int Status=1;
	public SysemAllDayLogDetails() {
		
	}
	public SysemAllDayLogDetails(int systemDayId, String time, long gapDuration) {
		super();
		SystemDayId = systemDayId;
		Time = time;
		OffDuration = gapDuration;
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
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public long getDuration() {
		return OffDuration;
	}
	public void setDuration(int duration) {
		OffDuration = duration;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	
	
	
	
}
