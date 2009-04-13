/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sdb.SDBFactory;
import com.hp.hpl.jena.sdb.StoreDesc;
import com.hp.hpl.jena.sdb.Store;
import java.util.Iterator;
import org.junit.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author Jei
 */
public class TestSDB {

   private Logger log=SWBUtils.getLogger(TestCreate.class);

    public TestSDB()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.createInstance(null);
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testSDB()
    {
        StoreDesc sd=new StoreDesc("layout2", SWBUtils.DB.getDatabaseName());
        Store store = SDBFactory.connectStore(SWBUtils.DB.getDefaultConnection(),sd);
        //store.getTableFormatter().format();
        store.getTableFormatter().create();
        //store.getTableFormatter().truncate();
        Model model=SDBFactory.connectDefaultModel(store);
        Iterator<Statement> it=model.listStatements();
        while(it.hasNext())
        {
            Statement st=it.next();
            System.out.println(st.getSubject()+" "+st.getPredicate()+" "+st.getObject());
        }
    }
}
