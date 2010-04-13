package org.semanticwb.process.model.base;


public abstract class BPMNConnectorBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass swp_BPMNLabel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNLabel");
    public static final org.semanticwb.platform.SemanticProperty swp_connectorLabel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#connectorLabel");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNBaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNBaseElement");
    public static final org.semanticwb.platform.SemanticProperty swp_contextType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#contextType");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNNode");
    public static final org.semanticwb.platform.SemanticProperty swp_connectorSource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#connectorSource");
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty swp_connectorTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swp#connectorTarget");
    public static final org.semanticwb.platform.SemanticClass swp_BPMNConnector=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNConnector");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#BPMNConnector");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector>(it, true);
        }

        public static org.semanticwb.process.model.BPMNConnector getBPMNConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNConnector)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.BPMNConnector createBPMNConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BPMNConnector)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeBPMNConnector(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasBPMNConnector(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBPMNConnector(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByConnectorLabel(org.semanticwb.process.model.BPMNLabel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorLabel, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByConnectorLabel(org.semanticwb.process.model.BPMNLabel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorLabel,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByContextType(org.semanticwb.process.model.BPMNBaseElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_contextType, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByContextType(org.semanticwb.process.model.BPMNBaseElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_contextType,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByConnectorSource(org.semanticwb.process.model.BPMNNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorSource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByConnectorSource(org.semanticwb.process.model.BPMNNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorSource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByConnectorTarget(org.semanticwb.process.model.BPMNNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_connectorTarget, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.BPMNConnector> listBPMNConnectorByConnectorTarget(org.semanticwb.process.model.BPMNNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BPMNConnector> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_connectorTarget,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public BPMNConnectorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setConnectorLabel(org.semanticwb.process.model.BPMNLabel value)
    {
        getSemanticObject().setObjectProperty(swp_connectorLabel, value.getSemanticObject());
    }

    public void removeConnectorLabel()
    {
        getSemanticObject().removeProperty(swp_connectorLabel);
    }

    public org.semanticwb.process.model.BPMNLabel getConnectorLabel()
    {
         org.semanticwb.process.model.BPMNLabel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_connectorLabel);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNLabel)obj.createGenericInstance();
         }
         return ret;
    }

    public void setContextType(org.semanticwb.process.model.BPMNBaseElement value)
    {
        getSemanticObject().setObjectProperty(swp_contextType, value.getSemanticObject());
    }

    public void removeContextType()
    {
        getSemanticObject().removeProperty(swp_contextType);
    }

    public org.semanticwb.process.model.BPMNBaseElement getContextType()
    {
         org.semanticwb.process.model.BPMNBaseElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_contextType);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNBaseElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setConnectorSource(org.semanticwb.process.model.BPMNNode value)
    {
        getSemanticObject().setObjectProperty(swp_connectorSource, value.getSemanticObject());
    }

    public void removeConnectorSource()
    {
        getSemanticObject().removeProperty(swp_connectorSource);
    }

    public org.semanticwb.process.model.BPMNNode getConnectorSource()
    {
         org.semanticwb.process.model.BPMNNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_connectorSource);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNNode)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isValid()
    {
        //Override this method in BPMNConnector object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

    public void setValid(boolean value)
    {
        //Override this method in BPMNConnector object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

    public void setConnectorTarget(org.semanticwb.process.model.BPMNNode value)
    {
        getSemanticObject().setObjectProperty(swp_connectorTarget, value.getSemanticObject());
    }

    public void removeConnectorTarget()
    {
        getSemanticObject().removeProperty(swp_connectorTarget);
    }

    public org.semanticwb.process.model.BPMNNode getConnectorTarget()
    {
         org.semanticwb.process.model.BPMNNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_connectorTarget);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.BPMNNode)obj.createGenericInstance();
         }
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
