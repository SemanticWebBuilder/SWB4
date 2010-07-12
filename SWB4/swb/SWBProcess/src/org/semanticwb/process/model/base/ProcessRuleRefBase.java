package org.semanticwb.process.model.base;


public abstract class ProcessRuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRule");
    public static final org.semanticwb.platform.SemanticProperty swp_processRule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processRule");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessRuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRef");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessRuleRef");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef>(it, true);
        }

        public static org.semanticwb.process.model.ProcessRuleRef createProcessRuleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessRuleRef.ClassMgr.createProcessRuleRef(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ProcessRuleRef getProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRuleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ProcessRuleRef createProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessRuleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasProcessRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessRuleRef(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefByProcessRule(org.semanticwb.process.model.ProcessRule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processRule, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ProcessRuleRef> listProcessRuleRefByProcessRule(org.semanticwb.process.model.ProcessRule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessRuleRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processRule,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ProcessRuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setProcessRule(org.semanticwb.process.model.ProcessRule value)
    {
        getSemanticObject().setObjectProperty(swp_processRule, value.getSemanticObject());
    }

    public void removeProcessRule()
    {
        getSemanticObject().removeProperty(swp_processRule);
    }

    public org.semanticwb.process.model.ProcessRule getProcessRule()
    {
         org.semanticwb.process.model.ProcessRule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processRule);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessRule)obj.createGenericInstance();
         }
         return ret;
    }
}
