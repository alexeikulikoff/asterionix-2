package com.asterionix.dao;



import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "queue_log")
public class QueueLogEntity implements IEntity{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	@NotNull	
	private int id;
	private String time;
	private String callid;
	private String queuename;
	private String agent;
	private String event;
	private String data1;
	private String data2;
	private String data3;
	private String data4;
	private String data5;

	public QueueLogEntity(){}
	
	@ManyToOne
	@JoinColumn(name = "callid", referencedColumnName="uniqueid", insertable=false, updatable=false)
	private CDREntity cdrEntity;
	
	public CDREntity getCDR(){
		return this.cdrEntity;
	}
	public void setCDR(CDREntity c){
		this.cdrEntity = c;
	}
	public void setId(int i){
		this.id = i;
	}
	public int getId(){
		return this.id;
	}
	
	public String getTime(){
		return this.time;
	}
	public void setTime(String s){
		this.time = s;
	}
	public String getCallid(){
		return this.callid;
	}
	public void setCallid(String s){
		this.callid = s;
	}
	public String getQueuename(){
		return this.queuename;
	}
	public void setQueuename(String s){
		this.queuename = s;
	}
	public String getAgent(){
		return this.agent;
	}
	public void setAgent(String s){
		this.agent = s;
	}
	public String getEvent(){
		return this.event;
	}
	public void setEvent(String s){
		this.event = s;
	}
	public String getData1(){
		return this.data1;
	}
	public void setData1(String s){
		this.data1 = s;
	}
	public String getData2(){
		return this.data2;
	}
	public void setData2(String s){
		this.data2 = s;
	}
	public String getData3(){
		return this.data3;
	}
	public void setData3(String s){
		this.data3 = s;
	}
	public String getData4(){
		return this.data4;
	}
	public void setData4(String s){
		this.data4 = s;
	}
	public String getData5(){
		return this.data5;
	}
	public void setData5(String s){
		this.data5 = s;
	}
	
	
}
