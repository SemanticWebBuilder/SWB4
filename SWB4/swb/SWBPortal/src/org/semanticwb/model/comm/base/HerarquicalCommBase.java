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
 
package org.semanticwb.model.comm.base;


public class HerarquicalCommBase extends org.semanticwb.model.comm.OrganizationComm implements org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Referensable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Hiddenable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Trashable,org.semanticwb.model.Activeable,org.semanticwb.model.Viewable,org.semanticwb.model.Resourceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Filterable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Indexable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_HerarquicalComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#HerarquicalComm");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#HerarquicalComm");

    public HerarquicalCommBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.HerarquicalComm> listHerarquicalComms(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.HerarquicalComm>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.comm.HerarquicalComm> listHerarquicalComms()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.comm.HerarquicalComm>(it, true);
    }

    public static org.semanticwb.model.comm.HerarquicalComm getHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.HerarquicalComm)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.comm.HerarquicalComm createHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.comm.HerarquicalComm)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasHerarquicalComm(String id, org.semanticwb.model.SWBModel model)
    {
        return (getHerarquicalComm(id, model)!=null);
    }
}
