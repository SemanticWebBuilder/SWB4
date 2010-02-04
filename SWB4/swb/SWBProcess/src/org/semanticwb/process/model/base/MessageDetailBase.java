package org.semanticwb.process.model.base;


public abstract class MessageDetailBase extends org.semanticwb.process.model.EventDetail implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Messageable,org.semanticwb.process.model.Implementable
{
       public static final org.semanticwb.platform.SemanticClass swp_MessageDetail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageDetail");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageDetail");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetails(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetails()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail>(it, true);
       }

       public static org.semanticwb.process.model.MessageDetail createMessageDetail(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.MessageDetail.ClassMgr.createMessageDetail(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.MessageDetail getMessageDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.MessageDetail)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.MessageDetail createMessageDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.MessageDetail)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeMessageDetail(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasMessageDetail(String id, org.semanticwb.model.SWBModel model)
       {
           return (getMessageDetail(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByInMessageRef(org.semanticwb.process.model.Message inmessageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_inMessageRef, inmessageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByInMessageRef(org.semanticwb.process.model.Message inmessageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(inmessageref.getSemanticObject().getModel().listSubjects(swp_inMessageRef,inmessageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByOutMessageRef(org.semanticwb.process.model.Message outmessageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_outMessageRef, outmessageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByOutMessageRef(org.semanticwb.process.model.Message outmessageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(outmessageref.getSemanticObject().getModel().listSubjects(swp_outMessageRef,outmessageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByMessageRef(org.semanticwb.process.model.Message messageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_messageRef, messageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageDetail> listMessageDetailByMessageRef(org.semanticwb.process.model.Message messageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageDetail> it=new org.semanticwb.model.GenericIterator(messageref.getSemanticObject().getModel().listSubjects(swp_messageRef,messageref.getSemanticObject()));
       return it;
   }
    }

    public MessageDetailBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setInMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_inMessageRef, value.getSemanticObject());
    }

    public void removeInMessageRef()
    {
        getSemanticObject().removeProperty(swp_inMessageRef);
    }


    public org.semanticwb.process.model.Message getInMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_inMessageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOutMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_outMessageRef, value.getSemanticObject());
    }

    public void removeOutMessageRef()
    {
        getSemanticObject().removeProperty(swp_outMessageRef);
    }


    public org.semanticwb.process.model.Message getOutMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outMessageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public String getImplementation()
    {
        return getSemanticObject().getProperty(swp_implementation);
    }

    public void setImplementation(String value)
    {
        getSemanticObject().setProperty(swp_implementation, value);
    }

    public void setMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_messageRef, value.getSemanticObject());
    }

    public void removeMessageRef()
    {
        getSemanticObject().removeProperty(swp_messageRef);
    }


    public org.semanticwb.process.model.Message getMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_messageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }
}
