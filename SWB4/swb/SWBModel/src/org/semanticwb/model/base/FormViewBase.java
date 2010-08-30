package org.semanticwb.model.base;


public abstract class FormViewBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass rdf_Property=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/1999/02/22-rdf-syntax-ns#Property");
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasCreateProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasCreateProperty");
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasViewProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasViewProperty");
   /**
   * Referencia a un objeto de tipo FormView
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormViewRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasFormViewRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasFormViewRefInv");
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasEditProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasEditProperty");
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");

    public static class ClassMgr
    {
       /**
       * Returns a list of FormView for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.FormView
       */

        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViews(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FormView for all models
       * @return Iterator of org.semanticwb.model.FormView
       */

        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViews()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView>(it, true);
        }

        public static org.semanticwb.model.FormView createFormView(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.FormView.ClassMgr.createFormView(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.FormView
       * @param id Identifier for org.semanticwb.model.FormView
       * @param model Model of the org.semanticwb.model.FormView
       * @return A org.semanticwb.model.FormView
       */
        public static org.semanticwb.model.FormView getFormView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormView)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FormView
       * @param id Identifier for org.semanticwb.model.FormView
       * @param model Model of the org.semanticwb.model.FormView
       * @return A org.semanticwb.model.FormView
       */
        public static org.semanticwb.model.FormView createFormView(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormView)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FormView
       * @param id Identifier for org.semanticwb.model.FormView
       * @param model Model of the org.semanticwb.model.FormView
       */
        public static void removeFormView(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FormView
       * @param id Identifier for org.semanticwb.model.FormView
       * @param model Model of the org.semanticwb.model.FormView
       * @return true if the org.semanticwb.model.FormView exists, false otherwise
       */

        public static boolean hasFormView(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormView(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.FormView with a determined FormViewRefInv
       * @param value FormViewRefInv of the type org.semanticwb.model.FormViewRef
       * @param model Model of the org.semanticwb.model.FormView
       * @return Iterator with all the org.semanticwb.model.FormView
       */

        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViewByFormViewRefInv(org.semanticwb.model.FormViewRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFormViewRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FormView with a determined FormViewRefInv
       * @param value FormViewRefInv of the type org.semanticwb.model.FormViewRef
       * @return Iterator with all the org.semanticwb.model.FormView
       */

        public static java.util.Iterator<org.semanticwb.model.FormView> listFormViewByFormViewRefInv(org.semanticwb.model.FormViewRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormView> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFormViewRefInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a FormViewBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FormView
   */
    public FormViewBase(org.semanticwb.platform.SemanticObject base)
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

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listCreateProperties()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swbxf_hasCreateProperty.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addCreateProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swbxf_hasCreateProperty, value);
    }

    public void removeAllCreateProperty()
    {
        getSemanticObject().removeProperty(swbxf_hasCreateProperty);
    }

    public void removeCreateProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasCreateProperty,value);
    }

/**
* Gets the CreateProperty property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getCreateProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_hasCreateProperty);
         return ret;
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listViewProperties()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swbxf_hasViewProperty.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addViewProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swbxf_hasViewProperty, value);
    }

    public void removeAllViewProperty()
    {
        getSemanticObject().removeProperty(swbxf_hasViewProperty);
    }

    public void removeViewProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasViewProperty,value);
    }

/**
* Gets the ViewProperty property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getViewProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_hasViewProperty);
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.FormViewRef
   * @return A GenericIterator with all the org.semanticwb.model.FormViewRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef> listFormViewRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef>(getSemanticObject().listObjectProperties(swb_hasFormViewRefInv));
    }

   /**
   * Gets true if has a FormViewRefInv
   * @param value org.semanticwb.model.FormViewRef to verify
   * @return true if the org.semanticwb.model.FormViewRef exists, false otherwise
   */
    public boolean hasFormViewRefInv(org.semanticwb.model.FormViewRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasFormViewRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the FormViewRefInv
   * @return a org.semanticwb.model.FormViewRef
   */
    public org.semanticwb.model.FormViewRef getFormViewRefInv()
    {
         org.semanticwb.model.FormViewRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasFormViewRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.FormViewRef)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listEditProperties()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swbxf_hasEditProperty.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addEditProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swbxf_hasEditProperty, value);
    }

    public void removeAllEditProperty()
    {
        getSemanticObject().removeProperty(swbxf_hasEditProperty);
    }

    public void removeEditProperty(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasEditProperty,value);
    }

/**
* Gets the EditProperty property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getEditProperty()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_hasEditProperty);
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
}
