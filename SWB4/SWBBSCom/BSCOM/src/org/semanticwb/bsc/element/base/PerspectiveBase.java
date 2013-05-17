package org.semanticwb.bsc.element.base;


public abstract class PerspectiveBase extends org.semanticwb.bsc.element.BSCElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Sortable,org.semanticwb.bsc.Recognizable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Theme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Theme");
    public static final org.semanticwb.platform.SemanticProperty bsc_hasTheme=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#hasTheme");
    public static final org.semanticwb.platform.SemanticClass bsc_Perspective=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Perspective");

    public static class ClassMgr
    {
       /**
       * Returns a list of Perspective for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Perspective for all models
       * @return Iterator of org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective>(it, true);
        }

        public static org.semanticwb.bsc.element.Perspective createPerspective(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Perspective.ClassMgr.createPerspective(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return A org.semanticwb.bsc.element.Perspective
       */
        public static org.semanticwb.bsc.element.Perspective getPerspective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Perspective)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return A org.semanticwb.bsc.element.Perspective
       */
        public static org.semanticwb.bsc.element.Perspective createPerspective(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Perspective)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       */
        public static void removePerspective(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Perspective
       * @param id Identifier for org.semanticwb.bsc.element.Perspective
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return true if the org.semanticwb.bsc.element.Perspective exists, false otherwise
       */

        public static boolean hasPerspective(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPerspective(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @param model Model of the org.semanticwb.bsc.element.Perspective
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByTheme(org.semanticwb.bsc.element.Theme value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasTheme, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Perspective with a determined Theme
       * @param value Theme of the type org.semanticwb.bsc.element.Theme
       * @return Iterator with all the org.semanticwb.bsc.element.Perspective
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Perspective> listPerspectiveByTheme(org.semanticwb.bsc.element.Theme value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Perspective> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasTheme,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PerspectiveBase.ClassMgr getPerspectiveClassMgr()
    {
        return new PerspectiveBase.ClassMgr();
    }

   /**
   * Constructs a PerspectiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Perspective
   */
    public PerspectiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
   * Gets all the org.semanticwb.bsc.element.Theme
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Theme
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme> listThemes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Theme>(getSemanticObject().listObjectProperties(bsc_hasTheme));
    }

   /**
   * Gets true if has a Theme
   * @param value org.semanticwb.bsc.element.Theme to verify
   * @return true if the org.semanticwb.bsc.element.Theme exists, false otherwise
   */
    public boolean hasTheme(org.semanticwb.bsc.element.Theme value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasTheme,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Theme
   * @param value org.semanticwb.bsc.element.Theme to add
   */

    public void addTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().addObjectProperty(bsc_hasTheme, value.getSemanticObject());
    }
   /**
   * Removes all the Theme
   */

    public void removeAllTheme()
    {
        getSemanticObject().removeProperty(bsc_hasTheme);
    }
   /**
   * Removes a Theme
   * @param value org.semanticwb.bsc.element.Theme to remove
   */

    public void removeTheme(org.semanticwb.bsc.element.Theme value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasTheme,value.getSemanticObject());
    }

   /**
   * Gets the Theme
   * @return a org.semanticwb.bsc.element.Theme
   */
    public org.semanticwb.bsc.element.Theme getTheme()
    {
         org.semanticwb.bsc.element.Theme ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasTheme);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Theme)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        return getSemanticObject().getProperty(bsc_prefix);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        getSemanticObject().setProperty(bsc_prefix, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
