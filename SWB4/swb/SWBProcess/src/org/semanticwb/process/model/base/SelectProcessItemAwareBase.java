/*
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
 */
package org.semanticwb.process.model.base;


public abstract class SelectProcessItemAwareBase extends org.semanticwb.model.SelectMultiple 
{
    public static final org.semanticwb.platform.SemanticClass swp_SelectProcessItemAware=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SelectProcessItemAware");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SelectProcessItemAware");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectProcessItemAware for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SelectProcessItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.SelectProcessItemAware> listSelectProcessItemAwares(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SelectProcessItemAware>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SelectProcessItemAware for all models
       * @return Iterator of org.semanticwb.process.model.SelectProcessItemAware
       */

        public static java.util.Iterator<org.semanticwb.process.model.SelectProcessItemAware> listSelectProcessItemAwares()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SelectProcessItemAware>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.SelectProcessItemAware
       * @param id Identifier for org.semanticwb.process.model.SelectProcessItemAware
       * @param model Model of the org.semanticwb.process.model.SelectProcessItemAware
       * @return A org.semanticwb.process.model.SelectProcessItemAware
       */
        public static org.semanticwb.process.model.SelectProcessItemAware getSelectProcessItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SelectProcessItemAware)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SelectProcessItemAware
       * @param id Identifier for org.semanticwb.process.model.SelectProcessItemAware
       * @param model Model of the org.semanticwb.process.model.SelectProcessItemAware
       * @return A org.semanticwb.process.model.SelectProcessItemAware
       */
        public static org.semanticwb.process.model.SelectProcessItemAware createSelectProcessItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SelectProcessItemAware)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SelectProcessItemAware
       * @param id Identifier for org.semanticwb.process.model.SelectProcessItemAware
       * @param model Model of the org.semanticwb.process.model.SelectProcessItemAware
       */
        public static void removeSelectProcessItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SelectProcessItemAware
       * @param id Identifier for org.semanticwb.process.model.SelectProcessItemAware
       * @param model Model of the org.semanticwb.process.model.SelectProcessItemAware
       * @return true if the org.semanticwb.process.model.SelectProcessItemAware exists, false otherwise
       */

        public static boolean hasSelectProcessItemAware(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectProcessItemAware(id, model)!=null);
        }
    }

    public static SelectProcessItemAwareBase.ClassMgr getSelectProcessItemAwareClassMgr()
    {
        return new SelectProcessItemAwareBase.ClassMgr();
    }

   /**
   * Constructs a SelectProcessItemAwareBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectProcessItemAware
   */
    public SelectProcessItemAwareBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
