package org.semanticwb.process.model.base;


public abstract class BPMNElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Category");
       public static final org.semanticwb.platform.SemanticProperty swp_hasCategory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasCategory");
       public static final org.semanticwb.platform.SemanticClass swp_BPMNElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNElement");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElements(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNElement>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElements()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNElement>(it, true);
       }

       public static org.semanticwb.process.model.BPMNElement createBPMNElement(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.BPMNElement.ClassMgr.createBPMNElement(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.BPMNElement getBPMNElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.BPMNElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.BPMNElement createBPMNElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.BPMNElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeBPMNElement(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasBPMNElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (getBPMNElement(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElementByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.BPMNElement> listBPMNElementByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNElement> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public BPMNElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category> listCategories()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Category>(getSemanticObject().listObjectProperties(swp_hasCategory));
    }

    public boolean hasCategory(org.semanticwb.process.model.Category category)
    {
        if(category==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasCategory,category.getSemanticObject());
    }

    public void addCategory(org.semanticwb.process.model.Category value)
    {
        getSemanticObject().addObjectProperty(swp_hasCategory, value.getSemanticObject());
    }

    public void removeAllCategory()
    {
        getSemanticObject().removeProperty(swp_hasCategory);
    }

    public void removeCategory(org.semanticwb.process.model.Category category)
    {
        getSemanticObject().removeObjectProperty(swp_hasCategory,category.getSemanticObject());
    }


    public org.semanticwb.process.model.Category getCategory()
    {
         org.semanticwb.process.model.Category ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasCategory);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Category)obj.createGenericInstance();
         }
         return ret;
    }
}
