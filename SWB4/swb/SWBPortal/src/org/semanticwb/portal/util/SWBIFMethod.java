/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

import com.arthurdo.parser.HtmlTag;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;

/**
 *
 * @author javier.solis
 */
public class SWBIFMethod
{
    private static Logger log = SWBUtils.getLogger(SWBIFMethod.class);
    private Template tpl;
    private HtmlTag tag;
    private String txt;
    private String type=null;
    private boolean notlangs=false;
    private ArrayList<String> langs=null;
    private boolean notdevices=false;
    private ArrayList<Device> devices=null;

    /**
     * @param method
     * @param obj
     * @param arguments
     * @param tpl  */
    public SWBIFMethod(HtmlTag tag, String txt, Template tpl)
    {
        this.tpl=tpl;
        //System.out.println("tag:"+tag);
        this.tag = tag;
        this.txt = txt;
        type=tag.getTagString().toLowerCase();

        initUser();
    }

    private void initUser()
    {
        String lang=tag.getParam("language");
        String device=tag.getParam("device");
        //System.out.println("lang:"+lang);
        //System.out.println("device:"+lang);
        langs=new ArrayList();
        devices=new ArrayList();

        try
        {
            if(lang!=null)
            {
                if(lang.startsWith("!"))
                {
                    lang=lang.substring(1);
                    notlangs=true;
                }
                StringTokenizer st=new StringTokenizer(lang,"|");
                while(st.hasMoreTokens())
                {
                    String lg=st.nextToken();
                    langs.add(lg);
                }
            }
        }catch(Exception e){log.error("Error reading if user langs...",e);}

        try
        {
            if(device!=null)
            {
                if(device.startsWith("!"))
                {
                    device=device.substring(1);
                    notdevices=true;
                }
                StringTokenizer st=new StringTokenizer(device,"|");
                while(st.hasMoreTokens())
                {
                    String dv=st.nextToken();
                    Device d=tpl.getWebSite().getDevice(dv);
                    if(d!=null)devices.add(d);
                }
            }
        }catch(Exception e){log.error("Error reading if user devices...",e);}
    }

    public String eval(User user, WebPage webpage)
    {
        String ret=txt;
        if(type.equals("if:user"))
        {
            if(!langs.isEmpty())
            {
                boolean cont=langs.contains(user.getLanguage());
                if((!cont && !notlangs)||(cont && notlangs))
                {
                    return "";
                }
            }
            if(!devices.isEmpty())
            {
                boolean cont=false;
                Iterator<Device> it=devices.iterator();
                while(it.hasNext())
                {
                    Device dvc=it.next();
                    if(user.hasDevice(dvc))
                    {
                        cont=true;
                        break;
                    }
                }
                if((!cont && !notdevices)||(cont && notdevices))
                {
                    return "";
                }
            }
        }else if(type.equals("if:topic") || type.equals("if:webpage"))
        {

        }else if(type.equals("if:template"))
        {

        }
        return ret;
    }


}
