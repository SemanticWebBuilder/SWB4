package org.semanticwb.portal;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.UserRepository;

/**
 *
 * @author serch
 */
public class ExternalRepositoryTest
{

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        //SWBPlatform.setUseDB(false);
        SWBPlatform.createInstance(null);
    //SWBPlatform.
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

    @Test
    public void syncAll(){
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("nuevo_usr");
        repository.syncUsers();
    }
}
