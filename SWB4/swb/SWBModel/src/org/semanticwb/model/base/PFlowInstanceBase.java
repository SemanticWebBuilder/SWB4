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


public class PFlowInstanceBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.PFlowable
{
    public static final org.semanticwb.platform.SemanticProperty swb_pfiTime=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiTime");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_pfinstResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfinstResource");
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiPFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiPFlow");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStatus=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStatus");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_pfiStep=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#pfiStep");
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");

    public PFlowInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(it, true);
    }

    public static org.semanticwb.model.PFlowInstance createPFlowInstance(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.model.PFlowInstance.createPFlowInstance(String.valueOf(id), model);
    }

    public static org.semanticwb.model.PFlowInstance getPFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.PFlowInstance createPFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PFlowInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPFlowInstance(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPFlowInstance(id, model)!=null);
    }

    public java.util.Date getTime()
    {
        return getSemanticObject().getDateProperty(swb_pfiTime);
    }

    public void setTime(java.util.Date pfiTime)
    {
        getSemanticObject().setDateProperty(swb_pfiTime, pfiTime);
    }

    public void setPfinstResource(org.semanticwb.model.Resource resource)
    {
        getSemanticObject().setObjectProperty(swb_pfinstResource, resource.getSemanticObject());
    }

    public void removePfinstResource()
    {
        getSemanticObject().removeProperty(swb_pfinstResource);
    }

    public org.semanticwb.model.Resource getPfinstResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfinstResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    public void setPflow(org.semanticwb.model.PFlow pflow)
    {
        getSemanticObject().setObjectProperty(swb_pfiPFlow, pflow.getSemanticObject());
    }

    public void removePflow()
    {
        getSemanticObject().removeProperty(swb_pfiPFlow);
    }

    public org.semanticwb.model.PFlow getPflow()
    {
         org.semanticwb.model.PFlow ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_pfiPFlow);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlow)obj.createGenericInstance();
         }
         return ret;
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(swb_pfiStatus);
    }

    public void setStatus(int pfiStatus)
    {
        getSemanticObject().setIntProperty(swb_pfiStatus, pfiStatus);
    }

    public int getVersion()
    {
        return getSemanticObject().getIntProperty(swb_pfiVersion);
    }

    public void setVersion(int pfiVersion)
    {
        getSemanticObject().setIntProperty(swb_pfiVersion, pfiVersion);
    }

    public String getStep()
    {
        return getSemanticObject().getProperty(swb_pfiStep);
    }

    public void setStep(String pfiStep)
    {
        getSemanticObject().setProperty(swb_pfiStep, pfiStep);
    }
}
