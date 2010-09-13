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
    * Objeto utiizado para definir un grupo de propiedades dentro de la administración de SWB (fieldset).
    */
public abstract class PropertyGroupBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
   
   /** Objeto utiizado para definir un grupo de propiedades dentro de la administración de SWB (fieldset). */
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of PropertyGroup for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.PropertyGroup
        */

        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.PropertyGroup for all models
       * @return Iterator of org.semanticwb.model.PropertyGroup
       */

        public static java.util.Iterator<org.semanticwb.model.PropertyGroup> listPropertyGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PropertyGroup>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       * @return A org.semanticwb.model.PropertyGroup
       */
        public static org.semanticwb.model.PropertyGroup getPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       * @return A org.semanticwb.model.PropertyGroup
       */
        public static org.semanticwb.model.PropertyGroup createPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PropertyGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       */
        public static void removePropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.PropertyGroup
       * @param id Identifier for org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.PropertyGroup
       * @return true if the org.semanticwb.model.PropertyGroup exists, false otherwise
       */

        public static boolean hasPropertyGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPropertyGroup(id, model)!=null);
        }
    }

   /**
    * Constructs a PropertyGroupBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the PropertyGroup
    */
    public PropertyGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the Index property.
 * 
 * @return int with the Index
 */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
 * Sets the Index property.
 * 
 * @param value long with the Index
 */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }
}
