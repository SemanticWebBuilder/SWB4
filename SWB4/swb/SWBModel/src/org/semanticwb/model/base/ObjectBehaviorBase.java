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


public class ObjectBehaviorBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.RoleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Trashable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Iconable,org.semanticwb.model.Viewable,org.semanticwb.model.Indexable,org.semanticwb.model.Filterable,org.semanticwb.model.Referensable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Hiddenable
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_interface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#interface");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorURL");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorPropertyFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorPropertyFilter");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorRefreshOnShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorRefreshOnShow");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorParams=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorParams");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");

    public ObjectBehaviorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior>(it, true);
    }

    public static org.semanticwb.model.ObjectBehavior getObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectBehavior)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.ObjectBehavior createObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectBehavior)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        return (getObjectBehavior(id, model)!=null);
    }

    public void setInterface(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(swbxf_interface, semanticobject);
    }

    public void removeInterface()
    {
        getSemanticObject().removeProperty(swbxf_interface);
    }

   public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByInterface(org.semanticwb.model.ObjectBehavior objectbehavior,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbxf_interface, objectbehavior.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByInterface(org.semanticwb.model.ObjectBehavior objectbehavior)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(objectbehavior.getSemanticObject().getModel().listSubjects(swbxf_interface,objectbehavior.getSemanticObject()));
       return it;
   }

    public org.semanticwb.platform.SemanticObject getInterface()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_interface);
         return ret;
    }

    public String getBehaviorURL()
    {
        return getSemanticObject().getProperty(swbxf_behaviorURL);
    }

    public void setBehaviorURL(String behaviorURL)
    {
        getSemanticObject().setProperty(swbxf_behaviorURL, behaviorURL);
    }

    public String getPropertyFilter()
    {
        return getSemanticObject().getProperty(swbxf_behaviorPropertyFilter);
    }

    public void setPropertyFilter(String behaviorPropertyFilter)
    {
        getSemanticObject().setProperty(swbxf_behaviorPropertyFilter, behaviorPropertyFilter);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(swb_iconClass, iconClass);
    }

    public boolean isRefreshOnShow()
    {
        return getSemanticObject().getBooleanProperty(swbxf_behaviorRefreshOnShow);
    }

    public void setRefreshOnShow(boolean behaviorRefreshOnShow)
    {
        getSemanticObject().setBooleanProperty(swbxf_behaviorRefreshOnShow, behaviorRefreshOnShow);
    }

    public String getBehaviorParams()
    {
        return getSemanticObject().getProperty(swbxf_behaviorParams);
    }

    public void setBehaviorParams(String behaviorParams)
    {
        getSemanticObject().setProperty(swbxf_behaviorParams, behaviorParams);
    }
}
