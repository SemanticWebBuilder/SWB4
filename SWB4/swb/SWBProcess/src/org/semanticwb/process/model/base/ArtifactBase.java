package org.semanticwb.process.model.base;


public abstract class ArtifactBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Artifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Artifact");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Artifact");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifacts(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifacts()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(it, true);
       }

       public static org.semanticwb.process.model.Artifact createArtifact(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.Artifact.ClassMgr.createArtifact(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.Artifact getArtifact(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Artifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.Artifact createArtifact(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.Artifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeArtifact(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasArtifact(String id, org.semanticwb.model.SWBModel model)
       {
           return (getArtifact(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public ArtifactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
