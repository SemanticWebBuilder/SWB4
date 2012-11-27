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


public abstract class DomPermissionBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAccessLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAccessLevel");
    public static final org.semanticwb.platform.SemanticProperty swb4d_accessLevel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#accessLevel");
   /**
   * Un usuario es una persona que tiene relación con el portal a través de un método de acceso.
   */
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb4d_user=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#user");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomPermission=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPermission");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomPermission");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomPermission for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomPermission
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomPermission for all models
       * @return Iterator of org.semanticwb.domotic.model.DomPermission
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission>(it, true);
        }

        public static org.semanticwb.domotic.model.DomPermission createDomPermission(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomPermission.ClassMgr.createDomPermission(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomPermission
       * @param id Identifier for org.semanticwb.domotic.model.DomPermission
       * @param model Model of the org.semanticwb.domotic.model.DomPermission
       * @return A org.semanticwb.domotic.model.DomPermission
       */
        public static org.semanticwb.domotic.model.DomPermission getDomPermission(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomPermission)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomPermission
       * @param id Identifier for org.semanticwb.domotic.model.DomPermission
       * @param model Model of the org.semanticwb.domotic.model.DomPermission
       * @return A org.semanticwb.domotic.model.DomPermission
       */
        public static org.semanticwb.domotic.model.DomPermission createDomPermission(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomPermission)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomPermission
       * @param id Identifier for org.semanticwb.domotic.model.DomPermission
       * @param model Model of the org.semanticwb.domotic.model.DomPermission
       */
        public static void removeDomPermission(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomPermission
       * @param id Identifier for org.semanticwb.domotic.model.DomPermission
       * @param model Model of the org.semanticwb.domotic.model.DomPermission
       * @return true if the org.semanticwb.domotic.model.DomPermission exists, false otherwise
       */

        public static boolean hasDomPermission(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomPermission(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPermission with a determined AccessLevel
       * @param value AccessLevel of the type org.semanticwb.domotic.model.DomAccessLevel
       * @param model Model of the org.semanticwb.domotic.model.DomPermission
       * @return Iterator with all the org.semanticwb.domotic.model.DomPermission
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissionByAccessLevel(org.semanticwb.domotic.model.DomAccessLevel value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_accessLevel, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPermission with a determined AccessLevel
       * @param value AccessLevel of the type org.semanticwb.domotic.model.DomAccessLevel
       * @return Iterator with all the org.semanticwb.domotic.model.DomPermission
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissionByAccessLevel(org.semanticwb.domotic.model.DomAccessLevel value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_accessLevel,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPermission with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.domotic.model.DomPermission
       * @return Iterator with all the org.semanticwb.domotic.model.DomPermission
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissionByUser(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_user, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomPermission with a determined User
       * @param value User of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.domotic.model.DomPermission
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomPermission> listDomPermissionByUser(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomPermission> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_user,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomPermissionBase.ClassMgr getDomPermissionClassMgr()
    {
        return new DomPermissionBase.ClassMgr();
    }

   /**
   * Constructs a DomPermissionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomPermission
   */
    public DomPermissionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property AccessLevel
   * @param value AccessLevel to set
   */

    public void setAccessLevel(org.semanticwb.domotic.model.DomAccessLevel value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_accessLevel, value.getSemanticObject());
        }else
        {
            removeAccessLevel();
        }
    }
   /**
   * Remove the value for AccessLevel property
   */

    public void removeAccessLevel()
    {
        getSemanticObject().removeProperty(swb4d_accessLevel);
    }

   /**
   * Gets the AccessLevel
   * @return a org.semanticwb.domotic.model.DomAccessLevel
   */
    public org.semanticwb.domotic.model.DomAccessLevel getAccessLevel()
    {
         org.semanticwb.domotic.model.DomAccessLevel ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_accessLevel);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomAccessLevel)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property User
   * @param value User to set
   */

    public void setUser(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_user, value.getSemanticObject());
        }else
        {
            removeUser();
        }
    }
   /**
   * Remove the value for User property
   */

    public void removeUser()
    {
        getSemanticObject().removeProperty(swb4d_user);
    }

   /**
   * Gets the User
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getUser()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_user);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
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
