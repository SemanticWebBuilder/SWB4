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

public class WebPageBase extends GenericObjectBase implements Descriptiveable,Portletable,TemplateRefable,Indexable,Calendarable,Viewable,Activeable,PFlowRefable,RuleRefable,RoleRefable,Hiddenable,Deleteable,Traceable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebPageBase(SemanticObject base)
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

    public int getHits()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_hits);
    }

    public void setHits(int hits)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_hits, hits);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.swb_updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.swb_updated, updated);
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

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_hidden);
    }

    public void setHidden(boolean hidden)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_hidden, hidden);
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_indexable, indexable);
    }

    public String getSortName()
    {
        return getSemanticObject().getProperty(vocabulary.swb_webPageSortName);
    }

    public void setSortName(String webPageSortName)
    {
        getSemanticObject().setProperty(vocabulary.swb_webPageSortName, webPageSortName);
    }

    public int getWebPageURLType()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_webPageURLType);
    }

    public void setWebPageURLType(int webPageURLType)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_webPageURLType, webPageURLType);
    }

    public long getDiskUsage()
    {
        //Implement this method in WebPage object
        throw new SWBMethodImplementationRequiredException();
    }

    public void setDiskUsage(long webPageDiskUsage)
    {
        //Implement this method in WebPage object
        throw new SWBMethodImplementationRequiredException();
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

    public GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasWebPageVirtualParent));    }

    public void addVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasWebPageVirtualParent, webpage.getSemanticObject());
    }

    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasWebPageVirtualParent);
    }

    public void removeVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasWebPageVirtualParent,webpage.getSemanticObject());
    }

    public WebPage getVirtualParent()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
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

    public GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.swb_hasWebPageVirtualChild.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public WebPage getWebPageVirtualChild()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_views, views);
    }

    public GenericIterator<org.semanticwb.model.Portlet> listPortlets()
    {
        return new GenericIterator<org.semanticwb.model.Portlet>(org.semanticwb.model.Portlet.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasPortlet));    }

    public void addPortlet(org.semanticwb.model.Portlet portlet)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasPortlet, portlet.getSemanticObject());
    }

    public void removeAllPortlet()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasPortlet);
    }

    public void removePortlet(org.semanticwb.model.Portlet portlet)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasPortlet,portlet.getSemanticObject());
    }

    public Portlet getPortlet()
    {
         Portlet ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasPortlet);
         if(obj!=null)
         {
             ret=(Portlet)vocabulary.swb_Portlet.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasPFlowRef));    }

    public void addPFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasPFlowRef, pflowref.getSemanticObject());
    }

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasPFlowRef);
    }

    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasPFlowRef,pflowref.getSemanticObject());
    }

    public PFlowRef getPFlowRef()
    {
         PFlowRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasPFlowRef);
         if(obj!=null)
         {
             ret=(PFlowRef)vocabulary.swb_PFlowRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new GenericIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasTemplateRef));    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasTemplateRef, templateref.getSemanticObject());
    }

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasTemplateRef);
    }

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasTemplateRef,templateref.getSemanticObject());
    }

    public TemplateRef getTemplateRef()
    {
         TemplateRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasTemplateRef);
         if(obj!=null)
         {
             ret=(TemplateRef)vocabulary.swb_TemplateRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.swb_hasWebPageChild.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public WebPage getChild()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasWebPageChild);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public String getWebPageURL()
    {
        return getSemanticObject().getProperty(vocabulary.swb_webPageURL);
    }

    public void setWebPageURL(String webPageURL)
    {
        getSemanticObject().setProperty(vocabulary.swb_webPageURL, webPageURL);
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

    public void setParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_webPageParent, webpage.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(vocabulary.swb_webPageParent);
    }

    public WebPage getParent()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_webPageParent);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
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
