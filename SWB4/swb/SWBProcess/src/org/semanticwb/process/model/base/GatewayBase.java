package org.semanticwb.process.model.base;


public abstract class GatewayBase extends org.semanticwb.process.model.FlowObject implements org.semanticwb.process.model.Gateable,org.semanticwb.process.model.Assignable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Gateway=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gateway");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Gateway");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGateways(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGateways()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway>(it, true);
       }

       public static org.semanticwb.process.model.Gateway createGateway(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Gateway.ClassMgr.createGateway(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Gateway getGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Gateway)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Gateway createGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Gateway)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeGateway(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasGateway(String id, org.semanticwb.model.SWBModel model)
       {
           return (getGateway(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasAssignment, hasassignment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGatewayByAssignment(org.semanticwb.process.model.Assignment hasassignment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway> it=new org.semanticwb.model.GenericIterator(hasassignment.getSemanticObject().getModel().listSubjects(swp_hasAssignment,hasassignment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGatewayByGate(org.semanticwb.process.model.Gate hasgate,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasGate, hasgate.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGatewayByGate(org.semanticwb.process.model.Gate hasgate)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway> it=new org.semanticwb.model.GenericIterator(hasgate.getSemanticObject().getModel().listSubjects(swp_hasGate,hasgate.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGatewayByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Gateway> listGatewayByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gateway> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public GatewayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate> listGates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Gate>(getSemanticObject().listObjectProperties(swp_hasGate));
    }

    public boolean hasGate(org.semanticwb.process.model.Gate gate)
    {
        if(gate==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasGate,gate.getSemanticObject());
    }

    public void addGate(org.semanticwb.process.model.Gate value)
    {
        getSemanticObject().addObjectProperty(swp_hasGate, value.getSemanticObject());
    }

    public void removeAllGate()
    {
        getSemanticObject().removeProperty(swp_hasGate);
    }

    public void removeGate(org.semanticwb.process.model.Gate gate)
    {
        getSemanticObject().removeObjectProperty(swp_hasGate,gate.getSemanticObject());
    }


    public org.semanticwb.process.model.Gate getGate()
    {
         org.semanticwb.process.model.Gate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasGate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Gate)obj.createGenericInstance();
         }
         return ret;
    }
}
