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
package org.semanticwb.portal.resources.sem.documents.base;


// TODO: Auto-generated Javadoc
/**
 * The Class DocumentsBase.
 */
public abstract class DocumentsBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.model.Sortable
{
    
    /** The Constant swb_index. */
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
    
    /** The Constant docs_Document. */
    public static final org.semanticwb.platform.SemanticClass docs_Document=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Document");
    
    /** The Constant docs_hasDocument. */
    public static final org.semanticwb.platform.SemanticProperty docs_hasDocument=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://semanticwb.org/SWBDocuments#hasDocument");
    
    /** The Constant docs_Documents. */
    public static final org.semanticwb.platform.SemanticClass docs_Documents=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Documents");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://semanticwb.org/SWBDocuments#Documents");

    /**
     * Instantiates a new documents base.
     */
    public DocumentsBase()
    {
    }

    /**
     * Instantiates a new documents base.
     * 
     * @param base the base
     */
    public DocumentsBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Gets the index.
     * 
     * @return the index
     */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    /**
     * Sets the index.
     * 
     * @param value the new index
     */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

    /**
     * List documents.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document> listDocuments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.documents.Document>(getSemanticObject().listObjectProperties(docs_hasDocument));
    }

    /**
     * Checks for document.
     * 
     * @param value the value
     * @return true, if successful
     */
    public boolean hasDocument(org.semanticwb.portal.resources.sem.documents.Document value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(docs_hasDocument,value.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the document.
     * 
     * @param value the value
     */
    public void addDocument(org.semanticwb.portal.resources.sem.documents.Document value)
    {
        getSemanticObject().addObjectProperty(docs_hasDocument, value.getSemanticObject());
    }

    /**
     * Removes the all document.
     */
    public void removeAllDocument()
    {
        getSemanticObject().removeProperty(docs_hasDocument);
    }

    /**
     * Removes the document.
     * 
     * @param value the value
     */
    public void removeDocument(org.semanticwb.portal.resources.sem.documents.Document value)
    {
        getSemanticObject().removeObjectProperty(docs_hasDocument,value.getSemanticObject());
    }

    /**
     * Gets the document.
     * 
     * @return the document
     */
    public org.semanticwb.portal.resources.sem.documents.Document getDocument()
    {
         org.semanticwb.portal.resources.sem.documents.Document ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(docs_hasDocument);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.documents.Document)obj.createGenericInstance();
         }
         return ret;
    }
}
