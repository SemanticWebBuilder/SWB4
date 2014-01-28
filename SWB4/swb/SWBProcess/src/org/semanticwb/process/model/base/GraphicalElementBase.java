package org.semanticwb.process.model.base;


public abstract class GraphicalElementBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
    public static final org.semanticwb.platform.SemanticProperty swp_hasInputConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasInputConnectionObjectInv");
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");
    public static final org.semanticwb.platform.SemanticProperty swp_hasChildInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasChildInv");
    public static final org.semanticwb.platform.SemanticProperty swp_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#height");
    public static final org.semanticwb.platform.SemanticProperty swp_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#width");
    public static final org.semanticwb.platform.SemanticProperty swp_parent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parent");
    public static final org.semanticwb.platform.SemanticClass swp_Containerable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Containerable");
    public static final org.semanticwb.platform.SemanticProperty swp_container=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#container");
    public static final org.semanticwb.platform.SemanticProperty swp_hasOutputConnectionObjectInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasOutputConnectionObjectInv");
    public static final org.semanticwb.platform.SemanticProperty swp_labelSize=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#labelSize");
    public static final org.semanticwb.platform.SemanticProperty swp_y=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#y");
    public static final org.semanticwb.platform.SemanticProperty swp_x=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#x");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of GraphicalElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.GraphicalElement for all models
       * @return Iterator of org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.GraphicalElement
       * @param id Identifier for org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return A org.semanticwb.process.model.GraphicalElement
       */
        public static org.semanticwb.process.model.GraphicalElement getGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GraphicalElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.GraphicalElement
       * @param id Identifier for org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return A org.semanticwb.process.model.GraphicalElement
       */
        public static org.semanticwb.process.model.GraphicalElement createGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GraphicalElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.GraphicalElement
       * @param id Identifier for org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       */
        public static void removeGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.GraphicalElement
       * @param id Identifier for org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return true if the org.semanticwb.process.model.GraphicalElement exists, false otherwise
       */

        public static boolean hasGraphicalElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGraphicalElement(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GraphicalElement with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.GraphicalElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.GraphicalElement> listGraphicalElementByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static GraphicalElementBase.ClassMgr getGraphicalElementClassMgr()
    {
        return new GraphicalElementBase.ClassMgr();
    }

   /**
   * Constructs a GraphicalElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GraphicalElement
   */
    public GraphicalElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ConnectionObject
   * @return A GenericIterator with all the org.semanticwb.process.model.ConnectionObject
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> listInputConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(getSemanticObject().listObjectProperties(swp_hasInputConnectionObjectInv));
    }

   /**
   * Gets true if has a InputConnectionObject
   * @param value org.semanticwb.process.model.ConnectionObject to verify
   * @return true if the org.semanticwb.process.model.ConnectionObject exists, false otherwise
   */
    public boolean hasInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasInputConnectionObjectInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the InputConnectionObject
   * @return a org.semanticwb.process.model.ConnectionObject
   */
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
   /**
   * Gets all the org.semanticwb.process.model.GraphicalElement
   * @return A GenericIterator with all the org.semanticwb.process.model.GraphicalElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasChildInv));
    }

   /**
   * Gets true if has a Child
   * @param value org.semanticwb.process.model.GraphicalElement to verify
   * @return true if the org.semanticwb.process.model.GraphicalElement exists, false otherwise
   */
    public boolean hasChild(org.semanticwb.process.model.GraphicalElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasChildInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Child
   * @return a org.semanticwb.process.model.GraphicalElement
   */
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

/**
* Gets the Height property
* @return int with the Height
*/
    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swp_height);
    }

/**
* Sets the Height property
* @param value long with the Height
*/
    public void setHeight(int value)
    {
        getSemanticObject().setIntProperty(swp_height, value);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
* Gets the Width property
* @return int with the Width
*/
    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swp_width);
    }

/**
* Sets the Width property
* @param value long with the Width
*/
    public void setWidth(int value)
    {
        getSemanticObject().setIntProperty(swp_width, value);
    }
   /**
   * Sets the value for the property Parent
   * @param value Parent to set
   */

    public void setParent(org.semanticwb.process.model.GraphicalElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_parent, value.getSemanticObject());
        }else
        {
            removeParent();
        }
    }
   /**
   * Remove the value for Parent property
   */

    public void removeParent()
    {
        getSemanticObject().removeProperty(swp_parent);
    }

   /**
   * Gets the Parent
   * @return a org.semanticwb.process.model.GraphicalElement
   */
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
   /**
   * Sets the value for the property Container
   * @param value Container to set
   */

    public void setContainer(org.semanticwb.process.model.Containerable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_container, value.getSemanticObject());
        }else
        {
            removeContainer();
        }
    }
   /**
   * Remove the value for Container property
   */

    public void removeContainer()
    {
        getSemanticObject().removeProperty(swp_container);
    }

   /**
   * Gets the Container
   * @return a org.semanticwb.process.model.Containerable
   */
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
   /**
   * Gets all the org.semanticwb.process.model.ConnectionObject
   * @return A GenericIterator with all the org.semanticwb.process.model.ConnectionObject
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> listOutputConnectionObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(getSemanticObject().listObjectProperties(swp_hasOutputConnectionObjectInv));
    }

   /**
   * Gets true if has a OutputConnectionObject
   * @param value org.semanticwb.process.model.ConnectionObject to verify
   * @return true if the org.semanticwb.process.model.ConnectionObject exists, false otherwise
   */
    public boolean hasOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasOutputConnectionObjectInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the OutputConnectionObject
   * @return a org.semanticwb.process.model.ConnectionObject
   */
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

/**
* Gets the LabelSize property
* @return int with the LabelSize
*/
    public int getLabelSize()
    {
        return getSemanticObject().getIntProperty(swp_labelSize);
    }

/**
* Sets the LabelSize property
* @param value long with the LabelSize
*/
    public void setLabelSize(int value)
    {
        getSemanticObject().setIntProperty(swp_labelSize, value);
    }

/**
* Gets the Y property
* @return int with the Y
*/
    public int getY()
    {
        return getSemanticObject().getIntProperty(swp_y);
    }

/**
* Sets the Y property
* @param value long with the Y
*/
    public void setY(int value)
    {
        getSemanticObject().setIntProperty(swp_y, value);
    }

/**
* Gets the X property
* @return int with the X
*/
    public int getX()
    {
        return getSemanticObject().getIntProperty(swp_x);
    }

/**
* Sets the X property
* @param value long with the X
*/
    public void setX(int value)
    {
        getSemanticObject().setIntProperty(swp_x, value);
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
