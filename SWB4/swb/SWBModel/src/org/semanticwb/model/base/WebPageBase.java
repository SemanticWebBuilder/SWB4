package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebPageBase extends GenericObjectBase implements Descriptiveable,PortletRefable,TemplateRefable,Indexable,Calendarable,Viewable,Activeable,PFlowRefable,RuleRefable,RoleRefable,Hiddenable,Deleteable,Traceable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public WebPageBase(SemanticObject base)
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

    public int getHits()
    {
        return getSemanticObject().getIntProperty(vocabulary.hits);
    }

    public void setHits(int hits)
    {
        getSemanticObject().setLongProperty(vocabulary.hits, hits);
    }

    public Date getUpdated()
    {
        return getSemanticObject().getDateProperty(vocabulary.updated);
    }

    public void setUpdated(Date updated)
    {
        getSemanticObject().setDateProperty(vocabulary.updated, updated);
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

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.hidden);
    }

    public void setHidden(boolean hidden)
    {
        getSemanticObject().setBooleanProperty(vocabulary.hidden, hidden);
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.indexable);
    }

    public void setIndexable(boolean indexable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.indexable, indexable);
    }

    public String getSortName()
    {
        return getSemanticObject().getProperty(vocabulary.webPageSortName);
    }

    public void setSortName(String webPageSortName)
    {
        getSemanticObject().setProperty(vocabulary.webPageSortName, webPageSortName);
    }

    public int getWebPageURLType()
    {
        return getSemanticObject().getIntProperty(vocabulary.webPageURLType);
    }

    public void setWebPageURLType(int webPageURLType)
    {
        getSemanticObject().setLongProperty(vocabulary.webPageURLType, webPageURLType);
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

    public GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, getSemanticObject().listObjectProperties(vocabulary.hasWebPageVirtualParent));    }

    public void addVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasWebPageVirtualParent, webpage.getSemanticObject());
    }

    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(vocabulary.hasWebPageVirtualParent);
    }

    public void removeVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasWebPageVirtualParent,webpage.getSemanticObject());
    }

    public WebPage getVirtualParent()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
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

    public GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasWebPageVirtualChild.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public WebPage getWebPageVirtualChild()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.views, views);
    }

    public GenericIterator<org.semanticwb.model.PortletRef> listPortletRefs()
    {
        return new GenericIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, getSemanticObject().listObjectProperties(vocabulary.hasPortletRef));    }

    public void addPortletRef(org.semanticwb.model.PortletRef portletref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasPortletRef, portletref.getSemanticObject());
    }

    public void removeAllPortletRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasPortletRef);
    }

    public void removePortletRef(org.semanticwb.model.PortletRef portletref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasPortletRef,portletref.getSemanticObject());
    }

    public PortletRef getPortletRef()
    {
         PortletRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPortletRef);
         if(obj!=null)
         {
             ret=(PortletRef)vocabulary.swb_PortletRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, getSemanticObject().listObjectProperties(vocabulary.hasPFlowRef));    }

    public void addPFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasPFlowRef, pflowref.getSemanticObject());
    }

    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasPFlowRef);
    }

    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasPFlowRef,pflowref.getSemanticObject());
    }

    public PFlowRef getPFlowRef()
    {
         PFlowRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasPFlowRef);
         if(obj!=null)
         {
             ret=(PFlowRef)vocabulary.swb_PFlowRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new GenericIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, getSemanticObject().listObjectProperties(vocabulary.hasTemplateRef));    }

    public void addTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasTemplateRef, templateref.getSemanticObject());
    }

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(vocabulary.hasTemplateRef);
    }

    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasTemplateRef,templateref.getSemanticObject());
    }

    public TemplateRef getTemplateRef()
    {
         TemplateRef ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasTemplateRef);
         if(obj!=null)
         {
             ret=(TemplateRef)vocabulary.swb_TemplateRef.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasWebPageChild.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public WebPage getChild()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasWebPageChild);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
         }
         return ret;
    }

    public String getWebPageURL()
    {
        return getSemanticObject().getProperty(vocabulary.webPageURL);
    }

    public void setWebPageURL(String webPageURL)
    {
        getSemanticObject().setProperty(vocabulary.webPageURL, webPageURL);
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

    public void setParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().setObjectProperty(vocabulary.webPageParent, webpage.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(vocabulary.webPageParent);
    }

    public WebPage getParent()
    {
         WebPage ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.webPageParent);
         if(obj!=null)
         {
             ret=(WebPage)vocabulary.swb_WebPage.newGenericInstance(obj);
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
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
