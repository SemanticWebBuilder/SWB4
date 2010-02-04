package org.semanticwb.process.model.base;


public abstract class ArtifactInputBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.process.model.ArtifactReferensable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticProperty swp_requiredForStart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#requiredForStart");
       public static final org.semanticwb.platform.SemanticClass swp_ArtifactInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ArtifactInput");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ArtifactInput");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputs(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputs()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput>(it, true);
       }

       public static org.semanticwb.process.model.ArtifactInput getArtifactInput(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ArtifactInput)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.ArtifactInput createArtifactInput(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.ArtifactInput)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeArtifactInput(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasArtifactInput(String id, org.semanticwb.model.SWBModel model)
       {
           return (getArtifactInput(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputByArtifactRef(org.semanticwb.process.model.Artifact artifactref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_artifactRef, artifactref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputByArtifactRef(org.semanticwb.process.model.Artifact artifactref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput> it=new org.semanticwb.model.GenericIterator(artifactref.getSemanticObject().getModel().listSubjects(swp_artifactRef,artifactref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ArtifactInputBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isRequiredForStart()
    {
        return getSemanticObject().getBooleanProperty(swp_requiredForStart);
    }

    public void setRequiredForStart(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_requiredForStart, value);
    }

    public void setArtifactRef(org.semanticwb.process.model.Artifact value)
    {
        getSemanticObject().setObjectProperty(swp_artifactRef, value.getSemanticObject());
    }

    public void removeArtifactRef()
    {
        getSemanticObject().removeProperty(swp_artifactRef);
    }


    public org.semanticwb.process.model.Artifact getArtifactRef()
    {
         org.semanticwb.process.model.Artifact ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_artifactRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Artifact)obj.createGenericInstance();
         }
         return ret;
    }
}
