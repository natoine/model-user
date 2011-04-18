package fr.natoine.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.natoine.model_resource.Resource;
import fr.natoine.model_resource.URI;
import fr.natoine.model_user.Agent;
import fr.natoine.model_user.Application;
import fr.natoine.model_user.Person;
import fr.natoine.model_user.UserAccount;

import junit.framework.TestCase;

public class UserTest extends TestCase
{
	public UserTest(String name) 
	{
	    super(name);
	}
	
	public void testCreate()
	{
		//créer
		Agent _testAgent = new Agent();
		_testAgent.setDescription("description de l'agent créé par le USerTest");
		_testAgent.setLabel("agent de test");
		_testAgent.setInscription(new Date());
		Application _app = new Application();
		_app.setDescription("application test user");
		_app.setInscription(new Date());
		_app.setLabel("application de test user");
		Resource representsApp = new Resource();
		representsApp.setContextCreation("test creation user application");
		representsApp.setCreation(new Date());
		representsApp.setLabel("resource pour représenter l'application de test");
		URI representsResourceURI = new URI();
		representsResourceURI.setEffectiveURI("http://www.mon.application.de.test");
		representsApp.setRepresentsResource(representsResourceURI);
		_app.setRepresents(representsApp);
		_testAgent.setContextInscription(_app);
		
		Person _testPerson = new Person();
		UserAccount _testUserAccount = new UserAccount();
		//setter les attributs
		_testPerson.setFirstName("firstName");
		_testPerson.setLastName("lastName");
		_testPerson.setMail("mail@mail");
		_testPerson.setLabel(_testPerson.getFirstName() + _testPerson.getLastName());
		_testPerson.setDescription("description de la personne créée par le UserTest");
		_testPerson.setContextInscription(_app);
		_testPerson.setInscription(new Date());
		Resource _represents = new Resource();
		_represents.setContextCreation("UserTest");
		_represents.setCreation(new Date());
		_represents.setLabel("Person Resource");
		URI _uri1 = new URI();
		_uri1.setEffectiveURI("http://person.pseudo.fr");
		_represents.setRepresentsResource(_uri1);
		_testPerson.setRepresents(_represents);
		
		_testUserAccount.setContextInscription(_app);
		_testUserAccount.setInscription(new Date());
		_testUserAccount.setPassword("password");
		_testUserAccount.setPseudonyme("pseudo");
		_testUserAccount.setLabel(_testUserAccount.getPseudonyme());
		_testUserAccount.setDescription("description du user account créé par le usertest");
		_testUserAccount.setUser(_testPerson);
		Resource _representsAccount = new Resource();
		_representsAccount.setContextCreation("UserTest");
		_representsAccount.setCreation(new Date());
		_representsAccount.setLabel("UserAccount Resource");
		URI _uri2 = new URI();
		_uri2.setEffectiveURI("http://userAccount.pseudo.fr");
		_representsAccount.setRepresentsResource(_uri2);
		_testUserAccount.setRepresents(_representsAccount);
		
		Collection<UserAccount> user_accounts = new ArrayList<UserAccount>() ;
		user_accounts.add(_testUserAccount);
		//_testPerson.setUserAccounts(user_accounts);
		//faire persister
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
        	tx.begin();
        	em.persist(_testAgent);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[UserTest] unable to persist agent" );
        	 System.out.println(e.getStackTrace());
        }
        try{
        	tx.begin();
        	em.persist(_testUserAccount);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[UserTest] unable to persist" );
        	 System.out.println(e.getStackTrace());
        }
        em.close();
	}
	public void testRetrieve()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("user");
        EntityManager newEm = emf.createEntityManager();
        EntityTransaction newTx = newEm.getTransaction();
        newTx.begin();
        List persons = newEm.createQuery("from Person").getResultList();
        System.out.println( persons.size() + " user(s) found" );
        Person loadedPerson ;
        for (Object u : persons) 
        {
        	loadedPerson = (Person) u;
            System.out.println("[UserTest] User id : " + loadedPerson.getId()  
            		+ " firstname : " + loadedPerson.getFirstName()
            		+ " lastname : " + loadedPerson.getLastName()
            		+ " mail : " + loadedPerson.getMail()
            		);
        }
        newTx.commit();
        newEm.close();
        // Shutting down the application
        emf.close();
	}
}
