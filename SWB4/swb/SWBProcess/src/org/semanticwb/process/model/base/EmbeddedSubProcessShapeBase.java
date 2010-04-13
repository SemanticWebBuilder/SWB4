package org.semanticwb.process.model.base;


public abstract class EmbeddedSubProcessShapeBase extends org.semanticwb.process.model.ActivityShape implements org.semanticwb.process.model.Collapsable,org.semanticwb.process.model.LaneCompartmentable,org.semanticwb.process.model.DiagramLinkable
{
    public static final org.semanticwb.platform.SemanticClass swp_EmbeddedSubProcessShape=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EmbeddedSubProcessShape");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#EmbeddedSubProcessShape");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcessShape>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcessShape>(it, true);
        }

        public static org.semanticwb.process.model.EmbeddedSubProcessShape createEmbeddedSubProcessShape(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.EmbeddedSubProcessShape.ClassMgr.createEmbeddedSubProcessShape(String.valueOf(id), model);
        }

        public static org.semanticwb.process.model.EmbeddedSubProcessShape getEmbeddedSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EmbeddedSubProcessShape)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.EmbeddedSubProcessShape createEmbeddedSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.EmbeddedSubProcessShape)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeEmbeddedSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasEmbeddedSubProcessShape(String id, org.semanticwb.model.SWBModel model)
        {
            return (getEmbeddedSubProcessShape(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapeByLaneCompartment(org.semanticwb.process.model.LaneCompartment value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcessShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneCompartment, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapeByLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcessShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasLaneCompartment,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcessShape> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.EmbeddedSubProcessShape> listEmbeddedSubProcessShapeByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.EmbeddedSubProcessShape> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public EmbeddedSubProcessShapeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment> listLaneCompartments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.LaneCompartment>(getSemanticObject().listObjectProperties(swp_hasLaneCompartment));
    }

    public boolean hasLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasLaneCompartment,value.getSemanticObject());
        }
        return ret;
    }

    public void addLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
    {
        getSemanticObject().addObjectProperty(swp_hasLaneCompartment, value.getSemanticObject());
    }

    public void removeAllLaneCompartment()
    {
        getSemanticObject().removeProperty(swp_hasLaneCompartment);
    }

    public void removeLaneCompartment(org.semanticwb.process.model.LaneCompartment value)
    {
        getSemanticObject().removeObjectProperty(swp_hasLaneCompartment,value.getSemanticObject());
    }

    public org.semanticwb.process.model.LaneCompartment getLaneCompartment()
    {
         org.semanticwb.process.model.LaneCompartment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasLaneCompartment);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.LaneCompartment)obj.createGenericInstance();
         }
         return ret;
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
