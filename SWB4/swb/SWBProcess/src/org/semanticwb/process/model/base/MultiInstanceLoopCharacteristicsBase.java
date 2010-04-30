package org.semanticwb.process.model.base;


public abstract class MultiInstanceLoopCharacteristicsBase extends org.semanticwb.process.model.LoopCharacteristics implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_DataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataInput");
    public static final org.semanticwb.platform.SemanticProperty swp_loopDataInput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopDataInput");
    public static final org.semanticwb.platform.SemanticClass swp_DataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#DataOutput");
    public static final org.semanticwb.platform.SemanticProperty swp_loopDataOutput=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopDataOutput");
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_loopCardinality=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#loopCardinality");
    public static final org.semanticwb.platform.SemanticProperty swp_sequential=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#sequential");
    public static final org.semanticwb.platform.SemanticClass swp_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Property");
    public static final org.semanticwb.platform.SemanticProperty swp_inputDataItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#inputDataItem");
    public static final org.semanticwb.platform.SemanticClass swp_EventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EventDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_noneBehaviorDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#noneBehaviorDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_outputDataItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#outputDataItem");
    public static final org.semanticwb.platform.SemanticProperty swp_behavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#behavior");
    public static final org.semanticwb.platform.SemanticProperty swp_oneBehaviorEventRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#oneBehaviorEventRef");
    public static final org.semanticwb.platform.SemanticClass swp_ComplexBehaviorDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ComplexBehaviorDefinition");
    public static final org.semanticwb.platform.SemanticProperty swp_hasComplexBehaviorDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#hasComplexBehaviorDefinition");
    public static final org.semanticwb.platform.SemanticClass swp_MultiInstanceLoopCharacteristics=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstanceLoopCharacteristics");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MultiInstanceLoopCharacteristics");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics>(it, true);
        }

        public static org.semanticwb.process.model.MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.MultiInstanceLoopCharacteristics.ClassMgr.createMultiInstanceLoopCharacteristics(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.MultiInstanceLoopCharacteristics getMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultiInstanceLoopCharacteristics)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.MultiInstanceLoopCharacteristics createMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.MultiInstanceLoopCharacteristics)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMultiInstanceLoopCharacteristics(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMultiInstanceLoopCharacteristics(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByLoopDataInput(org.semanticwb.process.model.DataInput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopDataInput, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByLoopDataInput(org.semanticwb.process.model.DataInput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopDataInput,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByLoopDataOutput(org.semanticwb.process.model.DataOutput value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopDataOutput, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByLoopDataOutput(org.semanticwb.process.model.DataOutput value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopDataOutput,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByLoopCardinality(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_loopCardinality, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByLoopCardinality(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_loopCardinality,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByInputDataItem(org.semanticwb.process.model.Property value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_inputDataItem, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByInputDataItem(org.semanticwb.process.model.Property value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_inputDataItem,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByNoneBehaviorDefinition(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_noneBehaviorDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByNoneBehaviorDefinition(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_noneBehaviorDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByOutputDataItem(org.semanticwb.process.model.Property value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_outputDataItem, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByOutputDataItem(org.semanticwb.process.model.Property value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_outputDataItem,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByOneBehaviorEventRef(org.semanticwb.process.model.EventDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_oneBehaviorEventRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByOneBehaviorEventRef(org.semanticwb.process.model.EventDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_oneBehaviorEventRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByComplexBehaviorDefinition(org.semanticwb.process.model.ComplexBehaviorDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasComplexBehaviorDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByComplexBehaviorDefinition(org.semanticwb.process.model.ComplexBehaviorDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasComplexBehaviorDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> listMultiInstanceLoopCharacteristicsByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MultiInstanceLoopCharacteristics> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public MultiInstanceLoopCharacteristicsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setLoopDataInput(org.semanticwb.process.model.DataInput value)
    {
        getSemanticObject().setObjectProperty(swp_loopDataInput, value.getSemanticObject());
    }

    public void removeLoopDataInput()
    {
        getSemanticObject().removeProperty(swp_loopDataInput);
    }

    public org.semanticwb.process.model.DataInput getLoopDataInput()
    {
         org.semanticwb.process.model.DataInput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_loopDataInput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataInput)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLoopDataOutput(org.semanticwb.process.model.DataOutput value)
    {
        getSemanticObject().setObjectProperty(swp_loopDataOutput, value.getSemanticObject());
    }

    public void removeLoopDataOutput()
    {
        getSemanticObject().removeProperty(swp_loopDataOutput);
    }

    public org.semanticwb.process.model.DataOutput getLoopDataOutput()
    {
         org.semanticwb.process.model.DataOutput ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_loopDataOutput);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DataOutput)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLoopCardinality(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_loopCardinality, value.getSemanticObject());
    }

    public void removeLoopCardinality()
    {
        getSemanticObject().removeProperty(swp_loopCardinality);
    }

    public org.semanticwb.process.model.Expression getLoopCardinality()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_loopCardinality);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isSequential()
    {
        return getSemanticObject().getBooleanProperty(swp_sequential);
    }

    public void setSequential(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_sequential, value);
    }

    public void setInputDataItem(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().setObjectProperty(swp_inputDataItem, value.getSemanticObject());
    }

    public void removeInputDataItem()
    {
        getSemanticObject().removeProperty(swp_inputDataItem);
    }

    public org.semanticwb.process.model.Property getInputDataItem()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_inputDataItem);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }

    public void setNoneBehaviorDefinition(org.semanticwb.process.model.EventDefinition value)
    {
        getSemanticObject().setObjectProperty(swp_noneBehaviorDefinition, value.getSemanticObject());
    }

    public void removeNoneBehaviorDefinition()
    {
        getSemanticObject().removeProperty(swp_noneBehaviorDefinition);
    }

    public org.semanticwb.process.model.EventDefinition getNoneBehaviorDefinition()
    {
         org.semanticwb.process.model.EventDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_noneBehaviorDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EventDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOutputDataItem(org.semanticwb.process.model.Property value)
    {
        getSemanticObject().setObjectProperty(swp_outputDataItem, value.getSemanticObject());
    }

    public void removeOutputDataItem()
    {
        getSemanticObject().removeProperty(swp_outputDataItem);
    }

    public org.semanticwb.process.model.Property getOutputDataItem()
    {
         org.semanticwb.process.model.Property ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outputDataItem);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Property)obj.createGenericInstance();
         }
         return ret;
    }

    public String getBehavior()
    {
        return getSemanticObject().getProperty(swp_behavior);
    }

    public void setBehavior(String value)
    {
        getSemanticObject().setProperty(swp_behavior, value);
    }

    public void setOneBehaviorEventRef(org.semanticwb.process.model.EventDefinition value)
    {
        getSemanticObject().setObjectProperty(swp_oneBehaviorEventRef, value.getSemanticObject());
    }

    public void removeOneBehaviorEventRef()
    {
        getSemanticObject().removeProperty(swp_oneBehaviorEventRef);
    }

    public org.semanticwb.process.model.EventDefinition getOneBehaviorEventRef()
    {
         org.semanticwb.process.model.EventDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_oneBehaviorEventRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.EventDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition> listComplexBehaviorDefinitions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ComplexBehaviorDefinition>(getSemanticObject().listObjectProperties(swp_hasComplexBehaviorDefinition));
    }

    public boolean hasComplexBehaviorDefinition(org.semanticwb.process.model.ComplexBehaviorDefinition value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasComplexBehaviorDefinition,value.getSemanticObject());
        }
        return ret;
    }

    public void addComplexBehaviorDefinition(org.semanticwb.process.model.ComplexBehaviorDefinition value)
    {
        getSemanticObject().addObjectProperty(swp_hasComplexBehaviorDefinition, value.getSemanticObject());
    }

    public void removeAllComplexBehaviorDefinition()
    {
        getSemanticObject().removeProperty(swp_hasComplexBehaviorDefinition);
    }

    public void removeComplexBehaviorDefinition(org.semanticwb.process.model.ComplexBehaviorDefinition value)
    {
        getSemanticObject().removeObjectProperty(swp_hasComplexBehaviorDefinition,value.getSemanticObject());
    }

    public org.semanticwb.process.model.ComplexBehaviorDefinition getComplexBehaviorDefinition()
    {
         org.semanticwb.process.model.ComplexBehaviorDefinition ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasComplexBehaviorDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ComplexBehaviorDefinition)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
