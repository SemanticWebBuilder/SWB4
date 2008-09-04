package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class WebPageBase extends GenericObjectBase implements Descriptiveable,PortletRefable,TemplateRefable,Indexable,Calendarable,Viewable,Activeable,RuleRefable,RoleRefable,Hiddenable,Deleteable,Traceable
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

    public GenericIterator<org.semanticwb.model.Calendar> listCalendar()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.Calendar>(org.semanticwb.model.Calendar.class, stit);
    }

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
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasCalendar.getRDFProperty());
         GenericIterator<org.semanticwb.model.Calendar> it=new GenericIterator<org.semanticwb.model.Calendar>(Calendar.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
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

    public GenericIterator<org.semanticwb.model.WebPage> listChildWebPage()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.hasChildWebPage.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit,true);
    }

    public GenericIterator<org.semanticwb.model.RoleRef> listRoleRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.RoleRef>(org.semanticwb.model.RoleRef.class, stit);
    }

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
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRoleRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.RoleRef> it=new GenericIterator<org.semanticwb.model.RoleRef>(RoleRef.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
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

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(vocabulary.deleted, deleted);
    }

    public int getViews()
    {
        return getSemanticObject().getIntProperty(vocabulary.views);
    }

    public void setViews(int views)
    {
        getSemanticObject().setLongProperty(vocabulary.views, views);
    }

    public GenericIterator<org.semanticwb.model.PortletRef> listPortletRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.PortletRef>(org.semanticwb.model.PortletRef.class, stit);
    }

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
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasPortletRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.PortletRef> it=new GenericIterator<org.semanticwb.model.PortletRef>(PortletRef.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.TemplateRef>(org.semanticwb.model.TemplateRef.class, stit);
    }

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
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasTemplateRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.TemplateRef> it=new GenericIterator<org.semanticwb.model.TemplateRef>(TemplateRef.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasPFlowRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.PFlowRef>(org.semanticwb.model.PFlowRef.class, stit);
    }

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
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasPFlowRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.PFlowRef> it=new GenericIterator<org.semanticwb.model.PFlowRef>(PFlowRef.class, stit);
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

    public GenericIterator<org.semanticwb.model.RuleRef> listRuleRef()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.RuleRef>(org.semanticwb.model.RuleRef.class, stit);
    }

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
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasRuleRef.getRDFProperty());
         GenericIterator<org.semanticwb.model.RuleRef> it=new GenericIterator<org.semanticwb.model.RuleRef>(RuleRef.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.WebPage> listParentWebPage()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasParentWebPage.getRDFProperty());
        return new GenericIterator<org.semanticwb.model.WebPage>(org.semanticwb.model.WebPage.class, stit);
    }

    public void addParentWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasParentWebPage, webpage.getSemanticObject());
    }

    public void removeAllParentWebPage()
    {
        getSemanticObject().removeProperty(vocabulary.hasParentWebPage);
    }

    public void removeParentWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasParentWebPage,webpage.getSemanticObject());
    }

    public WebPage getParentWebPage()
    {
         WebPage ret=null;
         StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasParentWebPage.getRDFProperty());
         GenericIterator<org.semanticwb.model.WebPage> it=new GenericIterator<org.semanticwb.model.WebPage>(WebPage.class, stit);
         if(it.hasNext())
         {
             ret=it.next();
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
