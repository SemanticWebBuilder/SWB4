package org.semanticwb.process.model.base;


public abstract class MessageBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.process.model.Modelable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Participant=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Participant");
       public static final org.semanticwb.platform.SemanticProperty swp_fromRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#fromRef");
       public static final org.semanticwb.platform.SemanticProperty swp_toRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#toRef");
       public static final org.semanticwb.platform.SemanticClass swp_Message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Message");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Message");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Message> listMessages(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Message> listMessages()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message>(it, true);
       }

       public static org.semanticwb.process.model.Message createMessage(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Message.ClassMgr.createMessage(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Message getMessage(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Message)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Message createMessage(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Message)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeMessage(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasMessage(String id, org.semanticwb.model.SWBModel model)
       {
           return (getMessage(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByFromRef(org.semanticwb.process.model.Participant fromref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_fromRef, fromref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByFromRef(org.semanticwb.process.model.Participant fromref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(fromref.getSemanticObject().getModel().listSubjects(swp_fromRef,fromref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByToRef(org.semanticwb.process.model.Participant toref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_toRef, toref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByToRef(org.semanticwb.process.model.Participant toref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(toref.getSemanticObject().getModel().listSubjects(swp_toRef,toref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByProperty(org.semanticwb.process.model.Property hasproperty,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasProperty, hasproperty.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByProperty(org.semanticwb.process.model.Property hasproperty)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(hasproperty.getSemanticObject().getModel().listSubjects(swp_hasProperty,hasproperty.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Message> listMessageByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Message> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public MessageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setFromRef(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().setObjectProperty(swp_fromRef, value.getSemanticObject());
    }

    public void removeFromRef()
    {
        getSemanticObject().removeProperty(swp_fromRef);
    }


    public org.semanticwb.process.model.Participant getFromRef()
    {
         org.semanticwb.process.model.Participant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_fromRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Participant)obj.createGenericInstance();
         }
         return ret;
    }

    public void setToRef(org.semanticwb.process.model.Participant value)
    {
        getSemanticObject().setObjectProperty(swp_toRef, value.getSemanticObject());
    }

    public void removeToRef()
    {
        getSemanticObject().removeProperty(swp_toRef);
    }


    public org.semanticwb.process.model.Participant getToRef()
    {
         org.semanticwb.process.model.Participant ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_toRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Participant)obj.createGenericInstance();
         }
         return ret;
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
