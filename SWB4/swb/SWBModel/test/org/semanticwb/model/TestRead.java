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
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class TestRead {
    private Logger log = SWBUtils.getLogger(TestRead.class);

    public TestRead() {}

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
    // @Test
    public void test() {
        WebSite site = SWBContext.getWebSite("sep");
        long    time = System.currentTimeMillis();

        for (int x = 0; x < 1000; x++) {
            WebPage page = site.getWebPage("Page" + x);

            // WebPage page=(WebPage)SWBInstance.getSemanticMgr().getOntology().getSemanticObject("Page"+x);
            // WebPage page=SWBContext.getWebPage("Page"+x);
            String title = page.getTitle();

            System.out.println("Title:" + title);
        }

        System.out.println("Time:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        Iterator<WebPage> it = site.listWebPages();

        while (it.hasNext()) {
            WebPage page  = it.next();
            String  title = page.getURI();

            System.out.println("Title:" + title);
        }

        System.out.println("Time:" + (System.currentTimeMillis() - time));
    }

    @Test
    public void testFormView() {
        SemanticOntology ont          = SWBPlatform.getSemanticMgr().getOntology();
        SemanticProperty propertyMode = new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI
                                            + "propertyMode"));
        SemanticProperty propertyRef = new SemanticProperty(ont.getRDFOntModel().getProperty(SemanticVocabulary.URI
                                           + "propertyRef"));
        Iterator<FormView> it = FormView.swbxf_FormView.listGenericInstances();

        while (it.hasNext()) {
            FormView view = it.next();

            System.out.println("View:" + view.getId());

            Iterator<SemanticObject> itp = view.listCreateProperties();

            while (itp.hasNext()) {
                SemanticObject   obj  = itp.next();
                String           mode = obj.getProperty(propertyMode);
                SemanticObject   ref  = obj.getObjectProperty(propertyRef);
                SemanticProperty prop = obj.transformToSemanticProperty();

                System.out.println("-->prop:" + prop.getURI() + " mode:" + mode + " ref:" + ref);
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
