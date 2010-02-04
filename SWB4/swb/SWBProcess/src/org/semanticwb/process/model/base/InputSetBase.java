package org.semanticwb.process.model.base;


public abstract class InputSetBase extends org.semanticwb.process.model.SupportingElement implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
       public static final org.semanticwb.platform.SemanticProperty swp_hasPropertyInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasPropertyInput");
       public static final org.semanticwb.platform.SemanticClass swp_ArtifactInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ArtifactInput");
       public static final org.semanticwb.platform.SemanticProperty swp_hasArtifactInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasArtifactInput");
       public static final org.semanticwb.platform.SemanticClass swp_InputSet=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#InputSet");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSets(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSets()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet>(it, true);
       }

       public static org.semanticwb.process.model.InputSet createInputSet(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.model.InputSet.ClassMgr.createInputSet(String.valueOf(id), model);
       }

       public static org.semanticwb.process.model.InputSet getInputSet(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.InputSet)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.InputSet createInputSet(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.InputSet)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeInputSet(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasInputSet(String id, org.semanticwb.model.SWBModel model)
       {
           return (getInputSet(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByPropertyInput(org.semanticwb.process.model.Property haspropertyinput,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasPropertyInput, haspropertyinput.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByPropertyInput(org.semanticwb.process.model.Property haspropertyinput)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(haspropertyinput.getSemanticObject().getModel().listSubjects(swp_hasPropertyInput,haspropertyinput.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByArtifactInput(org.semanticwb.process.model.ArtifactInput hasartifactinput,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasArtifactInput, hasartifactinput.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByArtifactInput(org.semanticwb.process.model.ArtifactInput hasartifactinput)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(hasartifactinput.getSemanticObject().getModel().listSubjects(swp_hasArtifactInput,hasartifactinput.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
    }

    public InputSetBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property> listPropertyInputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Property>(getSemanticObject().listObjectProperties(swp_hasPropertyInput));
    }

    public boolean hasPropertyInput(org.semanticwb.process.model.Property property)
    {
        if(property==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasPropertyInput,property.getSemanticObject());
    }

    public void addPropertyInput(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().addObjectProperty(swp_hasPropertyInput, value.getSemanticObject());
    }

    public void removeAllPropertyInput()
    {
        getSemanticObject().removeProperty(swp_hasPropertyInput);
    }

    public void removePropertyInput(org.semanticwb.process.model.Property property)
    {
        getSemanticObject().removeObjectProperty(swp_hasPropertyInput,property.getSemanticObject());
    }


    public org.semanticwb.process.model.Property getPropertyInput()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasPropertyInput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput> listArtifactInputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ArtifactInput>(getSemanticObject().listObjectProperties(swp_hasArtifactInput));
    }

    public boolean hasArtifactInput(org.semanticwb.process.model.ArtifactInput artifactinput)
    {
        if(artifactinput==null)return false;
        return getSemanticObject().hasObjectProperty(swp_hasArtifactInput,artifactinput.getSemanticObject());
    }

    public void addArtifactInput(org.semanticwb.process.model.ArtifactInput value)
    {
        getSemanticObject().addObjectProperty(swp_hasArtifactInput, value.getSemanticObject());
    }

    public void removeAllArtifactInput()
    {
        getSemanticObject().removeProperty(swp_hasArtifactInput);
    }

    public void removeArtifactInput(org.semanticwb.process.model.ArtifactInput artifactinput)
    {
        getSemanticObject().removeObjectProperty(swp_hasArtifactInput,artifactinput.getSemanticObject());
    }


    public org.semanticwb.process.model.ArtifactInput getArtifactInput()
    {
         org.semanticwb.process.model.ArtifactInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasArtifactInput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ArtifactInput)obj.createGenericInstance();
         }
         return ret;
    }
}
