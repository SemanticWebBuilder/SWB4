package org.semanticwb.process.model.base;


public abstract class ResourceAssignmentExpressionBase extends org.semanticwb.process.model.BPMNBaseElement implements org.semanticwb.process.model.Expressionable,org.semanticwb.process.model.Documentable
{
    public static final org.semanticwb.platform.SemanticClass swp_ResourceAssignmentExpression=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceAssignmentExpression");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#ResourceAssignmentExpression");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression>(it, true);
        }

        public static org.semanticwb.process.model.ResourceAssignmentExpression createResourceAssignmentExpression(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ResourceAssignmentExpression.ClassMgr.createResourceAssignmentExpression(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.ResourceAssignmentExpression getResourceAssignmentExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ResourceAssignmentExpression)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.ResourceAssignmentExpression createResourceAssignmentExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ResourceAssignmentExpression)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeResourceAssignmentExpression(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasResourceAssignmentExpression(String id, org.semanticwb.model.SWBModel model)
        {
            return (getResourceAssignmentExpression(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByExpression(org.semanticwb.process.model.Expression value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_expression, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByExpression(org.semanticwb.process.model.Expression value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_expression,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByExtensionDefinition(org.semanticwb.process.model.ExtensionDefinition value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionDefinition,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.ResourceAssignmentExpression> listResourceAssignmentExpressionByExtensionValue(org.semanticwb.process.model.ExtensionAttributeValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ResourceAssignmentExpression> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasExtensionValue,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ResourceAssignmentExpressionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setExpression(org.semanticwb.process.model.Expression value)
    {
        getSemanticObject().setObjectProperty(swp_expression, value.getSemanticObject());
    }

    public void removeExpression()
    {
        getSemanticObject().removeProperty(swp_expression);
    }

    public org.semanticwb.process.model.Expression getExpression()
    {
         org.semanticwb.process.model.Expression ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_expression);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Expression)obj.createGenericInstance();
         }
         return ret;
    }
}
