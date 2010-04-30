package org.semanticwb.process.model.base;


public abstract class CategoryValueBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticProperty swp_value=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#value");
    public static final org.semanticwb.platform.SemanticClass swp_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Category");
    public static final org.semanticwb.platform.SemanticProperty swp_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#category");
    public static final org.semanticwb.platform.SemanticClass swp_FlowElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FlowElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCategorizedFlowElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCategorizedFlowElement");
    public static final org.semanticwb.platform.SemanticClass swp_CategoryValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CategoryValue");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CategoryValue");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValues(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValues()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue>(it, true);
        }

        public static org.semanticwb.process.model.CategoryValue createCategoryValue(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CategoryValue.ClassMgr.createCategoryValue(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CategoryValue getCategoryValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CategoryValue)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CategoryValue createCategoryValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CategoryValue)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCategoryValue(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCategoryValue(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCategoryValue(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByCategory(org.semanticwb.process.model.Category value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_category, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByCategory(org.semanticwb.process.model.Category value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_category,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByCategorizedFlowElement(org.semanticwb.process.model.FlowElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCategorizedFlowElement, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByCategorizedFlowElement(org.semanticwb.process.model.FlowElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCategorizedFlowElement,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CategoryValue> listCategoryValueByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CategoryValueBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getValue()
    {
        return getSemanticObject().getProperty(swp_value);
    }

    public void setValue(String value)
    {
        getSemanticObject().setProperty(swp_value, value);
    }

    public void setCategory(org.semanticwb.process.model.Category value)
    {
        getSemanticObject().setObjectProperty(swp_category, value.getSemanticObject());
    }

    public void removeCategory()
    {
        getSemanticObject().removeProperty(swp_category);
    }

    public org.semanticwb.process.model.Category getCategory()
    {
         org.semanticwb.process.model.Category ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Category)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement> listCategorizedFlowElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FlowElement>(getSemanticObject().listObjectProperties(swp_hasCategorizedFlowElement));
    }

    public boolean hasCategorizedFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCategorizedFlowElement,value.getSemanticObject());
        }
        return ret;
    }

    public void addCategorizedFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().addObjectProperty(swp_hasCategorizedFlowElement, value.getSemanticObject());
    }

    public void removeAllCategorizedFlowElement()
    {
        getSemanticObject().removeProperty(swp_hasCategorizedFlowElement);
    }

    public void removeCategorizedFlowElement(org.semanticwb.process.model.FlowElement value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCategorizedFlowElement,value.getSemanticObject());
    }

    public org.semanticwb.process.model.FlowElement getCategorizedFlowElement()
    {
         org.semanticwb.process.model.FlowElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCategorizedFlowElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FlowElement)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
