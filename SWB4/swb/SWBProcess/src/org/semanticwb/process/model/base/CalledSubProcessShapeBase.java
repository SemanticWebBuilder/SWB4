package org.semanticwb.process.model.base;


public abstract class CalledSubProcessShapeBase extends org.semanticwb.process.model.ActivityShape implements org.semanticwb.process.model.Collapsable,org.semanticwb.process.model.DiagramLinkable
{
    public static final org.semanticwb.platform.SemanticClass swp_CalledSubProcessShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CalledSubProcessShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#CalledSubProcessShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessShape> listCalledSubProcessShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessShape> listCalledSubProcessShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessShape>(it, true);
        }

        public static org.semanticwb.process.model.CalledSubProcessShape createCalledSubProcessShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.CalledSubProcessShape.ClassMgr.createCalledSubProcessShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.CalledSubProcessShape getCalledSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CalledSubProcessShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.CalledSubProcessShape createCalledSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CalledSubProcessShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeCalledSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasCalledSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCalledSubProcessShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessShape> listCalledSubProcessShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.CalledSubProcessShape> listCalledSubProcessShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CalledSubProcessShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public CalledSubProcessShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getDiagramLink()
    {
        return getSemanticObject().getProperty(swp_diagramLink);
    }

    public void setDiagramLink(String value)
    {
        getSemanticObject().setProperty(swp_diagramLink, value);
    }

    public boolean isCollapsed()
    {
        return getSemanticObject().getBooleanProperty(swp_collapsed);
    }

    public void setCollapsed(boolean value)
    {
        getSemanticObject().setBooleanProperty(swp_collapsed, value);
    }
}
