/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.junit.*;
import org.semanticwb.portal.SWBDBAdmLog;


/**
 *
 * @author jorge.jimenez
 */
//@RunWith(Suite.class)
//@Suite.SuiteClasses({})
public class TestServices {

    @Test
    public void testcreateWebSite(){
        System.out.println("CreateSite");
        String uri="www.infotec.com.mx";
        
        //Model model = ModelFactory.createDefaultModel();
        //SemanticModel swbModel=new SemanticModel("GeorgeModel",model);
        //-NO-SWBModel swbModel1=swbModel.createSemanticObject(uri, SWBContext.getVocabulary().SWBModel);
        //SWBModel swbModel2=SWBContext.createSWBModel(swbModel, "www.infotec.com.mx");
        
        /*
        SemanticModel semModel=SWBInstance.getSemanticMgr().getSystemModel();
        
        System.out.println("CreateSite-1");
        
        HomePage hpage=SWBContext.createHomePage(semModel, uri);
        System.out.println("CreateSite-2");
         hpage.setTitle("home");
         System.out.println("CreateSite-3");
         WebSite website=SWBContext.createWebSite(semModel, uri);
         website.addHome(hpage);
         System.out.println("CreateSite-4");
         System.out.println("Entra a createWebSite-4");
         */
         SWBDBAdmLog swbAdmLog=new SWBDBAdmLog("user","create","www.infotec.com.mx",uri,"description",null);
         System.out.println("Entra a createWebSite-5");
         try{
            swbAdmLog.create();
         }catch(Exception e){
             e.printStackTrace();
         }
         
         System.out.println("Entra a createWebSite-6");
         //return website;
        
        
        //System.out.println("Model name:"+swbModel2.getName());
        
        
    }
    
}