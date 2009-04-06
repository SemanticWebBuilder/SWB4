
/*
 * WBMessageServer.java
 *
 * Created on 3 de octubre de 2002, 14:54
 */

package org.semanticwb.portal;

import java.net.*;
import java.util.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBObserver;


/** objecto: Procesa los mensajes UDP recibidos y los redireciona a su destino final, los mensajes pueden ser, "ini", "log", "hit".
 *
 * @author Javier Solis Gonzalez
 */
public class SWBMessageProcesor extends TimerTask
{
    public static Logger log = SWBUtils.getLogger(SWBMessageProcesor.class);
    Timer timer;
    SWBMessageCenter center = null;
    //static int c=0;

    /** Creates a new instance of WBMessageServer
     * @param center
     * @throws java.net.SocketException  */
    public SWBMessageProcesor(SWBMessageCenter center) throws java.net.SocketException
    {
        this.center = center;
    }
    
    public void init()
    {
        timer=new Timer();
        timer.schedule(this,1000,1000);        
    }

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
                    if(ini.equals("log"))
                    {
                        if (!SWBPlatform.isClient())
                        {
                            SWBAccessLog.getInstance().updateRes(str.substring(4));
                        }
                        //TODO
                        //SWBAccessIncrement.getInstance().log(str.substring(4));
                    } else if(ini.equals("hit"))
                    {
                        if (!SWBPlatform.isClient())
                        {
                            SWBAccessLog.getInstance().hitLog(str.substring(4));
                        }
                        //TODO
                        //SWBAccessIncrement.getInstance().logHit(str.substring(4));
                    } else if(ini.equals("ses"))
                    {
                        if (!SWBPlatform.isClient())
                        {
                            SWBAccessLog.getInstance().updateSess(str.substring(4));
                        }
                    } else if(ini.equals("lgn"))
                    {
                        if (!SWBPlatform.isClient())
                        {
                            SWBAccessLog.getInstance().updateLogin(str.substring(4));
                        }
                    } else if(ini.equals("ini"))
                    {
                        log.info("SWBMessageProcesor init...");
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
