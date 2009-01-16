
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import org.semanticwb.SWBPlatform;
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
        SWBPlatform.createInstance(null);
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

    @Test
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

    @Test
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
