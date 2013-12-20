/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integraciÃ³n,
 * colaboraciÃ³n y conocimiento, que gracias al uso de tecnologÃ­a semÃ¡ntica puede generar contextos de
 * informaciÃ³n alrededor de algÃºn tema de interÃ©s o bien integrar informaciÃ³n y aplicaciones de diferentes
 * fuentes, donde a la informaciÃ³n se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creaciÃ³n original del Fondo de InformaciÃ³n y DocumentaciÃ³n
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trÃ¡mite.
 * 
 * INFOTEC pone a su disposiciÃ³n la herramienta SemanticWebBuilder a travÃ©s de su licenciamiento abierto al pÃºblico (â€˜open sourceâ€™),
 * en virtud del cual, usted podrÃ¡ usarlo en las mismas condiciones con que INFOTEC lo ha diseÃ±ado y puesto a su disposiciÃ³n;
 * aprender de Ã©l; distribuirlo a terceros; acceder a su cÃ³digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los tÃ©rminos y condiciones de la LICENCIA ABIERTA AL PÃšBLICO que otorga INFOTEC para la utilizaciÃ³n
 * del SemanticWebBuilder 4.0.
 * 
 * INFOTEC no otorga garantÃ­a sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implÃ­cita ni explÃ­cita,
 * siendo usted completamente responsable de la utilizaciÃ³n que le dÃ© y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 * 
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposiciÃ³n la siguiente
 * direcciÃ³n electrÃ³nica:
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.dimensiondata;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.LinkedList;
import javax.net.ssl.HttpsURLConnection;
import org.semanticwb.SWBUtils;
import org.semanticwb.dimensiondata.TestChkStatus; 
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Encoder;

/**
 *
 * @author serch
 */
public class test extends instanceObject{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
//        URL url = new URL("https://api.opsourcecloud.net/oec/0.9/myaccount");
//        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
//        connection.setRequestMethod("GET");
        BASE64Encoder enc = new sun.misc.BASE64Encoder();
        //cola para manejo e servidores 
      //  int MaxInst = 3; 
        //instanceObject [] serverList = new instanceObject[MaxInst];
        LinkedList cola = new LinkedList();
        instanceObject ob = new instanceObject();
        String userpassword = "superserch" + ":" + "Inf0t3c#";
        String encodedAuthorization = enc.encode( userpassword.getBytes() );
        String netName = "redprueba"; //el nombre de la red creada por el usuario
        String imageName= "swbClient2"; // el nombre de la imagen clonada
        String orgId = getOrgId(encodedAuthorization); 
//        System.out.println(" orgId:  " + orgId);
        String imageId = getImageId(encodedAuthorization, orgId, imageName); 
//        System.out.println("imageId :  " + imageId);
        String netId = getNetId(encodedAuthorization, orgId, netName);
        String description = "Server Description"; 
        String serverName = "swbClient";
        ob.setServerName(serverName);
        String info = setInfo(orgId, imageId, netId, description, serverName); 
        String serverStatus = getServerStatus(encodedAuthorization, orgId); 
//        System.out.println(serverStatus);

      deployServer(orgId, encodedAuthorization, info); 
        String salida;
        String status;
//       Check the deployed status to continue 
         do {  
            System.out.println("Pause");
            try{ Thread.sleep(90000); } 
            catch(InterruptedException e) {System.out.println("Thread Interrupted"); } 
            System.out.println("Wake up");
            salida= getServerStatus(encodedAuthorization, orgId);
            status=isdeployed(salida, serverName); 
            System.out.println(status);
        }while (status.equals("false") || status.equals("not found"));
        
         serverStatus = salida; 

         String serverId = getSerId(serverStatus, serverName);  
         ob.setServerId(serverId);
         String realName = "RealSer"; 
         ob.setRealName(realName);
        createRealS(encodedAuthorization, orgId, netId, serverId, realName); 
        String realInfo = getRealInfo(encodedAuthorization, orgId,netId);
        String realId = getRealId(realInfo, realName); 
        ob.setRealId(realId);
        String realIp = getRealIp(realInfo, realName); 
        ob.setIp(realIp);
      String farmId = getFarmId(encodedAuthorization, orgId, netId );
//        addToFarm(encodedAuthorization, orgId, netId, farmId,realId);
//     Agregamos el objeto creado a la cola
        cola.offer(ob);
       double currentLoad = getTotalAverage(cola); 
        System.out.println(" current Load:  "+ currentLoad);
       double MaxLoad = 0.6; 
       int MaxServer = 2; 
       int serverCount = getServerNum(serverStatus);

