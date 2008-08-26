package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class DnsBase extends GenericObjectBase implements WebPageable,Descriptiveable,Traceable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public DnsBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.created, created);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.modifiedBy.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().addObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.creator.getRDFProperty());
         GenericIterator<org.semanticwb.model.User> it=new GenericIterator<org.semanticwb.model.User>(User.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
    }

    public GenericIterator<org.semanticwb.model.WebPage> listWebPage()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasWebPage.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit);
    }

    public void addWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasWebPage, webpage.getSemanticObject());
    }

    public void removeAllWebPage()
    {
        getSemanticObject().removeProperty(vocabulary.hasWebPage);
    }

    public void removeWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasWebPage,webpage.getSemanticObject());
    }

    public WebPage getWebPage()
    {
         WebPage ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasWebPage.getRDFProperty());
         GenericIterator<org.semanticwb.model.WebPage> it=new GenericIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public boolean isDnsDefault()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.dnsDefault);
    }

    public void setDnsDefault(boolean dnsDefault)
    {
        getSemanticObject().setBooleanProperty(vocabulary.dnsDefault, dnsDefault);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
