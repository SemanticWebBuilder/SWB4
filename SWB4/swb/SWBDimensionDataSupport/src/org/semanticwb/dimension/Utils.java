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
package org.semanticwb.dimension;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.dimensiondata.instanceObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.misc.BASE64Encoder;

public final class Utils {

    private static Logger log = SWBUtils.getLogger(Utils.class);
    private static BASE64Encoder enc = new sun.misc.BASE64Encoder();
    public static String encodedAuthorization;
    private static String netName = null; 
    private static String imageName = "swbClient2";
    private static LinkedList cola = new LinkedList(); 
    private static String orgId = null;
    public static String url = null; 
    public static String urlOrg = null; 
            
    public static void initServices() throws IOException {
        String access = SWBDimensionDataUtils.getValueOf("/user");
        String secret = SWBDimensionDataUtils.getValueOf("/password");
        String userpass = access + ":"+ secret; 
//        System.out.println("userpass:  " + userpass);
        if (null != access && null != secret) {
            encodedAuthorization = enc.encode( userpass.getBytes() );

               if (null != SWBPortal.getEnv("swb/regionEndPoint", null)) { 
               url = ("https://" + SWBPortal.getEnv("swb/regionEndPoint")+"/oec/0.9/");
               
            }
        }
    }
    
    public static String getOrgId() throws IOException {
//        System.out.println("url: " + url);
        if (url != null && url != ""){
        String xml = getRESTget( url +"myaccount");
        Document doc = SWBUtils.XML.xmlToDom(xml);  
        NodeList id = doc.getElementsByTagName("ns2:orgId");
        String orgId=id.item(0).getTextContent();
        System.out.println("orgid:"+orgId );
        return orgId; }
        return "not found"; 
    }

    public static String getImageId(String imageName) throws IOException {
         if (urlOrg != null && urlOrg!= ""){
        String img = getRESTget(urlOrg+"/image");
        Document doc2 = SWBUtils.XML.xmlToDom(img); 
       NodeList serverList = doc2.getElementsByTagName("ServerImage");                      
       int size = serverList.getLength(); 
       for (int i =0 ;i< size ; i++ ){
        Node nodo = serverList.item(i);
        NodeList atri = nodo.getChildNodes(); 
        int r = atri.getLength(); 
       // for (int j = 0 ; j< r ; j++){
      
       String name = atri.item(5).getTextContent(); 
//       System.out.println(name); 
       if (name.equals(imageName)){
       String imageId = atri.item(1).getTextContent();
           System.out.println(" imageID : " + imageId );     
       return imageId;
     //  }
       }
       }
       }
       String ms = "not found"; 
       return ms;
    }

    public static String getFarmId(String netId, String farmName) throws IOException {
         if (urlOrg != null && urlOrg!= ""){
        String doc = getRESTget(urlOrg+ "/network/"+netId+"/serverFarm");
        Document docn = SWBUtils.XML.xmlToDom(doc); 
 
       NodeList networkList = docn.getElementsByTagName("ns5:serverFarm");             
       int size = networkList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = networkList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
       
       String name = atributes.item(3).getTextContent(); 
//       System.out.println(name); 
       if (name.equals(farmName)){
       String farmId = atributes.item(1).getTextContent();
       System.out.println("  famrId: " + farmId);
       return farmId; 
       }
       }
       }  
       return "notfound"; 
    }
//
    public static List<String> getNetNames() throws IOException {
        List<String> netnames = new ArrayList<String> (); 
        if (urlOrg != null && urlOrg!= ""){
      String doc = getRESTget(urlOrg+"/networkWithLocation");
        Document docn = SWBUtils.XML.xmlToDom(doc); 
 
       NodeList networkList = docn.getElementsByTagName("ns4:network");             
       int size = networkList.getLength(); 
//        System.out.println("elementos tag " + size);
       for (int i =0 ;i< size ; i++ ){
//        System.out.println("entro a un network");
        Node nodo = networkList.item(i);
        NodeList atributes = nodo.getChildNodes();
       String name = atributes.item(3).getTextContent(); 
       netnames.add(name);  
       }
       return netnames; 
       }
       return netnames; 
       }
