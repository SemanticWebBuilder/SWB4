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
package org.semanticwb.process.schema.base;


public abstract class FileCollectionBase extends org.semanticwb.process.model.DataTypes 
{
    public static final org.semanticwb.platform.SemanticProperty swp_hasFileValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasFileValue");
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryFile");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRepositoryFileRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRepositoryFileRef");
    public static final org.semanticwb.platform.SemanticClass swps_FileCollection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#FileCollection");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/schema#FileCollection");

    public static class ClassMgr
    {
       /**
       * Returns a list of FileCollection for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.schema.FileCollection
       */

        public static java.util.Iterator<org.semanticwb.process.schema.FileCollection> listFileCollections(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.FileCollection>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.schema.FileCollection for all models
       * @return Iterator of org.semanticwb.process.schema.FileCollection
       */

        public static java.util.Iterator<org.semanticwb.process.schema.FileCollection> listFileCollections()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.FileCollection>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.schema.FileCollection
       * @param id Identifier for org.semanticwb.process.schema.FileCollection
       * @param model Model of the org.semanticwb.process.schema.FileCollection
       * @return A org.semanticwb.process.schema.FileCollection
       */
        public static org.semanticwb.process.schema.FileCollection getFileCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.FileCollection)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.schema.FileCollection
       * @param id Identifier for org.semanticwb.process.schema.FileCollection
       * @param model Model of the org.semanticwb.process.schema.FileCollection
       * @return A org.semanticwb.process.schema.FileCollection
       */
        public static org.semanticwb.process.schema.FileCollection createFileCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.schema.FileCollection)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.schema.FileCollection
       * @param id Identifier for org.semanticwb.process.schema.FileCollection
       * @param model Model of the org.semanticwb.process.schema.FileCollection
       */
        public static void removeFileCollection(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.schema.FileCollection
       * @param id Identifier for org.semanticwb.process.schema.FileCollection
       * @param model Model of the org.semanticwb.process.schema.FileCollection
       * @return true if the org.semanticwb.process.schema.FileCollection exists, false otherwise
       */

        public static boolean hasFileCollection(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFileCollection(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.schema.FileCollection with a determined RepositoryFile
       * @param value RepositoryFile of the type org.semanticwb.process.model.RepositoryFile
       * @param model Model of the org.semanticwb.process.schema.FileCollection
       * @return Iterator with all the org.semanticwb.process.schema.FileCollection
       */

        public static java.util.Iterator<org.semanticwb.process.schema.FileCollection> listFileCollectionByRepositoryFile(org.semanticwb.process.model.RepositoryFile value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.FileCollection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRepositoryFileRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.schema.FileCollection with a determined RepositoryFile
       * @param value RepositoryFile of the type org.semanticwb.process.model.RepositoryFile
       * @return Iterator with all the org.semanticwb.process.schema.FileCollection
       */

        public static java.util.Iterator<org.semanticwb.process.schema.FileCollection> listFileCollectionByRepositoryFile(org.semanticwb.process.model.RepositoryFile value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.schema.FileCollection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRepositoryFileRef,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FileCollectionBase.ClassMgr getFileCollectionClassMgr()
    {
        return new FileCollectionBase.ClassMgr();
    }

   /**
   * Constructs a FileCollectionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FileCollection
   */
    public FileCollectionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Iterator<String> listValues()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(swp_hasFileValue);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addValue(String value)
    {
        getSemanticObject().addLiteralProperty(swp_hasFileValue, new org.semanticwb.platform.SemanticLiteral(value));
    }

    public void removeAllValue()
    {
        getSemanticObject().removeProperty(swp_hasFileValue);
    }

    public void removeValue(String value)
    {
        getSemanticObject().removeLiteralProperty(swp_hasFileValue,new org.semanticwb.platform.SemanticLiteral(value));
    }
   /**
   * Gets all the org.semanticwb.process.model.RepositoryFile
   * @return A GenericIterator with all the org.semanticwb.process.model.RepositoryFile
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile> listRepositoryFiles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RepositoryFile>(getSemanticObject().listObjectProperties(swp_hasRepositoryFileRef));
    }

   /**
   * Gets true if has a RepositoryFile
   * @param value org.semanticwb.process.model.RepositoryFile to verify
   * @return true if the org.semanticwb.process.model.RepositoryFile exists, false otherwise
   */
    public boolean hasRepositoryFile(org.semanticwb.process.model.RepositoryFile value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRepositoryFileRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a RepositoryFile
   * @param value org.semanticwb.process.model.RepositoryFile to add
   */

    public void addRepositoryFile(org.semanticwb.process.model.RepositoryFile value)
    {
        getSemanticObject().addObjectProperty(swp_hasRepositoryFileRef, value.getSemanticObject());
    }
   /**
   * Removes all the RepositoryFile
   */

    public void removeAllRepositoryFile()
    {
        getSemanticObject().removeProperty(swp_hasRepositoryFileRef);
    }
   /**
   * Removes a RepositoryFile
   * @param value org.semanticwb.process.model.RepositoryFile to remove
   */

    public void removeRepositoryFile(org.semanticwb.process.model.RepositoryFile value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRepositoryFileRef,value.getSemanticObject());
    }

   /**
   * Gets the RepositoryFile
   * @return a org.semanticwb.process.model.RepositoryFile
   */
    public org.semanticwb.process.model.RepositoryFile getRepositoryFile()
    {
         org.semanticwb.process.model.RepositoryFile ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRepositoryFileRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.RepositoryFile)obj.createGenericInstance();
         }
         return ret;
    }
}
