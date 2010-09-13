/**  
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
    * Referencia a un objeto de tipo FormView.
    */
public abstract class FormViewRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
   
   /** Objeto utilizado para agrupar propiedades que en su conjunto definen una vista dentro de la administración de SWB. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormView");
    
    /** The Constant swb_formView. */
    public static final org.semanticwb.platform.SemanticProperty swb_formView=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#formView");
    
    /** The Constant swb_formMode. */
    public static final org.semanticwb.platform.SemanticProperty swb_formMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#formMode");
   
   /** Referencia a un objeto de tipo FormView. */
    public static final org.semanticwb.platform.SemanticClass swbxf_FormViewRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#FormViewRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of FormViewRef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.FormViewRef
        */

        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.FormViewRef for all models
       * @return Iterator of org.semanticwb.model.FormViewRef
       */

        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.FormViewRef
       * @param id Identifier for org.semanticwb.model.FormViewRef
       * @param model Model of the org.semanticwb.model.FormViewRef
       * @return A org.semanticwb.model.FormViewRef
       */
        public static org.semanticwb.model.FormViewRef getFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormViewRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.FormViewRef
       * @param id Identifier for org.semanticwb.model.FormViewRef
       * @param model Model of the org.semanticwb.model.FormViewRef
       * @return A org.semanticwb.model.FormViewRef
       */
        public static org.semanticwb.model.FormViewRef createFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.FormViewRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.FormViewRef
       * @param id Identifier for org.semanticwb.model.FormViewRef
       * @param model Model of the org.semanticwb.model.FormViewRef
       */
        public static void removeFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.FormViewRef
       * @param id Identifier for org.semanticwb.model.FormViewRef
       * @param model Model of the org.semanticwb.model.FormViewRef
       * @return true if the org.semanticwb.model.FormViewRef exists, false otherwise
       */

        public static boolean hasFormViewRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormViewRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.FormViewRef with a determined FormView
       * @param value FormView of the type org.semanticwb.model.FormView
       * @param model Model of the org.semanticwb.model.FormViewRef
       * @return Iterator with all the org.semanticwb.model.FormViewRef
       */

        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefByFormView(org.semanticwb.model.FormView value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_formView, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.FormViewRef with a determined FormView
       * @param value FormView of the type org.semanticwb.model.FormView
       * @return Iterator with all the org.semanticwb.model.FormViewRef
       */

        public static java.util.Iterator<org.semanticwb.model.FormViewRef> listFormViewRefByFormView(org.semanticwb.model.FormView value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.FormViewRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_formView,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a FormViewRefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the FormViewRef
    */
    public FormViewRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property FormView.
    * 
    * @param value FormView to set
    */

    public void setFormView(org.semanticwb.model.FormView value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_formView, value.getSemanticObject());
        }else
        {
            removeFormView();
        }
    }
   
   /**
    * Remove the value for FormView property.
    */

    public void removeFormView()
    {
        getSemanticObject().removeProperty(swb_formView);
    }

   /**
    * Gets the FormView.
    * 
    * @return a org.semanticwb.model.FormView
    */
    public org.semanticwb.model.FormView getFormView()
    {
         org.semanticwb.model.FormView ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_formView);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.FormView)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the FormMode property.
 * 
 * @return String with the FormMode
 */
    public String getFormMode()
    {
        return getSemanticObject().getProperty(swb_formMode);
    }

/**
 * Sets the FormMode property.
 * 
 * @param value long with the FormMode
 */
    public void setFormMode(String value)
    {
        getSemanticObject().setProperty(swb_formMode, value);
    }
}
