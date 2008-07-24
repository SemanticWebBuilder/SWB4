package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class DnsBase extends SemanticObject implements Descriptiveable,WebSiteable,WebPageable,Valueable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public DnsBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public Date getCreated()
    {
        return getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        setDateProperty(vocabulary.created, created);
    }

    public SemanticIterator<org.semanticwb.model.User> listUser()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit);
    }

    public void addUser(org.semanticwb.model.User user)
    {
        addObjectProperty(vocabulary.userModified, user);
    }

    public void removeAllUser()
    {
        getRDFResource().removeAll(vocabulary.userModified.getRDFProperty());
    }

    public User getUser()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }

    public String getValue()
    {
        return getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        setProperty(vocabulary.value, value);
    }

    public String getTitle()
    {
        return getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        setProperty(vocabulary.title, title);
    }

    public String getDescription()
    {
        return getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        setProperty(vocabulary.description, description);
    }

    public SemanticIterator<org.semanticwb.model.WebPage> listWebPage()
    {
        StmtIterator stit=getRDFResource().listProperties(vocabulary.hasWebPage.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit);
    }

    public void addWebPage(org.semanticwb.model.WebPage webpage)
    {
        addObjectProperty(vocabulary.hasWebPage, webpage);
    }

    public void removeAllWebPage()
    {
        getRDFResource().removeAll(vocabulary.hasWebPage.getRDFProperty());
    }

    public WebPage getWebPage()
    {
         StmtIterator stit=getRDFResource().listProperties(vocabulary.hasWebPage.getRDFProperty());
         SemanticIterator<org.semanticwb.model.WebPage> it=new SemanticIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         return it.next();
    }

    public Date getUpdated()
    {
        return getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        setDateProperty(vocabulary.updated, updated);
    }
}
