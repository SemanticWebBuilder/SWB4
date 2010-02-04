package org.semanticwb.process.model.base;


public abstract class OutputSetBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_ArtifactOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ArtifactOutput");
       public static final org.semanticwb.platform.SemanticProperty swp_hasArtifactOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasArtifactOutput");
       public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
       public static final org.semanticwb.platform.SemanticProperty swp_hasPropertyOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasPropertyOutput");
       public static final org.semanticwb.platform.SemanticClass swp_OutputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#OutputSet");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSets(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSets()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(it, true);
       }

       public static org.semanticwb.process.model.OutputSet createOutputSet(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.OutputSet.ClassMgr.createOutputSet(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.OutputSet getOutputSet(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.OutputSet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.OutputSet createOutputSet(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.OutputSet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeOutputSet(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasOutputSet(String id, org.semanticwb.model.SWBModel model)
       {
           return (getOutputSet(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByArtifactOutput(org.semanticwb.process.model.ArtifactOutput hasartifactoutput,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasArtifactOutput, hasartifactoutput.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByArtifactOutput(org.semanticwb.process.model.ArtifactOutput hasartifactoutput)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(hasartifactoutput.getSemanticObject().getModel().listSubjects(swp_hasArtifactOutput,hasartifactoutput.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByPropertyOutput(org.semanticwb.process.model.Property haspropertyoutput,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasPropertyOutput, haspropertyoutput.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.OutputSet> listOutputSetByPropertyOutput(org.semanticwb.process.model.Property haspropertyoutput)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> it=new org.semanticwb.model.GenericIterator(haspropertyoutput.getSemanticObject().getModel().listSubjects(swp_hasPropertyOutput,haspropertyoutput.getSemanticObject()));
       return it;
   }
    }

    public OutputSetBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactOutput> listArtifactOutputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactOutput>(getSemanticObject().listObjectProperties(swp_hasArtifactOutput));
    }

    public boolean hasArtifactOutput(org.semanticwb.process.model.ArtifactOutput artifactoutput)
    {
        if(artifactoutput==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasArtifactOutput,artifactoutput.getSemanticObject());
    }

    public void addArtifactOutput(org.semanticwb.process.model.ArtifactOutput value)
    {
        getSemanticObject().addObjectProperty(swp_hasArtifactOutput, value.getSemanticObject());
    }

    public void removeAllArtifactOutput()
    {
        getSemanticObject().removeProperty(swp_hasArtifactOutput);
    }

    public void removeArtifactOutput(org.semanticwb.process.model.ArtifactOutput artifactoutput)
    {
        getSemanticObject().removeObjectProperty(swp_hasArtifactOutput,artifactoutput.getSemanticObject());
    }


    public org.semanticwb.process.model.ArtifactOutput getArtifactOutput()
    {
         org.semanticwb.process.model.ArtifactOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasArtifactOutput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ArtifactOutput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listPropertyOutputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(getSemanticObject().listObjectProperties(swp_hasPropertyOutput));
    }

    public boolean hasPropertyOutput(org.semanticwb.process.model.Property property)
    {
        if(property==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasPropertyOutput,property.getSemanticObject());
    }

    public void addPropertyOutput(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().addObjectProperty(swp_hasPropertyOutput, value.getSemanticObject());
    }

    public void removeAllPropertyOutput()
    {
        getSemanticObject().removeProperty(swp_hasPropertyOutput);
    }

    public void removePropertyOutput(org.semanticwb.process.model.Property property)
    {
        getSemanticObject().removeObjectProperty(swp_hasPropertyOutput,property.getSemanticObject());
    }


    public org.semanticwb.process.model.Property getPropertyOutput()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasPropertyOutput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }
}
