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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.RDFexportable;


@Entity
@Table(name = "AGENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
public class Agent implements Serializable, RDFexportable
{
	@Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "AGENT_ID")
	private Long id;
	
	@Column(name = "LABEL")
	private String label;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "DATE_INSCRIPTION")
	private Date inscription;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Application.class)
	@JoinColumn(name = "APPLICATION_ID")
	private Application context_inscription;
	
	@ManyToMany(cascade = CascadeType.ALL, targetEntity = AgentStatus.class)
	@JoinTable(
			name="AGENT_TO_AGENTSTATUS",
			joinColumns=@JoinColumn(name="AGENT_ID"),
	        inverseJoinColumns=@JoinColumn(name="AGENTSTATUS_ID")
				)
	private Collection<AgentStatus> status;

	/**
	 * Gets the id of the agent.
	 * @return
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Gets the label of the agent
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Sets the label of the agent
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Gets the description of the agent
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of the agent
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets the date of creation of the Agent 
	 * @return
	 */
	public Date getInscription() {
		return inscription;
	}
	/**
	 * Sets the date of creation of the Agent 
	 * @param inscription
	 */
	public void setInscription(Date inscription) {
		this.inscription = inscription;
	}
	/**
	 * Gets the context of inscription of the Agent
	 * The context is a String used to define the application context.
	 * @return
	 */
	public Application getContextInscription() {
		return context_inscription;
	}
	/**
	 * Sets the context of inscription of the Agent
	 * The context is a String used to define the application context.
	 * @param contextInscription
	 */
	public void setContextInscription(Application contextInscription) {
		context_inscription = contextInscription;
	}
	/**
	 * Sets the collection of AgentStatus of an agent
	 * @param status
	 */
	public void setStatus(Collection<AgentStatus> status) {
		this.status = status;
	}
	/**
	 * Gets the collection of AgentStatus of an agent
	 * @return
	 */
	public Collection<AgentStatus> getStatus() {
		return status;
	}
	public List<String> rdfHeader() {
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
		return _nms;
	}
	public String toRDF(String _url_rdf_agent , String _rdf_toinsert) 
	{
		String _rdf = "<foaf:Agent rdf:about=\""+ _url_rdf_agent +"?id=" + id +"\" >" ;
		_rdf = _rdf.concat("<rdfs:label>"+ label +"</rdfs:label>");
		_rdf = _rdf.concat("<dcterms:created>" + inscription + "</dcterms:created>");
		if(description != null)_rdf = _rdf.concat("<rdfs:comment>"+ description +"</rdfs:comment>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</foaf:Agent>");
		return _rdf;
	}
	public String getRDFLabel()
	{
		String _labelRDF = this.getLabel().replaceAll(" ", "");
		_labelRDF = _labelRDF.replaceAll("'", "");
		_labelRDF = _labelRDF.replaceAll("é", "e");
		_labelRDF = _labelRDF.replaceAll("è", "e");
		_labelRDF = _labelRDF.replaceAll("ê", "e");
		_labelRDF = _labelRDF.replaceAll("ë", "e");
		_labelRDF = _labelRDF.replaceAll("à", "a");
		_labelRDF = _labelRDF.substring(0,1).toUpperCase() + _labelRDF.substring(1 , _labelRDF.length());
		return _labelRDF ;
	}
}
