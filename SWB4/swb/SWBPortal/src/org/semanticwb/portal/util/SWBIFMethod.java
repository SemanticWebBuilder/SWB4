/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.util;

import com.arthurdo.parser.HtmlTag;
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
    private HtmlTag tag;
    private String txt;
    private String type=null;
    private String lang=null;
    private String device=null;

    /**
     * @param method
     * @param obj
     * @param arguments
     * @param tpl  */
    public SWBIFMethod(HtmlTag tag, String txt)
    {
        //System.out.println("tag:"+tag);
        this.tag = tag;
        this.txt = txt;
        type=tag.getTagString().toLowerCase();
        lang=tag.getParam("language");
        device=tag.getParam("device");
        //System.out.println("lang:"+lang);
        //System.out.println("device:"+lang);
//        Enumeration en=tag.getParamNames();
//        while(en.hasMoreElements())
//        {
//            String prm=(String)en.nextElement();
//            System.out.println("-->"+prm+":"+tag.getParam(prm));
//        }
    }

    public String eval(User user, WebPage webpage, Template tpl)
    {
        String ret=txt;
        if(type.equals("if:user"))
        {
            if(lang!=null)
            {
                String lng=user.getLanguage();
                if(!lang.equals(lng))return "";
            }
            if(device!=null)
            {
                Device dvc=tpl.getWebSite().getDevice(device);
                if(dvc!=null)
                {
                    if(!user.hasDevice(dvc))return "";
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
