/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb;

import org.junit.*;

/**
 *
 * @author Jei
 */
public class TestSWBInstance {
    private Logger log=SWBUtils.getLogger(TestSWBInstance.class);

    public TestSWBInstance()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        SWBPlatform.createInstance(null);
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
    public void test()
    {
        long time=System.currentTimeMillis();
        
        //long x=SWBPlatform.getCounter("admin/Template");
        //System.out.println("Counter:"+x);
        
        System.out.println("Time:"+(System.currentTimeMillis()-time));
    }
}
