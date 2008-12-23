package org.semanticwb.model.base;


public class TemplateRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Inheritable,org.semanticwb.model.Priorityable,org.semanticwb.model.Activeable,org.semanticwb.model.Deleteable
{
    public static final org.semanticwb.platform.SemanticProperty swb_deleted=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#deleted");
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#template");
    public static final org.semanticwb.platform.SemanticProperty swb_inherita=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#inherita");
    public static final org.semanticwb.platform.SemanticProperty swb_priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#priority");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");

    public TemplateRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    public void setDeleted(boolean deleted)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, deleted);
    }

    public void setTemplate(org.semanticwb.model.Template template)
    {
        getSemanticObject().setObjectProperty(swb_template, template.getSemanticObject());
    }

    public void removeTemplate()
    {
        getSemanticObject().removeProperty(swb_template);
    }

    public org.semanticwb.model.Template getTemplate()
    {
         org.semanticwb.model.Template ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_template);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Template)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public int getInherita()
    {
        return getSemanticObject().getIntProperty(swb_inherita);
    }

    public void setInherita(int inherita)
    {
        getSemanticObject().setLongProperty(swb_inherita, inherita);
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    public void setPriority(int priority)
    {
        getSemanticObject().setLongProperty(swb_priority, priority);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return new org.semanticwb.model.WebSite(getSemanticObject().getModel().getModelObject());
    }
}
