package org.semanticwb.process.model.base;


public abstract class ParticipantRoleBase extends org.semanticwb.process.model.Participant implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
       public static final org.semanticwb.platform.SemanticProperty swp_roleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#roleRef");
       public static final org.semanticwb.platform.SemanticClass swp_ParticipantRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantRole");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ParticipantRole");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ParticipantRole> listParticipantRoles(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantRole>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ParticipantRole> listParticipantRoles()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantRole>(it, true);
       }

       public static org.semanticwb.process.model.ParticipantRole createParticipantRole(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.ParticipantRole.ClassMgr.createParticipantRole(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.ParticipantRole getParticipantRole(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ParticipantRole)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ParticipantRole createParticipantRole(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ParticipantRole)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeParticipantRole(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasParticipantRole(String id, org.semanticwb.model.SWBModel model)
       {
           return (getParticipantRole(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ParticipantRole> listParticipantRoleByRoleRef(org.semanticwb.model.Role roleref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_roleRef, roleref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ParticipantRole> listParticipantRoleByRoleRef(org.semanticwb.model.Role roleref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantRole> it=new org.semanticwb.model.GenericIterator(roleref.getSemanticObject().getModel().listSubjects(swp_roleRef,roleref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ParticipantRole> listParticipantRoleByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantRole> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ParticipantRole> listParticipantRoleByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ParticipantRole> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ParticipantRoleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setRoleRef(org.semanticwb.model.Role value)
    {
        getSemanticObject().setObjectProperty(swp_roleRef, value.getSemanticObject());
    }

    public void removeRoleRef()
    {
        getSemanticObject().removeProperty(swp_roleRef);
    }


    public org.semanticwb.model.Role getRoleRef()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_roleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
}
