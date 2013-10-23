package org.semanticwb.model.base;


   /**
   * Objeto utilizado para definir caracteristicas visuales de una clase dentro de la administracion de SWB 
   */
public abstract class DisplayObjectBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Iconable
{
   /**
   * Define que este tipo de objeto soporta drag and drop en el arbol de administracion de SWB
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_dragSupport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#dragSupport");
    public static final org.semanticwb.platform.SemanticProperty swbxf_dropMatchLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#dropMatchLevel");
    public static final org.semanticwb.platform.SemanticProperty swbxf_doTreeController=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#doTreeController");
   /**
   * Define el modo de desplieque del objeto en el arbol de administracion de SWB, valores (null (Valor heredado o full_access en caso de no herencia ), full_access, edit_only, herarquical_edit_only),
   */
    public static final org.semanticwb.platform.SemanticProperty swb_displayMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#displayMode");
    public static final org.semanticwb.platform.SemanticProperty swbxf_doDispatcher=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#doDispatcher");
   /**
   * Define que no se puedan crear instancias de la clase en el arbol de administracion de SWB
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_doNotInstanceable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#doNotInstanceable");
   /**
   * Objeto utilizado para definir caracteristicas visuales de una clase dentro de la administracion de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayObject");

    public static class ClassMgr
    {
       /**
       * Returns a list of DisplayObject for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.DisplayObject
       */

        public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.DisplayObject for all models
       * @return Iterator of org.semanticwb.model.DisplayObject
       */

        public static java.util.Iterator<org.semanticwb.model.DisplayObject> listDisplayObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayObject>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       * @return A org.semanticwb.model.DisplayObject
       */
        public static org.semanticwb.model.DisplayObject getDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       * @return A org.semanticwb.model.DisplayObject
       */
        public static org.semanticwb.model.DisplayObject createDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       */
        public static void removeDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.DisplayObject
       * @param id Identifier for org.semanticwb.model.DisplayObject
       * @param model Model of the org.semanticwb.model.DisplayObject
       * @return true if the org.semanticwb.model.DisplayObject exists, false otherwise
       */

        public static boolean hasDisplayObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDisplayObject(id, model)!=null);
        }
    }

    public static DisplayObjectBase.ClassMgr getDisplayObjectClassMgr()
    {
        return new DisplayObjectBase.ClassMgr();
    }

   /**
   * Constructs a DisplayObjectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DisplayObject
   */
    public DisplayObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the DragSupport property
* @return boolean with the DragSupport
*/
    public boolean isDragSupport()
    {
        return getSemanticObject().getBooleanProperty(swbxf_dragSupport);
    }

/**
* Sets the DragSupport property
* @param value long with the DragSupport
*/
    public void setDragSupport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_dragSupport, value);
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
* Gets the DropMatchLevel property
* @return int with the DropMatchLevel
*/
    public int getDropMatchLevel()
    {
        return getSemanticObject().getIntProperty(swbxf_dropMatchLevel);
    }

/**
* Sets the DropMatchLevel property
* @param value long with the DropMatchLevel
*/
    public void setDropMatchLevel(int value)
    {
        getSemanticObject().setIntProperty(swbxf_dropMatchLevel, value);
    }

/**
* Gets the TreeController property
* @return String with the TreeController
*/
    public String getTreeController()
    {
        return getSemanticObject().getProperty(swbxf_doTreeController);
    }

/**
* Sets the TreeController property
* @param value long with the TreeController
*/
    public void setTreeController(String value)
    {
        getSemanticObject().setProperty(swbxf_doTreeController, value);
    }

/**
* Gets the DisplayMode property
* @return String with the DisplayMode
*/
    public String getDisplayMode()
    {
        return getSemanticObject().getProperty(swb_displayMode);
    }

/**
* Sets the DisplayMode property
* @param value long with the DisplayMode
*/
    public void setDisplayMode(String value)
    {
        getSemanticObject().setProperty(swb_displayMode, value);
    }

/**
* Gets the DoDispatcher property
* @return String with the DoDispatcher
*/
    public String getDoDispatcher()
    {
        return getSemanticObject().getProperty(swbxf_doDispatcher);
    }

/**
* Sets the DoDispatcher property
* @param value long with the DoDispatcher
*/
    public void setDoDispatcher(String value)
    {
        getSemanticObject().setProperty(swbxf_doDispatcher, value);
    }

/**
* Gets the DoNotInstanceable property
* @return boolean with the DoNotInstanceable
*/
    public boolean isDoNotInstanceable()
    {
        return getSemanticObject().getBooleanProperty(swbxf_doNotInstanceable);
    }

/**
* Sets the DoNotInstanceable property
* @param value long with the DoNotInstanceable
*/
    public void setDoNotInstanceable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_doNotInstanceable, value);
    }
}
