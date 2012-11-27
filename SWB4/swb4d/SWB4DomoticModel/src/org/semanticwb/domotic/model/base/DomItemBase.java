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
package org.semanticwb.domotic.model.base;


public abstract class DomItemBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPermission");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasPermission");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomItem");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomItem");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomItem for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomItem
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomItem> listDomItems(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomItem>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomItem for all models
       * @return Iterator of org.semanticwb.domotic.model.DomItem
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomItem> listDomItems()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomItem>(it, true);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomItem
       * @param id Identifier for org.semanticwb.domotic.model.DomItem
       * @param model Model of the org.semanticwb.domotic.model.DomItem
       * @return A org.semanticwb.domotic.model.DomItem
       */
        public static org.semanticwb.domotic.model.DomItem getDomItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomItem)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomItem
       * @param id Identifier for org.semanticwb.domotic.model.DomItem
       * @param model Model of the org.semanticwb.domotic.model.DomItem
       * @return A org.semanticwb.domotic.model.DomItem
       */
        public static org.semanticwb.domotic.model.DomItem createDomItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomItem
       * @param id Identifier for org.semanticwb.domotic.model.DomItem
       * @param model Model of the org.semanticwb.domotic.model.DomItem
       */
        public static void removeDomItem(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomItem
       * @param id Identifier for org.semanticwb.domotic.model.DomItem
       * @param model Model of the org.semanticwb.domotic.model.DomItem
       * @return true if the org.semanticwb.domotic.model.DomItem exists, false otherwise
       */

        public static boolean hasDomItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomItem(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomItem with a determined Permission
       * @param value Permission of the type org.semanticwb.domotic.model.DomPermission
       * @param model Model of the org.semanticwb.domotic.model.DomItem
       * @return Iterator with all the org.semanticwb.domotic.model.DomItem
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomItem> listDomItemByPermission(org.semanticwb.domotic.model.DomPermission value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasPermission, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomItem with a determined Permission
       * @param value Permission of the type org.semanticwb.domotic.model.DomPermission
       * @return Iterator with all the org.semanticwb.domotic.model.DomItem
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomItem> listDomItemByPermission(org.semanticwb.domotic.model.DomPermission value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasPermission,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomItemBase.ClassMgr getDomItemClassMgr()
    {
        return new DomItemBase.ClassMgr();
    }

   /**
   * Constructs a DomItemBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomItem
   */
    public DomItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomPermission
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomPermission
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission> listPermissions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission>(getSemanticObject().listObjectProperties(swb4d_hasPermission));
    }

   /**
   * Gets true if has a Permission
   * @param value org.semanticwb.domotic.model.DomPermission to verify
   * @return true if the org.semanticwb.domotic.model.DomPermission exists, false otherwise
   */
    public boolean hasPermission(org.semanticwb.domotic.model.DomPermission value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasPermission,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Permission
   * @param value org.semanticwb.domotic.model.DomPermission to add
   */

    public void addPermission(org.semanticwb.domotic.model.DomPermission value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasPermission, value.getSemanticObject());
    }
   /**
   * Removes all the Permission
   */

    public void removeAllPermission()
    {
        getSemanticObject().removeProperty(swb4d_hasPermission);
    }
   /**
   * Removes a Permission
   * @param value org.semanticwb.domotic.model.DomPermission to remove
   */

    public void removePermission(org.semanticwb.domotic.model.DomPermission value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasPermission,value.getSemanticObject());
    }

   /**
   * Gets the Permission
   * @return a org.semanticwb.domotic.model.DomPermission
   */
    public org.semanticwb.domotic.model.DomPermission getPermission()
    {
         org.semanticwb.domotic.model.DomPermission ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasPermission);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomPermission)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
