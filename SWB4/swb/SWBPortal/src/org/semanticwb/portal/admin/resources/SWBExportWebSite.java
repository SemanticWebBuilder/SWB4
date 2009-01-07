/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author jorge.jimenez
 */
public class SWBExportWebSite extends GenericAdmResource {
    
    private static Logger log = SWBUtils.getLogger(SWBExportWebSite.class);

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            //Substituir x uri dinamica
            String uri = "sep";
            String path = SWBPlatform.getWorkPath()+"/";
            String zipdirectory = path + "sitetemplates/";
            //---------Generación de archivo zip de carpeta work de sitio especificado-------------
            java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(new FileOutputStream(zipdirectory + uri + ".zip"));
            java.io.File directory = new File(path + uri + "/");
            java.io.File base = new File(path + uri);
            org.semanticwb.SWBUtils.IO.zip(directory, base, zos);
            zos.close();
            //-------------Generación de archivo rdf del sitio especificado----------------
            try {
                WebSite ws = SWBContext.getWebSite(uri);
                File file = new File(zipdirectory + uri + ".rdf");
                //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                FileOutputStream out = new FileOutputStream(file);
                String text = "<!--|ns|http://www.sep.gob.mx|ns|-->\n";
                out.write(text.getBytes());
                ws.getSemanticObject().getModel().write(out);

                //System.out.println(outputStream.toByteArray());            
                //outputStream.flush();
                //outputStream.close();
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //--------------Agregar archivo rdf generado a zip generado---------------------            
            File existingzip = new File(zipdirectory + uri + ".zip");
            File rdfFile = new File(zipdirectory + uri + ".rdf");
            java.io.File[] files2add = {rdfFile};
            org.semanticwb.SWBUtils.IO.addFilesToExistingZip(existingzip, files2add);
            //Eliminar rdf generado y ya agregado a zip
            rdfFile.delete();
        } catch (Exception e) {
            log.debug(e);
        }
    }
}
