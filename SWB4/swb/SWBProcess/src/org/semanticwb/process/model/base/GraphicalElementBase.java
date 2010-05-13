package org.semanticwb.process.model.base;


public abstract class GraphicalElementBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#width");
    public static final org.semanticwb.platform.SemanticClass swp_Containerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Containerable");
    public static final org.semanticwb.platform.SemanticProperty swp_container=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#container");
    public static final org.semanticwb.platform.SemanticProperty swp_y=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#y");
    public static final org.semanticwb.platform.SemanticProperty swp_x=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#x");
    public static final org.semanticwb.platform.SemanticProperty swp_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#height");
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutputConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasOutputConnectionObjectInv");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInputConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasInputConnectionObjectInv");
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasChildInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasChildInv");
    public static final org.semanticwb.platform.SemanticProperty swp_parent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parent");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(it, true);
        }

        public static org.semanticwb.process.model.GraphicalElement getGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GraphicalElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.process.model.GraphicalElement createGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GraphicalElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGraphicalElement(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public GraphicalElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swp_width);
    }

    public void setWidth(int value)
    {
        getSemanticObject().setIntProperty(swp_width, value);
    }

    public void setContainer(org.semanticwb.process.model.Containerable value)
    {
        getSemanticObject().setObjectProperty(swp_container, value.getSemanticObject());
    }

    public void removeContainer()
    {
        getSemanticObject().removeProperty(swp_container);
    }

    public org.semanticwb.process.model.Containerable getContainer()
    {
         org.semanticwb.process.model.Containerable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_container);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Containerable)obj.createGenericInstance();
         }
         return ret;
    }

    public int getY()
    {
        return getSemanticObject().getIntProperty(swp_y);
    }

    public void setY(int value)
    {
        getSemanticObject().setIntProperty(swp_y, value);
    }

    public int getX()
    {
        return getSemanticObject().getIntProperty(swp_x);
    }

    public void setX(int value)
    {
        getSemanticObject().setIntProperty(swp_x, value);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swp_height);
    }

    public void setHeight(int value)
    {
        getSemanticObject().setIntProperty(swp_height, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> listOutputConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(getSemanticObject().listObjectProperties(swp_hasOutputConnectionObjectInv));
    }

    public boolean hasOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutputConnectionObjectInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.ConnectionObject getOutputConnectionObject()
    {
         org.semanticwb.process.model.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasOutputConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> listInputConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(getSemanticObject().listObjectProperties(swp_hasInputConnectionObjectInv));
    }

    public boolean hasInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasInputConnectionObjectInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.ConnectionObject getInputConnectionObject()
    {
         org.semanticwb.process.model.ConnectionObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasInputConnectionObjectInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ConnectionObject)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasChildInv));
    }

    public boolean hasChild(org.semanticwb.process.model.GraphicalElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasChildInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.process.model.GraphicalElement getChild()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasChildInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public void setParent(org.semanticwb.process.model.GraphicalElement value)
    {
        getSemanticObject().setObjectProperty(swp_parent, value.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(swp_parent);
    }

    public org.semanticwb.process.model.GraphicalElement getParent()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parent);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
