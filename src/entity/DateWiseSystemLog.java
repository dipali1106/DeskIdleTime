package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="DateWiseSystemLog")
@Entity(name="DateWiseSystemLog")
public class DateWiseSystemLog {

	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int Id;
	@Column(name="SystemIp")
	String SystemIp="";
	@Column(name="WorkDate")
	String WorkDate="";
	@Column(name="FirstLogin")
	String FirstLogin="";
	@Column(name="LastLogin")
	String LastLogin="";
	@Column(name="DeletedStatus")
	int DeletedStatus=0;
	//@Column(name="EntryDateTime")
	//String EntryDateTime="";
//	
	DateWiseSystemLog(){
		
	}
	
	public DateWiseSystemLog( String systemIp, String workDate, String firstLogin, String lastlogin, int deletedStatus) {
		super();
		
		this.SystemIp = systemIp;
		this.WorkDate = workDate;
		this.FirstLogin = firstLogin;
		this.LastLogin = lastlogin;
		this.DeletedStatus = deletedStatus;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getSystemIp() {
		return SystemIp;
	}
	public void setSystemIp(String systemIp) {
		SystemIp = systemIp;
	}
	public String getWorkDate() {
		return WorkDate;
	}
	public void setWorkDate(String workDate) {
		WorkDate = workDate;
	}
	public String getFirstLogin() {
		return FirstLogin;
	}
	public void setFirstLogin(String firstLogin) {
		FirstLogin = firstLogin;
	}
	public int getDeletedStatus() {
		return DeletedStatus;
	}
	public void setDeletedStatus(int deletedStatus) {
		DeletedStatus = deletedStatus;
	}
//	public String getEntryDateTime() {
//		return EntryDateTime;
//	}
//	public void setEntryDateTime(String entryDateTime) {
//		EntryDateTime = entryDateTime;
//	}
//	

	public String getLastLogin() {
		return LastLogin;
	}

	public void setLastLogin(String lastLogin) {
		LastLogin = lastLogin;
	}
	
	
	
}