         if (currentLoad > MaxLoad && serverCount < MaxServer){
             System.out.println(" Se pasó el limite permitido");
         deployServer(orgId, encodedAuthorization, info); 
        
//       Check the deployed status to continue 
         do {  
            System.out.println("Pause");
            try{ Thread.sleep(90000); } 
            catch(InterruptedException e) {System.out.println("Thread Interrupted"); } 
            System.out.println("Wake up");
            salida= getServerStatus(encodedAuthorization, orgId);
            status=isdeployed(salida, serverName); 
            System.out.println(status);
        }while (status.equals("false") || status.equals("not found"));
        
         serverStatus = salida; 
         }
//     Inicia proceso de Eliminacion de Servidor. 
//      obtengo el objeto de la cola
        instanceObject del;
        if (cola.peek()!= null){
             del = (instanceObject)cola.poll();
         realId = del.getRealId();
       serverId = del.getServerId(); 
       serverName = del.getServerName();
       realName = del.getRealName(); 
        System.out.println("real Id Cola  :" + realId);
        removeFromFarm(encodedAuthorization,orgId, farmId, netId,realId ); 
        removeRealServer(encodedAuthorization, orgId,netId, realId ); 
        removeServer(encodedAuthorization, orgId, serverId, serverName); }
        else {System.out.println("didn't find servers");}
       
     

    }
    
    public static String getRESTget(String url, String encodedAuth) throws IOException{
        String ret = "";
        URL lurl = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection)lurl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic "+encodedAuth);
        connection.connect();
        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        byte [] buff = new byte[8192];
        int length;
        while ((length=bis.read(buff))>0){
            ret = ret + new String(buff,0,length);
        }
        bis.close();
        return ret;
    }
     public static String postREST(String url, String encodedAuth, String info) throws IOException{
        String ret = "";
        byte [] infos = info.getBytes(); 
         System.out.println(infos);
        URL lurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)lurl.openConnection();
        con.setRequestProperty("Content-Type", "application/xml");
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic "+encodedAuth);
        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        if (!info.equals(null)&& !info.equals("")){
        out.write(infos);
        out.close();}
