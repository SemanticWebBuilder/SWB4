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
    * Referencia a un objeto de tipo Rule.
    */
public abstract class RuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
   /**
   * Objeto que define una regla de negocio, utilizando los atributos del usuario para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    
    /** The Constant swb_rule. */
    public static final org.semanticwb.platform.SemanticProperty swb_rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rule");
   
   /** Referencia a un objeto de tipo Rule. */
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of RuleRef for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.RuleRef
        */

        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.RuleRef for all models
       * @return Iterator of org.semanticwb.model.RuleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(it, true);
        }

        /**
         * Creates the rule ref.
         * 
         * @param model the model
         * @return the org.semanticwb.model. rule ref
         */
        public static org.semanticwb.model.RuleRef createRuleRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.RuleRef.ClassMgr.createRuleRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.RuleRef
       * @param id Identifier for org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.RuleRef
       * @return A org.semanticwb.model.RuleRef
       */
        public static org.semanticwb.model.RuleRef getRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RuleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.RuleRef
       * @param id Identifier for org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.RuleRef
       * @return A org.semanticwb.model.RuleRef
       */
        public static org.semanticwb.model.RuleRef createRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RuleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.RuleRef
       * @param id Identifier for org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.RuleRef
       */
        public static void removeRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.RuleRef
       * @param id Identifier for org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.RuleRef
       * @return true if the org.semanticwb.model.RuleRef exists, false otherwise
       */

        public static boolean hasRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRuleRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.RuleRef with a determined Rule
       * @param value Rule of the type org.semanticwb.model.Rule
       * @param model Model of the org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.model.RuleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefByRule(org.semanticwb.model.Rule value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_rule, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.RuleRef with a determined Rule
       * @param value Rule of the type org.semanticwb.model.Rule
       * @return Iterator with all the org.semanticwb.model.RuleRef
       */

        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefByRule(org.semanticwb.model.Rule value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_rule,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a RuleRefBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the RuleRef
    */
    public RuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   
   /**
    * Sets the value for the property Rule.
    * 
    * @param value Rule to set
    */

    public void setRule(org.semanticwb.model.Rule value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_rule, value.getSemanticObject());
        }else
        {
            removeRule();
        }
    }
   
   /**
    * Remove the value for Rule property.
    */

    public void removeRule()
    {
        getSemanticObject().removeProperty(swb_rule);
    }

   /**
    * Gets the Rule.
    * 
    * @return a org.semanticwb.model.Rule
    */
    public org.semanticwb.model.Rule getRule()
    {
         org.semanticwb.model.Rule ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_rule);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Rule)obj.createGenericInstance();
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
