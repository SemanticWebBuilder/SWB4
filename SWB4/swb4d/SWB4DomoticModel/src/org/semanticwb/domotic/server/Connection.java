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
package org.semanticwb.domotic.server;

import java.io.*;

import java.lang.*;

import java.net.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;


import java.util.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.SFBase64;
import org.semanticwb.domotic.model.DomDevice;
import org.semanticwb.domotic.model.DomGateway;
import org.semanticwb.model.SWBModel;


/**
 *
 * @author  Administrador
 */
public class Connection extends Thread
{
    private Socket sock = null;
    private Server server = null;
    private boolean running = false;
    private DomGateway gateway=null;
    private OutputStreamWriter writer=null;
    
    private boolean websocket=false;
    private boolean firstline=true;
    private boolean handshake=false;
    
    private String key=null;
    private String origin=null;
    private String magic="258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    
    
    /** Creates a new instance of SConn */
    public Connection(Socket sock, Server server)
    {
        this.sock = sock;
        this.server = server;
    }
    
    private String getAcceptKey()
    {
        String ret=null;
        try
        { 
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            ret=SFBase64.encodeBytes(messageDigest.digest((key+magic).getBytes("ISO8859-1")),false);    
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return ret;
        
    }
    
    public void accion(String acc)
    {
        if(firstline && acc.startsWith("GET"))
        {
            firstline=false;
            websocket=true;
            //System.out.println("Wensocket:true");
            handshake=true;
        }
        firstline=false;
        
        if(websocket)
        {
            //System.out.println("ws:"+acc);
            if(acc.startsWith("Sec-WebSocket-Key:"))
            {
                key=acc.substring(18).trim();
                //System.out.println("ws key:"+key);
            }else if(acc.startsWith("Origin:"))
            {
                origin=acc.substring(7).trim();
                //System.out.println("ws origin:"+origin);
            }else if(acc.isEmpty())
            {
                handshake=false;
                if(key!=null)
                {
                    String response="";
                    
                    response+="HTTP/1.1 101 Web Socket Protocol Handshake\r\n";
                    response+="Upgrade: WebSocket\r\n";
                    response+="Connection: Upgrade\r\n";
                    response+="Sec-WebSocket-Accept: "+getAcceptKey() +"\r\n";
                    response+="Server: SWB4Domotic Gateway\r\n";
                    //response+="Server: Kaazing Gateway\n";
                    response+="Date: "+(new Date().toGMTString()) +"\r\n";
                    response+="Access-Control-Allow-Origin: "+origin+"\r\n";
                    response+="Access-Control-Allow-Credentials: true\r\n";
                    response+="Access-Control-Allow-Headers: content-type\r\n";
                    response+="Access-Control-Allow-Headers: authorization\r\n";
                    response+="Access-Control-Allow-Headers: x-websocket-extensions\r\n";
                    response+="Access-Control-Allow-Headers: x-websocket-version\r\n";
                    response+="Access-Control-Allow-Headers: x-websocket-protocol\r\n";
                    response+="(Challenge Response):00:00:00:00:00:00:00:00:00:00:00:00:00:00:00:00\r\n";
                    //response+="Sec-WebSocket-Protocol: chat\n";
                    response+="\r\n";
                    //System.out.println("ws response:"+response);
                    sendMessage(response);                    
                }
            }
        }else
        {
            try
            {
                //System.out.println("sk:"+acc);

                StringTokenizer st = new StringTokenizer(acc, " ");
                String cb = st.nextToken();            

                if(cb.equals("rep"))
                {
                    String zn = st.nextToken();            
                    String id = st.nextToken();            
                    String cm = st.nextToken();   

                    DomDevice dev=findDomDevice(id, zn, server.getModel());
                    dev.setStatus(Integer.parseInt(cm),false);
                }else if(cb.equals("ini"))
                {
                    String serial = st.nextToken();
                    findDomGateway(serial, server.getModel());
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }            
        }        
    }
    
    public void run()
    {
        try
        {
            InputStream oin = sock.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(oin));
            byte[] buff = new byte[8192];
            
            while (running)
            {
                if(websocket && !handshake)
                {
                    //System.out.print(in.read()+" ");
                }else
                {
                    String aux=in.readLine();
                    if (aux!=null)
                    {
                        accion(aux);
                    } else
                    {
                        running = false;                    
                        break;
                    }
                }
            }            
        } catch (Exception e)
        {
            e.printStackTrace();
        }     
        
        running=false;
        if(getDomGateway().getConnection()==this)
        {
            getDomGateway().setConnection(null);
        }
        
        //System.out.println("out:"+name);
    }
    
    public void start()
    {
        running = true;
        super.start();
    }
    
    private DomDevice findDomDevice(String id, String zone, SWBModel model)
    {
        DomDevice ret=null;
        Iterator<DomDevice> it=gateway.listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            if(id.equals(domDevice.getDevId())&&zone.equals(domDevice.getDevZone()))
            {
                ret=domDevice;
            }
        }
        if(ret==null)
        {
            ret=DomDevice.ClassMgr.createDomDevice(model);
            ret.setDevId(id);
            ret.setDevZone(zone);
            ret.setDomGateway(gateway);
        }
        return ret;        
    }    
    
    private DomDevice findDomDevice(String serial, SWBModel model)
    {
        DomDevice ret=null;
        Iterator<DomDevice> it=gateway.listDomDevices();
        while (it.hasNext())
        {
            DomDevice domDevice = it.next();
            if(serial.equals(domDevice.getSerial()))
            {
                ret=domDevice;
            }
        }
        if(ret==null)
        {
            ret=DomDevice.ClassMgr.createDomDevice(model);
            ret.setSerial(serial);
        }
        return ret;        
    }
    
    private DomGateway findDomGateway(String serial, SWBModel model)
    {
        DomGateway gtw=DomGateway.getDomDeviceBySerial(serial, model);
        if(gtw==null)
        {
            gtw=DomGateway.ClassMgr.createDomGateway(model);
            gtw.setSerial(serial);
        }
        setDomGateway(gtw);
        return gtw;
    }

    public DomGateway getDomGateway()
    {
        return gateway;
    }

    public void setDomGateway(DomGateway gateway)
    {
        this.gateway = gateway;        
        if(gateway!=null)
        {
            gateway.setConnection(this);
        }
    }
    
    public boolean sendMessage(String msg) 
    {
        if(running)
        {
            try
            {
                if(writer==null)
                {
                    try
                    {
                        writer=new OutputStreamWriter(sock.getOutputStream(),Charset.forName("utf-8"));
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                writer.write(msg);
                writer.flush();        
                
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }    
    
        
}
