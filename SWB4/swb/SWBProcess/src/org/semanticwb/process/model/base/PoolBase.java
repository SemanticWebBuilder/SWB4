package org.semanticwb.process.model.base;


public abstract class PoolBase extends org.semanticwb.process.model.Swimlane implements org.semanticwb.process.model.ProcessReferensable,org.semanticwb.process.model.Laneable,org.semanticwb.process.model.Participable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_boundaryVisible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#boundaryVisible");
       public static final org.semanticwb.platform.SemanticProperty swp_mainPool=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#mainPool");
       public static final org.semanticwb.platform.SemanticClass swp_Pool=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Pool");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Pool");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Pool> listPools(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Pool> listPools()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool>(it, true);
       }

       public static org.semanticwb.process.model.Pool getPool(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Pool)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Pool createPool(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Pool)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removePool(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasPool(String id, org.semanticwb.model.SWBModel model)
       {
           return (getPool(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByLane(org.semanticwb.process.model.Lane haslane,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasLane, haslane.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByLane(org.semanticwb.process.model.Lane haslane)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(haslane.getSemanticObject().getModel().listSubjects(swp_hasLane,haslane.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByProcessRef(org.semanticwb.process.model.Process processref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_processRef, processref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByProcessRef(org.semanticwb.process.model.Process processref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(processref.getSemanticObject().getModel().listSubjects(swp_processRef,processref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByParticipantRef(org.semanticwb.process.model.Participant participantref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_participantRef, participantref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Pool> listPoolByParticipantRef(org.semanticwb.process.model.Participant participantref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Pool> it=new org.semanticwb.model.GenericIterator(participantref.getSemanticObject().getModel().listSubjects(swp_participantRef,participantref.getSemanticObject()));
       return it;
   }
    }

    public PoolBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isBoundaryVisible()
    {
        return getSemanticObject().getBooleanProperty(swp_boundaryVisible);
    }

    public void setBoundaryVisible(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_boundaryVisible, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> listLanes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(getSemanticObject().listObjectProperties(swp_hasLane));
    }

    public boolean hasLane(org.semanticwb.process.model.Lane lane)
    {
        if(lane==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasLane,lane.getSemanticObject());
    }

    public void addLane(org.semanticwb.process.model.Lane value)
    {
        getSemanticObject().addObjectProperty(swp_hasLane, value.getSemanticObject());
    }

    public void removeAllLane()
    {
        getSemanticObject().removeProperty(swp_hasLane);
    }

    public void removeLane(org.semanticwb.process.model.Lane lane)
    {
        getSemanticObject().removeObjectProperty(swp_hasLane,lane.getSemanticObject());
    }


    public org.semanticwb.process.model.Lane getLane()
    {
         org.semanticwb.process.model.Lane ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasLane);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Lane)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isMainPool()
    {
        return getSemanticObject().getBooleanProperty(swp_mainPool);
    }

    public void setMainPool(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_mainPool, value);
    }

    public void setProcessRef(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().setObjectProperty(swp_processRef, value.getSemanticObject());
    }

    public void removeProcessRef()
    {
        getSemanticObject().removeProperty(swp_processRef);
    }


    public org.semanticwb.process.model.Process getProcessRef()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParticipantRef(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().setObjectProperty(swp_participantRef, value.getSemanticObject());
    }

    public void removeParticipantRef()
    {
        getSemanticObject().removeProperty(swp_participantRef);
    }


    public org.semanticwb.process.model.Participant getParticipantRef()
    {
         org.semanticwb.process.model.Participant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_participantRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Participant)obj.createGenericInstance();
         }
         return ret;
    }
}
