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
package org.semanticwb.model;

//~--- non-JDK imports --------------------------------------------------------

import org.junit.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class TestWebPages {
    private Logger log = SWBUtils.getLogger(TestCreate.class);

    public TestWebPages() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
        String base = SWBUtils.getApplicationPath();

        SWBPlatform.createInstance();
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology(base + "../../../web/WEB-INF/owl/office.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {}

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testCreate() {
        WebSite site = SWBContext.getWebSite("sep");

        if (site == null) {
            site = SWBContext.createWebSite("sep", "http://www.sep.gob.mx#");

            WebPage home = site.createWebPage("home");

            home.setTitle("Home");
            site.setHomePage(home);

//          for(int x=0;x<10;x++)
//          {
//              WebPage page=site.createWebPage("page"+x);
//              page.setTitle("Page"+x);
//              page.addParentWebPage(home);
//              for(int y=0;y<10;y++)
//              {
//                  WebPage ipage=site.createWebPage("page"+x+"_"+y);
//                  ipage.setTitle("Page"+x+"."+y);
//                  ipage.addParentWebPage(page);
//              }
//          }
        }
    }

    // @Test
    public void testRead() {
        WebSite site = SWBContext.getWebSite("sep");
        WebPage home = site.getHomePage();

        System.out.println("Home:" + home);
        printChilds(home);
    }

    public void printChilds(WebPage page) {
        Iterator<WebPage> it = page.listChilds();

        while (it.hasNext()) {
            WebPage p = it.next();

            System.out.println("Page:" + p);
            printChilds(p);
        }
    }

    @Test
    public void testCreateLocale() {
        WebSite site = SWBContext.getWebSite("sep");
        WebPage home = site.getHomePage();

        home.setTitle("6_Inicio");
        home.setTitle("6_Inicio_es", "es");
        home.setTitle("6_Home_en", "en");
        home.setTitle("6_Home_fr", "fr");
    }

    @Test
    public void testReadLocale() {
        WebSite site = SWBContext.getWebSite("sep");
        WebPage home = site.getHomePage();

        System.out.println("Home:" + home.getId() + " " + home.getURI() + " " + home.hashCode() + " "
                           + home.getSemanticObject().getRDFResource().getModel());
        home = site.getWebPage("home");
        System.out.println("Home:" + home.getId() + " " + home.getURI() + " " + home.hashCode() + " "
                           + home.getSemanticObject().getRDFResource().getModel());
        System.out.println("getTitle:" + home.getTitle());
        System.out.println("getTitle_es:" + home.getTitle("es"));
        System.out.println("getTitle_en:" + home.getTitle("en"));
        System.out.println("getTitle_fr:" + home.getTitle("fr"));
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
