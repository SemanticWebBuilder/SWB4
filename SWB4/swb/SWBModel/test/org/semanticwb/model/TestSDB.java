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

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.Store;
import com.hp.hpl.jena.sdb.StoreDesc;

import org.junit.*;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

//~--- JDK imports ------------------------------------------------------------

import java.util.Iterator;

/**
 *
 * @author Jei
 */
public class TestSDB {
    private Logger log = SWBUtils.getLogger(TestCreate.class);

    public TestSDB() {}

    @BeforeClass
    public static void setUpClass() throws Exception {

        // SWBPlatform.createInstance(null);
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
    public void testSDB() {
        StoreDesc sd    = new StoreDesc("layout2", SWBUtils.DB.getDatabaseName());
        Store     store = SDBFactory.connectStore(SWBUtils.DB.getDefaultConnection(), sd);

        // store.getTableFormatter().format();
        store.getTableFormatter().create();

        // store.getTableFormatter().truncate();
        Model               model = SDBFactory.connectDefaultModel(store);
        Iterator<Statement> it    = model.listStatements();

        while (it.hasNext()) {
            Statement st = it.next();

            System.out.println(st.getSubject() + " " + st.getPredicate() + " " + st.getObject());
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