//
//    public static InstanceType[] getInstanceTypes() {
//        return InstanceType.class.getEnumConstants();
//    }
//
//    public static List<AvailabilityZone> getAvailabilityZones() {
//        return ec2.describeAvailabilityZones().getAvailabilityZones();
//    }

    public static void runInstance(final String info, String serverName) throws IOException {
         if (urlOrg != null && urlOrg!= ""){ 
        postREST(urlOrg+"/server", info);
          String salida;
          String serverStatus = getServerStatus(); 
        String status;
//       Check the deployed status to continue 
         do {  
            System.out.println("Pause");
            try{ Thread.sleep(90000); } 
            catch(InterruptedException e) {System.out.println("Thread Interrupted"); } 
            System.out.println("Wake up");
            salida= getServerStatus();
            status=isdeployed(salida, serverName); 
            System.out.println("status validación :  " + status);
        }while (status.equals("false") || status.equals("not found"));
         serverStatus = salida; 
         } 
        System.out.println("Cree el Servidor con nombre: " + serverName);
    }
    
    public static String setInfo(String imageId, String netId,  String serverName) {
       StringBuilder list = new StringBuilder(); 
       list.append("<Server xmlns='http://oec.api.opsource.net/schemas/server'>\n"); 
       list.append("<name>"); 
       list.append(serverName); 
       list.append("</name>\n"); 
       list.append("<description>");
       list.append("Server deployed by DimensionDAtaImplementation Service");
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

    /**
     *
     * @param instanceId
     * @throws IOException
     */
    public static void removeServer (InstanceData instance, String farmId, String netId) throws IOException {
        System.out.println("farm Id remove :  "+ farmId);
        System.out.println("netId remove : " + netId);
        String id = instance.getServerId();
        log.trace("Terminating "+id);   
         String realId = instance.getRealId();
         System.out.println("realId: "+instance.getRealId());
       String serverId = instance.getServerId(); 
        System.out.println("serverId" + instance.getServerId());
       String serverName = instance.getServerName();
        System.out.println("serverName" + instance.getServerName() );
        removeFromFarm( farmId, netId,realId ); 
        removeRealServer(netId, realId ); 
        removeServer(serverId, serverName); 
        
        
    }

//    public static String createLoadBalancer(final String name, final String protocol, final int balancedPort, final int instencesPort, final String zone) {
//        
//    
//               
//    }

    public static void addToFarm(String netId, String farmId, String realServerId) throws UnsupportedEncodingException, IOException {
       String info = "realServerId="+URLEncoder.encode(realServerId, "UTF-8")+  
                       "&realServerPort="+URLEncoder.encode("80", "UTF-8");
        if (urlOrg != null && urlOrg!= ""){
        postRESTtwo(urlOrg+"/network/"+netId+"/serverFarm/"+farmId+"/addRealServer", info);}
    }

  
//    public static List<LoadBalancerDescription> getLoadBalancerDescriptions() {
//        return alb.describeLoadBalancers().getLoadBalancerDescriptions();
//    }

   public static List<InstanceData> getRunningInstances() throws IOException {
       List <InstanceData> run = new ArrayList<InstanceData>();
       String salida = getServerStatus(); 
       if (salida != null ){
       Document docn = SWBUtils.XML.xmlToDom(salida);
       String name = ""; 
       String deployed = "";
       String started= "";
       if(docn != null){
       NodeList serversList = docn.getElementsByTagName("serverWithState");             
       int size = serversList.getLength(); 
      
       for (int i =0 ;i< size ; i++ ){
       Node nodo = serversList.item(i);
       NodeList atributes = nodo.getChildNodes(); 
       name = atributes.item(1).getTextContent(); 
//       System.out.println(name); 
       deployed = atributes.item(23).getTextContent(); 
       started = atributes.item(25).getTextContent(); 
       NamedNodeMap serverId = nodo.getAttributes(); 
       String id = serverId.getNamedItem("id").getTextContent();
//       System.out.println("serverId:   " + id);
       if (name!=null && !(name.equals("")) && deployed!= null && !(deployed.equals(""))
            && started!= null && !(started.equals("")) ){
       InstanceData ins = new InstanceData(name, id, deployed, started);
       run.add(ins);
       
       }
       }
       } }
       return run; 
    }

//    public static Reservation createInstance(final String placement, final String amiID,
//            final String instanceType, final Collection<String> securityGroups,
//            final String keyPair, final String memory, final String appServ, final String elasticAdminDNS) {
//        log.trace("creating instance");
//        Placement place = new Placement(placement);
//        return runInstance(amiID, InstanceType.fromValue(instanceType),
//                securityGroups, keyPair, getScriptEncoded(memory, appServ, elasticAdminDNS), place);
//    }

//    public static List<Address> getElastic() {
//        return ec2.describeAddresses().getAddresses();
//    }

    public static double getCPUAverage(InstanceData instance, String netId) throws IOException {
      System.out.println("ip instancia mandada"+  instance.getIp());
      String ip = instance.getIp(); 
      String doc = Utils.getRealInfo(netId); 
      
      ip = getRealIp(doc, instance.getServerName()); 
        System.out.println("ip obtenida por nombre: " + instance.getServerName() + "con ip: " + ip);
        Socket statusSocket = new Socket(ip, 666);
        PrintWriter out =
                new PrintWriter(statusSocket.getOutputStream(), true);
        BufferedReader in =
                new BufferedReader(
                new InputStreamReader(statusSocket.getInputStream()));
        String userInput = ""; 
        String infos;
                
        while ((infos=in.readLine()) != null) {
            userInput += infos + ("\n");
            System.out.println(userInput);
        }


        out.close();
        in.close();
        statusSocket.close();
        System.out.println("a promediar  " + userInput);
        
        String del = "[\\n]+"; 
        String [] info; 

        info = userInput.split(del); 
        System.out.println("# de valores" + info.length);

    double CpuAverage = getCPUAverage(info); 
        System.out.println(CpuAverage);
      return CpuAverage; 
        
    }

   

    private static String getServerStatus() throws IOException {
         if (urlOrg != null && urlOrg!= ""){
        String doc = getRESTget(urlOrg+"/serverWithState?");
        return doc;}
         return null;
    }

    private static String isdeployed(String salida, String serverName) {
  Document docn = SWBUtils.XML.xmlToDom(salida);
        
        NodeList serversList = docn.getElementsByTagName("serverWithState");             
       int size = serversList.getLength(); 
       for (int i =0 ;i< size ; i++ ){
        Node nodo = serversList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
       String name = atributes.item(1).getTextContent();       
       if (name.equals(serverName)){
       String status = atributes.item(23).getTextContent();
       System.out.println(" status is deployed" +status);
       return status; 
       }    
       }
       return "not found"; 
    }
  public static void removeFromFarm(String farmId, String netId, String realId) throws UnsupportedEncodingException, IOException {
     String info = "realServerId="+URLEncoder.encode(realId, "UTF-8")+  
                       "&realServerPort="+URLEncoder.encode("80", "UTF-8");
        postRESTtwo(urlOrg+"/network/"+netId+"/serverFarm/"+farmId+"/removeRealServer", info);
    }

    private static void postRESTtwo(String url, String info) throws IOException {
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

    private static void removeRealServer(String netId, String realId) throws IOException {
               getRESTget(urlOrg+"/network/"+netId+"/realServer/"+realId+"?delete");
    }

    private static void removeServer(String serverId, String serverName) throws IOException {
         if (urlOrg != null && urlOrg!= ""){  
        getRESTget(urlOrg+"/server/"+serverId+"?poweroff");
              String status= ""; 
        do{
              String doc = getServerStatus();
              Document docn = SWBUtils.XML.xmlToDom(doc); 
             NodeList serversList = docn.getElementsByTagName("serverWithState");             
             int size = serversList.getLength(); 
//            System.out.println("elementos tag " + size);
            for (int i =0 ;i< size ; i++ ){
             Node nodo = serversList.item(i);
            NodeList atributes = nodo.getChildNodes(); 
            String name = atributes.item(1).getTextContent(); 
                System.out.println("name to erase: "+ name + "compared with " + serverName);
               
            if (name.equals(serverName)){
            NamedNodeMap stat = nodo.getAttributes(); 
            status = atributes.item(25).getTextContent();
            System.out.println(" status in remove " +status);
            }} 
   }while (status.equals("true"));}
          
      getRESTget(urlOrg+"/server/"+serverId+"?delete");
    }
     public static String getRESTget(String url) throws IOException{
        String ret = "";
        URL lurl = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection)lurl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Basic "+encodedAuthorization);
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

     public static String postREST(String url, String info) throws IOException{
        String ret = "";
        byte [] infos = info.getBytes(); 
        URL lurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)lurl.openConnection();
        con.setRequestProperty("Content-Type", "application/xml");
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic "+encodedAuthorization);
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

    static Iterable<String> getImageNames() throws IOException {
    List<String> imgnames = new ArrayList<String> (); 
        if (urlOrg != null && urlOrg!= ""){
      String img = getRESTget(urlOrg+"/image");
        Document doc2 = SWBUtils.XML.xmlToDom(img); 
       NodeList serverList = doc2.getElementsByTagName("ServerImage");                      
       int size = serverList.getLength(); 
       for (int i =0 ;i< size ; i++ ){
        Node nodo = serverList.item(i);
        NodeList atri = nodo.getChildNodes(); 
        int r = atri.getLength(); 
       // for (int j = 0 ; j< r ; j++){
//        System.out.println("nodo "+i+": "+atri.item(i));
       String name = atri.item(5).getTextContent(); 
       imgnames.add(name); }
       return imgnames; 
       }
       return imgnames;     }

    static Iterable<String> getFarmNames(String NetName) throws IOException {
//        System.out.println(" entro getfarmnamess");
         List<String> farmNames = new ArrayList<String> (); 
         String netId = null; 
         if (NetName != null && !(NetName.equals(""))){
            netId=getNetId(NetName); 
         }
        if (urlOrg != null && !(urlOrg.equals("")) && netId != null && !(netId.equals(""))){
       String doc = getRESTget(urlOrg+ "/network/"+netId+"/serverFarm");
       Document docn = SWBUtils.XML.xmlToDom(doc); 
 
       NodeList networkList = docn.getElementsByTagName("ns5:serverFarm");             
       int size = networkList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = networkList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
       String name = atributes.item(3).getTextContent(); 
       farmNames.add(name); 
       }  
       return farmNames; 
       }
       
       return farmNames; 
    }
    
     public static String getNetId(String netName) throws IOException {
        if (urlOrg != null && urlOrg!= ""){
         String doc = getRESTget(urlOrg+"/networkWithLocation");
        Document docn = SWBUtils.XML.xmlToDom(doc); 
 
       NodeList networkList = docn.getElementsByTagName("ns4:network");             
       int size = networkList.getLength(); 
       for (int i =0 ;i< size ; i++ ){
        Node nodo = networkList.item(i);
        NodeList atributes = nodo.getChildNodes(); 
//        int r = atributes.getLength(); 
//        System.out.println(" atributos length: "+ r);
       String name = atributes.item(3).getTextContent(); 
//       System.out.println(name); 
       if (name.equals(netName)){
       String networkId = atributes.item(1).getTextContent();
       System.out.println("  netId: " + networkId);
       return networkId; 
   
       }
       }} 
     return null;

    }
     public static void createRealS( String serverId, String name, String netId) throws IOException {
        String info = "<NewRealServer xmlns='http://oec.api.opsource.net/schemas/vip'>\n" +
                      "<name>"+name+"</name>\n" +
                      "<serverId>"+serverId+"</serverId>\n" +
                      "<inService>true</inService>\n" +
                      "</NewRealServer>"; 
        if (urlOrg != null && urlOrg!= ""){
        postREST(urlOrg+"/network/"+netId+"/realServer",  info);}
   

    }
 public static String getRealId(String realname, String doc) throws IOException {
     
       Document docn = SWBUtils.XML.xmlToDom(doc); 
       NodeList serverList = docn.getElementsByTagName("ns5:realServer");             
       int size = serverList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = serverList.item(i);
        NodeList atributes = nodo.getChildNodes();     
       String name = atributes.item(3).getTextContent(); 
//       System.out.println(name); 
       if (name.equals(realname)){
       String realId = atributes.item(1).getTextContent();
       System.out.println("  realId: " + realId);
       return realId; 
   
       }
       }
       return "not found"; 
    }
 
  public static String getRealInfo(String netId) throws IOException {
      String doc = "";  
      if (urlOrg != null && urlOrg!= "" && netId != null && !"".equals(netId)){  
      doc = getRESTget(urlOrg+"/network/"+netId+"/realServer"); 
         }
       return doc; 
    }
   public static String getRealIp(String realInfo, String realName) {
         Document docn = SWBUtils.XML.xmlToDom(realInfo); 
 
       NodeList serverList = docn.getElementsByTagName("ns5:realServer");             
       int size = serverList.getLength();
       for (int i =0 ;i< size ; i++ ){
       
        Node nodo = serverList.item(i);
        NodeList atributes = nodo.getChildNodes();     
       String name = atributes.item(3).getTextContent(); 
//       System.out.println(name); 
       if (name.equals(realName)){
       String realIp= atributes.item(9).getTextContent();
       System.out.println("  realIp: " + realIp);
       return realIp; 
   
       }
       }
       return "not found"; 
    }

    private static double getCPUAverage(String[] info) {
        double val= 0.0;
       int den = info.length; 
       String [] each; 
       for (int i =0; i < info.length; i++ ){    
            each = info[i].split("\\s+"); 
            if (each != null){
            val += (Double.parseDouble(each[1]));
            System.out.println(val);
//            for (int j = 0 ; j< each.length; j ++ )
//                System.out.println(each[j]);
            }}
        System.out.println(den);
       double averag = val/den;
       
       return (averag);
    }
}

