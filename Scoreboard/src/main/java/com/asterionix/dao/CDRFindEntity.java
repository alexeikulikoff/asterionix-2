package com.asterionix.dao;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cdr")
public class CDRFindEntity  implements IEntity{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	@NotNull	
	private int id;
	
	public CDRFindEntity()
	{
		
	}
	public void setId(int i){
		this.id = i;
	}
	public int getId(){
		return this.id;
	}
	
	@Column(name = "calldate")
	private String calldate;
	
	public 	String getCalldate(){
		return this.calldate;
	}
	public void setCalldate(String s){
		this.calldate = s;
	}
	
	@Column(name = "clid")
	private String clid;
	public String getClid(){
		return this.clid;
	}
	public void setClid(String s){
		this.clid = s;
	}
	
	@Column(name = "src")
	private String src;
	public String getSrc(){
		
		return this.src;
	}
	public void setSrc(String s){
		this.src = s;
	}
	
	@Column(name = "dst")
	private String dst;
	public String getDst(){
		return this.dst;
	}
	public void setDst(String s){
		this.dst = s;
	}
	
	@Column(name = "dcontext")
	private String dcontext;
	public String getdcontext(){
		return this.dcontext;
	}
	public void setdcontext(String s){
		this.dcontext = s;
	}	
	
	
	@Column(name = "lastapp")
	private String lastapp;
	public String getlastapp(){
		return this.lastapp;
	}
	public void setlastapp(String s){
		this.lastapp = s;
	}
	
	
	@Column(name = "lastdata")
	private String lastdata;
	public String getlastdata(){
		return this.lastdata;
	}
	public void setlastdata(String s){
		this.lastdata = s;
	}
	
	@Column(name = "duration")
	private String duration;
	public String getduration(){
		return this.duration;
	}
	public void setduration(String s){
		this.duration = s;
	}
	
	
	@Column(name = "billsec")
	private String billsec;
	public String getbillsec(){
		return this.billsec;
	}
	public void setbillsec(String s){
		this.billsec = s;
	}
	
	@Column(name = "disposition")
	private String disposition;
	public String getdisposition(){
		return this.disposition;
	}
	public void setdisposition(String s){
		this.src = disposition;
	}
	
	@Column(name = "channel")
	private String channel;
	public String getchannel(){
		return this.channel;
	}
	public void setchannel(String s){
		this.src = channel;
	}
	
	
	@Column(name = "dstchannel")
	private String dstchannel;
	public String getdstchannel(){
		return this.dstchannel;
	}
	public void setdstchannel(String s){
		this.src = dstchannel;
	}	
	@Column(name = "amaflags")
	private String amaflags;
	public String getamaflags(){
		return this.amaflags;
	}
	public void setamaflags(String s){
		this.src = amaflags;
	}	
	
	@Column(name = "accountcode")
	private String accountcode;
	public String getaccountcode(){
		return this.accountcode;
	}
	public void setaccountcode(String s){
		this.src = accountcode;
	}	
	@Column(name = "uniqueid")
	private String uniqueid;
	public String getuniqueid(){
		return this.uniqueid;
	}
	public void setuniqueid(String s){
		this.src = uniqueid;
	}	
	@Column(name = "userfield")
	private String userfield;
	public String getuserfield(){
		return this.userfield;
	}
	public void setuserfield(String s){
		this.src = userfield;
	}	
	@Column(name = "answer")
	private String answer;
	public String getanswer(){
		return this.answer;
	}
	public void setanswer(String s){
		this.src = answer;
	}	
	@Column(name = "end")
	private String end;
	public String getend(){
		return this.end;
	}
	public void setend(String s){
		this.src = end;
	}	

	

}
