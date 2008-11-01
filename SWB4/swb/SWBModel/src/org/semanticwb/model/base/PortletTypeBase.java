package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletTypeBase extends GenericObjectBase implements Descriptiveable,Traceable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletTypeBase(SemanticObject base)
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
        getSemanticObject().setObjectProperty(vocabulary.modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getPortletBundle()
    {
        return getSemanticObject().getProperty(vocabulary.portletBundle);
    }

    public void setPortletBundle(String portletBundle)
    {
        getSemanticObject().setProperty(vocabulary.portletBundle, portletBundle);
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
        return getSemanticObject().getProperty(vocabulary.title, null, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public int getPortletCache()
    {
        return getSemanticObject().getIntProperty(vocabulary.portletCache);
    }

    public void setPortletCache(int portletCache)
    {
        getSemanticObject().setLongProperty(vocabulary.portletCache, portletCache);
    }

    public String getPortletClassName()
    {
        return getSemanticObject().getProperty(vocabulary.portletClassName);
    }

    public void setPortletClassName(String portletClassName)
    {
        getSemanticObject().setProperty(vocabulary.portletClassName, portletClassName);
    }

    public GenericIterator<org.semanticwb.model.Portlet> listPortlets()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasPTPortlet.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.Portlet>(org.semanticwb.model.Portlet.class, stit,true);
    }

    public Portlet getPortlet()
    {
         Portlet ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPTPortlet);
         if(obj!=null)
         {
             ret=(Portlet)vocabulary.Portlet.newGenericInstance(obj);
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

    public int getPortletMode()
    {
        return getSemanticObject().getIntProperty(vocabulary.portletMode);
    }

    public void setPortletMode(int portletMode)
    {
        getSemanticObject().setLongProperty(vocabulary.portletMode, portletMode);
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.User.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.PortletSubType> listSubTypes()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasPTSubType.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.PortletSubType>(org.semanticwb.model.PortletSubType.class, stit,true);
    }

    public PortletSubType getSubType()
    {
         PortletSubType ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPTSubType);
         if(obj!=null)
         {
             ret=(PortletSubType)vocabulary.PortletSubType.newGenericInstance(obj);
         }
         return ret;
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
        return getSemanticObject().getProperty(vocabulary.description, null, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, null, getSemanticObject().getRDFResource());
        return new GenericIterator((SemanticClass)null, stit,true);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
