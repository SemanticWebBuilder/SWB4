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
package org.semanticwb.portal.community.base;


public abstract class DocumentResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_DocumentElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDocument");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DocumentResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentResource");

    public DocumentResourceBase()
    {
    }

    public DocumentResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> listDocuments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement>(getSemanticObject().listObjectProperties(swbcomm_hasDocument));
    }

    public boolean hasDocument(org.semanticwb.portal.community.DocumentElement documentelement)
    {
        boolean ret=false;
        if(documentelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasDocument,documentelement.getSemanticObject());
        }
        return ret;
    }

    public void addDocument(org.semanticwb.portal.community.DocumentElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasDocument, value.getSemanticObject());
    }

    public void removeAllDocument()
    {
        getSemanticObject().removeProperty(swbcomm_hasDocument);
    }

    public void removeDocument(org.semanticwb.portal.community.DocumentElement documentelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasDocument,documentelement.getSemanticObject());
    }

    public org.semanticwb.portal.community.DocumentElement getDocument()
    {
         org.semanticwb.portal.community.DocumentElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasDocument);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.DocumentElement)obj.createGenericInstance();
         }
         return ret;
    }
}
