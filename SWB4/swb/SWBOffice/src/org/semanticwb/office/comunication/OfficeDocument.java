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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.office.comunication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.UUID;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.AdminFilter;
import org.semanticwb.model.CalendarRef;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.Language;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.Rule;
import org.semanticwb.model.RuleRef;
import org.semanticwb.model.SWBContext;

import org.semanticwb.model.UserGroup;
import org.semanticwb.model.UserGroupRef;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.office.interfaces.CalendarInfo;
import org.semanticwb.office.interfaces.CategoryInfo;
import org.semanticwb.office.interfaces.ContentInfo;
import org.semanticwb.office.interfaces.IOfficeDocument;
import org.semanticwb.office.interfaces.LanguageInfo;
import org.semanticwb.office.interfaces.PFlow;

import org.semanticwb.office.interfaces.PageInfo;
import org.semanticwb.office.interfaces.PropertyInfo;
import org.semanticwb.office.interfaces.ResourceInfo;
import org.semanticwb.office.interfaces.ElementInfo;
import org.semanticwb.office.interfaces.SiteInfo;
import org.semanticwb.office.interfaces.Value;
import org.semanticwb.office.interfaces.VersionInfo;
import org.semanticwb.office.interfaces.WebPageInfo;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.OfficeManager;
import org.semanticwb.repository.RepositoryManagerLoader;
import org.semanticwb.repository.SWBRepositoryManager;
import org.semanticwb.repository.WorkspaceNotFoudException;
import org.semanticwb.repository.office.OfficeContent;
import org.semanticwb.resource.office.sem.ExcelResource;
import org.semanticwb.resource.office.sem.OfficeResource;
import org.semanticwb.resource.office.sem.PPTResource;
import org.semanticwb.resource.office.sem.WordResource;
import org.semanticwb.xmlrpc.Part;
import org.semanticwb.xmlrpc.XmlRpcObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class OfficeDocument extends XmlRpcObject implements IOfficeDocument
{
    public static final String FILE_HTML = "filehtmlCache";
    private static final DecimalFormat format = new DecimalFormat("#.0");
    private SWBRepositoryManager manager;
    private OfficeApplication officeApplication = new OfficeApplication();
    private static final String MIGRATE_WBRESOURCESCONTENT = "com.infotec.wb.resources.Content";
    private static final String MIGRATE_WBRESOURCESEXCELCONTENT = "com.infotec.wb.resources.ExcelContent";
    private static final String MIGRATE_WBRESOURCESPPTCONTENT = "com.infotec.wb.resources.PPTContent";
    private static final String categoryBydefault = "Contenidos migrados";
    private static final String descriptionByDefault = "Contenidos migrados de versión 3.2";
    private final SemanticClass cm_content = OfficeContent.swboffice_OfficeContent;
    private static final SemanticProperty prop_content = OfficeResource.swboffice_content;
    private static final SemanticClass swb_office = org.semanticwb.repository.office.OfficeDocument.swboffice_OfficeDocument;
    private static final SemanticProperty PROP_JCR_DATA = org.semanticwb.repository.office.OfficeDocument.jcr_data;
    private static final String JCR_DATA = PROP_JCR_DATA.getPrefix() + ":" + PROP_JCR_DATA.getName();
    private static final SemanticProperty PROP_JCR_LASTMODIFIED = org.semanticwb.repository.office.OfficeDocument.jcr_lastModified;
    private static final String JCR_LASTMODIFIED = PROP_JCR_LASTMODIFIED.getPrefix() + ":" + PROP_JCR_LASTMODIFIED.getName();
    private static final SemanticProperty PROP_LASTMODIFIED = org.semanticwb.repository.office.OfficeContent.swboffice_lastModified;
    private static final String LASTMODIFIED = PROP_LASTMODIFIED.getPrefix() + ":" + PROP_LASTMODIFIED.getName();
    public static final String JCR_CONTENT = "jcr:content";
    private static final String JCR_FROZEN_NODE = "jcr:frozenNode";
    private static final String WORD_RESOURCE_TYPE = "WordContent";
    private static final String WORD_RESOURCE_DESCRIPTION = "WordContent";
    private static final String WORD_RESOURCE_TITLE = WORD_RESOURCE_DESCRIPTION;
    private static final String PPT_RESOURCE_TYPE = "PPTContent";
    private static final String PPT_RESOURCE_DESCRIPTION = "PPTContent";
    private static final String PPT_RESOURCE_TITLE = PPT_RESOURCE_DESCRIPTION;
    private static final String EXCEL_RESOURCE_TYPE = "ExcelContent";
    private static final String EXCEL_RESOURCE_DESCRIPTION = "ExcelContent";
    private static final String EXCEL_RESOURCE_TITLE = EXCEL_RESOURCE_DESCRIPTION;
    private static final String CONTENT_NOT_FOUND = "El contenido no se encontró en el repositorio.";
    private static Logger log = SWBUtils.getLogger(OfficeDocument.class);
    private static final String DEFAULT_MIME_TYPE = "application/octet-stream";
    private static final RepositoryManagerLoader loader = RepositoryManagerLoader.getInstance();

    public OfficeDocument()
    {
        try
        {
            manager = new SWBRepositoryManager();
        }
        catch (Exception ex)
        {
            log.error(ex);
        }
    }

    public OfficeDocument(String user, String password)
    {
        this();
        this.user = user;
        this.password = password;
    }

    public static String[] getOfficeTypes()
    {
        String[] getOfficeTypes = new String[3];
        getOfficeTypes[0] = WORD_RESOURCE_TYPE;
        getOfficeTypes[1] = EXCEL_RESOURCE_TYPE;
        getOfficeTypes[2] = PPT_RESOURCE_TYPE;
        return getOfficeTypes;
    }

    public Resource migrateResource(String webworkpath, File workpath, String xml, String className, String siteid, String webpageId, String resourceid, String version, String title, String description, String file) throws Exception
    {
        if (!workpath.exists())
        {
            throw new Exception("The workpath " + workpath.getAbsolutePath() + " does not exist");
        }
        if (!workpath.isDirectory())
        {
            throw new Exception("The workpath " + workpath.getAbsolutePath() + " is not a directory");
        }
        if (className.equals(MIGRATE_WBRESOURCESEXCELCONTENT))
        {
            PropertyInfo[] viewProperties = new PropertyInfo[0];
            String[] viewValues = new String[0];
            return migrateExcelResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, viewProperties, viewValues, file);
        }
        else if (className.equals(MIGRATE_WBRESOURCESPPTCONTENT))
        {
            PropertyInfo[] viewProperties = getViewPropertiesPPT(xml);
            String[] viewValues = getStringViewPropertiesPPT(xml);
            return migratePPTResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, viewProperties, viewValues, file);
        }
        else if (className.equals(MIGRATE_WBRESOURCESCONTENT))
        {
            PropertyInfo[] viewProperties = getViewPropertiesWord(xml);
            String[] viewValues = getStringViewPropertiesWord(xml);
            return migrateWordResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, viewProperties, viewValues, file);
        }
        else
        {
            throw new Exception("The resource is not a office resource");
        }
    }

    private Resource migrateWordResource(String webworkpath, File workpath, String siteid, String webpageId, String resourceid, String version, String title, String description, PropertyInfo[] viewProperties, String[] viewValues, String file) throws Exception
    {
        file = file.replaceAll(".html", ".doc");
        return migrateResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, "WORD", viewProperties, viewValues, file);
    }

    private Resource migrateExcelResource(String webworkpath, File workpath, String siteid, String webpageId, String resourceid, String version, String title, String description, PropertyInfo[] viewProperties, String[] viewValues, String file) throws Exception
    {
        file = file.replaceAll(".html", ".xls");
        return migrateResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, "EXCEL", viewProperties, viewValues, file);
    }

    private Resource migratePPTResource(String webworkpath, File workpath, String siteid, String webpageId, String resourceid, String version, String title, String description, PropertyInfo[] viewProperties, String[] viewValues, String file) throws Exception
    {
        file = file.replaceAll(".html", ".ppt");
        return migrateResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, "PPT", viewProperties, viewValues, file);
    }

    public boolean isOfficeDocument(String className, String workpath, String contentid, String file, String version)
    {
        if (className.equals(MIGRATE_WBRESOURCESEXCELCONTENT))
        {
            return true;
        }
        if (className.equals(MIGRATE_WBRESOURCESPPTCONTENT))
        {
            return true;
        }
        if (className.equals(MIGRATE_WBRESOURCESCONTENT))
        {
            File ofile = new File(workpath + "/" + version + "/" + file);
            if (ofile.exists())
            {
                int pos = file.lastIndexOf(".");
                if (pos != -1)
                {
                    String wordfile = file.substring(0, pos) + ".doc";
                    File oword = new File(workpath + "/" + version + "/" + wordfile);
                    File xmlfile = new File(workpath + "/" + version + "/" + "/filelist.xml");
                    if (oword.exists() && xmlfile.exists())
                    {
                        return true;
                    }
                }
            }
            return false;
        }
        else
        {
            return false;
        }

    }

    private void addValuesFromOldVersion(String name, Document doc, SemanticProperty prop, ArrayList<String> values)
    {
        NodeList nodes = doc.getElementsByTagName(name);
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Element element = (Element) nodes.item(i);
            if (element.getFirstChild() != null && element.getFirstChild().getNodeValue() != null)
            {
                String value = element.getFirstChild().getNodeValue();
                if (value != null)
                {
                    //Agregado Jorge
                    if(name.equals("pages") && value.equals("1")) values.add("true");
                    else values.add(value);
                }
            }
        }
    }

    private void addPropertyInfoFromOldVersion(String name, Document doc, SemanticProperty prop, ArrayList<PropertyInfo> props)
    {
        NodeList nodes = doc.getElementsByTagName(name);
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Element element = (Element) nodes.item(i);
            if (element.getFirstChild() != null && element.getFirstChild().getNodeValue() != null)
            {
                String value = element.getFirstChild().getNodeValue();
                if (value != null)
                {
                    PropertyInfo info = new PropertyInfo();
                    if (prop.isString())
                    {
                        info.type = "String";
                    }
                    else if (prop.isBoolean())
                    {
                        info.type = "Boolean";
                    }
                    else if (prop.isInt())
                    {
                        info.type = "Integer";
                    }
                    info.isRequired = prop.isRequired();
                    /*info.values = new Value[1];
                    info.values[0] = new Value();
                    info.values[0].key = value;
                    info.values[1].title = prop.getDisplayName();*/
                    info.id = prop.getPrefix() + ":" + prop.getName();
                }
            }
        }
    }

    private PropertyInfo[] getViewPropertiesWord(String xml)
    {
        ArrayList<PropertyInfo> props = new ArrayList<PropertyInfo>();
        Document doc = SWBUtils.XML.xmlToDom(xml);
        addPropertyInfoFromOldVersion("position", doc, WordResource.swboffice_position, props);
        addPropertyInfoFromOldVersion("txtant", doc, WordResource.swboffice_txtant, props);
        addPropertyInfoFromOldVersion("txtsig", doc, WordResource.swboffice_txtsig, props);
        addPropertyInfoFromOldVersion("tfont", doc, WordResource.swboffice_tfont, props);
        addPropertyInfoFromOldVersion("npages", doc, WordResource.swboffice_npages, props);
        // texto predefinido
        addPropertyInfoFromOldVersion("tpred", doc, WordResource.swboffice_tpred, props);
        addPropertyInfoFromOldVersion("pages", doc, WordResource.swboffice_pages, props);
        return props.toArray(new PropertyInfo[props.size()]);
    }

    private String[] getStringViewPropertiesWord(String xml)
    {
        ArrayList<String> props = new ArrayList<String>();
        Document doc = SWBUtils.XML.xmlToDom(xml);
        addValuesFromOldVersion("position", doc, WordResource.swboffice_position, props);
        addValuesFromOldVersion("txtant", doc, WordResource.swboffice_txtant, props);
        addValuesFromOldVersion("txtsig", doc, WordResource.swboffice_txtsig, props);
        addValuesFromOldVersion("tfont", doc, WordResource.swboffice_tfont, props);
        addValuesFromOldVersion("npages", doc, WordResource.swboffice_npages, props);
        // texto predefinido
        addValuesFromOldVersion("tpred", doc, WordResource.swboffice_tpred, props);
        addValuesFromOldVersion("pages", doc, WordResource.swboffice_pages, props);
        return props.toArray(new String[props.size()]);
    }

    private String[] getStringViewPropertiesPPT(String xml)
    {
        ArrayList<String> props = new ArrayList<String>();
        Document doc = SWBUtils.XML.xmlToDom(xml);
        addValuesFromOldVersion("link", doc, PPTResource.swboffice_showDownload, props);
        return props.toArray(new String[props.size()]);
    }

    private PropertyInfo[] getViewPropertiesPPT(String xml)
    {
        ArrayList<PropertyInfo> props = new ArrayList<PropertyInfo>();
        Document doc = SWBUtils.XML.xmlToDom(xml);
        addPropertyInfoFromOldVersion("link", doc, PPTResource.swboffice_showDownload, props);
        return props.toArray(new PropertyInfo[props.size()]);
    }

    /**
     * 
     * @param siteid
     * @param webpageId
     * @param resourceid
     * @param version
     * @param title
     * @param description
     * @param repositoryName
     * @param categoryID
     * @param type  word, excel o ppt
     * @param viewProperties
     * @param viewValues
     * @param user
     * @param password     
     * @param file
     * @return
     * @throws Exception
     */
    private Resource migrateResource(String webworkpath, File workpath, String siteid, String webpageId, String resourceid, String version, String title, String description, String type, PropertyInfo[] viewProperties, String[] viewValues, String file) throws Exception
    {
        
        String repositoryName = siteid + "_rep@" + manager.getName(); // se almacena en el repositorio del sitio
        
        officeApplication.setUser(this.user);
        officeApplication.setPassword(this.password);
        String categoryId = officeApplication.createCategory(repositoryName, categoryBydefault, descriptionByDefault);
        return migrateResource(webworkpath, workpath, siteid, webpageId, resourceid, version, title, description, repositoryName, categoryId, type, viewProperties, viewValues, file);
    }

    private Resource migrateResource(String webworkpath, File workpath, String siteid, String webpageId, String resourceid, String version, String title, String description, String repositoryName, String categoryID, String type, PropertyInfo[] viewProperties, String[] viewValues, String file) throws Exception
    {
        log.trace("Migrando documento de office con id :"+resourceid);
        String nodeType = cm_content.getPrefix() + ":" + cm_content.getName();
        // guarda en repositorio y publica
        WebSite site = WebSite.ClassMgr.getWebSite(siteid);
        if (site == null)
        {
            throw new Exception("The site " + siteid + " was not found");
        }
        WebPage page = site.getWebPage(webpageId);
        if (page == null)
        {
            throw new Exception("The page " + webpageId + " was not found");
        }
        PropertyInfo[] contentProperties = new PropertyInfo[0];
        String[] contentValues = new String[0];
        String contentid = migrateResourceToRepository(webworkpath, siteid, workpath, resourceid, version, title, description, repositoryName, categoryID, type, nodeType, file, contentProperties, contentValues);
        WebPageInfo info = new WebPageInfo();
        info.id = page.getId();
        info.active = page.isActive();
        info.title = page.getTitle();
        info.siteID = site.getId();
        info.description = page.getDescription();
        info.url = page.getUrl();
        int childs = 0;
        info.childs = childs;
        // mantiene el id original
        // ciudado el contenido original ya no se puede publicar igual, pero se agrega funcionlidad para modalidad restauración
        log.trace("Publicando documento con id :"+contentid);
        ResourceInfo res = this.publishToResourceContent(resourceid, repositoryName, contentid, "*", title, description, info, viewProperties, viewValues);
        return Resource.ClassMgr.getResource(res.id, site);
    }

    private String migrateResourceToRepository(String webworkpath, String siteid, File workpath, String resourceid, String version, String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values) throws Exception
    {
        String sourcepath = webworkpath;
        String targetpath = "";
        File fileZip = zipResourceDirectory(resourceid, workpath.getAbsolutePath(), version, sourcepath, targetpath);
        FileInputStream in = new FileInputStream(fileZip);
        String contentid = save(title, description, repositoryName, categoryID, type, nodeType, file, properties, values, in, fileZip.getName());
        if (fileZip.exists())
        {
            fileZip.delete();
        }
        return contentid;
    }

    public static File zipResourceDirectory(String id, String workpath, String version, String sourcePath, String targetPath) throws IOException
    {
        File directory = new File(workpath + "/" + version);
        String pathZip = workpath + "/" + id + ".zip";
        File ozipfile = new File(pathZip);
        if (ozipfile.exists())
        {
            ozipfile.delete();
        }
        try
        {
            FileOutputStream out = new FileOutputStream(pathZip);
            ZipOutputStream sos = new ZipOutputStream(out);
            zipDir(directory.getAbsolutePath(), sos, sourcePath, targetPath);
            sos.close();
            File filezip = new File(pathZip);
            return filezip;
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static void zipDir(String dir2zip, ZipOutputStream zos, String sourcePath, String targetPath)
    {
        try
        {
            //create a new File object based on the directory we   have to zip
            File zipDir = new File(dir2zip);
            //get a listing of the directory content
            String[] dirList = zipDir.list();
            byte[] readBuffer = new byte[2156];
            int bytesIn = 0;
            //loop through dirList, and zip the files
            for (int i = 0; i < dirList.length; i++)
            {
                File f = new File(zipDir, dirList[i]);
                if (f.isDirectory())
                {
                    //if the File object is a directory, call this
                    //function again to add its content recursively
                    String filePath = f.getPath();
                    zipDir(filePath, zos, sourcePath, targetPath);
                    //loop again
                    continue;
                }
                InputStream fis = null;
                if (f.getName().endsWith(".htm") || f.getName().endsWith(".html") || f.getName().endsWith(".html.orig") || f.getName().endsWith(".htm.orig"))
                {
                    FileInputStream source = new FileInputStream(f);
                    String content = SWBUtils.IO.readInputStream(source);
                    content = content.replaceAll(sourcePath, targetPath);
                    fis = new java.io.ByteArrayInputStream(content.getBytes());
                }
                else
                {
                    fis = new FileInputStream(f);
                }
                //if we reached here, the File object f was not  a directory
                //create a FileInputStream on top of f                

                //create a new zip entry
                ZipEntry anEntry = new ZipEntry(f.getName());
                //place the zip entry in the ZipOutputStream object
                zos.putNextEntry(anEntry);
                //now write the content of the file to the ZipOutputStream
                while ((bytesIn = fis.read(readBuffer)) != -1)
                {
                    zos.write(readBuffer, 0, bytesIn);
                }
                //close the Stream
                fis.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String save(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values, InputStream in, String filename) throws Exception
    {
        String encode=System.getProperty("file.encoding","utf-8");        
        if (encode == null || encode.equals(""))
        {
            encode = "utf-8";
        }
        file = java.net.URLDecoder.decode(file, encode);
        Session session = null;
        Node categoryNode = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            categoryNode = session.getNodeByUUID(categoryID);
            try
            {
                String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
                String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
                Node contentNode = categoryNode.addNode(nodeType, nodeType);
                contentNode.setProperty(cm_title, title);
                String cm_type = loader.getOfficeManager(repositoryName).getPropertyType();
                String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
                contentNode.setProperty(cm_type, type);
                contentNode.setProperty(cm_description, description);
                Calendar lastModified = Calendar.getInstance();
                lastModified.setTimeInMillis(System.currentTimeMillis());
                contentNode.setProperty(LASTMODIFIED, lastModified);
                if (properties != null)
                {
                    int i = 0;
                    for (PropertyInfo prop : properties)
                    {
                        String value = values[i];
                        contentNode.setProperty(prop.id, value);
                        i++;
                    }
                }

                String mimeType = DEFAULT_MIME_TYPE;
                if (config != null && config.getServletContext() != null)
                {
                    mimeType = config.getServletContext().getMimeType(filename);
                    if (mimeType == null)
                    {
                        mimeType = DEFAULT_MIME_TYPE;
                    }
                }
                Node resNode = contentNode.addNode(JCR_CONTENT, swb_office.getPrefix() + ":" + swb_office.getName());
                resNode.addMixin("mix:versionable");
                resNode.setProperty("jcr:mimeType", mimeType);
                resNode.setProperty("jcr:encoding", "");
                resNode.setProperty(cm_file, file);
                String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                resNode.setProperty(cm_user, this.user);
                resNode.setProperty(JCR_DATA, in);
                in.close();
                resNode.setProperty(JCR_LASTMODIFIED, lastModified);
                categoryNode.save();
                Version version = resNode.checkin();
                log.trace("Version created with number " + version.getName());


                return contentNode.getUUID();
            }
            catch (ItemExistsException e)
            {
                throw new Exception("Ya existe un contenido con ese nombre", e);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                //categoryNode.unlock();
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception("La categoria indica no existe", infe);
        }
        catch (LockException e)
        {
            e.printStackTrace();
            throw e;
        }
        catch (Exception e)
        {
            if (categoryNode != null)
            {
                //categoryNode.unlock();
            }
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String save(String title, String description, String repositoryName, String categoryID, String type, String nodeType, String file, PropertyInfo[] properties, String[] values) throws Exception
    {
        for (Part part : requestParts)
        {
            InputStream in = new ByteArrayInputStream(part.getContent());
            return save(title, description, repositoryName, categoryID, type, nodeType, file, properties, values, in, part.getName());
        }
        return null;
    }

    public int getNumberOfVersions(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node nodeRes = nodeContent.getNode(JCR_CONTENT);
            VersionHistory history = nodeRes.getVersionHistory();
            // the version minus the root version
            return (int) history.getAllVersions().getSize() - 1;
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public boolean allVersionsArePublished(String repositoryName, String contentId) throws Exception
    {
        for (VersionInfo version : getVersions(repositoryName, contentId))
        {
            if (!version.published)
            {
                return false;
            }
        }
        return true;
    }

    public void deleteVersionOfContent(String repositoryName, String contentId, String versionName) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            String lastVersion = getLastVersionOfcontent(repositoryName, contentId);
            VersionHistory history = resContent.getVersionHistory();
            Version version = history.getVersion(versionName);
            version.remove();
            history.save();
            nodeContent.save();
            if (lastVersion != null && lastVersion.equals(versionName))
            {
                Iterator<WebSite> sites = SWBContext.listWebSites();
                while (sites.hasNext())
                {
                    WebSite site = sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                            if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName) && officeResource.getVersionToShow().equals("*"))
                            {                                                                
                                InputStream in = getContent(repositoryName, contentId, officeResource.getVersionToShow());
                                final org.semanticwb.model.User wbuser=SWBContext.getAdminRepository().getUserByLogin(user);
                                officeResource.loadContent(in,wbuser);                                                               
                            }
                        }
                    }
                }
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    /**
     * Update a Content
     * @param contentId ID of the content, the id is a UUID
     * @return The version name created
     * @throws java.lang.Exception
     */
    public String updateContent(String repositoryName, String contentId, String file, ResourceInfo[] resources, PFlow[] flows, String[] msg) throws Exception
    {
        String encode=System.getProperty("file.encoding","utf-8");
        if (encode == null || encode.equals(""))
        {
            encode = "utf-8";
        }
        file = java.net.URLDecoder.decode(file, encode);
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);

            String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
            String cm_user = loader.getOfficeManager(repositoryName).getUserType();
            Calendar lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(System.currentTimeMillis());
            nodeContent.setProperty(LASTMODIFIED, lastModified);
            nodeContent.save();
            String versionName = null;
            try
            {
                if (requestParts.isEmpty())
                {
                    throw new Exception("The content can not be updated, The content document was not found");
                }
                else
                {
                    Part part = requestParts.iterator().next();
                    String mimeType = DEFAULT_MIME_TYPE;
                    if (this.config != null && this.config.getServletContext() != null)
                    {
                        mimeType = this.config.getServletContext().getMimeType(part.getName());
                    }
                    if (mimeType == null)
                    {
                        mimeType = DEFAULT_MIME_TYPE;
                    }
                    Node resNode = nodeContent.getNode(JCR_CONTENT);
                    resNode.checkout();
                    resNode.setProperty("jcr:mimeType", mimeType);
                    resNode.setProperty("jcr:encoding", "");
                    resNode.setProperty(cm_file, file);
                    resNode.setProperty(cm_user, this.user);
                    InputStream in = new ByteArrayInputStream(part.getContent());
                    resNode.getProperty(JCR_DATA).setValue(in);
                    resNode.setProperty(JCR_LASTMODIFIED, lastModified);
                    session.save();
                    Version version = resNode.checkin();
                    versionName = version.getName();
                    return versionName;
                }


            }
            catch (RepositoryException e)
            {
                e.printStackTrace();
                throw e;
            }
            finally
            {
                Iterator<WebSite> sites = SWBContext.listWebSites();
                while (sites.hasNext())
                {
                    WebSite site = sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                            if (officeResource.getVersionToShow().equals("*") && officeResource.getResourceBase() != null && officeResource.getResourceBase().getPflowInstance() != null)
                            {
                                // remueve flujos para los que se quieren sólo la última version
                                officeResource.getResourceBase().removePflowInstance();
                            }
                        }
                    }
                }
                // actualiza version si no tiene fujo
                sites = SWBContext.listWebSites();
                while (sites.hasNext())
                {
                    WebSite site = sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                            ResourceInfo resInfo = getResourceInfo(officeResource);
                            if (officeResource.getVersionToShow().equals("*") && this.isInFlow(resInfo))
                            {
                                officeResource.getResourceBase().removePflowInstance();
                            }
                            if (this.needsSendToPublish(resInfo))
                            {
                                if (resources != null && flows != null)
                                {
                                    int i = 0;
                                    for (ResourceInfo res : resources)
                                    {
                                        if (res.id.equals(resInfo.id))
                                        {
                                            break;
                                        }
                                        i++;
                                    }
                                    PFlow flow = flows[i];
                                    String message = msg[i];
                                    this.sendToAuthorize(resInfo, flow, message);
                                }
                            }
                            else
                            {
                                if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName))
                                {
                                    String versionToShow = officeResource.getVersionToShow();
                                    if (versionToShow.equals("*") || versionToShow.equals(versionName))
                                    {
                                        InputStream in = getContent(repositoryName, contentId, versionToShow);
                                        final org.semanticwb.model.User wbuser=SWBContext.getAdminRepository().getUserByLogin(user);
                                        officeResource.loadContent(in,wbuser);                                        
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }

    }

    public String getPath(String contentID)
    {
        return "/";
    }

    public boolean exists(String repositoryName, String contentId) throws Exception
    {
        boolean exists = false;
        Session session = null;
        try
        {
            boolean existsRep = false;
            for (String rep : loader.getWorkspaces())
            {
                if (rep.equals(repositoryName))
                {
                    existsRep = true;
                    break;
                }
            }
            if (existsRep)
            {
                session = loader.openSession(repositoryName, this.user, this.password);
                session.getNodeByUUID(contentId);
                exists = true;
            }
        }
        catch (WorkspaceNotFoudException wsnf)
        {
            exists = false;
        }
        catch (ItemNotFoundException pnfe)
        {
            exists = false;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return exists;

    }
    private boolean isSu()
    {
        UserGroup su=UserGroup.ClassMgr.getUserGroup("su", SWBContext.getAdminRepository());
        org.semanticwb.model.User ouser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(user);
        return ((su!=null && ouser.hasUserGroup(su)));
    }
    public void delete(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            String cm_user = loader.getOfficeManager(repositoryName).getUserType();
            Node resNode = nodeContent.getNode(JCR_CONTENT);
            String userlogin=resNode.getProperty(cm_user).getString();
            if(isSu() || (userlogin!=null && userlogin.equals(this.user)))
            {
                Node parent = nodeContent.getParent();
                Iterator<WebSite> sites = SWBContext.listWebSites();
                while (sites.hasNext())
                {
                    WebSite site = sites.next();
                    Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentID);
                    while (it.hasNext())
                    {
                        SemanticObject obj = it.next();
                        if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                        {
                            OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                            site.removeResource(officeResource.getId());
                        }
                    }
                }
                VersionIterator it = resNode.getVersionHistory().getAllVersions();
                while (it.hasNext())
                {
                    Version version = it.nextVersion();
                    if (!version.getName().equals("jcr:rootVersion"))
                    {
                        version.getNode(JCR_FROZEN_NODE).remove();
                    }
                }
                resNode.getVersionHistory().save();
                resNode.remove();
                nodeContent.remove();
                parent.save();
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getLastVersionOfcontent(Session session, String repositoryName, String contentId) throws Exception
    {
        String getLastVersionOfcontent = null;
        ArrayList<Version> versions = new ArrayList<Version>();
        try
        {
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            try
            {
                Version version=resContent.getBaseVersion();
                getLastVersionOfcontent = version.getName();
            }
            catch(Exception ue)
            {
                log.error(ue);
                VersionIterator it = resContent.getVersionHistory().getAllVersions();
                while (it.hasNext())
                {
                    Version version = it.nextVersion();
                    if (!version.getName().equals("jcr:rootVersion"))
                    {
                        versions.add(version);
                    }
                }
                for (Version version : versions)
                {
                    if (getLastVersionOfcontent == null)
                    {
                        getLastVersionOfcontent = version.getName();
                    }
                    else
                    {
                        try
                        {

                            float currentVersion = format.parse(version.getName()).floatValue();
                            if (Float.parseFloat(getLastVersionOfcontent) < currentVersion)
                            {
                                getLastVersionOfcontent = version.getName();
                            }
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }
                    }
                }
            }

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return getLastVersionOfcontent;
    }

    public String getLastVersionOfcontent(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            return getLastVersionOfcontent(session, repositoryName, contentId);
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public VersionInfo[] getVersions(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        ArrayList<VersionInfo> versions = new ArrayList<VersionInfo>();
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            VersionIterator it = resContent.getVersionHistory().getAllVersions();
            while (it.hasNext())
            {
                Version version = it.nextVersion();
                if (!version.getName().equals("jcr:rootVersion"))
                {
                    VersionInfo info = new VersionInfo();
                    info.contentId = contentId;
                    info.nameOfVersion = version.getName();
                    info.created = version.getProperty("jcr:created").getDate().getTime();
                    String cm_user = loader.getOfficeManager(repositoryName).getUserType();
                    info.user = version.getNode(JCR_FROZEN_NODE).getProperty(cm_user).getString();
                    info.published = false;
                    Iterator<WebSite> sites = SWBContext.listWebSites();
                    while (sites.hasNext())
                    {
                        WebSite site = sites.next();
                        if (info.published)
                        {
                            break;
                        }
                        Iterator<SemanticObject> itSubjects = site.getSemanticObject().getModel().listSubjects(prop_content, contentId);
                        while (itSubjects.hasNext())
                        {
                            SemanticObject obj = itSubjects.next();
                            if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                            {
                                OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                                if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName) && officeResource.getVersionToShow() != null)
                                {
                                    if (officeResource.getVersionToShow().equals("*"))
                                    {
                                        String maxversion = getLastVersionOfcontent(repositoryName, contentId);
                                        if (maxversion != null)
                                        {
                                            if (maxversion.equals(info.nameOfVersion))
                                            {
                                                info.published = true;
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (officeResource.getVersionToShow().equals(info.nameOfVersion))
                                        {
                                            info.published = true;
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                    versions.add(info);
                }
            }
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
        return versions.toArray(new VersionInfo[versions.size()]);
    }

    public void setTitle(String repositoryName, String contentID, String title) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();

            nodeContent.setProperty(cm_title, title);
            nodeContent.getProperty(LASTMODIFIED).setValue(Calendar.getInstance());
            nodeContent.save();


        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getTitle(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            return nodeContent.getProperty(cm_title).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getDescription(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
            return nodeContent.getProperty(cm_description).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void setDescription(String repositoryName, String contentID, String description) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();

            nodeContent.setProperty(cm_description, description);
            nodeContent.getProperty(LASTMODIFIED).setValue(Calendar.getInstance());
            nodeContent.save();



        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public Date getLastUpdate(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            return nodeContent.getProperty(LASTMODIFIED).getDate().getTime();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public static ResourceInfo getResourceInfo(OfficeResource officeResource)
    {
        ResourceInfo info = null;
        GenericIterator<Resourceable> portlables = officeResource.getResourceBase().listResourceables();
        if (portlables != null)
        {
            while (portlables.hasNext())
            {
                Resourceable resourceable = portlables.next();
                if (resourceable != null && resourceable instanceof WebPage)
                {
                    WebPage page = (WebPage) resourceable;
                    info = new ResourceInfo(officeResource.getId(), page.getId());
                    info.active = officeResource.getResourceBase().isActive();
                    info.description = officeResource.getResourceBase().getDescription();
                    info.title = officeResource.getResourceBase().getTitle();
                    info.version = officeResource.getVersionToShow();
                    info.page.title = page.getTitle();
                    info.page.active = page.isActive();
                    info.page.description = page.getDescription();
                    info.page.site = new SiteInfo();
                    info.page.site.title = page.getWebSite().getTitle();
                    info.page.site.description = page.getWebSite().getDescription();
                    info.page.url = page.getUrl();
                    info.page.site.id = page.getWebSite().getId();
                }
            }
        }
        return info;
    }

    public ResourceInfo[] listResources(String repositoryName, String contentid) throws Exception
    {
        ArrayList<ResourceInfo> listResources = new ArrayList<ResourceInfo>();
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            Iterator<SemanticObject> it = site.getSemanticObject().getModel().listSubjects(prop_content, contentid);
            while (it.hasNext())
            {
                SemanticObject obj = it.next();
                if (obj.getSemanticClass().isSubClass(OfficeResource.sclass) || obj.getSemanticClass().equals(OfficeResource.sclass))
                {
                    OfficeResource officeResource = OfficeResource.getOfficeResource(obj.getId(), site);
                    if (officeResource.getRepositoryName() != null && officeResource.getRepositoryName().equals(repositoryName))
                    {
                        Resource base = site.getResource(obj.getId());
                        if (base != null)
                        {
                            officeResource.setResourceBase(base);
                            ResourceInfo info = getResourceInfo(officeResource);
                            if (info != null)
                            {
                                listResources.add(info);
                            }
                        }
                    }
                }
            }
        }
        return listResources.toArray(new ResourceInfo[listResources.size()]);
    }

    public static void registerContents(WebSite site)
    {
        ResourceType resourceType = site.getResourceType(EXCEL_RESOURCE_TYPE);
        if (resourceType == null)
        {
            resourceType = site.createResourceType(EXCEL_RESOURCE_TYPE);
            resourceType.setCreated(new Date(System.currentTimeMillis()));
            resourceType.setDescription(EXCEL_RESOURCE_DESCRIPTION);
            resourceType.setResourceBundle(ExcelResource.class.getCanonicalName());
            resourceType.setTitle(EXCEL_RESOURCE_TITLE);
            resourceType.setResourceMode(1);
            resourceType.setResourceClassName(ExcelResource.class.getCanonicalName());
            resourceType.setUpdated(new Date(System.currentTimeMillis()));
            log.event("Resource  " + EXCEL_RESOURCE_TYPE + " registered in " + site.getTitle() + "...");
        }
        resourceType = site.getResourceType(PPT_RESOURCE_TYPE);
        if (resourceType == null)
        {
            resourceType = site.createResourceType(PPT_RESOURCE_TYPE);
            resourceType.setCreated(new Date(System.currentTimeMillis()));
            resourceType.setDescription(PPT_RESOURCE_DESCRIPTION);
            resourceType.setResourceBundle(PPTResource.class.getCanonicalName());
            resourceType.setTitle(PPT_RESOURCE_TITLE);
            resourceType.setResourceMode(1);
            resourceType.setResourceClassName(PPTResource.class.getCanonicalName());
            resourceType.setUpdated(new Date(System.currentTimeMillis()));
            log.event("Resource  " + PPT_RESOURCE_TYPE + " registered in " + site.getTitle() + "...");
        }
        resourceType = site.getResourceType(WORD_RESOURCE_TYPE);
        if (resourceType == null)
        {
            resourceType = site.createResourceType(WORD_RESOURCE_TYPE);
            resourceType.setCreated(new Date(System.currentTimeMillis()));
            resourceType.setDescription(WORD_RESOURCE_DESCRIPTION);
            resourceType.setTitle(WORD_RESOURCE_TITLE);
            resourceType.setResourceBundle(WordResource.class.getCanonicalName());
            resourceType.setResourceMode(1);
            resourceType.setResourceClassName(WordResource.class.getCanonicalName());
            resourceType.setUpdated(new Date(System.currentTimeMillis()));
            log.event("Resource  " + WORD_RESOURCE_TYPE + " registered in " + site.getTitle() + "...");
        }
    }

    public static void registerContents()
    {
        Iterator<WebSite> sites = SWBContext.listWebSites();
        while (sites.hasNext())
        {
            WebSite site = sites.next();
            if (!(site.getId().equals(SWBContext.WEBSITE_ADMIN) || site.getId().equals(SWBContext.WEBSITE_GLOBAL) || site.getId().equals(SWBContext.WEBSITE_ONTEDITOR)))
            {
                registerContents(site);
            }
        }
    }

    public ResourceInfo publishToResourceContent(String id, String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage, PropertyInfo[] properties, String[] values) throws Exception
    {
        WebSite site = SWBContext.getWebSite(webpage.siteID);
        WebPage page = site.getWebPage(webpage.id);
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node contentNode = session.getNodeByUUID(contentId);
            String cm_officeType = loader.getOfficeManager(repositoryName).getPropertyType();
            String type = contentNode.getProperty(cm_officeType).getString();
            ResourceType resourceType = null;
            Resource resource = site.createResource(id);
            OfficeResource officeResource = null;
            if (type.equalsIgnoreCase("EXCEL"))
            {
                officeResource = ExcelResource.createExcelResource(id, site);
                resourceType = site.getResourceType(EXCEL_RESOURCE_TYPE);
                if (resourceType == null)
                {
                    resourceType = site.createResourceType(EXCEL_RESOURCE_TYPE);
                    resourceType.setCreated(new Date(System.currentTimeMillis()));
                    resourceType.setDescription(EXCEL_RESOURCE_DESCRIPTION);
                    resourceType.setResourceBundle(ExcelResource.class.getSimpleName());
                    resourceType.setTitle(EXCEL_RESOURCE_TITLE);
                    resourceType.setResourceMode(1);
                    resourceType.setResourceClassName(ExcelResource.class.getCanonicalName());
                    resourceType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            else if (type.equalsIgnoreCase("PPT"))
            {
                officeResource = PPTResource.createPPTResource(id, site);
                resourceType = site.getResourceType(PPT_RESOURCE_TYPE);
                if (resourceType == null)
                {
                    resourceType = site.createResourceType(PPT_RESOURCE_TYPE);
                    resourceType.setCreated(new Date(System.currentTimeMillis()));
                    resourceType.setDescription(PPT_RESOURCE_DESCRIPTION);
                    resourceType.setResourceBundle(PPTResource.class.getSimpleName());
                    resourceType.setTitle(PPT_RESOURCE_TITLE);
                    resourceType.setResourceMode(1);
                    resourceType.setResourceClassName(PPTResource.class.getCanonicalName());
                    resourceType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            else
            {
                officeResource = WordResource.createWordResource(id, site);

                resourceType = site.getResourceType(WORD_RESOURCE_TYPE);
                if (resourceType == null)
                {
                    resourceType = site.createResourceType(WORD_RESOURCE_TYPE);
                    resourceType.setCreated(new Date(System.currentTimeMillis()));
                    resourceType.setDescription(WORD_RESOURCE_DESCRIPTION);
                    resourceType.setTitle(WORD_RESOURCE_TITLE);
                    resourceType.setResourceBundle(WordResource.class.getSimpleName());
                    resourceType.setResourceMode(1);
                    resourceType.setResourceClassName(WordResource.class.getCanonicalName());
                    resourceType.setUpdated(new Date(System.currentTimeMillis()));
                }
            }
            officeResource.setResourceBase(resource);
            resource.setResourceType(resourceType);
            officeResource.setContent(contentId);
            resource.setResourceType(resourceType);
            org.semanticwb.model.User creator = SWBContext.getAdminRepository().getUserByLogin(user);
            if (creator != null)
            {
                resource.setCreator(creator);
            }
            officeResource.setRepositoryName(repositoryName);
            resource.setTitle(title);
            resource.setPriority(1);
            resource.setDescription(description);
            officeResource.setVersionToShow(version);
            resource.setCreated(new Date(System.currentTimeMillis()));
            resource.setUpdated(new Date(System.currentTimeMillis()));
            int i = 0;
            for (PropertyInfo prop : properties)
            {
                String value = values[i];
                Iterator<SemanticProperty> semanticProperties = officeResource.getSemanticObject().getSemanticClass().listProperties();
                while (semanticProperties.hasNext())
                {
                    SemanticProperty semanticProperty = semanticProperties.next();
                    if (semanticProperty.getURI().equals(prop.id))
                    {
                        if (semanticProperty.isBoolean())
                        {
                            boolean bvalue = Boolean.parseBoolean(value);
                            officeResource.getSemanticObject().setBooleanProperty(semanticProperty, bvalue);
                        }
                        else if (semanticProperty.isInt())
                        {
                            int bvalue = Integer.parseInt(value);
                            officeResource.getSemanticObject().setIntProperty(semanticProperty, bvalue);
                        }
                        else if (semanticProperty.isByte())
                        {
                            byte bvalue = Byte.parseByte(value);
                            officeResource.getSemanticObject().setIntProperty(semanticProperty, bvalue);
                        }
                        else if (semanticProperty.isDouble())
                        {
                            double bvalue = Double.parseDouble(value);
                            officeResource.getSemanticObject().setDoubleProperty(semanticProperty, bvalue);
                        }
                        else if (semanticProperty.isFloat())
                        {
                            float bvalue = Float.parseFloat(value);
                            officeResource.getSemanticObject().setFloatProperty(semanticProperty, bvalue);
                        }
                        else if (semanticProperty.isLong())
                        {
                            long bvalue = Long.parseLong(value);
                            officeResource.getSemanticObject().setLongProperty(semanticProperty, bvalue);
                        }
                        else if (semanticProperty.isShort())
                        {
                            short bvalue = Short.parseShort(value);
                            officeResource.getSemanticObject().setIntProperty(semanticProperty, bvalue);
                        }
                        else
                        {
                            officeResource.getSemanticObject().setProperty(semanticProperty, value);
                        }
                    }
                }
                i++;
            }
            try
            {
                page.addResource(resource);
            }
            catch (Exception e)
            {
                log.error(e);
            }
            try
            {
                InputStream in = getContent(repositoryName, contentId, version);
                final org.semanticwb.model.User wbuser=SWBContext.getAdminRepository().getUserByLogin(user);
                officeResource.loadContent(in,wbuser);
                ResourceInfo info = getResourceInfo(officeResource);
                return info;
            }
            catch (Exception e)
            {
                officeResource.getResourceBase().remove();
                officeResource.getSemanticObject().remove();
                throw e;
            }


        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public boolean canPublishToResourceContent(String type,WebPageInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.siteID);
        WebPage page=site.getWebPage(info.id);
        org.semanticwb.model.User wbuser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
        if (type.equalsIgnoreCase("EXCEL"))
        {
            SemanticClass clazz = ExcelResource.sclass;
            return SWBPortal.getAdminFilterMgr().haveClassAction(wbuser, clazz, AdminFilter.ACTION_ADD) && SWBPortal.getAdminFilterMgr().haveAccessToSemanticObject(wbuser, page.getSemanticObject());
        }
        else if (type.equalsIgnoreCase("PPT"))
        {
            SemanticClass clazz = PPTResource.sclass;
            return SWBPortal.getAdminFilterMgr().haveClassAction(wbuser, clazz, AdminFilter.ACTION_ADD) && SWBPortal.getAdminFilterMgr().haveAccessToSemanticObject(wbuser, page.getSemanticObject());

        }
        else
        {
            SemanticClass clazz = WordResource.sclass;
            return SWBPortal.getAdminFilterMgr().haveClassAction(wbuser, clazz, AdminFilter.ACTION_ADD) && SWBPortal.getAdminFilterMgr().haveAccessToSemanticObject(wbuser, page.getSemanticObject());
        }
    }

    public ResourceInfo publishToResourceContent(String repositoryName, String contentId, String version, String title, String description, WebPageInfo webpage, PropertyInfo[] properties, String[] values) throws Exception
    {
        String id = UUID.randomUUID().toString();
        return publishToResourceContent(id, repositoryName, contentId, version, title, description, webpage, properties, values);
    }

    public void setResourceProperties(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        OfficeResource resource = OfficeResource.getOfficeResource(resourceInfo.id, site);
        SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(propertyInfo.id);
        if (prop.isBoolean())
        {
            boolean bvalue = Boolean.parseBoolean(value);
            resource.getSemanticObject().setBooleanProperty(prop, bvalue);
        }
        else if (prop.isInt())
        {
            int bvalue = Integer.parseInt(value);
            resource.getSemanticObject().setIntProperty(prop, bvalue);
        }
        else if (prop.isByte())
        {
            byte bvalue = Byte.parseByte(value);
            resource.getSemanticObject().setIntProperty(prop, bvalue);
        }
        else if (prop.isDouble())
        {
            double bvalue = Double.parseDouble(value);
            resource.getSemanticObject().setDoubleProperty(prop, bvalue);
        }
        else if (prop.isFloat())
        {
            float bvalue = Float.parseFloat(value);
            resource.getSemanticObject().setFloatProperty(prop, bvalue);
        }
        else if (prop.isLong())
        {
            long bvalue = Long.parseLong(value);
            resource.getSemanticObject().setLongProperty(prop, bvalue);
        }
        else if (prop.isShort())
        {
            short bvalue = Short.parseShort(value);
            resource.getSemanticObject().setIntProperty(prop, bvalue);
        }
        else
        {
            resource.getSemanticObject().setProperty(prop, value);
        }
    }

    public void setViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo, String value) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        OfficeResource resource = OfficeResource.getOfficeResource(resourceInfo.id, site);
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        if (prop.isBoolean())
        {
            boolean bvalue = Boolean.parseBoolean(value);
            resource.getSemanticObject().setBooleanProperty(prop, bvalue);
        }
        else if (prop.isInt())
        {
            int bvalue = Integer.parseInt(value);
            resource.getSemanticObject().setIntProperty(prop, bvalue);
        }
        else if (prop.isByte())
        {
            byte bvalue = Byte.parseByte(value);
            resource.getSemanticObject().setIntProperty(prop, bvalue);
        }
        else if (prop.isDouble())
        {
            double bvalue = Double.parseDouble(value);
            resource.getSemanticObject().setDoubleProperty(prop, bvalue);
        }
        else if (prop.isFloat())
        {
            float bvalue = Float.parseFloat(value);
            resource.getSemanticObject().setFloatProperty(prop, bvalue);
        }
        else if (prop.isLong())
        {
            long bvalue = Long.parseLong(value);
            resource.getSemanticObject().setLongProperty(prop, bvalue);
        }
        else if (prop.isShort())
        {
            short bvalue = Short.parseShort(value);
            resource.getSemanticObject().setIntProperty(prop, bvalue);
        }
        else
        {
            resource.getSemanticObject().setProperty(prop, value);
        }
    }

    public CalendarInfo[] getCalendarsOfResource(ResourceInfo resourceInfo) throws Exception
    {
        HashSet<CalendarInfo> getCalendarInfo = new HashSet<CalendarInfo>();
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        GenericIterator<CalendarRef> calendars = resource.listCalendarRefs();
        while (calendars.hasNext())
        {
            org.semanticwb.model.CalendarRef ref = calendars.next();
            org.semanticwb.model.Calendar cal = ref.getCalendar();
            CalendarInfo info = new CalendarInfo();
            info.id = cal.getId();
            info.xml = cal.getXml();
            info.active = ref.isActive();
            info.title = cal.getTitle();
            getCalendarInfo.add(info);
        }
        return getCalendarInfo.toArray(new CalendarInfo[getCalendarInfo.size()]);
    }

    public CalendarInfo[] getCatalogCalendars(SiteInfo siteInfo) throws Exception
    {
        HashSet<CalendarInfo> getCalendarInfo = new HashSet<CalendarInfo>();
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        Iterator<org.semanticwb.model.Calendar> calendars = site.listCalendars();
        while (calendars.hasNext())
        {
            org.semanticwb.model.Calendar cal = calendars.next();
            if (cal.getXml() != null)
            {
                CalendarInfo info = new CalendarInfo();
                info.id = cal.getId();
                info.xml = cal.getXml();
                info.active = cal.isActive();
                info.title = cal.getTitle();
                getCalendarInfo.add(info);
            }
        }
        return getCalendarInfo.toArray(new CalendarInfo[getCalendarInfo.size()]);
    }

    public String getViewPropertyValue(ResourceInfo resourceInfo, PropertyInfo propertyInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        OfficeResource resource = OfficeResource.getOfficeResource(resourceInfo.id, site);
        SemanticProperty prop = site.getSemanticObject().getModel().getSemanticProperty(propertyInfo.id);
        return resource.getSemanticObject().getProperty(prop);
    }

    public void validateViewValues(String repositoryName, String contentID, PropertyInfo[] properties, Object[] values) throws Exception
    {
        String contentType = getContentType(repositoryName, contentID);
        OfficeResource officeResource;
        SemanticClass clazz;
        if (contentType.equalsIgnoreCase("excel"))
        {
            officeResource = new ExcelResource();
            clazz = ExcelResource.sclass;
        }
        else if (contentType.equalsIgnoreCase("ppt"))
        {
            officeResource = new PPTResource();
            clazz = PPTResource.sclass;
        }
        else
        {
            officeResource = new WordResource();
            clazz = WordResource.sclass;
        }
        if (properties.length == values.length)
        {
            HashMap<SemanticProperty, Object> valuesToValidate = new HashMap<SemanticProperty, Object>();
            int i = 0;
            for (PropertyInfo propertyInfo : properties)
            {
                Object value = values[i];
                Iterator<SemanticProperty> propertiesClazz = clazz.listProperties();
                while (propertiesClazz.hasNext())
                {
                    SemanticProperty prop = propertiesClazz.next();
                    if (prop.getURI().equals(propertyInfo.id))
                    {
                        valuesToValidate.put(prop, value);
                    }
                }
                i++;
            }
            officeResource.validateViewPropertyValues(valuesToValidate);
        }
        else
        {
            throw new Exception("The number of properties is not equals to the number of values");
        }
    }

    private boolean isSuperProperty(SemanticProperty prop, SemanticClass clazz)
    {
        Iterator<SemanticClass> classes = clazz.listSuperClasses();
        while (classes.hasNext())
        {
            SemanticClass superClazz = classes.next();
            if (superClazz.isSWBClass())
            {
                Iterator<SemanticProperty> propertiesClazz = superClazz.listProperties();
                while (propertiesClazz.hasNext())
                {
                    SemanticProperty propSuperClazz = propertiesClazz.next();
                    if (propSuperClazz.getURI().equals(prop.getURI()))
                    {
                        return true;
                    }
                    else
                    {
                        boolean res = isSuperProperty(prop, superClazz);
                        if (res == true)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public PropertyInfo[] getContentProperties(String repositoryName, String type) throws Exception
    {
        return loader.getOfficeManager(repositoryName).getContentProperties(type);
    }

    public PropertyInfo[] getResourceProperties(String repositoryName, String contentID) throws Exception
    {
        ArrayList<PropertyInfo> properties = new ArrayList<PropertyInfo>();
        String type = getContentType(repositoryName, contentID);
        SemanticClass clazz = null;
        if (type.equalsIgnoreCase("excel"))
        {
            clazz = ExcelResource.sclass;
        }
        else if (type.equalsIgnoreCase("ppt"))
        {
            clazz = PPTResource.sclass;
        }
        else
        {
            clazz = WordResource.sclass;
        }
        Iterator<SemanticProperty> propertiesClazz = clazz.listProperties();
        while (propertiesClazz.hasNext())
        {
            SemanticProperty prop = propertiesClazz.next();

            if (!isSuperProperty(prop, clazz) && prop.isDataTypeProperty() && !prop.isBinary() && prop.getPrefix() != null)
            {

                SemanticObject displayObj = prop.getDisplayProperty();
                if (displayObj != null)
                {
                    DisplayProperty propDisplay = new DisplayProperty(displayObj);
                    if (!propDisplay.isHidden() && !propDisplay.isDisabled())
                    {
                        PropertyInfo info = new PropertyInfo();
                        info.id = prop.getURI();
                        info.isRequired = prop.isRequired();
                        info.title = prop.getDisplayName();
                        String values = propDisplay.getDisplaySelectValues("es");
                        if (values != null)
                        {
                            StringTokenizer st = new StringTokenizer(values, "|");
                            if (st.countTokens() > 0)
                            {
                                info.values = new Value[st.countTokens()];
                                int i = 0;
                                while (st.hasMoreTokens())
                                {
                                    String value = st.nextToken();
                                    int pos = value.indexOf(":");
                                    if (pos != -1)
                                    {
                                        info.values[i] = new Value();
                                        info.values[i].key = value.substring(0, pos);
                                        info.values[i].title = value.substring(pos + 1);
                                    }
                                    i++;
                                }
                            }
                        }
                        if (prop.isString())
                        {
                            info.type = "String";
                            properties.add(info);
                        }
                        else if (prop.isBoolean())
                        {
                            info.type = "Boolean";
                            properties.add(info);
                        }
                        else if (prop.isInt())
                        {
                            info.type = "Integer";
                            properties.add(info);
                        }
                    }
                }
            }
        }
        return properties.toArray(new PropertyInfo[properties.size()]);
    }

    public void deleteContentOfPage(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.id);
        site.removeResource(info.id);
    }

    public InputStream getContent(String repositoryName, String contentId, String version) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            if (version.equals("*"))
            {
                String lastVersion = getLastVersionOfcontent(repositoryName, contentId);
                Version versionNode = resContent.getVersionHistory().getVersion(lastVersion);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty(JCR_DATA).getStream();
                }
                else
                {
                    return null;
                }
            }
            else
            {
                Version versionNode = resContent.getVersionHistory().getVersion(version);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty(JCR_DATA).getStream();
                }
                else
                {
                    return null;
                }
            }

        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentFile(Session session, String repositoryName, String contentId, String version) throws Exception
    {
        try
        {            
            Node nodeContent = session.getNodeByUUID(contentId);
            String cm_file = loader.getOfficeManager(repositoryName).getPropertyFileType();
            Node resContent = nodeContent.getNode(JCR_CONTENT);
            if (version.equals("*"))
            {                
                String lastVersion = getLastVersionOfcontent(session, repositoryName, contentId);
                Version versionNode = resContent.getVersionHistory().getVersion(lastVersion);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty(cm_file).getString();
                }
                else
                {
                    return null;
                }

            }
            else
            {
                Version versionNode = resContent.getVersionHistory().getVersion(version);
                if (versionNode != null)
                {
                    Node frozenNode = versionNode.getNode(JCR_FROZEN_NODE);
                    return frozenNode.getProperty(cm_file).getString();
                }
                else
                {
                    return null;
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }
    public Session getSession(String repositoryName,org.semanticwb.model.User user) throws Exception
    {
        Session session = null;
        session = loader.openSession(repositoryName, user);
        return session;
    }


    public String getContentFile(String repositoryName, String contentId, String version, org.semanticwb.model.User user) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, user);
            return getContentFile(session, repositoryName, contentId, version);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentFile(String repositoryName, String contentId, String version) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            return getContentFile(session, repositoryName, contentId, version);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String createPreview(String repositoryName, String contentId, String version, String type) throws Exception
    {
        String name = UUID.randomUUID().toString();
        String dir = "/" + name;
        InputStream in = getContent(repositoryName, contentId, version);
        final org.semanticwb.model.User wbuser=SWBContext.getAdminRepository().getUserByLogin(user);
        OfficeResource.loadContentPreview(in, dir, type,wbuser);
        in.close();
        return name;
    }

    public String getContentType(String repositoryName, String contentId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            String cm_Type = loader.getOfficeManager(repositoryName).getPropertyType();
            return nodeContent.getProperty(cm_Type).getString();
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void activateResource(ResourceInfo info, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        resource.setActive(active);
    }

    public String getCategory(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            Node parent = nodeContent.getParent();
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            return parent.getProperty(cm_title).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void changeCategory(String repositoryName, String contentId, String newCategoryId) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentId);
            Node newCategory = session.getNodeByUUID(newCategoryId);
            session.move(nodeContent.getPath(), newCategory.getPath());
            Node resource = nodeContent.getNode(JCR_CONTENT);
            resource.getProperty(LASTMODIFIED).setValue(Calendar.getInstance());
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void changeVersionPorlet(ResourceInfo info, String newVersion) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(info.id, site);
        officeResource.setResourceBase(resource);
        officeResource.setVersionToShow(newVersion);
        InputStream in = getContent(officeResource.getRepositoryName(), officeResource.getContent(), newVersion);
        final org.semanticwb.model.User wbuser=SWBContext.getAdminRepository().getUserByLogin(user);
        officeResource.loadContent(in,wbuser);        
    }

    public CategoryInfo getCategoryInfo(String repositoryName, String contentid) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentid);
            Node parent = nodeContent.getParent();
            String cm_title = loader.getOfficeManager(repositoryName).getPropertyTitleType();
            String cm_description = loader.getOfficeManager(repositoryName).getPropertyDescriptionType();
            CategoryInfo info = new CategoryInfo();
            info.UDDI = parent.getUUID();
            info.childs = 0;
            info.description = parent.getProperty(cm_description).getString();
            info.title = parent.getProperty(cm_title).getString();
            return info;
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void deleteResource(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(info.id, site);
        officeResource.setResourceBase(resource);
        try
        {
            officeResource.clean();
        }
        catch (Exception e)
        {
            log.event(e);
        }
        finally
        {
            site.removeResource(officeResource.getId());
        }
    }

    public String getVersionToShow(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(info.id, site);
        officeResource.setResourceBase(resource);
        return officeResource.getVersionToShow();
    }

    public void deletePreview(String dir) throws Exception
    {
        if (!dir.startsWith("/"))
        {
            dir = "/" + dir;
        }
        OfficeResource.clean(dir);
    }

    public void updateCalendar(SiteInfo siteInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarInfo.id);
        cal.setXml(calendarInfo.xml);
        cal.setUpdated(new Date(System.currentTimeMillis()));
    }

    public void insertCalendartoResource(ResourceInfo resourceInfo, CalendarInfo calendar) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendar.id);
        Resource resource = site.getResource(resourceInfo.id);
        boolean exists = false;
        GenericIterator<CalendarRef> refs = resource.listCalendarRefs();
        while (refs.hasNext())
        {
            CalendarRef ref = refs.next();
            if (ref.getCalendar().getId().equals(calendar.id))
            {
                exists = true;
                break;
            }
        }
        if (!exists)
        {
            CalendarRef ref = CalendarRef.ClassMgr.createCalendarRef(site);
            ref.setCalendar(cal);
            ref.setActive(true);
            resource.addCalendarRef(ref);
        }
    }

    public void deleteCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        GenericIterator<CalendarRef> refs = resource.listCalendarRefs();
        while (refs.hasNext())
        {
            CalendarRef ref = refs.next();
            if (ref.getCalendar().getId().equals(calendarInfo.id))
            {
                ref.remove();
            }
        }
    }

    public void activeCalendar(ResourceInfo resourceInfo, CalendarInfo calendarInfo, boolean active) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        GenericIterator<CalendarRef> refs = resource.listCalendarRefs();
        while (refs.hasNext())
        {
            CalendarRef ref = refs.next();
            if (ref.getCalendar().getId().equals(calendarInfo.id))
            {
                ref.setActive(active);
            }
        }
    }

    public void updatePorlet(ResourceInfo resourceInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(resourceInfo.page.site.id);
        Resource resource = site.getResource(resourceInfo.id);
        OfficeResource officeResource = OfficeResource.getOfficeResource(resourceInfo.id, site);
        officeResource.setResourceBase(resource);
        resource.setTitle(resourceInfo.title);
        resource.setDescription(resourceInfo.description);
        resource.setActive(resourceInfo.active);
        officeResource.setVersionToShow(resourceInfo.version);
    }

    public void validateContentValues(String repositoryName, PropertyInfo[] properties, Object[] values, String type) throws Exception
    {
        OfficeManager officemanager = loader.getOfficeManager(repositoryName);
        officemanager.validateContentValues(properties, values, type);
    }

    public void setContentPropertyValue(String repositoryName, String contentID, PropertyInfo propertyInfo, String value) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            nodeContent.setProperty(propertyInfo.id, value);
            nodeContent.save();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public void setContentProperties(String repositoryName, String contentID, PropertyInfo[] properties, String[] values) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);

            int i = 0;
            for (PropertyInfo propertyInfo : properties)
            {
                String value = values[i];
                nodeContent.setProperty(propertyInfo.id, value);
                i++;
            }
            nodeContent.save();

        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getNameOfContent(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            return nodeContent.getName();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public String getContentProperty(PropertyInfo prop, String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            return nodeContent.getProperty(prop.id).getString();
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }

    public boolean needsSendToPublish(ResourceInfo info)
    {
        boolean needsSendToPublish = false;
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        try
        {
            needsSendToPublish = SWBPortal.getPFlowManager().needAnAuthorization(resource);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e);
        }
        return needsSendToPublish;
    }

    public PFlow[] getFlows(ResourceInfo info)
    {
        HashSet<PFlow> flows = new HashSet<PFlow>();
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        for (org.semanticwb.model.PFlow flow : SWBPortal.getPFlowManager().getFlowsToSendContent(resource))
        {
            PFlow pflow = new PFlow();
            pflow.id = flow.getId();
            pflow.title = flow.getTitle();
            flows.add(pflow);
        }
        return flows.toArray(new PFlow[flows.size()]);
    }

    public void sendToAuthorize(ResourceInfo info, org.semanticwb.office.interfaces.PFlow flow, String message) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        if (resource.getPflowInstance() != null)
        {
            if (resource.getPflowInstance().getStatus() == 3 || resource.getPflowInstance().getStatus() == -1)
            {
                resource.getPflowInstance().remove();
            }
            else
            {
                throw new Exception("The content is in flow, and is nor rejected or aproveed");
            }
        }
        org.semanticwb.model.User wbuser = SWBContext.getAdminWebSite().getUserRepository().getUserByLogin(this.user);
        org.semanticwb.model.PFlow pflow = site.getPFlow(flow.id);
        SWBPortal.getPFlowManager().sendResourceToAuthorize(resource, pflow, message, wbuser);
    }

    public boolean isInFlow(ResourceInfo info)
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        return SWBPortal.getPFlowManager().isInFlow(resource);

    }

    public boolean isAuthorized(ResourceInfo info)
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        return SWBPortal.getPFlowManager().isAuthorized(resource);
    }

    public void setEndDate(ResourceInfo info, Date date) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        resource.setExpiration(date);
    }

    public void deleteEndDate(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        resource.setExpiration(null);
    }

    public Date getEndDate(ResourceInfo info) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        return resource.getExpiration();
    }

    public void deleteCalendarFromCatalog(SiteInfo siteInfo, CalendarInfo calendarIndo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(siteInfo.id);
        org.semanticwb.model.Calendar cal = site.getCalendar(calendarIndo.id);
        site.removeCalendar(calendarIndo.id);
    }

    public ElementInfo[] getElementsOfResource(ResourceInfo info) throws Exception
    {
        HashSet<ElementInfo> rules = new HashSet<ElementInfo>();
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        {
            GenericIterator<RuleRef> refs = resource.listRuleRefs();
            while (refs.hasNext())
            {
                RuleRef ref = refs.next();
                Rule rule = ref.getRule();
                ElementInfo rinfo = new ElementInfo();
                rinfo.id = rule.getId();
                rinfo.title = rule.getTitle();
                rinfo.active = ref.isActive();
                rinfo.type = Rule.sclass.getName();
                rules.add(rinfo);
            }
        }
        {
            GenericIterator<RoleRef> refs = resource.listRoleRefs();
            while (refs.hasNext())
            {
                RoleRef ref = refs.next();
                Role role = ref.getRole();
                ElementInfo rinfo = new ElementInfo();
                rinfo.id = role.getId();
                rinfo.title = role.getTitle();
                rinfo.active = ref.isActive();
                rinfo.type = Role.sclass.getName();
                rules.add(rinfo);
            }
        }
        {
            GenericIterator<UserGroupRef> refs = resource.listUserGroupRefs();
            while (refs.hasNext())
            {
                UserGroupRef ref = refs.next();
                UserGroup userGroupRef = ref.getUserGroup();
                ElementInfo rinfo = new ElementInfo();
                rinfo.id = userGroupRef.getId();
                rinfo.title = userGroupRef.getTitle();
                rinfo.active = ref.isActive();
                rinfo.type = UserGroup.sclass.getName();
                rules.add(rinfo);
            }
        }
        return rules.toArray(new ElementInfo[rules.size()]);
    }

    public void addElementToResource(ResourceInfo info, ElementInfo ruleInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = Resource.ClassMgr.getResource(info.id, site);
        if (ruleInfo.type.equals(Rule.sclass.getName()))
        {
            GenericIterator<RuleRef> refs = resource.listRuleRefs();
            boolean exists = false;
            while (refs.hasNext())
            {
                RuleRef ref = refs.next();
                Rule rule = ref.getRule();
                if (rule.getId().equals(ruleInfo.id))
                {
                    ref.setActive(ruleInfo.active);
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                RuleRef ref = RuleRef.ClassMgr.createRuleRef(site);
                ref.setActive(ruleInfo.active);
                ref.setRule(Rule.ClassMgr.getRule(ruleInfo.id, site));
                resource.addRuleRef(ref);
            }
        }
        if (ruleInfo.type.equals(Role.sclass.getName()))
        {
            GenericIterator<RoleRef> refs = resource.listRoleRefs();
            boolean exists = false;
            while (refs.hasNext())
            {
                RoleRef ref = refs.next();
                Role rule = ref.getRole();
                if (rule.getId().equals(ruleInfo.id))
                {
                    ref.setActive(ruleInfo.active);
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                RoleRef ref = RoleRef.ClassMgr.createRoleRef(site);
                ref.setActive(ruleInfo.active);
                ref.setRole(site.getUserRepository().getRole(ruleInfo.id));
                resource.addRoleRef(ref);
            }
        }
        if (ruleInfo.type.equals(UserGroup.sclass.getName()))
        {
            GenericIterator<UserGroupRef> refs = resource.listUserGroupRefs();
            boolean exists = false;
            while (refs.hasNext())
            {
                UserGroupRef ref = refs.next();
                UserGroup rule = ref.getUserGroup();
                if (rule.getId().equals(ruleInfo.id))
                {
                    ref.setActive(ruleInfo.active);
                    exists = true;
                    break;
                }
            }
            if (!exists)
            {
                UserGroupRef ref = UserGroupRef.ClassMgr.createUserGroupRef(site);
                ref.setActive(ruleInfo.active);
                ref.setUserGroup(site.getUserRepository().getUserGroup(ruleInfo.id));
                resource.addUserGroupRef(ref);
            }
        }

    }

    public void deleteElementToResource(ResourceInfo info, ElementInfo ruleInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        if (ruleInfo.type.equals(Rule.sclass.getName()))
        {
            GenericIterator<RuleRef> refs = resource.listRuleRefs();
            while (refs.hasNext())
            {
                RuleRef ref = refs.next();
                Rule rule = ref.getRule();
                if (rule.getId().equals(ruleInfo.id))
                {
                    resource.removeRuleRef(ref);
                    break;
                }
            }
        }
        if (ruleInfo.type.equals(Role.sclass.getName()))
        {
            GenericIterator<RoleRef> refs = resource.listRoleRefs();
            while (refs.hasNext())
            {
                RoleRef ref = refs.next();
                Role rule = ref.getRole();
                if (rule.getId().equals(ruleInfo.id))
                {
                    resource.removeRoleRef(ref);
                    break;
                }
            }
        }
        if (ruleInfo.type.equals(UserGroup.sclass.getName()))
        {
            GenericIterator<UserGroupRef> refs = resource.listUserGroupRefs();
            while (refs.hasNext())
            {
                UserGroupRef ref = refs.next();
                UserGroup rule = ref.getUserGroup();
                if (rule.getId().equals(ruleInfo.id))
                {
                    resource.removeUserGroupRef(ref);
                    break;
                }
            }
        }
    }

    public void changeResourceOfWebPage(ResourceInfo info, WebPageInfo webPageInfo) throws Exception
    {
        WebSite site = SWBContext.getWebSite(info.page.site.id);
        Resource resource = site.getResource(info.id);
        WebPage oldWebPage = site.getWebPage(info.page.id);
        WebPage newpageofResource = site.getWebPage(webPageInfo.id);
        newpageofResource.addResource(resource);
        oldWebPage.removeResource(resource);
    }

    public LanguageInfo[] getLanguages(SiteInfo site) throws Exception
    {
        ArrayList<LanguageInfo> languages = new ArrayList<LanguageInfo>();
        WebSite osite = WebSite.ClassMgr.getWebSite(site.id);
        Iterator<Language> itlenguages = osite.listLanguages();
        while (itlenguages.hasNext())
        {
            Language language = itlenguages.next();
            LanguageInfo info = new LanguageInfo();
            info.id = language.getId();
            info.title = language.getTitle();
            languages.add(info);
        }
        return languages.toArray(new LanguageInfo[languages.size()]);
    }

    public String getTitleOfWebPage(PageInfo webPageInfo, LanguageInfo laguage) throws Exception
    {
        WebSite osite = WebSite.ClassMgr.getWebSite(webPageInfo.site.id);
        WebPage page = osite.getWebPage(webPageInfo.id);
        return page.getTitle(laguage.id);
    }

    public void setTitlesOfWebPage(PageInfo webPageInfo, LanguageInfo[] languages, String[] values) throws Exception
    {
        WebSite osite = WebSite.ClassMgr.getWebSite(webPageInfo.site.id);
        WebPage page = osite.getWebPage(webPageInfo.id);
        int i = 0;
        for (LanguageInfo lang : languages)
        {
            String value = values[i];
            if (value != null && value.equals(""))
            {
                value = null;
            }
            page.setTitle(value, lang.id);
            i++;
        }
    }

    public ContentInfo existContentOldVersion(String contentid, String topicmap, String topicid) throws Exception
    {
        WebSite site = WebSite.ClassMgr.getWebSite(topicmap);
        if (site != null)
        {
            WebPage page = WebPage.ClassMgr.getWebPage(topicid, site);
            if (page != null)
            {
                GenericIterator<Resource> resources = page.listResources();
                while (resources.hasNext())
                {
                    Resource res = resources.next();
                    if (res.getId().equals(contentid))
                    {
                        OfficeResource resource = OfficeResource.getOfficeResource(contentid, site);
                        if (resource != null)
                        {
                            ContentInfo info = new ContentInfo();
                            info.id = resource.getContent();
                            info.respositoryName = resource.getRepositoryName();
                            return info;
                        }
                    }
                }
            }
        }
        return null;
    }    
    public boolean canModify(String repositoryName, String contentID) throws Exception
    {
        Session session = null;
        try
        {
            session = loader.openSession(repositoryName, this.user, this.password);
            Node nodeContent = session.getNodeByUUID(contentID);
            String cm_user = loader.getOfficeManager(repositoryName).getUserType();
            Node resNode = nodeContent.getNode(JCR_CONTENT);
            String userlogin=resNode.getProperty(cm_user).getString();
            return isSu() || (userlogin!=null && userlogin.equals(this.user));           
        }
        catch (ItemNotFoundException infe)
        {
            throw new Exception(CONTENT_NOT_FOUND, infe);
        }
        finally
        {
            if (session != null)
            {
                session.logout();
            }
        }
    }
}

