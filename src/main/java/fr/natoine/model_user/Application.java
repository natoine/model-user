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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.RDFexportable;
import fr.natoine.model_resource.Resource;
import fr.natoine.model_resource.ResourceRepresentable;

@Entity
@Table(name = "APPLICATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
public class Application implements Serializable, ResourceRepresentable, RDFexportable
{
	@Id @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "APPLICATION_ID")
	private Long id;
	
	@Column(name = "LABEL", nullable=false, unique=true)
	private String label;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "DATE_INSCRIPTION")
	private Date inscription;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Resource.class)
	@JoinColumn(name = "RESOURCE_ID" , nullable=false)
	private Resource represents;

	/**
	 * Gets the label of an application
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Sets the label of an application
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Gets the description of an application
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the description of an application
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets the date of inscription of an application
	 * @return
	 */
	public Date getInscription() {
		return inscription;
	}
	/**
	 * Sets the date of inscription of an application
	 * @param inscription
	 */
	public void setInscription(Date inscription) {
		this.inscription = inscription;
	}
	/**
	 * Gets the id of an application
	 * @return
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Sets the Resource that represents the application
	 * @param represents
	 */
	public void setRepresents(Resource represents) {
		this.represents = represents;
	}
	/**
	 * Gets the Resource that represents the application
	 * @return
	 */
	public Resource getRepresents() {
		return represents;
	}
	public List<String> rdfHeader() {
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:irw=\"http://ontologydesignpatterns.org/ont/web/irw.owl\"");
		return _nms ;
	}
	public String toRDF(String _url_rdf_agent , String _rdf_toinsert) 
	{
		String _rdf = "<irw:AbstractResource rdf:about=\""+ _url_rdf_agent +"?id=" + id +"\" >" ;
		if(! _url_rdf_agent.equalsIgnoreCase(represents.getRepresentsResource().getEffectiveURI()))
		{
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + represents.getRepresentsResource().getEffectiveURI() + "\" />");
		}
		_rdf = _rdf.concat("<rdfs:label>"+ label +"</rdfs:label>");
		_rdf = _rdf.concat("<dcterms:created>" + inscription + "</dcterms:created>");
		if(description!=null) _rdf = _rdf.concat("<rdfs:comment>"+ description +"</rdfs:comment>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</irw:AbstractResource>");
		return _rdf;
	}
}