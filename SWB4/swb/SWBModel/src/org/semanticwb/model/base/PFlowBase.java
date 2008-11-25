package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PFlowBase extends GenericObjectBase implements Deleteable,Groupable,XMLable,Versionable,Traceable,Descriptiveable,Activeable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PFlowBase(SemanticObject base)
    {
        super(base);
    }

    public Date getCreated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_created);
    }

    public void setCreated(Date created)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_created, created);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_deleted, deleted);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_active, active);
    }

    public void setModifiedBy(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_modifiedBy, user.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(vocabulary.swb_modifiedBy);
    }

    public User getModifiedBy()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_title, title, lang);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(vocabulary.swb_actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_actualVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.swb_VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public String getXml()
    {
        return getSemanticObject().getProperty(vocabulary.swb_xml);
    }

    public void setXml(String xml)
    {
        getSemanticObject().setProperty(vocabulary.swb_xml, xml);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_updated, updated);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(vocabulary.swb_lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_lastVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.swb_VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User user)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_creator, user.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(vocabulary.swb_creator);
    }

    public User getCreator()
    {
         User ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_creator);
         if(obj!=null)
         {
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_description, description, lang);
    }

    public GenericIterator<org.semanticwb.model.ObjectGroup> listGroups()
    {
        return new GenericIterator<org.semanticwb.model.ObjectGroup>(org.semanticwb.model.ObjectGroup.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasGroup));    }

    public void addGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasGroup, objectgroup.getSemanticObject());
    }

    public void removeAllGroup()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasGroup);
    }

    public void removeGroup(org.semanticwb.model.ObjectGroup objectgroup)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasGroup,objectgroup.getSemanticObject());
    }

    public ObjectGroup getGroup()
    {
         ObjectGroup ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasGroup);
         if(obj!=null)
         {
             ret=(ObjectGroup)vocabulary.swb_ObjectGroup.newGenericInstance(obj);
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
