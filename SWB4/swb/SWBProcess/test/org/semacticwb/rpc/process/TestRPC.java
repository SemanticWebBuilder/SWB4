/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semacticwb.rpc.process;

import java.net.URI;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.xmlrpc.RPCProcess;

import org.semanticwb.xmlrpc.XmlRpcProxyFactory;

/**
 *
 * @author victor.lorenzana
 */
public class TestRPC {

    public TestRPC() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void getProcessInstanceStatusTest() {

        try
        {
            RPCProcess rpcProcessProxy=XmlRpcProxyFactory.newInstance(RPCProcessProxy.class, new URI("http://192.168.5.117:8888/rpcprocess"));
            int status=rpcProcessProxy.getProcessInstanceStatus("APIKEY", "9221","eworkplace");
            
            Assert.assertEquals(status, 4);
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        
    }

}