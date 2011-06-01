/**
 * 
 */
package cn.bc.message.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.bc.core.DefaultEntity;
import cn.bc.identity.domain.ActorImpl;

/**
 * 消息
 * 
 * @author dragon
 */
@Entity
@Table(name = "BC_MESSAGE")
public class Message extends DefaultEntity {
	private static final long serialVersionUID = 1L;

	private String subject;// 标题
	private String content;// 内容
	private ActorImpl sender;// 发送人

	@ManyToOne(targetEntity = ActorImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "AID", nullable = true, updatable = false)
	public ActorImpl getSender() {
		return sender;
	}

	public void setSender(ActorImpl sender) {
		this.sender = sender;
	}
}
