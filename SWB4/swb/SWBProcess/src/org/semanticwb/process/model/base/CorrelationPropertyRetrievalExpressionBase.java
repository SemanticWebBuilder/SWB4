package org.semanticwb.process.model.base;


public abstract class CorrelationPropertyRetrievalExpressionBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_Message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#Message");
    public static final org.semanticwb.platform.SemanticProperty swp_message=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#message");
    public static final org.semanticwb.platform.SemanticClass swp_FormalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FormalExpression");
    public static final org.semanticwb.platform.SemanticProperty swp_messagePath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#messagePath");
    public static final org.semanticwb.platform.SemanticClass swp_CorrelationPropertyRetrievalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyRetrievalExpression");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CorrelationPropertyRetrievalExpression");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression>(it, true);
        }

        public static org.semanticwb.process.model.CorrelationPropertyRetrievalExpression createCorrelationPropertyRetrievalExpression(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CorrelationPropertyRetrievalExpression.ClassMgr.createCorrelationPropertyRetrievalExpression(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CorrelationPropertyRetrievalExpression getCorrelationPropertyRetrievalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationPropertyRetrievalExpression)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CorrelationPropertyRetrievalExpression createCorrelationPropertyRetrievalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CorrelationPropertyRetrievalExpression)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCorrelationPropertyRetrievalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCorrelationPropertyRetrievalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCorrelationPropertyRetrievalExpression(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByMessage(org.semanticwb.process.model.Message value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_message, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByMessage(org.semanticwb.process.model.Message value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_message,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByMessagePath(org.semanticwb.process.model.FormalExpression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_messagePath, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByMessagePath(org.semanticwb.process.model.FormalExpression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_messagePath,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> listCorrelationPropertyRetrievalExpressionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CorrelationPropertyRetrievalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CorrelationPropertyRetrievalExpressionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setMessage(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_message, value.getSemanticObject());
    }

    public void removeMessage()
    {
        getSemanticObject().removeProperty(swp_message);
    }

    public org.semanticwb.process.model.Message getMessage()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_message);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMessagePath(org.semanticwb.process.model.FormalExpression value)
    {
        getSemanticObject().setObjectProperty(swp_messagePath, value.getSemanticObject());
    }

    public void removeMessagePath()
    {
        getSemanticObject().removeProperty(swp_messagePath);
    }

    public org.semanticwb.process.model.FormalExpression getMessagePath()
    {
         org.semanticwb.process.model.FormalExpression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_messagePath);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.FormalExpression)obj.createGenericInstance();
         }
         return ret;
    }
}
