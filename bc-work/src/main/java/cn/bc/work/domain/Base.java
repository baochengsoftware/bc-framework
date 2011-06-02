/**
 * 
 */
package cn.bc.work.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import cn.bc.core.DefaultEntity;
import cn.bc.identity.domain.ActorImpl;

/**
 * 待办\已办\草稿事项的基类
 * 
 * @author dragon
 */
@MappedSuperclass
public class Base extends DefaultEntity {
	private static final long serialVersionUID = 1L;

	private Work work;// 工作
	private Calendar sendDate;// 发送时间
	private ActorImpl sender;// 发送人
	private ActorImpl worker;// 办理人
	private String info;// 附加说明

	@Column(name = "SEND_DATE")
	public Calendar getSendDate() {
		return sendDate;
	}

	public void setSendDate(Calendar sendDate) {
		this.sendDate = sendDate;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false, targetEntity = ActorImpl.class)
	@JoinColumn(name = "SENDER_ID", referencedColumnName = "ID")
	public ActorImpl getSender() {
		return sender;
	}

	public void setSender(ActorImpl sender) {
		this.sender = sender;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "WORK_ID", referencedColumnName = "ID")
	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public ActorImpl getWorker() {
		return worker;
	}

	public void setWorker(ActorImpl worker) {
		this.worker = worker;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
