package org.semanticwb.process.model.base;


public abstract class FormalExpressionBase extends org.semanticwb.process.model.Expression implements org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticProperty swp_evaluatesToType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#evaluatesToType");
    public static final org.semanticwb.platform.SemanticProperty swp_body=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#body");
    public static final org.semanticwb.platform.SemanticClass swp_FormalExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FormalExpression");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#FormalExpression");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression>(it, true);
        }

        public static org.semanticwb.process.model.FormalExpression createFormalExpression(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.FormalExpression.ClassMgr.createFormalExpression(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.FormalExpression getFormalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FormalExpression)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.FormalExpression createFormalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.FormalExpression)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeFormalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasFormalExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormalExpression(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.FormalExpression> listFormalExpressionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.FormalExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public FormalExpressionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getEvaluatesToType()
    {
        return getSemanticObject().getProperty(swp_evaluatesToType);
    }

    public void setEvaluatesToType(String value)
    {
        getSemanticObject().setProperty(swp_evaluatesToType, value);
    }

    public String getBody()
    {
        return getSemanticObject().getProperty(swp_body);
    }

    public void setBody(String value)
    {
        getSemanticObject().setProperty(swp_body, value);
    }
}
