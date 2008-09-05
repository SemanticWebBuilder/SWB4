package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletSubTypeBase extends GenericObjectBase implements Traceable,Descriptiveable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletSubTypeBase(SemanticObject base)
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
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         return (User)vocabulary.User.newGenericInstance(obj);
    }

    public GenericIterator<org.semanticwb.model.Portlet> listPortlets()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasPSTPortlets.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.Portlet>(org.semanticwb.model.Portlet.class, stit,true);
    }

    public Portlet getPortlet()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPSTPortlets);
         return (Portlet)vocabulary.Portlet.newGenericInstance(obj);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setType(org.semanticwb.model.PortletType portlettype)
    {
        getSemanticObject().addObjectProperty(vocabulary.PSTType, portlettype.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(vocabulary.PSTType);
    }

    public PortletType getType()
    {
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.PSTType);
         return (PortletType)vocabulary.PortletType.newGenericInstance(obj);
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
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         return (User)vocabulary.User.newGenericInstance(obj);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
