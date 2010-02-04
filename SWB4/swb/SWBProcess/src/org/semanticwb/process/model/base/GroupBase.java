package org.semanticwb.process.model.base;


public abstract class GroupBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.process.model.Diagramable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Category");
       public static final org.semanticwb.platform.SemanticProperty swp_categoryRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#categoryRef");
       public static final org.semanticwb.platform.SemanticClass swp_Group=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Group");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Group");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Group> listGroups(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Group> listGroups()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group>(it, true);
       }

       public static org.semanticwb.process.model.Group createGroup(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Group.ClassMgr.createGroup(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Group getGroup(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Group)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Group createGroup(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Group)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeGroup(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasGroup(String id, org.semanticwb.model.SWBModel model)
       {
           return (getGroup(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Group> listGroupByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement, hasgraphicalelement.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Group> listGroupByGraphicalElement(org.semanticwb.process.model.GraphicalElement hasgraphicalelement)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group> it=new org.semanticwb.model.GenericIterator(hasgraphicalelement.getSemanticObject().getModel().listSubjects(swp_hasGraphicalElement,hasgraphicalelement.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Group> listGroupByCategoryRef(org.semanticwb.process.model.Category categoryref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_categoryRef, categoryref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Group> listGroupByCategoryRef(org.semanticwb.process.model.Category categoryref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group> it=new org.semanticwb.model.GenericIterator(categoryref.getSemanticObject().getModel().listSubjects(swp_categoryRef,categoryref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Group> listGroupByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Group> listGroupByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Group> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public GroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasGraphicalElement));
    }

    public boolean hasGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement)
    {
        if(graphicalelement==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasGraphicalElement,graphicalelement.getSemanticObject());
    }

    public void addGraphicalElement(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().addObjectProperty(swp_hasGraphicalElement, value.getSemanticObject());
    }

    public void removeAllGraphicalElement()
    {
        getSemanticObject().removeProperty(swp_hasGraphicalElement);
    }

    public void removeGraphicalElement(org.semanticwb.process.model.GraphicalElement graphicalelement)
    {
        getSemanticObject().removeObjectProperty(swp_hasGraphicalElement,graphicalelement.getSemanticObject());
    }


    public org.semanticwb.process.model.GraphicalElement getGraphicalElement()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasGraphicalElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCategoryRef(org.semanticwb.process.model.Category value)
    {
        getSemanticObject().setObjectProperty(swp_categoryRef, value.getSemanticObject());
    }

    public void removeCategoryRef()
    {
        getSemanticObject().removeProperty(swp_categoryRef);
    }


    public org.semanticwb.process.model.Category getCategoryRef()
    {
         org.semanticwb.process.model.Category ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_categoryRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Category)obj.createGenericInstance();
         }
         return ret;
    }
}
