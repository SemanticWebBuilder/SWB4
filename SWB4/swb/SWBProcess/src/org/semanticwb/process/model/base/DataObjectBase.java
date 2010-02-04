package org.semanticwb.process.model.base;


public abstract class DataObjectBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.process.model.Modelable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_state=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#state");
       public static final org.semanticwb.platform.SemanticClass swp_DataObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataObject");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjects(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjects()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject>(it, true);
       }

       public static org.semanticwb.process.model.DataObject createDataObject(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.DataObject.ClassMgr.createDataObject(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.DataObject getDataObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.DataObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.DataObject createDataObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.DataObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeDataObject(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasDataObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (getDataObject(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.DataObject> listDataObjectByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataObject> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public DataObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getState()
    {
        return getSemanticObject().getProperty(swp_state);
    }

    public void setState(String value)
    {
        getSemanticObject().setProperty(swp_state, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listProperties()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(getSemanticObject().listObjectProperties(swp_hasProperty));
    }

    public boolean hasProperty(org.semanticwb.process.model.Property property)
    {
        if(property==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasProperty,property.getSemanticObject());
    }

    public void addProperty(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().addObjectProperty(swp_hasProperty, value.getSemanticObject());
    }

    public void removeAllProperty()
    {
        getSemanticObject().removeProperty(swp_hasProperty);
    }

    public void removeProperty(org.semanticwb.process.model.Property property)
    {
        getSemanticObject().removeObjectProperty(swp_hasProperty,property.getSemanticObject());
    }


    public org.semanticwb.process.model.Property getProperty()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProperty);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }
}
