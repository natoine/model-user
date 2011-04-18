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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.Resource;
import fr.natoine.model_resource.ResourceRepresentable;


@Entity
@Table(name = "USERACCOUNT" ,
		uniqueConstraints=@UniqueConstraint(columnNames = {"PSEUDO" , "APPLICATION_ID"}))
@XmlRootElement
public class UserAccount extends Agent implements ResourceRepresentable
{
	@Column(name = "PSEUDO" , nullable=false)
	private String pseudonyme;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Person.class)
	@JoinColumn(name = "PERSON_ID")
	private Person user;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Resource.class)
	@JoinColumn(name = "RESOURCE_ID" , nullable=false)
	private Resource represents;
	
	/**
	 * Gets the Resource that represents the UserAccount.
	 * A UserAccount must be represented by a Resource.
	 * The Resource can represent many other things and is not necessary accessible. 
	 * @return
	 */
	public Resource getRepresents() {
		return represents;
	}
	/**
	 * Sets the Resource that represents the UserAccount.
	 * A UserAccount must be represented by a Resource.
	 * The Resource can represent many other things and is not necessary accessible. 
	 * @param 
	 */
	public void setRepresents(Resource represents) {
		this.represents = represents;
	}
	/**
	 * Gets the pseudonyme of the person for this UserAccount
	 * @return
	 */
	public String getPseudonyme() {
		return pseudonyme;
	}
	/**
	 * Sets the pseudonyme of the person for this UserAccount
	 * @param
	 */
	public void setPseudonyme(String pseudonyme) {
		this.pseudonyme = pseudonyme;
	}
	/**
	 * Gets the user/person associated to this account.
	 * @return a Person
	 */
	public Person getUser() {
		return user;
	}
	/**
	 * Sets the user/person associated to this account
	 * @param user
	 */
	public void setUser(Person user) {
		this.user = user;
	}
	/**
	 * Gets the password of the UserAccount
	 * TODO : encrypt this method
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password of the UserAccount
	 * TODO : encrypt this method
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> rdfHeader() {
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
		_nms.add("xmlns:sioc=\"http://rdfs.org/sioc/ns#\"");
		return _nms;
	}
	public String toRDF(String _url_rdf_agent, String _rdf_toinsert) 
	{
		String _rdf = "<sioc:UserAccount rdf:about=\""+ _url_rdf_agent +"?id=" + getId() +"\" rdfs:label=\"" + pseudonyme + "\" >" ;
		if(! _url_rdf_agent.equalsIgnoreCase(represents.getRepresentsResource().getEffectiveURI()))
		{
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + represents.getRepresentsResource().getEffectiveURI() + "\" />");
		}
		_rdf = _rdf.concat("<sioc:account_of>");
		_rdf = _rdf.concat(this.getUser().toRDF(_url_rdf_agent , ""));
		_rdf = _rdf.concat("</sioc:account_of>");
		_rdf = _rdf.concat("<dcterms:created>" + this.getInscription() + "</dcterms:created>");
		if(this.getDescription()!=null) _rdf = _rdf.concat("<rdfs:comment>"+ this.getDescription() +"</rdfs:comment>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</sioc:UserAccount>");
		return _rdf;
	}
}