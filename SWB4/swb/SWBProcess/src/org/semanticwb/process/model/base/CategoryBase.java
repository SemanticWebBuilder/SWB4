package org.semanticwb.process.model.base;


public abstract class CategoryBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_CategoryValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CategoryValue");
    public static final org.semanticwb.platform.SemanticProperty swp_hasCategoryValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCategoryValue");
    public static final org.semanticwb.platform.SemanticClass swp_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Category");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Category");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategories(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategories()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category>(it, true);
        }

        public static org.semanticwb.process.model.Category createCategory(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Category.ClassMgr.createCategory(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.Category getCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Category)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.Category createCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Category)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCategory(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCategory(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCategory(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByCategoryValue(org.semanticwb.process.model.CategoryValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasCategoryValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByCategoryValue(org.semanticwb.process.model.CategoryValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasCategoryValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.Category> listCategoryByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CategoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue> listCategoryValues()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CategoryValue>(getSemanticObject().listObjectProperties(swp_hasCategoryValue));
    }

    public boolean hasCategoryValue(org.semanticwb.process.model.CategoryValue value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasCategoryValue,value.getSemanticObject());
        }
        return ret;
    }

    public void addCategoryValue(org.semanticwb.process.model.CategoryValue value)
    {
        getSemanticObject().addObjectProperty(swp_hasCategoryValue, value.getSemanticObject());
    }

    public void removeAllCategoryValue()
    {
        getSemanticObject().removeProperty(swp_hasCategoryValue);
    }

    public void removeCategoryValue(org.semanticwb.process.model.CategoryValue value)
    {
        getSemanticObject().removeObjectProperty(swp_hasCategoryValue,value.getSemanticObject());
    }

    public org.semanticwb.process.model.CategoryValue getCategoryValue()
    {
         org.semanticwb.process.model.CategoryValue ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCategoryValue);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.CategoryValue)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
