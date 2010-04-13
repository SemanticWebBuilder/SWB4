package org.semanticwb.process.model.base;


public abstract class TimerEventDefinitionBase extends org.semanticwb.process.model.EventDefinition implements org.semanticwb.process.model.Documentable,org.semanticwb.process.model.Rootable
{
    public static final org.semanticwb.platform.SemanticClass swp_Expression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Expression");
    public static final org.semanticwb.platform.SemanticProperty swp_timeCycle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#timeCycle");
    public static final org.semanticwb.platform.SemanticProperty swp_timeDate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#timeDate");
    public static final org.semanticwb.platform.SemanticClass swp_TimerEventDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerEventDefinition");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#TimerEventDefinition");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition>(it, true);
        }

        public static org.semanticwb.process.model.TimerEventDefinition createTimerEventDefinition(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.TimerEventDefinition.ClassMgr.createTimerEventDefinition(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.TimerEventDefinition getTimerEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TimerEventDefinition)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.TimerEventDefinition createTimerEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.TimerEventDefinition)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeTimerEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasTimerEventDefinition(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTimerEventDefinition(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByTimeCycle(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_timeCycle, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByTimeCycle(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_timeCycle,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByTimeDate(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_timeDate, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByTimeDate(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_timeDate,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.TimerEventDefinition> listTimerEventDefinitionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.TimerEventDefinition> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public TimerEventDefinitionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setTimeCycle(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_timeCycle, value.getSemanticObject());
    }

    public void removeTimeCycle()
    {
        getSemanticObject().removeProperty(swp_timeCycle);
    }

    public org.semanticwb.process.model.Expression getTimeCycle()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_timeCycle);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }

    public void setTimeDate(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_timeDate, value.getSemanticObject());
    }

    public void removeTimeDate()
    {
        getSemanticObject().removeProperty(swp_timeDate);
    }

    public org.semanticwb.process.model.Expression getTimeDate()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_timeDate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }
}
