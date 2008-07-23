package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class DnsBase extends SemanticObject implements Descriptiveable,WebSiteable,WebPageable,Valueable
{
    public DnsBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public Date getCreated()
    {
        return getDateProperty(Vocabulary.created);
    }
    public void setCreated(Date created)
    {
        setDateProperty(Vocabulary.created, created);
    }
    public SemanticIterator<org.semanticwb.model.User> listUser()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.userModified.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.User>(org.semanticwb.model.User.class, stit);
    }
    public void addUser(org.semanticwb.model.User user)
    {
        addObjectProperty(Vocabulary.userModified, user);
    }
    public void removeAllUser()
    {
        getRDFResource().removeAll(Vocabulary.userModified.getRDFProperty());
    }
    public User getUser()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.userModified.getRDFProperty());
         SemanticIterator<org.semanticwb.model.User> it=new SemanticIterator<org.semanticwb.model.User>(User.class, stit);
         return it.next();
    }
    public String getValue()
    {
        return getProperty(Vocabulary.value);
    }
    public void setValue(String value)
    {
        setProperty(Vocabulary.value, value);
    }
    public String getTitle()
    {
        return getProperty(Vocabulary.title);
    }
    public void setTitle(String title)
    {
        setProperty(Vocabulary.title, title);
    }
    public String getDescription()
    {
        return getProperty(Vocabulary.description);
    }
    public void setDescription(String description)
    {
        setProperty(Vocabulary.description, description);
    }
    public SemanticIterator<org.semanticwb.model.WebPage> listWebPage()
    {
        StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasWebPage.getRDFProperty());
        return new SemanticIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit);
    }
    public void addWebPage(org.semanticwb.model.WebPage webpage)
    {
        addObjectProperty(Vocabulary.hasWebPage, webpage);
    }
    public void removeAllWebPage()
    {
        getRDFResource().removeAll(Vocabulary.hasWebPage.getRDFProperty());
    }
    public WebPage getWebPage()
    {
         StmtIterator stit=getRDFResource().listProperties(Vocabulary.hasWebPage.getRDFProperty());
         SemanticIterator<org.semanticwb.model.WebPage> it=new SemanticIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         return it.next();
    }
    public Date getUpdated()
    {
        return getDateProperty(Vocabulary.updated);
    }
    public void setUpdated(Date updated)
    {
        setDateProperty(Vocabulary.updated, updated);
    }
}
