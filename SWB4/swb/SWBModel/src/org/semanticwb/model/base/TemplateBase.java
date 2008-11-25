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

public class TemplateBase extends GenericObjectBase implements RoleRefable,Calendarable,Versionable,Activeable,Deleteable,Traceable,RuleRefable,Descriptiveable,Localeable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TemplateBase(SemanticObject base)
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

    public void setTemplateGroup(org.semanticwb.model.TemplateGroup templategroup)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_templateGroup, templategroup.getSemanticObject());
    }

    public void removeTemplateGroup()
    {
        getSemanticObject().removeProperty(vocabulary.swb_templateGroup);
    }

    public TemplateGroup getTemplateGroup()
    {
         TemplateGroup ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_templateGroup);
         if(obj!=null)
         {
             ret=(TemplateGroup)vocabulary.swb_TemplateGroup.newGenericInstance(obj);
         }
         return ret;
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
