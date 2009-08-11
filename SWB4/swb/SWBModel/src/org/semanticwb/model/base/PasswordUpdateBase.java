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


public class PasswordUpdateBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swb_passUpdVerify=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#passUpdVerify");
    public static final org.semanticwb.platform.SemanticClass swbxf_PasswordUpdate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PasswordUpdate");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PasswordUpdate");

    public PasswordUpdateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.model.PasswordUpdate> listPasswordUpdates(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PasswordUpdate>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.PasswordUpdate> listPasswordUpdates()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PasswordUpdate>(it, true);
    }

    public static org.semanticwb.model.PasswordUpdate getPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PasswordUpdate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.model.PasswordUpdate createPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.PasswordUpdate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removePasswordUpdate(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasPasswordUpdate(String id, org.semanticwb.model.SWBModel model)
    {
        return (getPasswordUpdate(id, model)!=null);
    }

    public String getVerifyText()
    {
        return getSemanticObject().getProperty(swb_passUpdVerify);
    }

    public void setVerifyText(String passUpdVerify)
    {
        getSemanticObject().setProperty(swb_passUpdVerify, passUpdVerify);
    }

    public String getVerifyText(String lang)
    {
        return getSemanticObject().getProperty(swb_passUpdVerify, null, lang);
    }

    public String getDisplayVerifyText(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_passUpdVerify, lang);
    }

    public void setVerifyText(String passUpdVerify, String lang)
    {
        getSemanticObject().setProperty(swb_passUpdVerify, passUpdVerify, lang);
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
