package org.semanticwb.model.base;


public abstract class AssMemberBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
    public static final org.semanticwb.platform.SemanticProperty swb_assMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assMember");
    public static final org.semanticwb.platform.SemanticProperty swb_assRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#assRole");
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    public static final org.semanticwb.platform.SemanticProperty swb_associationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#associationInv");
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMembers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(it, true);
        }

        public static org.semanticwb.model.AssMember createAssMember(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.AssMember.ClassMgr.createAssMember(String.valueOf(id), model);
        }

        public static org.semanticwb.model.AssMember getAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AssMember)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.model.AssMember createAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.AssMember)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static void removeAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasAssMember(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAssMember(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByMember(org.semanticwb.model.Topic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_assMember, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByMember(org.semanticwb.model.Topic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_assMember,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByRole(org.semanticwb.model.Topic value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_assRole, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByRole(org.semanticwb.model.Topic value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_assRole,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_associationInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.model.AssMember> listAssMemberByAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_associationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public AssMemberBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setMember(org.semanticwb.model.Topic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_assMember, value.getSemanticObject());
        }else
        {
            removeMember();
        }
    }

    public void removeMember()
    {
        getSemanticObject().removeProperty(swb_assMember);
    }

    public org.semanticwb.model.Topic getMember()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assMember);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }

    public void setRole(org.semanticwb.model.Topic value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_assRole, value.getSemanticObject());
        }else
        {
            removeRole();
        }
    }

    public void removeRole()
    {
        getSemanticObject().removeProperty(swb_assRole);
    }

    public org.semanticwb.model.Topic getRole()
    {
         org.semanticwb.model.Topic ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_assRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Topic)obj.createGenericInstance();
         }
         return ret;
    }

    public void setAssociation(org.semanticwb.model.Association value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_associationInv, value.getSemanticObject());
        }else
        {
            removeAssociation();
        }
    }

    public void removeAssociation()
    {
        getSemanticObject().removeProperty(swb_associationInv);
    }

    public org.semanticwb.model.Association getAssociation()
    {
         org.semanticwb.model.Association ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_associationInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Association)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
