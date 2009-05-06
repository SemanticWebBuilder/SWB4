/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;


import java.util.ArrayList;
import org.junit.Test;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.semanticwb.SWBPlatform;
/**
 *
 * @author Administrador
 */
public class Devices {
    //static PrintStream out;

    @BeforeClass
    public static void setUpClass() throws Exception {
        SWBPlatform.createInstance(null);
        //out = System.out;
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
    public void display() {
        StringBuilder ret = new StringBuilder();
        String space="";

        WebSite webSite = SWBContext.getWebSite("x");

        ArrayList x=new ArrayList();
        Iterator<Device> itDevices = webSite.listDevices();
        while (itDevices.hasNext()) {
            x.add(itDevices.next());
        }
        x.trimToSize();
        
        ret.append("<select id=\"wb_device\" name=\"wb_device\" size=\"10\" multiple=\"multiple\">\n");
        //Iterator<Device> itDevices = webSite.listDevices();
        while(!x.isEmpty()) {
            Device device = (Device)x.get(0);
            ret.append("<option value=\""+device.getId()+"\">"+space+device.getTitle()+"</option>\n");
            x.remove(0);
            if(device.listChilds().hasNext()) {
                display2(x, device, ret, space+"&nbsp;&nbsp;&nbsp;");
            }            
        }
        ret.append("</select>\n");
        System.out.println(ret);
    }

    public void display2(ArrayList x, Device node, StringBuilder ret, String space) {
        ArrayList xx=new ArrayList();
        Iterator<Device> itDevices = node.listChilds();
        while (itDevices.hasNext()) {
            xx.add(itDevices.next());
        }
        xx.trimToSize();

        while(!xx.isEmpty()) {
            Device device = (Device)xx.get(0);
            ret.append("<option value=\""+device.getId()+"\">"+space+device.getTitle()+"</option>\n");
            x.remove(device);
            xx.remove(0);
            if(device.listChilds().hasNext()) {
                display2(x, device, ret, space+"&nbsp;&nbsp;&nbsp;");
            }            
        }
    }

}
