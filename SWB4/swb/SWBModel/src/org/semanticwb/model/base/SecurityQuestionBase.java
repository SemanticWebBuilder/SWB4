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
 * The Class SecurityQuestionBase.
 */
public abstract class SecurityQuestionBase extends org.semanticwb.model.base.FormElementBase 
{
    
    /** The Constant swbxf_SecurityQuestion. */
    public static final org.semanticwb.platform.SemanticClass swbxf_SecurityQuestion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SecurityQuestion");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SecurityQuestion");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List security questions.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SecurityQuestion> listSecurityQuestions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SecurityQuestion>(it, true);
        }

        /**
         * List security questions.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.SecurityQuestion> listSecurityQuestions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SecurityQuestion>(it, true);
        }

        /**
         * Gets the security question.
         * 
         * @param id the id
         * @param model the model
         * @return the security question
         */
        public static org.semanticwb.model.SecurityQuestion getSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SecurityQuestion)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the security question.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. security question
         */
        public static org.semanticwb.model.SecurityQuestion createSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SecurityQuestion)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the security question.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for security question.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasSecurityQuestion(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSecurityQuestion(id, model)!=null);
        }
    }

    /**
     * Instantiates a new security question base.
     * 
     * @param base the base
     */
    public SecurityQuestionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Removes the.
     */
    public void remove()
    {
        getSemanticObject().remove();
    }

    /**
     * List related objects.
     * 
     * @return the java.util. iterator
     */
    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