//        FileInputStream fileIn = new FileInputStream(info);
//        byte[] buffer = new byte[1024];
//        int nbRead;
//        do
//        {
//        nbRead = fileIn.read(buffer);
//        if (nbRead>0) {
//        out.write(buffer, 0, nbRead);
//        }
//        } while (nbRead>=0);
//        out.close(); 
       
       
        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
        byte [] buff = new byte[8192];
        int length;
        while ((length=bis.read(buff))>0){
            ret = ret + new String(buff,0,length);
        }
        bis.close();
        return ret;
    }

    private static String getOrgId(String aut) throws IOException {
       String xml = getRESTget("https://api.opsourcecloud.net/oec/0.9/myaccount", aut); 
        System.out.println(xml);
        Document doc = SWBUtils.XML.xmlToDom(xml);  
        NodeList id = doc.getElementsByTagName("ns2:orgId");
        String orgId=id.item(0).getTextContent();
        System.out.println("orgid:"+orgId );
        return orgId; 
    }

    private static String getImageId(String encodedAuthorization,String orgId, String imageName) throws IOException {
        String img = getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/image", encodedAuthorization);
        Document doc2 = SWBUtils.XML.xmlToDom(img); 
      
       
       NodeList serverList = doc2.getElementsByTagName("ServerImage");
                       
       int size = serverList.getLength(); 
       for (int i =0 ;i< size ; i++ ){
        Node nodo = serverList.item(i);
        NodeList atri = nodo.getChildNodes(); 
        int r = atri.getLength(); 
       // for (int j = 0 ; j< r ; j++){
        System.out.println("nodo "+i+": "+atri.item(i));
       String name = atri.item(5).getTextContent(); 
       System.out.println(name); 
       if (name.equals(imageName)){
       String imageId = atri.item(1).getTextContent();
           System.out.println(" imageID : " + imageId );     
       return imageId;
     //  }
       }
       }

       String ms = "not found"; 
       return ms; 
    }

    private static String getNetId(String encodedAuthorization, String orgId, String netName) throws IOException {
       String doc = getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/networkWithLocation", encodedAuthorization);
        System.out.println(doc);
        Document docn = SWBUtils.XML.xmlToDom(doc); 
 
       NodeList networkList = docn.getElementsByTagName("ns4:network");             
       int size = networkList.getLength(); 
        System.out.println("elementos tag " + size);
       for (int i =0 ;i< size ; i++ ){
        System.out.println("entro a un network");
        Node nodo = networkList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
        int r = atributes.getLength(); 
        System.out.println(" atributos length: "+ r);
       String name = atributes.item(3).getTextContent(); 
       System.out.println(name); 
       if (name.equals(netName)){
       String networkId = atributes.item(1).getTextContent();
       System.out.println("  netId: " + networkId);
       return networkId; 
   
       }
       }
        
        
        
       return null;

    }

    private static String setInfo(String orgId, String imageId, String netId, String description, String serverName) {
       StringBuilder list = new StringBuilder(); 
       list.append("<Server xmlns='http://oec.api.opsource.net/schemas/server'>\n"); 
       list.append("<name>"); 
       list.append(serverName); 
       list.append("</name>\n"); 
       list.append("<description>");
       list.append(description);
       list.append("</description>\n");
       list.append("<vlanResourcePath>/oec/"); 
       list.append(orgId);
       list.append("/network/"); 
       list.append(netId);
       list.append("</vlanResourcePath>\n");
       list.append("<imageResourcePath>/oec/base/image/");
       list.append(imageId); 
       list.append("</imageResourcePath>\n"); 
       list.append("<administratorPassword>zyxw4321</administratorPassword>\n");
       list.append("<isStarted>true</isStarted>\n"); 
       list.append("</Server>");
       String texto = list.toString(); 
       return texto;
       
    }

    private static String getServerStatus(String encodedAuthorization, String orgId) throws IOException {
        String doc = getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/serverWithState?", encodedAuthorization);
        return doc;
      
    }

    private static void deployServer(String orgId, String encodedAuthorization, String info) throws IOException {
       postREST("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/server", encodedAuthorization, info);
       
    }

    private static String getSerId(String salida, String serverName) {
        Document docn = SWBUtils.XML.xmlToDom(salida);
        String prueba; 
        NodeList serversList = docn.getElementsByTagName("serverWithState");             
       int size = serversList.getLength(); 
        System.out.println("elementos tag " + size);
       for (int i =0 ;i< size ; i++ ){
        Node nodo = serversList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
       String name = atributes.item(1).getTextContent(); 
       System.out.println(name); 
        int r = atributes.getLength(); 
            for (int j=0 ; j<r ; j++){
                System.out.println("atributo  " + j+ ":" + atributes.item(j));
                
            }
       if (name.equals(serverName)){
       NamedNodeMap serverId = nodo.getAttributes(); 
       String id = serverId.getNamedItem("id").getTextContent();
       System.out.println("serverId:   " + id);
       return id; 
   
       }
        
         
       }
        
        return "not found"; 
    }

    private static void createRealS(String encodedAuthorization, String orgId, String netId, String serverId, String name) throws IOException {
        String info = "<NewRealServer xmlns='http://oec.api.opsource.net/schemas/vip'>\n" +
                      "<name>"+name+"</name>\n" +
                      "<serverId>"+serverId+"</serverId>\n" +
                      "<inService>true</inService>\n" +
                      "</NewRealServer>"; 
        postREST("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/network/"+netId+"/realServer", encodedAuthorization, info);
   

    }

    private static String getFarmId(String encodedAuthorization, String orgId, String netId) throws IOException {
         String doc = getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/network/"+netId+"/serverFarm", encodedAuthorization);
        System.out.println(doc);
        Document docn = SWBUtils.XML.xmlToDom(doc); 
 
       NodeList networkList = docn.getElementsByTagName("ns5:serverFarm");             
       int size = networkList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = networkList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
       
       String name = atributes.item(3).getTextContent(); 
       System.out.println(name); 
       if (name.equals("swbFarm")){
       String farmId = atributes.item(1).getTextContent();
       System.out.println("  famrId: " + farmId);
       return farmId; 
   
       }
       }  
       return "notfound"; 
    }

    private static void addToFarm(String encodedAuthorization, String orgId, String netId, String farmId, String serverId) throws IOException {
        String info = "realServerId="+URLEncoder.encode(serverId, "UTF-8")+  
                       "&realServerPort="+URLEncoder.encode("80", "UTF-8");
        postRESTtwo("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/network/"+netId+"/serverFarm/"+farmId+"/addRealServer", encodedAuthorization, info);
    }

    private static String isdeployed(String salida, String serverName) {
         Document docn = SWBUtils.XML.xmlToDom(salida);
        
        NodeList serversList = docn.getElementsByTagName("serverWithState");             
       int size = serversList.getLength(); 
        System.out.println("elementos tag " + size);
       for (int i =0 ;i< size ; i++ ){
        Node nodo = serversList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
       String name = atributes.item(1).getTextContent(); 
       System.out.println(name); 
      
       if (name.equals(serverName)){
       String status = atributes.item(23).getTextContent();
       System.out.println(" status " +status);
       return status; 
       }    
       }
       return "not found"; 
    }

    private static String getRealInfo(String encodedAuthorization, String orgId, String netId) throws IOException {
        String doc = getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/network/"+netId+"/realServer", encodedAuthorization);
        System.out.println(doc);
        
       return doc; 
    }

    private static void postRESTtwo(String url, String encodedAuthorization, String info) throws IOException {
           String ret = "";
            byte [] infos = info.getBytes();
        URL lurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)lurl.openConnection();
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic "+encodedAuthorization);
        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        if (!info.equals(null)&& !info.equals("")){
        out.write(infos);
        out.close();}
        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
        byte [] buff = new byte[8192];
        int length;
        while ((length=bis.read(buff))>0){
            ret = ret + new String(buff,0,length);
        }
        bis.close();
    }

    private static void removeFromFarm(String encodedAuthorization, String orgId, String farmId, String netId, String realId) throws UnsupportedEncodingException, IOException {
        String info = "realServerId="+URLEncoder.encode(realId, "UTF-8")+  
                       "&realServerPort="+URLEncoder.encode("80", "UTF-8");
        postRESTtwo("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/network/"+netId+"/serverFarm/"+farmId+"/removeRealServer", encodedAuthorization, info);
    }

    private static void removeRealServer(String encodedAuthorization, String orgId, String netId, String realId) throws IOException {
       getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/network/"+netId+"/realServer/"+realId+"?delete", encodedAuthorization);
    }

    private static void removeServer(String encodedAuthorization, String orgId, String serverId, String serverName) throws IOException {
           getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/server/"+serverId+"?poweroff", encodedAuthorization);
              String status= ""; 
             do{
            String doc = getServerStatus(encodedAuthorization, orgId);
              Document docn = SWBUtils.XML.xmlToDom(doc); 
             NodeList serversList = docn.getElementsByTagName("serverWithState");             
             int size = serversList.getLength(); 
            System.out.println("elementos tag " + size);
            for (int i =0 ;i< size ; i++ ){
             Node nodo = serversList.item(i);
            NodeList atributes = nodo.getChildNodes(); 
//            int r = atributes.getLength(); 
//            for (int j=0 ; j<r ; j++){
//                System.out.println("atributo  " + j+ ":" + atributes.item(j));
//                
//            }
            String name = atributes.item(1).getTextContent(); 
            System.out.println(name); 
            if (name.equals(serverName)){
            NamedNodeMap stat = nodo.getAttributes(); 
            status = atributes.item(25).getTextContent();
            System.out.println(" status " +status);
            }} 
             

           }while (status.equals("true"));
      getRESTget("https://api.opsourcecloud.net/oec/0.9/"+orgId+"/server/"+serverId+"?delete", encodedAuthorization);
    }

    private static String getRealId(String realInfo, String realname) {
         Document docn = SWBUtils.XML.xmlToDom(realInfo); 
 
       NodeList serverList = docn.getElementsByTagName("ns5:realServer");             
       int size = serverList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = serverList.item(i);
        NodeList atributes = nodo.getChildNodes();     
       String name = atributes.item(3).getTextContent(); 
       System.out.println(name); 
       if (name.equals(realname)){
       String realId = atributes.item(1).getTextContent();
       System.out.println("  realId: " + realId);
       return realId; 
   
       }
       }
       return "not found"; 
    }

    private static String getRealIp(String realInfo, String realName) {
         Document docn = SWBUtils.XML.xmlToDom(realInfo); 
 
       NodeList serverList = docn.getElementsByTagName("ns5:realServer");             
       int size = serverList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = serverList.item(i);
        NodeList atributes = nodo.getChildNodes();     
       String name = atributes.item(3).getTextContent(); 
       System.out.println(name); 
       if (name.equals(realName)){
       String realIp= atributes.item(9).getTextContent();
       System.out.println("  realIp: " + realIp);
       return realIp; 
   
       }
       }
       return "not found"; 
    }

    private static double getTotalAverage(LinkedList cola) throws IOException {
        double sum = 0.0; 
        int count = 1; 
        instanceObject ob;
        String ip; 
        String id; 
        System.out.println( "size cola :  "+ cola.size());
        for (int j = 0; j< cola.size(); j++ ){
         ob = (instanceObject)cola.get(j); 
         id = ob.getRealId(); 
            System.out.println("id"+id);
         ip = ob.getIp();
            System.out.println("ip" + ip);
         sum += TestChkStatus.getCurrentUsage(ip); 
         count ++;  
        }
       double avg= sum/count;
       return avg; 
    }

    private static int getServerNum(String serverStatus) {
            String doc = serverStatus;
              Document docn = SWBUtils.XML.xmlToDom(doc); 
             NodeList serversList = docn.getElementsByTagName("serverWithState");             
             int size = serversList.getLength(); 
             int count = size -1 ; 
             System.out.println(count);
             return count; 
    }
    
    
}