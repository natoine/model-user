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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.Resource;
import fr.natoine.model_resource.ResourceRepresentable;


@Entity
@Table(name = "PERSON")
@XmlRootElement
public class Person extends Agent implements ResourceRepresentable
{
	@Column(name = "MAIL" , unique=true, nullable=false)
	private String mail;
	
	@Column(name = "FIRSTNAME")
	private String firstName;
	
	@Column(name = "LASTNAME")
	private String lastName;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Resource.class)
	@JoinColumn(name = "RESOURCE_ID" , nullable=false)
	private Resource represents;
	
	/**
	 * Gets the Resource that represents the Person.
	 * A Person must be represented by a Resource.
	 * The Resource can represent many other things and is not necessary accessible.
	 * @return
	 */
	public Resource getRepresents() {
		return represents;
	}
	/**
	 * Sets the Resource that represents the Person.
	 * A Person must be represented by a Resource.
	 * The Resource can represent many other things and is not necessary accessible. 
	 * @param represents
	 */
	public void setRepresents(Resource represents) {
		this.represents = represents;
	}
	/**
	 * Gets the firstname of the person.
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Sets the firstname of the person.
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Gets the lastname of the person.
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Sets the lastname of the person.
	 * @param
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Gets the mail of the Person.
	 * @return
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * Sets the mail of the Person.
	 * @param 
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
		String _rdf = "<foaf:Person rdf:about=\""+ _url_rdf_agent +"?id=" + getId() +"\" >" ;
		if(! _url_rdf_agent.equalsIgnoreCase(represents.getRepresentsResource().getEffectiveURI()))
		{
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + represents.getRepresentsResource().getEffectiveURI() + "\" />");
		}
		_rdf = _rdf.concat("<foaf:name>"+ this.getLabel() +"</foaf:name>");
		//_rdf = _rdf.concat("<rdfs:label>"+ this.getLabel() +"</rdfs:label>");
		_rdf = _rdf.concat("<foaf:familyName>" + lastName + "</foaf:familyName>");
		_rdf = _rdf.concat("<foaf:firstName>" + firstName + "</foaf:firstName>");
		_rdf = _rdf.concat("<dcterms:created>" + this.getInscription() + "</dcterms:created>");
		if( this.getDescription() != null) _rdf = _rdf.concat("<rdfs:comment>"+ this.getDescription() +"</rdfs:comment>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</foaf:Person>");
		return _rdf;
	}
}