package org.semanticwb.process.model.base;


public abstract class PropertyBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
       public static final org.semanticwb.platform.SemanticProperty swp_propertyValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#propertyValue");
       public static final org.semanticwb.platform.SemanticProperty swp_correlation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#correlation");
       public static final org.semanticwb.platform.SemanticProperty swp_propertyType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#propertyType");
       public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Property> listProperties(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Property> listProperties()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(it, true);
       }

       public static org.semanticwb.process.model.Property createProperty(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Property.ClassMgr.createProperty(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Property getProperty(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Property)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Property createProperty(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Property)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeProperty(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasProperty(String id, org.semanticwb.model.SWBModel model)
       {
           return (getProperty(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Property> listPropertyByPropertyValue(org.semanticwb.process.model.Expression propertyvalue,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_propertyValue, propertyvalue.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Property> listPropertyByPropertyValue(org.semanticwb.process.model.Expression propertyvalue)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> it=new org.semanticwb.model.GenericIterator(propertyvalue.getSemanticObject().getModel().listSubjects(swp_propertyValue,propertyvalue.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Property> listPropertyByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Property> listPropertyByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public PropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setPropertyValue(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_propertyValue, value.getSemanticObject());
    }

    public void removePropertyValue()
    {
        getSemanticObject().removeProperty(swp_propertyValue);
    }


    public org.semanticwb.process.model.Expression getPropertyValue()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_propertyValue);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isCorrelation()
    {
        return getSemanticObject().getBooleanProperty(swp_correlation);
    }

    public void setCorrelation(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_correlation, value);
    }

    public String getPropertyType()
    {
        return getSemanticObject().getProperty(swp_propertyType);
    }

    public void setPropertyType(String value)
    {
        getSemanticObject().setProperty(swp_propertyType, value);
    }
}
