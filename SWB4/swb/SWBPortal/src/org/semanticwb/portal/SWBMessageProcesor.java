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
package org.semanticwb.portal;

import org.semanticwb.platform.SWBMessageCenter;
import java.net.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;


// TODO: Auto-generated Javadoc
/** objecto: Procesa los mensajes UDP recibidos y los redireciona a su destino final, los mensajes pueden ser, "ini", "log", "hit".
 *
 * @author Javier Solis Gonzalez
 */
public class SWBMessageProcesor extends TimerTask
{
    
    /** The log. */
    public static Logger log = SWBUtils.getLogger(SWBMessageProcesor.class);
    
    /** The timer. */
    Timer timer;
    
    /** The center. */
    SWBMessageCenter center = null;
    //static int c=0;

    /**
     * Creates a new instance of WBMessageServer.
     * 
     * @param center the center
     * @throws SocketException the socket exception
     */
    public SWBMessageProcesor(SWBMessageCenter center)
    {
        this.center = center;
    }
    
    /**
     * Inits the.
     */
    public void init()
    {
        timer=new Timer("SWBMessageProcessor");
        timer.schedule(this,1000,1000);        
    }

    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     */
    public void run()
    {
//        while (true)
//        {
            try
            {
                //c++;
                //int cc=c;
                //int x=0;
                //System.out.println("procesor start:"+cc);
                while (center.hasMessages())
                {
                    //x++;
                    String str = center.popMessage();
                    int i = str.indexOf('|');
                    String ini = str.substring(0, i);
                    //System.out.println("ini:"+ini);
                    //System.out.println("popMessage:"+str);
                    if(ini.equals("log") && SWBPortal.getAccessLog()!=null)
                    {
                        if (!SWBPortal.isClient())
                        {
                            SWBPortal.getAccessLog().updateRes(str.substring(4));
                        }
                        SWBPortal.getAccessIncrement().log(str.substring(4));
                    } else if(ini.equals("hit") && SWBPortal.getAccessLog()!=null)
                    {
                        if (!SWBPortal.isClient())
                        {
                            SWBPortal.getAccessLog().hitLog(str.substring(4));
                        }
                        SWBPortal.getAccessIncrement().logHit(str.substring(4));
                    } else if(ini.equals("ses") && SWBPortal.getAccessLog()!=null)
                    {
                        if (!SWBPortal.isClient())
                        {
                            SWBPortal.getAccessLog().updateSess(str.substring(4));
                        }
                    } else if(ini.equals("lgn") && SWBPortal.getAccessLog()!=null)
                    {
                        if (!SWBPortal.isClient())
                        {
                            SWBPortal.getAccessLog().updateLogin(str.substring(4));
                        }
                    } else if(ini.equals("ini") || ini.equals("syn"))
                    {
                        StringTokenizer st=new StringTokenizer(str, "|");
                        String init=st.nextToken();
                        String time=st.nextToken();
                        String aux=st.nextToken();
                        if(aux.equals("hel"))
                        {
                            String addr=st.nextToken();
                            log.info("Registering Message Client:"+addr);
                            
                            //System.out.println("Registering Message Client:"+addr);
                            
                            int j=addr.lastIndexOf(":"); //MAPS74 IPV6
                            center.addAddress(InetAddress.getByName(addr.substring(0,j)),Integer.parseInt(addr.substring(j+1)));
                            
                            if(!SWBPortal.isClient())
                            {
                                //System.out.println("Server...");
                                center.sendMessage("ini|upd|"+center.getListAddress());
                            }
                        }else if(aux.equals("upd"))
                        {
                            if(SWBPortal.isClient())
                            {
                                while(st.hasMoreTokens())
                                {
                                    String addr=st.nextToken();
                                    int j=addr.lastIndexOf(":"); //MAPS74 IPV6
                                    center.addAddress(InetAddress.getByName(addr.substring(0,j)),Integer.parseInt(addr.substring(j+1)));
                                }
                                
                            }
                        }
                        
                        
                    } else if(center.getObserver(ini) != null)
                    {
                        SWBObserver obs = center.getObserver(ini);
                        obs.sendDBNotify(ini, str);
                    } else
                    {
                        log.error("SWBMessageProcesor Bad Message Error:"+str);
                    }
                }
                //System.out.println("procesor end:"+cc+":"+x);
            } catch (Exception e)
            {
                log.error(e);
            }finally
            {
                SWBPlatform.createInstance().endThreadRequest();                
            }
//            try
//            {
//                this.currentThread().sleep(1000);
//            }catch(Exception e)
//            {
//                AFUtils.log(e);
//                break;
//            }
//        }
    }

}
