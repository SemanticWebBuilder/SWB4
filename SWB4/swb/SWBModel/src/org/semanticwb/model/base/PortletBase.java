package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PortletBase extends GenericObjectBase implements Versionable,Indexable,XMLable,XMLConfable,Descriptiveable,Viewable,Calendarable,Activeable,RuleRefable,Traceable,RoleRefable,Priorityable,Deleteable,Localeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletBase(SemanticObject base)
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
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public void setCamp(org.semanticwb.model.Camp camp)
    {
        getSemanticObject().setObjectProperty(vocabulary.camp, camp.getSemanticObject());
    }

    public void removeCamp()
    {
        getSemanticObject().removeProperty(vocabulary.camp);
    }

    public Camp getCamp()
    {
         Camp ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.camp);
         if(obj!=null)
         {
             ret=(Camp)vocabulary.swb_Camp.newGenericInstance(obj);
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

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.title, null, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(vocabulary.actualVersion);
    }

    public VersionInfo getActualVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.actualVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.swb_VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public int getHits()
    {
        return getSemanticObject().getIntProperty(vocabulary.hits);
    }

    public void setHits(int hits)
    {
        getSemanticObject().setLongProperty(vocabulary.hits, hits);
    }

    public String getXml()
    {
        return getSemanticObject().getProperty(vocabulary.xml);
    }

    public void setXml(String xml)
    {
        getSemanticObject().setProperty(vocabulary.xml, xml);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(vocabulary.lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(vocabulary.lastVersion);
    }

    public VersionInfo getLastVersion()
    {
         VersionInfo ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.lastVersion);
         if(obj!=null)
         {
             ret=(VersionInfo)vocabulary.swb_VersionInfo.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return new GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, getSemanticObject().listObjectProperties(vocabulary.hasCalendar));    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasCalendar, calendar.getSemanticObject());
    }

    public void removeAllCalendar()
    {
        getSemanticObject().removeProperty(vocabulary.hasCalendar);
    }

    public void removeCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasCalendar,calendar.getSemanticObject());
    }

    public Calendar getCalendar()
    {
         Calendar ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasCalendar);
         if(obj!=null)
         {
             ret=(Calendar)vocabulary.swb_Calendar.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.indexable, indexable);
    }

    public boolean isPortletWindow()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.portletWindow);
    }

    public void setPortletWindow(boolean portletWindow)
    {
        getSemanticObject().setBooleanProperty(vocabulary.portletWindow, portletWindow);
    }

    public String getXmlConf()
    {
        return getSemanticObject().getProperty(vocabulary.xmlConf);
    }

    public void setXmlConf(String xmlConf)
    {
        getSemanticObject().setProperty(vocabulary.xmlConf, xmlConf);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.priority, priority);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, getSemanticObject().listObjectProperties(vocabulary.hasRoleRef));    }

    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRoleRef, roleref.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRoleRef,roleref.getSemanticObject());
    }

    public RoleRef getRoleRef()
    {
         RoleRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRoleRef);
         if(obj!=null)
         {
             ret=(RoleRef)vocabulary.swb_RoleRef.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.views, views);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().setObjectProperty(vocabulary.language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.language);
    }

    public Language getLanguage()
    {
         Language ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.language);
         if(obj!=null)
         {
             ret=(Language)vocabulary.swb_Language.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, getSemanticObject().listObjectProperties(vocabulary.hasRuleRef));    }

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasRuleRef, ruleref.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasRuleRef,ruleref.getSemanticObject());
    }

    public RuleRef getRuleRef()
    {
         RuleRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasRuleRef);
         if(obj!=null)
         {
             ret=(RuleRef)vocabulary.swb_RuleRef.newGenericInstance(obj);
         }
         return ret;
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
             ret=(User)vocabulary.swb_User.newGenericInstance(obj);
         }
         return ret;
    }

    public void setPortletSubType(org.semanticwb.model.PortletSubType portletsubtype)
    {
        getSemanticObject().setObjectProperty(vocabulary.portletSubType, portletsubtype.getSemanticObject());
    }

    public void removePortletSubType()
    {
        getSemanticObject().removeProperty(vocabulary.portletSubType);
    }

    public PortletSubType getPortletSubType()
    {
         PortletSubType ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.portletSubType);
         if(obj!=null)
         {
             ret=(PortletSubType)vocabulary.swb_PortletSubType.newGenericInstance(obj);
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

    public void setPortletType(org.semanticwb.model.PortletType portlettype)
    {
        getSemanticObject().setObjectProperty(vocabulary.portletType, portlettype.getSemanticObject());
    }

    public void removePortletType()
    {
        getSemanticObject().removeProperty(vocabulary.portletType);
    }

    public PortletType getPortletType()
    {
         PortletType ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.portletType);
         if(obj!=null)
         {
             ret=(PortletType)vocabulary.swb_PortletType.newGenericInstance(obj);
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
