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
 
package org.semanticwb.portal.resources.sem.base;


public class HTMLContentBase extends org.semanticwb.portal.resources.sem.Content implements org.semanticwb.model.Versionable,org.semanticwb.model.ResourceVersionable
{
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    public static final org.semanticwb.platform.SemanticClass swbres_HTMLContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    public HTMLContentBase()
    {
    }

    public HTMLContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(swb_actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(swb_lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }
}
