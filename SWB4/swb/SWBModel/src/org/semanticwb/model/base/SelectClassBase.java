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
package org.semanticwb.model.base;


public abstract class SelectClassBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectClass");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectClass");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectClass for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SelectClass
       */

        public static java.util.Iterator<org.semanticwb.model.SelectClass> listSelectClasses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectClass>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SelectClass for all models
       * @return Iterator of org.semanticwb.model.SelectClass
       */

        public static java.util.Iterator<org.semanticwb.model.SelectClass> listSelectClasses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectClass>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       * @return A org.semanticwb.model.SelectClass
       */
        public static org.semanticwb.model.SelectClass getSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectClass)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       * @return A org.semanticwb.model.SelectClass
       */
        public static org.semanticwb.model.SelectClass createSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectClass)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       */
        public static void removeSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SelectClass
       * @param id Identifier for org.semanticwb.model.SelectClass
       * @param model Model of the org.semanticwb.model.SelectClass
       * @return true if the org.semanticwb.model.SelectClass exists, false otherwise
       */

        public static boolean hasSelectClass(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectClass(id, model)!=null);
        }
    }

    public static SelectClassBase.ClassMgr getSelectClassClassMgr()
    {
        return new SelectClassBase.ClassMgr();
    }

   /**
   * Constructs a SelectClassBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectClass
   */
    public SelectClassBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
