/*
 * Copyright 2010 Antoine Seilles (Natoine)
 *   This file is part of model-user.

    model-user is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    model-user is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with model-user.  If not, see <http://www.gnu.org/licenses/>.

 */
package fr.natoine.model_user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "AGENTSTATUS")
@XmlRootElement
public class AgentStatus implements Serializable
{
	@Id @GeneratedValue
    @Column(name = "AGENTSTATUS_ID")
	private Long id;
	
	@Column(name = "LABEL" , unique=true, nullable=false)
	private String label;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = AgentStatus.class)
	@JoinColumn(name = "FATHERAGENTSTATUS_ID")
	private AgentStatus father;

	/**
	 * Gets the label of the Status
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Sets the label of the Status
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Gets the comment of the status
	 * @return
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * Sets the comment of the status
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * Gets the father of the status
	 * @return
	 */
	public AgentStatus getFather() {
		return father;
	}
	/**
	 * Sets the father of the status
	 * @param father
	 */
	public void setFather(AgentStatus father) {
		this.father = father;
	}
	/**
	 * Gets the id of the status
	 * @return
	 */
	public Long getId() {
		return id;
	}
}
