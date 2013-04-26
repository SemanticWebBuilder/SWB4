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
 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;

/**
 *
 * @author Jei
 */
public class TestDelete {

    public TestDelete() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base=SWBUtils.getApplicationPath();
        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base+"../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    //@Test
    public void exportWebSite() {
        try {
            //Substituir x uri dinamica
            String uri = "sep";
            //Substituir x ruta dinamica
            String path = "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/SWB4/swb/build/web/work/";
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
                ws.getSemanticObject().getModel().write(out);
                //System.out.println(outputStream.toByteArray());            
                //outputStream.flush();
                //outputStream.close();
                out.flush();
                out.close();
            } catch (Exception e) {
            }
            //--------------Agregar archivo rdf generado a zip generado---------------------            
            File existingzip = new File(zipdirectory + uri + ".zip");
            File rdfFile = new File(zipdirectory + uri + ".rdf");
            java.io.File[] files2add = {rdfFile};
            org.semanticwb.SWBUtils.IO.addFilesToExistingZip(existingzip, files2add);
            //Eliminar rdf generado y ya agregado a zip
            rdfFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Test
    public void importWebSite() {
        try {
             //Substituir x uri dinamica
            String uri = "sep";
            String newUri="jorge";
            //Substituir x ruta dinamica
            String path = "C:/Archivos de programa/Apache Software Foundation/Tomcat 5.5/webapps/SWB4/swb/build/web/work/";
            String zipdirectory = path + "sitetemplates/";
            File zip = new File(zipdirectory + uri + ".zip");
            java.io.File extractTo=new File(path+newUri);
            //Archivos a parsear
            java.util.ArrayList aFiles=new java.util.ArrayList();
            aFiles.add("rdf");
            String nsparse="http://www.sep.gob.mx";
            String ns2parse="http://www.jorge.gob.mx";
            org.semanticwb.SWBUtils.IO.unzip(zip, extractTo, aFiles, nsparse, ns2parse);
            //Tomar rdf ya descomprimido y parseado para crear nuevo sitio   
            File rdf = new File(path+newUri+uri+".rdf");
            FileInputStream rdfis=new FileInputStream(rdf);
            //Mediante inputStream creado, generar sitio con metodo de jei, el cual no recuerdo su nombre :)
            
            //Eliminar archivo rdf
            rdf.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
