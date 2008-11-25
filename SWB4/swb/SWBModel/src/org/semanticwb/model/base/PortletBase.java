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

public class PortletBase extends GenericObjectBase implements Versionable,Indexable,XMLable,XMLConfable,Descriptiveable,Viewable,Calendarable,Activeable,RuleRefable,Traceable,RoleRefable,Priorityable,Deleteable,Localeable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PortletBase(SemanticObject base)
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

    public void setCamp(org.semanticwb.model.Camp camp)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_camp, camp.getSemanticObject());
    }

    public void removeCamp()
    {
        getSemanticObject().removeProperty(vocabulary.swb_camp);
    }

    public Camp getCamp()
    {
         Camp ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_camp);
         if(obj!=null)
         {
             ret=(Camp)vocabulary.swb_Camp.newGenericInstance(obj);
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

    public int getHits()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_hits);
    }

    public void setHits(int hits)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_hits, hits);
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

    public GenericIterator<org.semanticwb.model.Calendar> listCalendars()
    {
        return new GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasCalendar));    }

    public void addCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasCalendar, calendar.getSemanticObject());
    }

    public void removeAllCalendar()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasCalendar);
    }

    public void removeCalendar(org.semanticwb.model.Calendar calendar)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasCalendar,calendar.getSemanticObject());
    }

    public Calendar getCalendar()
    {
         Calendar ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasCalendar);
         if(obj!=null)
         {
             ret=(Calendar)vocabulary.swb_Calendar.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_indexable, indexable);
    }

    public boolean isPortletWindow()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_portletWindow);
    }

    public void setPortletWindow(boolean portletWindow)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_portletWindow, portletWindow);
    }

    public String getXmlConf()
    {
        return getSemanticObject().getProperty(vocabulary.swb_xmlConf);
    }

    public void setXmlConf(String xmlConf)
    {
        getSemanticObject().setProperty(vocabulary.swb_xmlConf, xmlConf);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_priority, priority);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_deleted, deleted);
    }

    public GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasRoleRef));    }

    public void addRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasRoleRef, roleref.getSemanticObject());
    }

    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasRoleRef);
    }

    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasRoleRef,roleref.getSemanticObject());
    }

    public RoleRef getRoleRef()
    {
         RoleRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(RoleRef)vocabulary.swb_RoleRef.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_active, active);
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_views, views);
    }

    public void setLanguage(org.semanticwb.model.Language language)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_language, language.getSemanticObject());
    }

    public void removeLanguage()
    {
        getSemanticObject().removeProperty(vocabulary.swb_language);
    }

    public Language getLanguage()
    {
         Language ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_language);
         if(obj!=null)
         {
             ret=(Language)vocabulary.swb_Language.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasRuleRef));    }

    public void addRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasRuleRef, ruleref.getSemanticObject());
    }

    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasRuleRef);
    }

    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasRuleRef,ruleref.getSemanticObject());
    }

    public RuleRef getRuleRef()
    {
         RuleRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(RuleRef)vocabulary.swb_RuleRef.newGenericInstance(obj);
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

    public void setPortletSubType(org.semanticwb.model.PortletSubType portletsubtype)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_portletSubType, portletsubtype.getSemanticObject());
    }

    public void removePortletSubType()
    {
        getSemanticObject().removeProperty(vocabulary.swb_portletSubType);
    }

    public PortletSubType getPortletSubType()
    {
         PortletSubType ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_portletSubType);
         if(obj!=null)
         {
             ret=(PortletSubType)vocabulary.swb_PortletSubType.newGenericInstance(obj);
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

    public void setPortletType(org.semanticwb.model.PortletType portlettype)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_portletType, portlettype.getSemanticObject());
    }

    public void removePortletType()
    {
        getSemanticObject().removeProperty(vocabulary.swb_portletType);
    }

    public PortletType getPortletType()
    {
         PortletType ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_portletType);
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
