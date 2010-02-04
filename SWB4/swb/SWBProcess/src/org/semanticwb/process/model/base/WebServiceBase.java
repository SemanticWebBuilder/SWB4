package org.semanticwb.process.model.base;


public abstract class WebServiceBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.process.model.Participable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_hasOperation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOperation");
       public static final org.semanticwb.platform.SemanticProperty swp_interface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#interface");
       public static final org.semanticwb.platform.SemanticClass swp_WebService=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#WebService");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#WebService");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.WebService> listWebServices(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebService>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.WebService> listWebServices()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebService>(it, true);
       }

       public static org.semanticwb.process.model.WebService getWebService(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.WebService)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.WebService createWebService(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.WebService)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeWebService(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasWebService(String id, org.semanticwb.model.SWBModel model)
       {
           return (getWebService(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.WebService> listWebServiceByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebService> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.WebService> listWebServiceByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebService> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.WebService> listWebServiceByParticipantRef(org.semanticwb.process.model.Participant participantref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebService> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_participantRef, participantref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.WebService> listWebServiceByParticipantRef(org.semanticwb.process.model.Participant participantref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebService> it=new org.semanticwb.model.GenericIterator(participantref.getSemanticObject().getModel().listSubjects(swp_participantRef,participantref.getSemanticObject()));
       return it;
   }
    }

    public WebServiceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Iterator<String> listOperations()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swp_hasOperation);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addOperation(String operation)
    {
        getSemanticObject().setProperty(swp_hasOperation, operation);
    }

    public void removeAllOperation()
    {
        getSemanticObject().removeProperty(swp_hasOperation);
    }

    public void removeOperation(String operation)
    {
        getSemanticObject().removeProperty(swp_hasOperation,operation);
    }

    public String getInterface()
    {
        return getSemanticObject().getProperty(swp_interface);
    }

    public void setInterface(String value)
    {
        getSemanticObject().setProperty(swp_interface, value);
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
