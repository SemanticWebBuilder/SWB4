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
 * The Class RuleRefBase.
 */
public abstract class RuleRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
    
    /** The Constant swb_Rule. */
    public static final org.semanticwb.platform.SemanticClass swb_Rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Rule");
    
    /** The Constant swb_rule. */
    public static final org.semanticwb.platform.SemanticProperty swb_rule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rule");
    
    /** The Constant swb_RuleRef. */
    public static final org.semanticwb.platform.SemanticClass swb_RuleRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#RuleRef");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List rule refs.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(it, true);
        }

        /**
         * List rule refs.
         * 
         * @return the java.util. iterator
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
         * Gets the rule ref.
         * 
         * @param id the id
         * @param model the model
         * @return the rule ref
         */
        public static org.semanticwb.model.RuleRef getRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RuleRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the rule ref.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. rule ref
         */
        public static org.semanticwb.model.RuleRef createRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.RuleRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the rule ref.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for rule ref.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasRuleRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRuleRef(id, model)!=null);
        }

        /**
         * List rule ref by rule.
         * 
         * @param rule the rule
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefByRule(org.semanticwb.model.Rule rule,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_rule, rule.getSemanticObject()));
            return it;
        }

        /**
         * List rule ref by rule.
         * 
         * @param rule the rule
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.RuleRef> listRuleRefByRule(org.semanticwb.model.Rule rule)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> it=new org.semanticwb.model.GenericIterator(rule.getSemanticObject().getModel().listSubjects(swb_rule,rule.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new rule ref base.
     * 
     * @param base the base
     */
    public RuleRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Sets the rule.
     * 
     * @param value the new rule
     */
    public void setRule(org.semanticwb.model.Rule value)
    {
        getSemanticObject().setObjectProperty(swb_rule, value.getSemanticObject());
    }

    /**
     * Removes the rule.
     */
    public void removeRule()
    {
        getSemanticObject().removeProperty(swb_rule);
    }

    /**
     * Gets the rule.
     * 
     * @return the rule
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
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
