/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.FileUpload;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Administrador
 */
public class ImageGallery extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(WBSiteMap.class);
    private WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    private String workPath;
    private String webWorkPath;
    
    private static int idx;
    
    static {
        idx = 1;
    }
    
    @Override
    public void setResourceBase(Portlet base)
    {
        try 
        {
            super.setResourceBase(base);
            workPath = (String) SWBPlatform.getWorkPath() +  base.getWorkPath();
            webWorkPath = (String) SWBPlatform.getWebWorkPath() +  base.getWorkPath();
        }
        catch(Exception e) { log.error("Error while setting resource base: "+base.getId() +"-"+ base.getTitle(), e);  }
    }
    
    /**
     * @param request
     * @param response
     * @param reqParams
     * @throws AFException
     * @throws IOException
     */
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
        response.setHeader("Pragma","no-cache"); //HTTP 1.0
        response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
        PrintWriter out = response.getWriter();
        
        Portlet base=getResourceBase();
        
        StringBuffer ret = new StringBuffer();
        try {
            ret.append("<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/jquery-imagegallery.js\"></script>\n");            
            ret.append("<script type=\"text/javascript\"> \n");
            
            ret.append("    simpleGallery_navpanel={ \n");
            ret.append("        panel: {height:'45px', opacity:0.5, paddingTop:'5px', fontStyle:'bold 9px Verdana'}, //customize nav panel container \n");
            ret.append("        images: [ '/swb/swbadmin/js/jquery/themes/control_rewind_blue.png', '/swb/swbadmin/js/jquery/themes/control_play_blue.png', '/swb/swbadmin/js/jquery/themes/control_fastforward_blue.png', '/swb/swbadmin/js/jquery/themes/control_pause_blue.png'], //nav panel images (in that order) \n");
            ret.append("        imageSpacing: {offsetTop:[-3, 0, -3], spacing:10}, //top offset of left, play, and right images, PLUS spacing between the 3 images \n");            
            ret.append("        slideduration: 500 //duration of slide up animation to reveal panel \n");
            ret.append("    }; \n");            
            
            ret.append("    var mygallery=new simpleGallery({ \n");
            ret.append("	wrapperid: 'imggallery_"+ base.getId()+"', //ID of main gallery container, \n");
            ret.append("	dimensions: ["+base.getAttribute("imgwidth","220")+", "+base.getAttribute("imgheight","150")+"], //width/height of gallery in pixels. Should reflect dimensions of the images exactly \n");
            ret.append("	imagearray: [ \n");            
            String input;
            for(int j=1; j<16; j++) {
                input = "imggallery_" + base.getId() + "_" + j;
                if(base.getAttribute(input)!=null) {
                    ret.append("['"+webWorkPath +"/"+ base.getAttribute(input)+"','','']");
                }
                if(j!=15) {
                    ret.append(", ");
                }
            }            
            ret.append("	], \n");
            ret.append("	autoplay: "+base.getAttribute("autoplay","false")+", \n");
            ret.append("	persist: false, \n");
            ret.append("	pause: "+base.getAttribute("pause","2500")+", //pause between slides (milliseconds) \n");
            ret.append("	fadeduration: "+base.getAttribute("fadetime","500")+", //transition duration (milliseconds) \n");
            ret.append("	oninit:function(){ //event that fires when gallery has initialized/ ready to run \n");
            ret.append("	}, \n");
            ret.append("	onslide:function(curslide, i){ //event that fires after each slide is shown \n");
            ret.append("		//curslide: returns DOM reference to current slide's DIV (ie: try alert(curslide.innerHTML) \n");
            ret.append("		//i: integer reflecting current image within collection being shown (0=1st image, 1=2nd etc) \n");
            ret.append("	} \n");            
            ret.append("        ,fullwidth:"+base.getAttribute("fullwidth","350")+" \n");
            ret.append("        ,fullheight:"+base.getAttribute("fullheight","280")+" \n");        
            ret.append("        ,imageClosing: '/swb/swbadmin/js/jquery/themes/cancel.png'");
            ret.append("    } \n");
            ret.append("); \n");            
            ret.append("</script> \n");
            ret.append("<div>");
            ret.append("<span>"+base.getAttribute("title","")+"</span>");
            ret.append("<div class=\"imagegallery\" id=\"imggallery_"+base.getId()+"\"></div> \n");
            ret.append("</div>");
        }catch(Exception e) {
            log.error(e);
        }
        out.println(ret.toString());        
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {        
        response.setContentType("text/html;charset=iso-8859-1");
        StringBuffer ret = new StringBuffer("");
        Portlet base=getResourceBase();
        
        String msg=paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_undefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        
        if(action.equals("add") || action.equals("edit")) {
            ret.append(getForm(request, paramRequest));
        }else if(action.equals("update")) {
            FileUpload fup = new FileUpload();
            try
            {
                fup.getFiles(request, response);
                
                String value = null!=fup.getValue("title") && !"".equals(fup.getValue("title").trim()) ? fup.getValue("title").trim() : null;
                base.setAttribute("title", value);                
                value = null!=fup.getValue("imgwidth") && !"".equals(fup.getValue("imgwidth").trim()) ? fup.getValue("imgwidth").trim() : null;
                base.setAttribute("imgwidth", value);
                value = null!=fup.getValue("imgheight") && !"".equals(fup.getValue("imgheight").trim()) ? fup.getValue("imgheight").trim() : null;
                base.setAttribute("imgheight", value);                
                value = null!=fup.getValue("fullwidth") && !"".equals(fup.getValue("fullwidth").trim()) ? fup.getValue("fullwidth").trim() : null;
                base.setAttribute("fullwidth", value);
                value = null!=fup.getValue("fullheight") && !"".equals(fup.getValue("fullheight").trim()) ? fup.getValue("fullheight").trim() : null;
                base.setAttribute("fullheight", value);                
                value = null!=fup.getValue("autoplay") && !"".equals(fup.getValue("autoplay").trim()) ? fup.getValue("autoplay").trim() : null;
                base.setAttribute("autoplay", value);
                value = null!=fup.getValue("pause") && !"".equals(fup.getValue("pause").trim()) ? fup.getValue("pause").trim() : null;
                base.setAttribute("pause", value);
                value = null!=fup.getValue("fadetime") && !"".equals(fup.getValue("fadetime").trim()) ? fup.getValue("fadetime").trim() : null;
                base.setAttribute("fadetime", value);
                                
                int i = 1;
                String fileInput, filename, removeChk;
                //do {
                for(int j=0; j<15; j++) {
                    fileInput = "imggallery_" + base.getId() + "_" + i;
                    removeChk = "remove_" + base.getId() + "_" + i;                                        
                    value = null!=fup.getValue(removeChk) && !"".equals(fup.getValue(removeChk).trim()) ? fup.getValue(removeChk).trim() : "0";
                    
                    if("1".equals(value) && base.getAttribute(fileInput)!=null) {
                        File imgFile = new File(workPath + "/" + base.getAttribute(fileInput).trim());
                        imgFile.delete();
                        base.removeAttribute(fileInput);
                    }else {
                        value = null!=fup.getFileName(fileInput) && !"".equals(fup.getFileName(fileInput).trim()) ? fup.getFileName(fileInput).trim() : null;
                        if(value!=null) {
                            filename = admResUtils.getFileName(base, value);
                            if(filename!=null && !filename.trim().equals(""))
                            {
                                if (!admResUtils.isFileType(filename, "bmp|jpg|jpeg|gif|png")){
                                    msg=paramRequest.getLocaleString("msgErrFileType") +" <i>bmp, jpg, jpeg, gif, png</i>: " + filename;
                                }else {
                                    if (admResUtils.uploadFile(base, fup, fileInput)){
                                        base.setAttribute(fileInput, filename);
                                    }else {
                                        msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                                    }
                                }
                            }else {
                                msg=paramRequest.getLocaleString("msgErrUploadFile") +" <i>" + value + "</i>.";
                            }
                        }
                    }
                    i++;
                }
                //}while(value!=null || base.getAttribute(fileInput)!=null);
                base.updateAttributesToDB();
                
                msg=paramRequest.getLocaleString("msgOkUpdateResource") +" "+ base.getId();
                ret.append("<script type=\"text/javascript\" language=\"JavaScript\">\n");
                ret.append("   alert('"+msg+"');\n");
                ret.append("   location='"+paramRequest.getRenderUrl().setAction("edit").toString()+"';\n");
                ret.append("</script>\n");
            }catch(Exception e) {
                log.error(e); msg=paramRequest.getLocaleString("msgErrUpdateResource") +" "+ base.getId(); 
            }
        }
        else if(action.equals("remove")) 
        {
            msg=admResUtils.removeResource(base);  
            ret.append(
                "<script type=\"text/javascript\" language=\"JavaScript\">\n"+
                "   alert('"+msg+"');\n"+
                "</script>\n");             
        }
        response.getWriter().print(ret.toString());        
    }
    
    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest)
    {
        StringBuffer ret=new StringBuffer("");
        Portlet base=getResourceBase();
        try
        {
            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN);
            url.setAction("update");
            
            ret.append("<div class=\"swbform\"> \n");
            ret.append("<form name=\"frmImgGalleryResource_"+base.getId()+"\" method=\"post\" enctype=\"multipart/form-data\" action=\""+ url.toString()+"\"> \n");            
            ret.append("<fieldset> \n");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> \n");
            ret.append("<tr> \n");
            ret.append("<td colspan=\"2\">");
            ret.append(paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_section"));
            ret.append("</td> \n");
            ret.append("</tr> \n");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_title") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"title\" ");
            ret.append(" value=\"" + base.getAttribute("title", "").trim().replaceAll("\"", "&#34;") + "\" />");
            ret.append("</td> \n");
            ret.append("\n</tr>");            
            ret.append("\n<tr>");
            ret.append("\n<td>* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_img") + "&nbsp;(<i>bmp, jpg, jpeg, gif, png</i>)</td>");
            ret.append("\n<td>");
            ret.append("<div id=\"igcontainer_"+base.getId()+"\" style=\"background-color:#F0F0F0; width:602px; height:432px; overflow:visible\">\n");
            ret.append("<table width=\"99%\" border=\"0\" align=\"center\">\n");
            ret.append("<tr>\n");
            ret.append("<td><span>" + paramRequest.getLocaleString("usrmsg_ImageGallery_imggrid") + "</span></td>\n");
            ret.append("<td align=\"right\"></td>\n");
            ret.append("<td align=\"right\">\n");
            ret.append("    <input type=\"button\" value=\"Agregar\" onclick=\"addRowToTable('igtbl_"+base.getId()+"');\" />&nbsp; \n");
            ret.append("    <input type=\"button\" value=\"Cancelar\" onclick=\"removeRowFromTable('igtbl_"+base.getId()+"');\"/></td>\n");
            ret.append("</tr>\n");
            ret.append("</table>\n");
            ret.append("<div id=\"iggrid_"+base.getId()+"\" style=\"width:600px;height:400px;left:2px;top:20px;overflow:scroll; background-color:#EFEFEF\">\n");
            ret.append("  <table id=\"igtbl_"+base.getId()+"\" width=\"99%\" cellspacing=\"1\" bgcolor=\"#8fbc8f\" align=\"center\">\n");
            ret.append("  <tr bgcolor=\"#808000\">\n");
            ret.append("    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"10\" height=\"20\" nowrap=\"nowrap\">&nbsp;</th>\n");
            ret.append("    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"20\" height=\"20\" nowrap=\"nowrap\">Editar</th>\n");
            ret.append("    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"30\" height=\"20\" nowrap=\"nowrap\">Eliminar</th>\n");
            ret.append("    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Archivo</th>\n");
            ret.append("    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Imagen</th>\n");
            ret.append("  </tr>\n");
            ret.append("</table>\n");
            ret.append("</div>\n");
            ret.append("</div>\n");
            ret.append("</td> \n");
            ret.append("</tr> \n");
            
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_imgwidth") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"imgwidth\" ");
            ret.append(" value=\"" + base.getAttribute("imgwidth", "220").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_imgheight") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"imgheight\" ");
            ret.append(" value=\"" + base.getAttribute("imgheight", "150").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fullwidth") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fullwidth\" ");
            ret.append(" value=\"" + base.getAttribute("fullwidth", "350").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fullheight") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fullheight\" ");
            ret.append(" value=\"" + base.getAttribute("fullheight", "280").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");            
            
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_autoplay") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"checkbox\" value=\"true\" name=\"autoplay\" ");
            if ("true".equals(base.getAttribute("autoplay", "false"))) {
                ret.append(" checked=\"checked\"");
            }
            ret.append("/>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_pause") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"pause\" ");
            ret.append(" value=\"" + base.getAttribute("pause", "2500").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fadetime") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fadetime\" ");
            ret.append(" value=\"" + base.getAttribute("fadetime", "500").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("</table> \n");
            ret.append("</fieldset>\n");
            
            ret.append("<fieldset>\n");
            ret.append("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n");
            ret.append("\n<tr>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"submit\" name=\"btnSave\" value=\"" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_submit") + "\" onClick=\"if(jsValida(this.form)) return true; else return false;\"/>&nbsp;");
            ret.append("\n<input type=\"reset\" name=\"btnReset\" value=\"" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_reset") + "\"/>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("</table>\n");
            ret.append("</fieldset>\n");
            
            ret.append("</form> \n");
            ret.append("* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_required"));
            ret.append("</div> \n");
            
            ret.append("<script type=\"text/javascript\">\n");                        
            ret.append("function addRowToTable(tblId, filename, img, cellSufix) {\n");
            ret.append("    var tbl = document.getElementById(tblId);\n");
            ret.append("    var lastRow = tbl.rows.length;\n");
            ret.append("    var iteration = lastRow;\n");
            ret.append("    var row = tbl.insertRow(lastRow);\n");
            ret.append("    row.style.backgroundColor = '#eee8aa';\n");
            ret.append("\n");
            ret.append("    // celda folio\n");
            ret.append("    var folioCell = row.insertCell(0);\n");
            ret.append("    folioCell.style.textAlign = 'right';\n");
            ret.append("    var folioTextNode = document.createTextNode(iteration);\n");
            ret.append("    folioCell.appendChild(folioTextNode);\n");
            
            ret.append("\n");
            ret.append("    // cell check edit\n");
            ret.append("    var editCheckCell = row.insertCell(1);\n");
            ret.append("    editCheckCell.style.textAlign = 'center';\n");
            ret.append("    var editCheckInput = document.createElement('input');\n");
            ret.append("    editCheckInput.type = 'checkbox';\n");
            ret.append("    if(cellSufix) {\n");
            ret.append("        editCheckInput.name = 'edit_'+cellSufix;\n");
            ret.append("        editCheckInput.id = 'edit_'+cellSufix;\n");
            ret.append("    }else {\n");
            ret.append("        editCheckInput.name = 'edit_"+base.getId()+"_'+iteration;\n");
            ret.append("        editCheckInput.id = 'edit_"+base.getId()+"_'+iteration;\n");
            ret.append("    }");
            
            ret.append("    editCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altEdit")+"';\n");
            ret.append("    editCheckInput.disabled = true;\n");
            ret.append("    editCheckInput.onclick = function(){\n");
            ret.append("        if(editCheckInput.checked) {\n");
            ret.append("            row.cells[row.cells.length-1].innerHTML = '<input type=\"file\" id=\"imggallery_"+base.getId()+"_'+iteration+'\" name=\"imggallery_"+base.getId()+"_'+iteration+'\" size=\"40\" />';\n");
            ret.append("            editCheckInput.checked = false;\n");
            ret.append("            editCheckInput.disabled = true;\n");
            ret.append("        }\n");
            ret.append("    };\n");
            ret.append("    editCheckCell.appendChild(editCheckInput);\n");
            
            ret.append("\n");
            ret.append("    // cell check remove\n");
            ret.append("    var removeCheckCell = row.insertCell(2);\n");
            ret.append("    removeCheckCell.style.textAlign = 'center';\n");
            ret.append("    var removeCheckInput = document.createElement('input');\n");
            ret.append("    removeCheckInput.type = 'checkbox';\n");
            ret.append("    if(cellSufix) {\n");
            ret.append("        removeCheckInput.name = 'remove_'+cellSufix;\n");
            ret.append("        removeCheckInput.id = 'remove_'+cellSufix;\n");
            ret.append("    }else {\n");
            ret.append("        removeCheckInput.name = 'remove_"+base.getId()+"_'+iteration;\n");
            ret.append("        removeCheckInput.id = 'remove_"+base.getId()+"_'+iteration;\n");
            ret.append("    }");
            ret.append("    removeCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altRemove")+"';\n");
            ret.append("    if(filename && img) {\n");
            ret.append("        removeCheckInput.disabled = false;\n");
            ret.append("    }else {\n");
            ret.append("        removeCheckInput.disabled = true;\n");
            ret.append("    }\n");
            ret.append("    removeCheckInput.value = '1';\n");
            ret.append("    removeCheckCell.appendChild(removeCheckInput);\n");
            ret.append("\n");
                        
            ret.append("    // celda nombre de archivo\n");
            ret.append("    var filenameCell = row.insertCell(3);\n");
            ret.append("    if(filename) {\n");
            ret.append("        var fnTxt = document.createTextNode(filename);\n");
            ret.append("        filenameCell.appendChild(fnTxt);\n");
            ret.append("    }\n");
            ret.append("    filenameCell.style.textAlign = 'left';\n");
            ret.append("\n");            
            
            ret.append("    var imgCell = row.insertCell(4);\n");
            ret.append("    if(img) {\n");
            ret.append("        imgCell.style.textAlign = 'center';\n");
            ret.append("        imgCell.innerHTML = img;\n");
            ret.append("            editCheckInput.disabled = false;\n");
            ret.append("    }else {\n");            
            ret.append("        // file uploader\n");
            ret.append("        imgCell.style.textAlign = 'right';\n");
            ret.append("        var fileInput = document.createElement('input');\n");
            ret.append("        fileInput.type = 'file';\n");
            ret.append("        fileInput.name = 'imggallery_"+base.getId()+"_'+iteration;\n");
            ret.append("        fileInput.id = 'imggallery_"+base.getId()+"_'+iteration;\n");
            ret.append("        fileInput.size = 40;\n");
            ret.append("        imgCell.appendChild(fileInput);\n");            
            ret.append("    }\n");            
            ret.append("}\n");            
            
            ret.append("\n");            
            ret.append("function removeRowFromTable(tblId) {\n");
            ret.append("    var tbl = document.getElementById(tblId);\n");
            ret.append("    var lastRow = tbl.rows.length;\n");
            ret.append("    if(lastRow >= 2) {\n");
            ret.append("        tbl.deleteRow(lastRow - 1);\n");
            ret.append("    }\n");
            ret.append("}\n");
            
            
            int i = 1;
            String input;
            //do {
            for(int j=0; j<15; j++) {
                input = "imggallery_" + base.getId() + "_" + i;
                if( base.getAttribute(input)!=null ) {
                    ret.append("addRowToTable('igtbl_"+base.getId()+"', '"+base.getAttribute(input)+"', '"+admResUtils.displayImage(base, base.getAttribute(input), input)+"', '"+input.substring(11)+"');\n");
                }
                i++;
            }
            //}while( base.getAttribute(input)!=null );
            
            ret.append("</script>\n");            
            ret.append(getScript(request, paramRequest));
        }
        catch(Exception e) {  log.error(e); }
        return ret.toString();
    }
    
    private String getScript(HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        StringBuffer ret = new StringBuffer();
        try
        {
            ret.append("\n<script type=\"text/javascript\">");
            /*ret.append("\nvar swOk=0, optionObj;");*/
            ret.append("\nfunction jsValida(pForm) {");
            /*ret.append("\n   if(pForm.question.value==null || pForm.question.value=='' || pForm.question.value==' ')");
            ret.append("\n   {");
            ret.append("\n       alert('" + paramsRequest.getLocaleString("usrmsg_Encuesta_doAdmin_msgQuestion") + "');");
            ret.append("\n       pForm.question.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   if(pForm.selOption.length < 2)");
            ret.append("\n   {");
            ret.append("\n       alert('" + paramsRequest.getLocaleString("usrmsg_Encuesta_doAdmin_msgOption") + "');");
            ret.append("\n       pForm.txtOption.focus();");
            ret.append("\n       return false;");
            ret.append("\n   }");
            ret.append("\n   if (!setPrefix(pForm.selLink, 'http://')) return false;");            
            ret.append("\n   if(!isFileType(pForm.imgencuesta, 'bmp|jpg|jpeg|gif')) return false;");
            ret.append("\n   if(!isFileType(pForm.button, 'bmp|jpg|jpeg|gif')) return false;");
            ret.append("\n   if(!isFileType(pForm.backimgres, 'bmp|jpg|jpeg|gif')) return false;");
            ret.append("\n   if(pForm.textcolor.value==null || pForm.textcolor.value=='' || pForm.textcolor.value==' ')");
            ret.append("\n       pForm.textcolor.value='#'+ document.selColor.getColor();");
            ret.append("\n   if(!isHexadecimal(pForm.textcolor)) return false;");
            ret.append("\n   if(pForm.textcolores.value==null || pForm.textcolores.value=='' || pForm.textcolores.value==' ')");
            ret.append("\n       pForm.textcolores.value='#'+ document.selColorBack.getColor();");
            ret.append("\n   if(!isHexadecimal(pForm.textcolores)) return false;");*/
            ret.append("\n   if(!isNumber(pForm.imgwidth)) return false;");
            ret.append("\n   if(!isNumber(pForm.imgheight)) return false;");
            ret.append("\n   if(!isNumber(pForm.fullwidth)) return false;");
            ret.append("\n   if(!isNumber(pForm.fullheight)) return false;");
            ret.append("\n   if(!isNumber(pForm.pause)) return false;");
            ret.append("\n   if(!isNumber(pForm.fadetime)) return false;");            
            /*ret.append("\n   if(!isNumber(pForm.branches)) return false;");
            ret.append("\n   if(!isNumber(pForm.width)) return false;");
            ret.append("\n   if(!isNumber(pForm.height)) return false;");
            ret.append("\n   if(!isNumber(pForm.top)) return false;");
            ret.append("\n   if(!isNumber(pForm.left)) return false;");            
            ret.append("\n   pForm.option.value='';");
            ret.append("\n   for(var i=0; i<pForm.selOption.length; i++)");
            ret.append("\n   {");
            ret.append("\n       if(i>0) pForm.option.value+=\"|\";");
            ret.append("\n       pForm.option.value+=pForm.selOption.options[i].value;");
            ret.append("\n   }");
            ret.append("\n   pForm.link.value='';");
            ret.append("\n   for(var i=0; i<pForm.selLink.length; i++)");
            ret.append("\n   {");
            ret.append("\n       if(i>0) pForm.link.value+=\"|\";");
            ret.append("\n       pForm.link.value+=pForm.selLink.options[i].value;");
            ret.append("\n   }");*/
            ret.append("\n   return true;");
            ret.append("\n}");
            /*ret.append(admResUtils.loadAddOption());            
            ret.append(admResUtils.loadEditOption());
            ret.append(admResUtils.loadUpdateOption());
            ret.append(admResUtils.loadDeleteOption());
            ret.append(admResUtils.loadDuplicateOption());
            ret.append(admResUtils.loadIsFileType());*/
            ret.append(admResUtils.loadIsNumber());
            /*ret.append(admResUtils.loadSetPrefix());
            ret.append(admResUtils.loadIsHexadecimal());*/
            ret.append("\n</script>");
        }
        catch(Exception e) {
            log.error(e); 
        }
        return ret.toString();
    }
    
    private void removeAllNodes(Document dom, short nodeType, String name)
    {
        NodeList list = dom.getElementsByTagName(name);
        for (int i = 0; i < list.getLength(); i++)
        {
            Node node=list.item(i);
            if (node.getNodeType() == nodeType)
            {
                node.getParentNode().removeChild(node);
                if(node.hasChildNodes()) {
                    removeAllNodes(dom, nodeType, name);
                }
            }
        }
    }
}