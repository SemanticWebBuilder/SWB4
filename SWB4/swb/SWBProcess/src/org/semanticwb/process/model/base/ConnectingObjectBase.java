package org.semanticwb.process.model.base;


public abstract class ConnectingObjectBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#GraphicalElement");
       public static final org.semanticwb.platform.SemanticProperty swp_sourceRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#sourceRef");
       public static final org.semanticwb.platform.SemanticProperty swp_targetRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#targetRef");
       public static final org.semanticwb.platform.SemanticClass swp_ConnectingObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConnectingObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ConnectingObject");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjects(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjects()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject>(it, true);
       }

       public static org.semanticwb.process.model.ConnectingObject createConnectingObject(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ConnectingObject.ClassMgr.createConnectingObject(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ConnectingObject getConnectingObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ConnectingObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ConnectingObject createConnectingObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ConnectingObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeConnectingObject(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasConnectingObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (getConnectingObject(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjectBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_sourceRef, sourceref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjectBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject> it=new org.semanticwb.model.GenericIterator(sourceref.getSemanticObject().getModel().listSubjects(swp_sourceRef,sourceref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjectByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjectByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjectByTargetRef(org.semanticwb.process.model.GraphicalElement targetref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_targetRef, targetref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ConnectingObject> listConnectingObjectByTargetRef(org.semanticwb.process.model.GraphicalElement targetref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectingObject> it=new org.semanticwb.model.GenericIterator(targetref.getSemanticObject().getModel().listSubjects(swp_targetRef,targetref.getSemanticObject()));
       return it;
   }
    }

    public ConnectingObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSourceRef(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().setObjectProperty(swp_sourceRef, value.getSemanticObject());
    }

    public void removeSourceRef()
    {
        getSemanticObject().removeProperty(swp_sourceRef);
    }


    public org.semanticwb.process.model.GraphicalElement getSourceRef()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_sourceRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setTargetRef(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().setObjectProperty(swp_targetRef, value.getSemanticObject());
    }

    public void removeTargetRef()
    {
        getSemanticObject().removeProperty(swp_targetRef);
    }


    public org.semanticwb.process.model.GraphicalElement getTargetRef()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_targetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }
}
