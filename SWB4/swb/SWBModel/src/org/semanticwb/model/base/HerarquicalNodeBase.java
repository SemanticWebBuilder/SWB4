package org.semanticwb.model.base;


   /**
   * Objeto utilizado para definir un node del arbol de navegacion dentro de la administración de SWB, que contiene elemento de una clase definida. 
   */
public abstract class HerarquicalNodeBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Iconable,org.semanticwb.model.HerarquicalNodeable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
   /**
   * Tipo de clase hija del nodo
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_heClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heClass");
    public static final org.semanticwb.platform.SemanticProperty swbxf_heTreeController=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heTreeController");
   /**
   * Superclase de todos los tipos de Modelos de SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");
    public static final org.semanticwb.platform.SemanticProperty swbxf_heModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#heModel");
    public static final org.semanticwb.platform.SemanticProperty swbxf_hePropertyFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hePropertyFilter");
   /**
   * Objeto utilizado para definir un node del arbol de navegacion dentro de la administración de SWB, que contiene elemento de una clase definida.
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_HerarquicalNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#HerarquicalNode");

    public static class ClassMgr
    {
       /**
       * Returns a list of HerarquicalNode for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.HerarquicalNode
       */

        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.HerarquicalNode for all models
       * @return Iterator of org.semanticwb.model.HerarquicalNode
       */

        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.HerarquicalNode
       * @param id Identifier for org.semanticwb.model.HerarquicalNode
       * @param model Model of the org.semanticwb.model.HerarquicalNode
       * @return A org.semanticwb.model.HerarquicalNode
       */
        public static org.semanticwb.model.HerarquicalNode getHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HerarquicalNode)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.HerarquicalNode
       * @param id Identifier for org.semanticwb.model.HerarquicalNode
       * @param model Model of the org.semanticwb.model.HerarquicalNode
       * @return A org.semanticwb.model.HerarquicalNode
       */
        public static org.semanticwb.model.HerarquicalNode createHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.HerarquicalNode)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.HerarquicalNode
       * @param id Identifier for org.semanticwb.model.HerarquicalNode
       * @param model Model of the org.semanticwb.model.HerarquicalNode
       */
        public static void removeHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.HerarquicalNode
       * @param id Identifier for org.semanticwb.model.HerarquicalNode
       * @param model Model of the org.semanticwb.model.HerarquicalNode
       * @return true if the org.semanticwb.model.HerarquicalNode exists, false otherwise
       */

        public static boolean hasHerarquicalNode(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHerarquicalNode(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.HerarquicalNode with a determined HerarquicalNode
       * @param value HerarquicalNode of the type org.semanticwb.model.HerarquicalNode
       * @param model Model of the org.semanticwb.model.HerarquicalNode
       * @return Iterator with all the org.semanticwb.model.HerarquicalNode
       */

        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodeByHerarquicalNode(org.semanticwb.model.HerarquicalNode value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasHerarquicalNode, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.HerarquicalNode with a determined HerarquicalNode
       * @param value HerarquicalNode of the type org.semanticwb.model.HerarquicalNode
       * @return Iterator with all the org.semanticwb.model.HerarquicalNode
       */

        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodeByHerarquicalNode(org.semanticwb.model.HerarquicalNode value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasHerarquicalNode,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.HerarquicalNode with a determined Model
       * @param value Model of the type org.semanticwb.model.SWBModel
       * @param model Model of the org.semanticwb.model.HerarquicalNode
       * @return Iterator with all the org.semanticwb.model.HerarquicalNode
       */

        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodeByModel(org.semanticwb.model.SWBModel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbxf_heModel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.HerarquicalNode with a determined Model
       * @param value Model of the type org.semanticwb.model.SWBModel
       * @return Iterator with all the org.semanticwb.model.HerarquicalNode
       */

        public static java.util.Iterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodeByModel(org.semanticwb.model.SWBModel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbxf_heModel,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static HerarquicalNodeBase.ClassMgr getHerarquicalNodeClassMgr()
    {
        return new HerarquicalNodeBase.ClassMgr();
    }

   /**
   * Constructs a HerarquicalNodeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the HerarquicalNode
   */
    public HerarquicalNodeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.model.HerarquicalNode
   * @return A GenericIterator with all the org.semanticwb.model.HerarquicalNode
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode> listHerarquicalNodes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.HerarquicalNode>(getSemanticObject().listObjectProperties(swb_hasHerarquicalNode));
    }

   /**
   * Gets true if has a HerarquicalNode
   * @param value org.semanticwb.model.HerarquicalNode to verify
   * @return true if the org.semanticwb.model.HerarquicalNode exists, false otherwise
   */
    public boolean hasHerarquicalNode(org.semanticwb.model.HerarquicalNode value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasHerarquicalNode,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a HerarquicalNode
   * @param value org.semanticwb.model.HerarquicalNode to add
   */

    public void addHerarquicalNode(org.semanticwb.model.HerarquicalNode value)
    {
        getSemanticObject().addObjectProperty(swb_hasHerarquicalNode, value.getSemanticObject());
    }
   /**
   * Removes all the HerarquicalNode
   */

    public void removeAllHerarquicalNode()
    {
        getSemanticObject().removeProperty(swb_hasHerarquicalNode);
    }
   /**
   * Removes a HerarquicalNode
   * @param value org.semanticwb.model.HerarquicalNode to remove
   */

    public void removeHerarquicalNode(org.semanticwb.model.HerarquicalNode value)
    {
        getSemanticObject().removeObjectProperty(swb_hasHerarquicalNode,value.getSemanticObject());
    }

   /**
   * Gets the HerarquicalNode
   * @return a org.semanticwb.model.HerarquicalNode
   */
    public org.semanticwb.model.HerarquicalNode getHerarquicalNode()
    {
         org.semanticwb.model.HerarquicalNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasHerarquicalNode);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.HerarquicalNode)obj.createGenericInstance();
         }
         return ret;
    }

    public void setHClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbxf_heClass, value);
    }

    public void removeHClass()
    {
        getSemanticObject().removeProperty(swbxf_heClass);
    }

/**
* Gets the HClass property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getHClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_heClass);
         return ret;
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the TreeController property
* @return String with the TreeController
*/
    public String getTreeController()
    {
        return getSemanticObject().getProperty(swbxf_heTreeController);
    }

/**
* Sets the TreeController property
* @param value long with the TreeController
*/
    public void setTreeController(String value)
    {
        getSemanticObject().setProperty(swbxf_heTreeController, value);
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
   * Sets the value for the property Model
   * @param value Model to set
   */

    public void setModel(org.semanticwb.model.SWBModel value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swbxf_heModel, value.getSemanticObject());
        }else
        {
            removeModel();
        }
    }
   /**
   * Remove the value for Model property
   */

    public void removeModel()
    {
        getSemanticObject().removeProperty(swbxf_heModel);
    }

   /**
   * Gets the Model
   * @return a org.semanticwb.model.SWBModel
   */
    public org.semanticwb.model.SWBModel getModel()
    {
         org.semanticwb.model.SWBModel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbxf_heModel);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBModel)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the PropertyFilter property
* @return String with the PropertyFilter
*/
    public String getPropertyFilter()
    {
        return getSemanticObject().getProperty(swbxf_hePropertyFilter);
    }

/**
* Sets the PropertyFilter property
* @param value long with the PropertyFilter
*/
    public void setPropertyFilter(String value)
    {
        getSemanticObject().setProperty(swbxf_hePropertyFilter, value);
    }

/**
* Gets the IconClass property
* @return String with the IconClass
*/
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

/**
* Sets the IconClass property
* @param value long with the IconClass
*/
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
}
