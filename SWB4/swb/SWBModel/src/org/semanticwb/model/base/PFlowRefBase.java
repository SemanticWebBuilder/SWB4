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
    * Referencia a un objeto de tipo PFlow.
    */
public abstract class PFlowRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable,org.semanticwb.model.PFlowable
{
   
   /** Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    
    /** The Constant swb_pflow. */
    public static final org.semanticwb.platform.SemanticProperty swb_pflow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pflow");
   
   /** Referencia a un objeto de tipo PFlow. */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of PFlowRef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.PFlowRef
        */

        public static java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.PFlowRef for all models
       * @return Iterator of org.semanticwb.model.PFlowRef
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(it, true);
        }

        /**
         * Creates the p flow ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. p flow ref
         */
        public static org.semanticwb.model.PFlowRef createPFlowRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.PFlowRef.ClassMgr.createPFlowRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.PFlowRef
       * @param id Identifier for org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.PFlowRef
       * @return A org.semanticwb.model.PFlowRef
       */
        public static org.semanticwb.model.PFlowRef getPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.PFlowRef
       * @param id Identifier for org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.PFlowRef
       * @return A org.semanticwb.model.PFlowRef
       */
        public static org.semanticwb.model.PFlowRef createPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlowRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.PFlowRef
       * @param id Identifier for org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.PFlowRef
       */
        public static void removePFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.PFlowRef
       * @param id Identifier for org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.PFlowRef
       * @return true if the org.semanticwb.model.PFlowRef exists, false otherwise
       */

        public static boolean hasPFlowRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPFlowRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.PFlowRef with a determined Pflow
       * @param value Pflow of the type org.semanticwb.model.PFlow
       * @param model Model of the org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.model.PFlowRef
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefByPflow(org.semanticwb.model.PFlow value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_pflow, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlowRef with a determined Pflow
       * @param value Pflow of the type org.semanticwb.model.PFlow
       * @return Iterator with all the org.semanticwb.model.PFlowRef
       */

        public static java.util.Iterator<org.semanticwb.model.PFlowRef> listPFlowRefByPflow(org.semanticwb.model.PFlow value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_pflow,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a PFlowRefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the PFlowRef
    */
    public PFlowRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Pflow.
    * 
    * @param value Pflow to set
    */

    public void setPflow(org.semanticwb.model.PFlow value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_pflow, value.getSemanticObject());
        }else
        {
            removePflow();
        }
    }
   
   /**
    * Remove the value for Pflow property.
    */

    public void removePflow()
    {
        getSemanticObject().removeProperty(swb_pflow);
    }

   /**
    * Gets the Pflow.
    * 
    * @return a org.semanticwb.model.PFlow
    */
    public org.semanticwb.model.PFlow getPflow()
    {
         org.semanticwb.model.PFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pflow);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlow)obj.createGenericInstance();
         }
         return ret;
    }

   /**
    * Gets the WebSite.
    * 
    * @return a instance of org.semanticwb.model.WebSite
    */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
