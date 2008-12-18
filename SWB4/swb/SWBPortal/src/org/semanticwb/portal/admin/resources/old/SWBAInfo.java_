/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.Camp;
import org.semanticwb.model.Device;
import org.semanticwb.model.Dns;
import org.semanticwb.model.Language;
import org.semanticwb.model.PFlow;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.PortletSubType;
import org.semanticwb.model.PortletType;
import org.semanticwb.model.Role;
import org.semanticwb.model.Rule;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author juan.fernandez
 */
public class SWBAInfo extends GenericResource {
    
    /** Creates a new instance of WBAInfo */
    public SWBAInfo()
    {
    }

    /**
     * @param request
     * @param response
     * @param paramRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
     
        WebPage topic=paramRequest.getAdminTopic();
        String webpath=SWBPlatform.getContextPath();
        
        String stm=request.getParameter("tm");
        //String stp=request.getParameter("tp");
        String sid=request.getParameter("id");
        String title=request.getParameter("title");
        
        PrintWriter out=response.getWriter();
        out.println("      <TABLE width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"3\">");
        
        if(topic.getId().equals("WBAd_infoi_TopicInfo"))
        {
            //TopicMap tm=TopicMgr.getInstance().getTopicMap(stm);
            //Topic tp=tm.getTopic(stp);
            WebPage tp=paramRequest.getTopic();
            title=tp.getTitle();
            addTitle(title,out);
            addAttribute("ID","<a href=\""+tp.getUrl()+"\" target=\"work\">"+tp.getId()+"</a>",out);
            //TODO: Falta implementar dateFormat()
            //addAttribute("Creaci&oacute;n",WBUtils.dateFormat(tp.getDbdata().getCreated()),out);
            //addAttribute("&Uacute;ltima Act.",WBUtils.dateFormat(tp.getDbdata().getLastupdate()),out);
            addAttribute("Creaci&oacute;n",tp.getCreated().toString(),out);
            addAttribute("&Uacute;ltima Act.",tp.getUpdated().toString(),out);
            
            addAttribute("Accesos",""+tp.getViews(),out);
        }
/*
        else if(topic.getId().equals("WBAd_infoi_TopicTitles"))
        {
            TopicMap tm=TopicMgr.getInstance().getTopicMap(stm);
            Topic tp=tm.getTopic(stp);
            title=tp.getDisplayName();
            addTitle(title,out);
            Iterator it=tp.getBaseNames().iterator();
            int col=1;
            while(it.hasNext())
            {
                BaseName bn=(BaseName)it.next();
                String sc="";
                if (bn.getScope() != null)
                {
                    Iterator sit = bn.getScope().getTopicRefs().values().iterator();
                    while (sit.hasNext())
                    {
                        sc+=((Topic)(sit.next())).getId();
                    }
                }
                if(sc.startsWith("IDM_WB"))sc=sc.substring(6);
                addListAttribute("<B>"+sc+"<B>",bn.getBaseNameString(),out,col);
 
                Iterator va = bn.getVariants().iterator();
                while (va.hasNext())
                {
                    Variant v = (Variant) va.next();
                    VariantName vn = v.getVariantName();
                    addListAttribute("","&nbsp;&nbsp;&nbsp"+vn.getResource(),out,2);
                }
                col=col^1;
            }
        }
 */
        else if(topic.getId().equals("WBAd_infoi_TopicMapInfo"))
        {
            int size = 0;
            WebSite tm=SWBContext.getWebSite(stm);
            Iterator<WebPage>  itTopics = tm.listWebPages();
            while(itTopics.hasNext())
            {
                String stpid=itTopics.next().getId();
                if(!stpid.toLowerCase().startsWith("idm_wb") && !stpid.toLowerCase().startsWith("cnf_wb") && !stpid.toLowerCase().startsWith("rec_wb"))
                {
                    size=size+1;
                }
            }
            title=tm.getTitle();
            addTitle(title,out);
            //TODO: falta implementar dateFormat()
//            addAttribute("Creaci&oacute;n",WBUtils.dateFormat(tm.getDbdata().getCreated()),out);
//            addAttribute("&Uacute;ltima Act.",WBUtils.dateFormat(tm.getDbdata().getLastupdate()),out);
            addAttribute("Creaci&oacute;n",tm.getCreated().toString(),out);
            addAttribute("&Uacute;ltima Act.",tm.getUpdated().toString(),out);
            addAttribute("secciones",""+size+"",out);
            addAttribute("home","<a href=\""+tm.getHomePage().getUrl()+"\" target=\"work\">"+tm.getHomePage().getTitle()+"</a>",out);
            
        }else if(topic.getId().equals("WBAd_infoi_ResourceInfo"))
        {
            Portlet recRes=SWBContext.getWebSite(stm).getPortlet(sid);
            title=recRes.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recRes.getId(),out);
            addAttribute("Descripci&oacute;n",recRes.getDescription(),out);
            if(recRes.isActive())
                addAttribute("activo","Si",out);
            else
                addAttribute("activo","No",out);
            //TODO: falta implementar dateFormat()
            addAttribute("Creaci&oacute;n",recRes.getCreated().toString(),out);
            addAttribute("&Uacute;ltima Act.",recRes.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_ResourceSubTypeInfo"))
        {
            PortletSubType recSType=null;
            if(sid.startsWith("0"))
            {
                recSType=SWBContext.getWebSite(SWBContext.WEBSITE_GLOBAL).getPortletSubType(sid);
            }else
            {
                recSType=SWBContext.getWebSite(stm).getPortletSubType(sid);
            }
            title=recSType.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recSType.getId(),out);
            addAttribute("Descripci&oacute;n",recSType.getDescription(),out);
            //TODO: dateFormat
            addAttribute("&Uacute;ltima Act.",recSType.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_ResourceTypeInfo"))
        {
            PortletType recObj=null;
            if(sid.startsWith("0"))
            {
                recObj=SWBContext.getWebSite(SWBContext.WEBSITE_GLOBAL).getPortletType(sid);
            }else
            {
                recObj=SWBContext.getWebSite(stm).getPortletType(sid);
            }
            title=recObj.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recObj.getId(),out);
            addAttribute("Nombre",""+recObj.getTitle(),out);
            addAttribute("Descripci&oacute;n",recObj.getDescription(),out);
            //TODO: dateFormat()
            addAttribute("&Uacute;ltima Act.",recObj.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_GrpTemplateInfo"))
        {
            TemplateGroup recGrpTpl=SWBContext.getWebSite(stm).getTemplateGroup(sid);
            title=recGrpTpl.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recGrpTpl.getId(),out);
            addAttribute("Descripci&oacute;n",recGrpTpl.getDescription(),out);
            //TODO: dateFormat()
            addAttribute("Creaci&oacute;n",recGrpTpl.getUpdated().toString(),out);
            addAttribute("&Uacute;ltima Act.",recGrpTpl.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_TemplatesInfo"))
        {
            Template recTpl=SWBContext.getWebSite(stm).getTemplate(sid);
            title=recTpl.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recTpl.getId(),out);
            addAttribute("Descripci&oacute;n",recTpl.getDescription(),out);
            //TODO: dateFormat
            addAttribute("Creaci&oacute;n",recTpl.getUpdated().toString(),out);
            addAttribute("&Uacute;ltima Act.",recTpl.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_LasnguagesInfo"))
        {
            Language recLang=SWBContext.getWebSite(stm).getLanguage(sid);
            title=recLang.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recLang.getId(),out);
            //TODO: dateFormat
            addAttribute("&Uacute;ltima Act.",recLang.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_RulesInfo"))
        {
            Rule recRule=SWBContext.getWebSite(stm).getRule(sid);
            title=recRule.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recRule.getId(),out);
            addAttribute("Descripci&oacute;n",recRule.getDescription(),out);
            //TODO: dateFormat
            addAttribute("Creaci&oacute;n",recRule.getCreated().toString(),out);
            addAttribute("&Uacute;ltima Act.",recRule.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_DeviceInfo"))
        {
            Device recDevice=SWBContext.getWebSite(stm).getDevice(sid);
            title=recDevice.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recDevice.getId(),out);
            addAttribute("Descripci&oacute;n",recDevice.getDescription(),out);
            //TODO: dateFormat
            addAttribute("&Uacute;ltima Act.",recDevice.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_DNSInfo"))
        {
            Dns recDns=SWBContext.getWebSite(stm).getDns(sid);
            title=recDns.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recDns.getId(),out);
            if(recDns.isDefault())
                addAttribute("Por Defecto","Si",out);
            else
                addAttribute("Por Defecto","No",out);
            //TODO: dateFormat()
            addAttribute("&Uacute;ltima Act.",recDns.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_RolesInfo"))
        {
            Role recRole=SWBContext.getWebSite(stm).getUserRepository().getRole(sid);
            title=recRole.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+recRole.getId(),out);
            addAttribute("Descripci&oacute;n",recRole.getDescription(),out);
            addAttribute("Repositorio",recRole.getUserRepository().getId(),out);
            //TODO: dateFormat
            addAttribute("&Uacute;ltima Act.",recRole.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_MDTablesInfo"))
        {
            //TODO: MDTable
//            RecMDTable rMDT = DBMetaData.getInstance().getMDTable(Integer.parseInt(sid),stm);
//            
//            title=rMDT.getName();
//            addTitle(title,out);
//            addAttribute("Identificador",""+rMDT.getId(),out);
//            addAttribute("Descripci&oacute;n",rMDT.getDescription(),out);
//            addAttribute("Creaci&oacute;n",WBUtils.dateFormat(rMDT.getCreationDate()),out);
//            addAttribute("&Uacute;ltima Act.",WBUtils.dateFormat(rMDT.getLastupdate()),out);
        }else if(topic.getId().equals("WBAd_infoi_FlowsInfo"))
        {
            PFlow rPF = SWBContext.getWebSite(stm).getPFlow(sid);
            
            title=rPF.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+rPF.getId(),out);
            addAttribute("Descripci&oacute;n",rPF.getDescription(),out);
            //TODO: dateFormat
            addAttribute("Creaci&oacute;n",rPF.getCreated().toString(),out);
            addAttribute("&Uacute;ltima Act.",rPF.getUpdated().toString(),out);
        }else if(topic.getId().equals("WBAd_infoi_CampInfo"))
        {
            Camp rC = SWBContext.getWebSite(stm).getCamp(sid);
            
            title=rC.getTitle();
            addTitle(title,out);
            addAttribute("Identificador",""+rC.getId(),out);
            addAttribute("Descripci&oacute;n",rC.getDescription(),out);
            //TODO: dateFormat
            addAttribute("&Uacute;ltima Act.",rC.getUpdated().toString(),out);
        }
//        else if(topic.getId().equals("WBAd_infoi_DataSourceInfo"))
//        {
//            RecDataSource rec=DBDataSource.getInstance().getDataSource(stm,Integer.parseInt(sid));
//            title=rec.getTitle();
//            addTitle(title,out);
//            addAttribute("Identificador",""+rec.getId(),out);
//            addAttribute("Descripci&oacute;n",rec.getDescription(),out);
//            //TODO: dateFormat
//            addAttribute("Creaci&oacute;n",rec.getUpdated(),out);
//            addAttribute("&Uacute;ltima Act.",rec.getUpdated(),out);
//        } 
        
        out.println("      </TABLE>");
    }
    
    void addTitle(String title, PrintWriter out)
    {
        out.println("          <tr><td colspan=2><FONT color=\"#006600\" size=\"2\" face=\"Verdana, Arial, Helvetica, sans-serif\">");
        out.print(title);
        out.println("</FONT></td></tr>");
    }
    
    void addListElement(String element, PrintWriter out, int color)
    {
        String col="#FFFFFF";
        if(color==1)col="#428AD4";
        if(color==1)col="#62aAf4";
        out.println("          <tr><td colspan=2 bgcolor=\""+col+"\"><FONT color=\"#006666\" size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">");
        out.print(element);
        out.println("</FONT></td></tr>");
    }
    
    void addListAttribute(String name, String value, PrintWriter out, int color)
    {
        String col="#FFFFFF";
        if(color==1)col="#E1EDE4";
        if(color==2)col="#f1fDf4";
        out.println("          <tr><td bgcolor=\""+col+"\"><FONT color=\"#006666\" size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">"+name+"</FONT></td>");
        out.println("          <td bgcolor=\""+col+"\"><FONT color=\"#006666\" size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">"+value+"</FONT></td></TR>");
    }
    
    
    void addAttribute(String name, String value, PrintWriter out)
    {
        out.println("            <tr><td align=\"right\"><FONT size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\" color=\"#006666\">"+name+":</font></td>");
        out.println("                <td><FONT size=\"1\" face=\"Verdana, Arial, Helvetica, sans-serif\">"+value+"</font></td></tr>");
    }

}
