package org.semanticwb.portal;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.*;
import org.semanticwb.platform.SemanticClass;

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

    //@Test
    public void syncAll(){
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("nuevo_usr");
        repository.syncUsers();
    }

    @Test
    public void setExternalAtts(){
        UserRepository repository = null;
        repository = SWBContext.getUserRepository("nuevo_usr");
        User user = repository.getUserByLogin("alma");
        if (null==user){
            System.out.println("Creando User alma");
            user = repository.createUser();
            user.setLogin("alma");
            user.setActive(true);
            user.setPassword("alma");
            
        }
        Iterator<SemanticClass> it = UserTypeDef.sclass.listSubClasses();
        while (it.hasNext()){
            SemanticClass utd = it.next();
            //user.addUserType(utd.getName());
            System.out.println(utd.getName());
        }
    }
}
