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
package org.semanticwb.migration.office;

import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.core.db.DBResourceType;
import com.infotec.wb.core.db.RecResourceType;
import com.infotec.wb.lib.WBResource;
import com.infotec.wb.util.WBUtils;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.office.comunication.OfficeDocument;

/**
 *
 * @author victor.lorenzana
 */
public class MigrateOfficeContents
{
    private static Logger log = SWBUtils.getLogger(MigrateOfficeContents.class);
    static OfficeDocument document = new OfficeDocument();
    public static Resource migrateResourceFrom32(WBResource resource, String siteid, File newpath) throws Exception
    {
        // para funcionar el repositorio debe estar en swbrep/repositoryTrusted=true

        log.debug("MigrateResourceFrom32...id:"+resource.getResourceBase().getId());

        Resource res = null;
        
        String workpath = WBUtils.getInstance().getWorkPath() + resource.getResourceBase().getResourceWorkPath();
        String className = resource.getResourceBase().getResourceType().getObjclass();
        String contentid = String.valueOf(resource.getResourceBase().getId());
        String file = resource.getResourceBase().getAttribute("url1");
        String version = String.valueOf(resource.getResourceBase().getActualversion());
        if (!document.isOfficeDocument(className, workpath, contentid, file, version))
        {
            return null;
        }
        document.setUser("");
        document.setPassword("");
        String title = resource.getResourceBase().getTitle();
        String description = resource.getResourceBase().getDescription();
        String xml = resource.getResourceBase().getRecResource().getXml();


        String webpageId = getTopics(resource, siteid);
        log.debug("=== class: "+className);
        log.debug("=== Topic ID asociado... "+webpageId);

        int Otype = resource.getResourceBase().getType();
        RecResourceType recobj = DBResourceType.getInstance().getResourceType(resource.getResourceBase().getTypeMap(), Otype);

        String webworkpath = WBUtils.getInstance().getWebWorkPath()+"/sites/" + resource.getResourceBase().getTopicMapId() + "/resources/" + recobj.getName() + "/" + resource.getResourceBase().getId() + "/" + version + "/";
        if (webpageId != null)
        {
            log.debug("Migrando el recurso..... Office Content");
            try{
                log.debug("webworkpath:"+webworkpath);
                log.debug("workpath:"+workpath);
                log.debug("xml:"+xml);
                log.debug("siteid:"+siteid);
                log.debug("webpageId:"+webpageId);
                log.debug("contentid:"+contentid);
                log.debug("version:"+version);
                log.debug("title:"+title);
                log.debug("description:"+description);
                log.debug("file:"+file);
                File filework = new File(workpath);
                if(filework!=null)
                {
                    try {
                        res = document.migrateResource(webworkpath,filework, xml, className, siteid, webpageId, contentid, version, title, description, file);
                    } catch (Exception e) {
                        //AFUtils.log(e,"Error document.migrateResource...",true);
                        log.error("Error document.migrateResource...",e);
                        res=null;
                    }
                }
            }
            catch(Exception e){
                //AFUtils.log(e);
                log.error(e);
                res=null;
            }
        }

        String source = SWBPortal.getWorkPath() + "/" + "models/" + siteid + "/";
        SWBUtils.IO.copyStructure(source, newpath.getAbsolutePath() + "/" + siteid + "/");
        SWBUtils.IO.removeDirectory(source);
        source = SWBPortal.getWorkPath() + "/" + "models/" + siteid + "_rep/";
        SWBUtils.IO.copyStructure(source, newpath.getAbsolutePath() + "/" + siteid + "_rep/");
        SWBUtils.IO.removeDirectory(source);
        log.debug("=== terminado de migrar Office Content....");
        return res;

    }

    private static String getTopics(WBResource resource, String siteid)
    {
        String webpageId = null;
        Connection con = AFUtils.getDBConnection("wb");
        String search = "#REC_WBContent\"/>%<resourceData>" + resource.getResourceBase().getId() + "</resourceData>";
        try
        {
            String sql = "Select idtp from wboccurrence where xml like '%" + search + "%' and idtm='" + siteid + "'";            
            PreparedStatement pt = con.prepareStatement(sql);
            ResultSet rs = pt.executeQuery();
            if (rs.next())
            {
                webpageId = rs.getString("idtp");
            }
            else
            {
                log.debug("No se encontro donde esta publicado el contenido de office " + resource.getResourceBase().getId());
                log.debug("CONSULTA: "+sql);
            }
            rs.close();
            pt.close();
        }
        catch (Exception e)
        {
            AFUtils.log(e);
            log.error(e);
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (Exception ue)
            {
                AFUtils.log(ue);
                log.error(ue);
            }
        }
        return webpageId;
    }

    public static boolean isOfficeDocument(WBResource resource, String siteid)
    {
        String workpath = WBUtils.getInstance().getWorkPath() + resource.getResourceBase().getResourceWorkPath();
        String className = resource.getResourceBase().getResourceType().getObjclass();
        String contentid = String.valueOf(resource.getResourceBase().getId());
        String file = resource.getResourceBase().getAttribute("url1");
        String version = String.valueOf(resource.getResourceBase().getActualversion());
        //OfficeDocument document = new OfficeDocument();
        return document.isOfficeDocument(className, workpath, contentid, file, version);

    }
}
