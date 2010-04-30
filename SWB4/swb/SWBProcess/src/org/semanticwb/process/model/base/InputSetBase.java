package org.semanticwb.process.model.base;


public abstract class InputSetBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.OutputSetReferensable,org.semanticwb.process.model.Documentable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.DataInputReferensable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOptionalInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasOptionalInput");
    public static final org.semanticwb.platform.SemanticProperty swp_hasWhileExecutingInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasWhileExecutingInput");
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

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByOptionalInput(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOptionalInput, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByOptionalInput(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOptionalInput,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByWhileExecutingInput(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasWhileExecutingInput, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByWhileExecutingInput(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasWhileExecutingInput,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByOutputSetRef(org.semanticwb.process.model.OutputSet value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByOutputSetRef(org.semanticwb.process.model.OutputSet value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputSetRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByDataInputRef(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByDataInputRef(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDataInputRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.InputSet> listInputSetByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputSet> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public InputSetBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> listOptionalInputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(getSemanticObject().listObjectProperties(swp_hasOptionalInput));
    }

    public boolean hasOptionalInput(org.semanticwb.process.model.DataInput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOptionalInput,value.getSemanticObject());
        }
        return ret;
    }

    public void addOptionalInput(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().addObjectProperty(swp_hasOptionalInput, value.getSemanticObject());
    }

    public void removeAllOptionalInput()
    {
        getSemanticObject().removeProperty(swp_hasOptionalInput);
    }

    public void removeOptionalInput(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOptionalInput,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataInput getOptionalInput()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOptionalInput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> listWhileExecutingInputs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(getSemanticObject().listObjectProperties(swp_hasWhileExecutingInput));
    }

    public boolean hasWhileExecutingInput(org.semanticwb.process.model.DataInput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasWhileExecutingInput,value.getSemanticObject());
        }
        return ret;
    }

    public void addWhileExecutingInput(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().addObjectProperty(swp_hasWhileExecutingInput, value.getSemanticObject());
    }

    public void removeAllWhileExecutingInput()
    {
        getSemanticObject().removeProperty(swp_hasWhileExecutingInput);
    }

    public void removeWhileExecutingInput(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasWhileExecutingInput,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataInput getWhileExecutingInput()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasWhileExecutingInput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet> listOutputSetRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputSet>(getSemanticObject().listObjectProperties(swp_hasOutputSetRef));
    }

    public boolean hasOutputSetRef(org.semanticwb.process.model.OutputSet value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutputSetRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addOutputSetRef(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().addObjectProperty(swp_hasOutputSetRef, value.getSemanticObject());
    }

    public void removeAllOutputSetRef()
    {
        getSemanticObject().removeProperty(swp_hasOutputSetRef);
    }

    public void removeOutputSetRef(org.semanticwb.process.model.OutputSet value)
    {
        getSemanticObject().removeObjectProperty(swp_hasOutputSetRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.OutputSet getOutputSetRef()
    {
         org.semanticwb.process.model.OutputSet ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputSetRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.OutputSet)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput> listDataInputRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DataInput>(getSemanticObject().listObjectProperties(swp_hasDataInputRef));
    }

    public boolean hasDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasDataInputRef,value.getSemanticObject());
        }
        return ret;
    }

    public void addDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().addObjectProperty(swp_hasDataInputRef, value.getSemanticObject());
    }

    public void removeAllDataInputRef()
    {
        getSemanticObject().removeProperty(swp_hasDataInputRef);
    }

    public void removeDataInputRef(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().removeObjectProperty(swp_hasDataInputRef,value.getSemanticObject());
    }

    public org.semanticwb.process.model.DataInput getDataInputRef()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasDataInputRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
