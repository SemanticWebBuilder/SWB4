/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author jorge.jimenez
 */
public class SWBExportWebSite extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBExportWebSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        StringBuffer strbr = new StringBuffer();       
        String action = paramRequest.getAction();
        try {
            if (action != null && action.equals("step2")) {
                String uri = request.getParameter("wsid");
                WebSite site=SWBContext.getWebSite(uri);
                String path = SWBPlatform.getWorkPath() + "/";
                String modelspath=path + "models/";
                String zipdirectory = path + "sitetemplates/";
                String zipFile=zipdirectory + site.getId() + ".zip";
                //---------Generación de archivo zip de carpeta work de sitio especificado-------------
                java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(zipFile));
                java.io.File directory = new File(modelspath + site.getId() + "/");
                java.io.File base = new File(modelspath);
                org.semanticwb.SWBUtils.IO.zip(directory, base, zos);
                //Graba archivo cualquiera
                zos.setComment("Model File SemanticWebBuilderOS");
                try{
                    ZipEntry entry = new ZipEntry("readme.txt");
                    zos.putNextEntry(entry);
                    zos.write("Model File SemanticWebBuilderOS".getBytes());
                    zos.closeEntry();
                }catch(Exception e){}
                
                //-------------Generación de archivo rdf del sitio especificado----------------
                try {
                    File file = new File(zipdirectory + site.getId() + ".rdf");
                    FileOutputStream out = new FileOutputStream(file);
                    site.getSemanticObject().getModel().write(out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //----------Generación de archivo siteInfo.xml del sitio especificado-----------
                ArrayList aFiles=new ArrayList();
                File file = new File(zipdirectory + "siteInfo.xml");
                FileOutputStream out = new FileOutputStream(file);
                try {
                    strbr.append("<siteinfo>\n");
                    strbr.append("<model>");
                    strbr.append("<id>"+site.getId()+"</id>\n");
                    strbr.append("<namespace>"+site.getNameSpace()+"</namespace>\n");
                    strbr.append("<title>"+site.getTitle()+"</title>\n");
                    strbr.append("<description>"+site.getDescription()+"</description>\n");
                    //--------------Generación de submodelos------------------------------------------------
                    Iterator<SemanticObject> sitSubModels=site.getSemanticObject().listObjectProperties(site.swb_hasSubModel);
                    while(sitSubModels.hasNext())
                    {
                        SemanticObject sObj=sitSubModels.next();
                        File fileSubModel=new File(zipdirectory + "/"+sObj.getId()+".rdf");
                        FileOutputStream rdfout = new FileOutputStream(fileSubModel);
                        sObj.getModel().write(rdfout);
                        rdfout.flush();
                        rdfout.close();
                        //Agregar c/archivo .rdf de submodelos a arreglo de archivos
                        aFiles.add(fileSubModel);
                        //graba el directorio work de c/submodelo en archivo zip
                        directory = new File(modelspath + sObj.getId() + "/");
                        org.semanticwb.SWBUtils.IO.zip(directory, base, zos);
                        //Genera datos de c/summodelo en archivo siteInfo.xml
                        strbr.append("<model>");
                        //if(obj.instanceOf(WebSite.sclass)) //Que datos saco si es un rep de usuarios o de documentos y como los parseo despues
                        strbr.append("<id>"+sObj.getId()+"</id>\n");
                        strbr.append("<namespace>"+sObj.getModel().getNameSpace()+"</namespace>\n");
                        strbr.append("<title>"+sObj.getProperty(WebPage.swb_title)+"</title>\n");
                        strbr.append("<description>"+sObj.getProperty(WebPage.swb_description)+"</description>\n");
                        strbr.append("</model>");
                    }
                    strbr.append("</model>");
                    strbr.append("</siteinfo>");
                    out.write(strbr.toString().getBytes("utf-8"));
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                zos.close();
                

                 //--------------Agregar archivo rdf y xml generados a arraylist---------------------
                aFiles.add(new File(zipdirectory + site.getId() + ".rdf"));
                aFiles.add(new File(zipdirectory + "siteInfo.xml"));
                //--------------Barrer archivos de arrayList para pasar a arreglo de Files y eliminar---
                File [] files2add=new File[aFiles.size()];
                int cont=0;
                Iterator <File>itFiles=aFiles.iterator();
                while(itFiles.hasNext()){
                    File filetmp=itFiles.next();
                    files2add[cont]=filetmp;
                    cont++;
                }
                //Agregar archivos rfd de modelo y submodelos y archivo siteInfo.xml a zip existente
                org.semanticwb.SWBUtils.IO.addFilesToExistingZip(new File(zipFile), files2add);
                itFiles=aFiles.iterator();
                while(itFiles.hasNext()){
                    File filetmp=itFiles.next();
                    filetmp.delete();
                }
                
                new File(zipdirectory + site.getId() + ".rdf").delete();
                new File(zipdirectory + "siteInfo.xml").delete();


                //Envia mensage de estatus en admin de wb
                PrintWriter pout=response.getWriter();
                pout.println("<script type=\"text/javascript\">");
                pout.println("hideDialog();");
                pout.println("showStatus('Sitio Exportado');");
                pout.println("</script>");
            } else {
                strbr.append("<div class=\"swbform\">");
                strbr.append("<fieldset>");
                strbr.append("Seleccione el sitio a guardar como plantilla:");
                strbr.append("<table>");
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setAction("step2");
                Iterator<WebSite> itws = SWBContext.listWebSites();
                while (itws.hasNext()) {
                    WebSite ws = itws.next();
                    strbr.append("<tr><td>");
                    url.setParameter("wsid", ws.getId());
                    strbr.append("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + ws.getTitle() + "</a>");
                    strbr.append("</td></tr>");
                }
                strbr.append("</table>");
                strbr.append("</fieldset>");
                strbr.append("</div>");
                response.getWriter().println(strbr.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }

}
