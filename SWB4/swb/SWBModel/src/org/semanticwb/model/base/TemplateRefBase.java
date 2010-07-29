package org.semanticwb.model.base;


public abstract class TemplateRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Priorityable,org.semanticwb.model.Inheritable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swb_Template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Template");
    public static final org.semanticwb.platform.SemanticProperty swb_template=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#template");
    public static final org.semanticwb.platform.SemanticClass swb_TemplateRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#TemplateRef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(it, true);
        }

        public static org.semanticwb.model.TemplateRef createTemplateRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.TemplateRef.ClassMgr.createTemplateRef(String.valueOf(id), model);
        }

        public static org.semanticwb.model.TemplateRef getTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.TemplateRef createTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TemplateRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTemplateRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateRef(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefByTemplate(org.semanticwb.model.Template value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_template, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.TemplateRef> listTemplateRefByTemplate(org.semanticwb.model.Template value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_template,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public TemplateRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setTemplate(org.semanticwb.model.Template value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_template, value.getSemanticObject());
        }else
        {
            removeTemplate();
        }
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
             ret=(org.semanticwb.model.Template)obj.createGenericInstance();
         }
         return ret;
    }

    public int getPriority()
    {
        return getSemanticObject().getIntProperty(swb_priority);
    }

    public void setPriority(int value)
    {
        getSemanticObject().setIntProperty(swb_priority, value);
    }

    public int getInherit()
    {
        return getSemanticObject().getIntProperty(swb_inherit);
    }

    public void setInherit(int value)
    {
        getSemanticObject().setIntProperty(swb_inherit, value);
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
