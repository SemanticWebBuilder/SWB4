package org.semanticwb.transparencia.model.base;


public abstract class AreaBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass trans_Area=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Area");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.transparencia.org/ontology#Area");

    public static class ClassMgr
    {
       /**
       * Returns a list of Area for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.transparencia.model.Area
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Area> listAreas(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Area>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.transparencia.model.Area for all models
       * @return Iterator of org.semanticwb.transparencia.model.Area
       */

        public static java.util.Iterator<org.semanticwb.transparencia.model.Area> listAreas()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.transparencia.model.Area>(it, true);
        }

        public static org.semanticwb.transparencia.model.Area createArea(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.transparencia.model.Area.ClassMgr.createArea(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.transparencia.model.Area
       * @param id Identifier for org.semanticwb.transparencia.model.Area
       * @param model Model of the org.semanticwb.transparencia.model.Area
       * @return A org.semanticwb.transparencia.model.Area
       */
        public static org.semanticwb.transparencia.model.Area getArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Area)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.transparencia.model.Area
       * @param id Identifier for org.semanticwb.transparencia.model.Area
       * @param model Model of the org.semanticwb.transparencia.model.Area
       * @return A org.semanticwb.transparencia.model.Area
       */
        public static org.semanticwb.transparencia.model.Area createArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.transparencia.model.Area)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.transparencia.model.Area
       * @param id Identifier for org.semanticwb.transparencia.model.Area
       * @param model Model of the org.semanticwb.transparencia.model.Area
       */
        public static void removeArea(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.transparencia.model.Area
       * @param id Identifier for org.semanticwb.transparencia.model.Area
       * @param model Model of the org.semanticwb.transparencia.model.Area
       * @return true if the org.semanticwb.transparencia.model.Area exists, false otherwise
       */

        public static boolean hasArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (getArea(id, model)!=null);
        }
    }

   /**
   * Constructs a AreaBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Area
   */
    public AreaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
}
