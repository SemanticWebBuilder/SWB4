
package org.semanticwb.portal.resources;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.FileUpload;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;


public class ImageGallery extends GenericAdmResource {
    private static Logger log = SWBUtils.getLogger(ImageGallery.class);
    private WBAdmResourceUtils admResUtils=new WBAdmResourceUtils();
    private String workPath;
    private String webWorkPath;
    
    private static int idx = 1;
    
    @Override
    public void setResourceBase(Resource base)
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
        
        Resource base=getResourceBase();        
        
        try {
            out.println("<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/jquery-imagegallery.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"/swb/swbadmin/js/jquery/jquery-1.3.js\"></script>");

            out.println("<script type=\"text/javascript\"> ");            
            out.println("    simpleGallery_navpanel={ ");
            out.println("        panel: {height:'45px', opacity:0.5, paddingTop:'5px', fontStyle:'bold 9px Verdana'}, //customize nav panel container ");
            out.println("        images: [ '/swb/swbadmin/js/jquery/themes/control_rewind_blue.png', '/swb/swbadmin/js/jquery/themes/control_play_blue.png', '/swb/swbadmin/js/jquery/themes/control_fastforward_blue.png', '/swb/swbadmin/js/jquery/themes/control_pause_blue.png'], //nav panel images (in that order) ");
            out.println("        imageSpacing: {offsetTop:[-3, 0, -3], spacing:10}, //top offset of left, play, and right images, PLUS spacing between the 3 images ");
            out.println("        slideduration: 500 //duration of slide up animation to reveal panel ");
            out.println("    }; ");
            
            out.println("    var mygallery=new simpleGallery({ ");
            out.println("	wrapperid: 'imggallery_"+ base.getId()+"', //ID of main gallery container, ");
            out.println("	dimensions: ["+base.getAttribute("imgwidth","220")+", "+base.getAttribute("imgheight","150")+"], //width/height of gallery in pixels. Should reflect dimensions of the images exactly ");
            out.println("	imagearray: [ ");

            Iterator<String> it = base.getAttributeNames();
            while(it.hasNext()) {
                String attname = it.next();
                String attval = base.getAttribute(attname);
                if(attval!=null && attname.startsWith("imggallery_" + base.getId())) {
                    out.println("['"+webWorkPath +"/"+attval+"','','']");
                    if(it.hasNext()) {
                        out.print(", ");
                    }
                }
            }
            
            out.println("	], ");
            out.println("	autoplay: "+base.getAttribute("autoplay","false")+", ");
            out.println("	persist: false, ");
            out.println("	pause: "+base.getAttribute("pause","2500")+", //pause between slides (milliseconds) ");
            out.println("	fadeduration: "+base.getAttribute("fadetime","500")+", //transition duration (milliseconds) ");
            out.println("	oninit:function(){ //event that fires when gallery has initialized/ ready to run ");
            out.println("	}, ");
            out.println("	onslide:function(curslide, i){ //event that fires after each slide is shown ");
            out.println("		//curslide: returns DOM reference to current slide's DIV (ie: try alert(curslide.innerHTML) ");
            out.println("		//i: integer reflecting current image within collection being shown (0=1st image, 1=2nd etc) ");
            out.println("	} ");
            out.println("        ,fullwidth:"+base.getAttribute("fullwidth","350")+" ");
            out.println("        ,fullheight:"+base.getAttribute("fullheight","280")+" ");
            out.println("        ,imageClosing: '/swb/swbadmin/js/jquery/themes/cancel.png'");
            out.println("    } ");
            out.println("); ");
            out.println("</script> ");
            out.println("<div class=\"swb-galeria\">");
            out.println("<p>"+base.getAttribute("title","")+"</p>");
            out.println("<span><div id=\"imggallery_"+base.getId()+"\" style=\"position:relative; visibility:hidden\" /></span> ");
            out.println("</div>");
        }catch(Exception e) {
            log.error(e);
        }
        out.flush();
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {        
        response.setContentType("text/html;charset=iso-8859-1");
        PrintWriter out = response.getWriter();

        Resource base=getResourceBase();
        
        String msg=paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_undefinedOperation");
        String action = null != request.getParameter("act") && !"".equals(request.getParameter("act").trim()) ? request.getParameter("act").trim() : paramRequest.getAction();
        
        if(action.equals("add") || action.equals("edit")) {
            out.println(getForm(request, paramRequest));
        }else if(action.equals("update")) {
            FileUpload fup = new FileUpload();
            try {
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
                          
                
                Iterator<String> it = base.getAttributeNames();
                while(it.hasNext()) {
                    String attname = it.next();
                    System.out.println("attribute  ["+attname+", "+base.getAttribute(attname)+"]");
                }
                
                
                int i = 1;
                String fileInput, filename, removeChk;
                do {
                //for(int j=0; j<15; j++) {
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
                //}
                } while(value!=null || base.getAttribute(fileInput)!=null);
                
                base.updateAttributesToDB();
                
                msg=paramRequest.getLocaleString("msgOkUpdateResource") +" "+ base.getId();
                out.println("<script type=\"text/javascript\" language=\"JavaScript\">");
                out.println("   alert('"+msg+"');");
                out.println("   location='"+paramRequest.getRenderUrl().setAction("edit").toString()+"';");
                out.println("</script>");
            }catch(Exception e) {
                log.error(e); msg=paramRequest.getLocaleString("msgErrUpdateResource") +" "+ base.getId(); 
            }
        }
        else if(action.equals("remove")) 
        {
            msg=admResUtils.removeResource(base);  
            out.println(
                "<script type=\"text/javascript\" language=\"JavaScript\">"+
                "   alert('"+msg+"');"+
                "</script>");             
        }
        out.flush();
    }
    
    private String getForm(javax.servlet.http.HttpServletRequest request, SWBParamRequest paramRequest) {
        StringBuffer ret=new StringBuffer();
        Resource base=getResourceBase();
        try {
            SWBResourceURL url = paramRequest.getRenderUrl().setMode(paramRequest.Mode_ADMIN);
            url.setAction("update");
            
            ret.append("\n<div class=\"swbform\"> ");
            ret.append("\n<form name=\"frmImgGalleryResource_"+base.getId()+"\" method=\"post\" enctype=\"multipart/form-data\" action=\""+ url.toString()+"\"> ");
            ret.append("\n<fieldset> ");
            ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");

            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_title") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"title\" ");
            ret.append("\n value=\"" + base.getAttribute("title", "").trim().replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td> ");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_img") + "&nbsp;(<i>bmp, jpg, jpeg, gif, png</i>)</td>");
            ret.append("\n<td>");
            ret.append("\n<div id=\"igcontainer_"+base.getId()+"\" style=\"background-color:#F0F0F0; width:602px; height:432px; overflow:visible\"> ");
            ret.append("\n<table width=\"99%\" border=\"0\" align=\"center\"> ");
            ret.append("\n<tr> ");
            ret.append("\n<td><span>" + paramRequest.getLocaleString("usrmsg_ImageGallery_imggrid") + "</span></td> ");
            ret.append("\n<td align=\"right\"></td> ");
            ret.append("\n<td align=\"right\"> ");
            ret.append("\n    <input type=\"button\" value=\"Agregar\" onclick=\"addRowToTable('igtbl_"+base.getId()+"');\" />&nbsp;  ");
            ret.append("\n    <input type=\"button\" value=\"Cancelar\" onclick=\"removeRowFromTable('igtbl_"+base.getId()+"');\"/></td> ");
            ret.append("\n</tr> ");
            ret.append("\n</table> ");
            ret.append("\n<div id=\"iggrid_"+base.getId()+"\" style=\"width:600px;height:400px;left:2px;top:20px;overflow:scroll; background-color:#EFEFEF\"> ");
            ret.append("\n  <table id=\"igtbl_"+base.getId()+"\" width=\"99%\" cellspacing=\"1\" bgcolor=\"#8fbc8f\" align=\"center\"> ");
            ret.append("\n  <tr bgcolor=\"#808000\"> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"10\" height=\"20\" nowrap=\"nowrap\">&nbsp;</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"20\" height=\"20\" nowrap=\"nowrap\">Editar</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"30\" height=\"20\" nowrap=\"nowrap\">Eliminar</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Archivo</th> ");
            ret.append("\n    <th align=\"center\" scope=\"col\" style=\"text-align:center;\" width=\"40%\" height=\"20\" nowrap=\"nowrap\">Imagen</th> ");
            ret.append("\n  </tr> ");
            ret.append("\n</table> ");
            ret.append("\n</div> ");
            ret.append("\n</div> ");
            ret.append("\n</td>  ");
            ret.append("\n</tr>  ");
            
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_imgwidth") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"imgwidth\" ");
            ret.append("\n value=\"" + base.getAttribute("imgwidth", "220").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_imgheight") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"imgheight\" ");
            ret.append("\n value=\"" + base.getAttribute("imgheight", "150").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fullwidth") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fullwidth\" ");
            ret.append("\n value=\"" + base.getAttribute("fullwidth", "350").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fullheight") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fullheight\" ");
            ret.append("\n value=\"" + base.getAttribute("fullheight", "280").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_autoplay") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"checkbox\" value=\"true\" name=\"autoplay\" ");
            if ("true".equals(base.getAttribute("autoplay", "false"))) {
                ret.append("\n checked=\"checked\"");
            }
            ret.append("\n/>");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_pause") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"pause\" ");
            ret.append("\n value=\"" + base.getAttribute("pause", "2500").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n<tr>");
            ret.append("\n<td>" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_fadetime") + "</td>");
            ret.append("\n<td>");
            ret.append("\n<input type=\"text\" size=\"50\" maxlength=\"50\" name=\"fadetime\" ");
            ret.append("\n value=\"" + base.getAttribute("fadetime", "500").replaceAll("\"", "&#34;") + "\" />");
            ret.append("\n</td>");
            ret.append("\n</tr>");
            ret.append("\n</table> ");
            ret.append("\n</fieldset> ");
            
            ret.append("\n<fieldset> ");
            ret.append("\n<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\"> ");
            ret.append("\n <tr>");
            ret.append("\n <td>");
            ret.append("\n <input type=\"submit\" name=\"btnSave\" value=\"" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_submit") + "\" onClick=\"if(jsValida(this.form)) return true; else return false;\"/>&nbsp;");
            ret.append("\n <input type=\"reset\" name=\"btnReset\" value=\"" + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_reset") + "\"/>");
            ret.append("\n </td>");
            ret.append("\n </tr>");
            ret.append("\n</table> ");
            ret.append("\n</fieldset> ");
            
            ret.append("\n</form>  ");
            ret.append("\n* " + paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_required"));
            ret.append("\n</div>  ");
            
            ret.append("\n<script type=\"text/javascript\"> ");
            ret.append("\nfunction addRowToTable(tblId, filename, img, cellSufix) { ");
            ret.append("\n    var tbl = document.getElementById(tblId); ");
            ret.append("\n    var lastRow = tbl.rows.length; ");
            ret.append("\n    var iteration = lastRow; ");
            ret.append("\n    var row = tbl.insertRow(lastRow); ");
            ret.append("\n    row.style.backgroundColor = '#eee8aa'; ");
            ret.append("\n ");
            ret.append("\n    // celda folio ");
            ret.append("\n    var folioCell = row.insertCell(0); ");
            ret.append("\n    folioCell.style.textAlign = 'right'; ");
            ret.append("\n    var folioTextNode = document.createTextNode(iteration); ");
            ret.append("\n    folioCell.appendChild(folioTextNode); ");
            
            ret.append("\n ");
            ret.append("\n    // cell check edit ");
            ret.append("\n    var editCheckCell = row.insertCell(1); ");
            ret.append("\n    editCheckCell.style.textAlign = 'center'; ");
            ret.append("\n    var editCheckInput = document.createElement('input'); ");
            ret.append("\n    editCheckInput.type = 'checkbox'; ");
            ret.append("\n    if(cellSufix) { ");
            ret.append("\n        editCheckInput.name = 'edit_'+cellSufix; ");
            ret.append("\n        editCheckInput.id = 'edit_'+cellSufix; ");
            ret.append("\n    }else { ");
            ret.append("\n        editCheckInput.name = 'edit_"+base.getId()+"_'+iteration; ");
            ret.append("\n        editCheckInput.id = 'edit_"+base.getId()+"_'+iteration; ");
            ret.append("\n    }");
            
            ret.append("\n    editCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altEdit")+"'; ");
            ret.append("\n    editCheckInput.disabled = true; ");
            ret.append("\n    editCheckInput.onclick = function(){ ");
            ret.append("\n        if(editCheckInput.checked) { ");
            ret.append("\n            row.cells[row.cells.length-1].innerHTML = '<input type=\"file\" id=\"imggallery_"+base.getId()+"_'+iteration+'\" name=\"imggallery_"+base.getId()+"_'+iteration+'\" size=\"40\" />'; ");
            ret.append("\n            editCheckInput.checked = false; ");
            ret.append("\n            editCheckInput.disabled = true; ");
            ret.append("\n        } ");
            ret.append("\n    }; ");
            ret.append("\n    editCheckCell.appendChild(editCheckInput); ");
            
            ret.append("\n ");
            ret.append("\n    // cell check remove ");
            ret.append("\n    var removeCheckCell = row.insertCell(2); ");
            ret.append("\n    removeCheckCell.style.textAlign = 'center'; ");
            ret.append("\n    var removeCheckInput = document.createElement('input'); ");
            ret.append("\n    removeCheckInput.type = 'checkbox'; ");
            ret.append("\n    if(cellSufix) { ");
            ret.append("\n        removeCheckInput.name = 'remove_'+cellSufix; ");
            ret.append("\n        removeCheckInput.id = 'remove_'+cellSufix; ");
            ret.append("\n    }else { ");
            ret.append("\n        removeCheckInput.name = 'remove_"+base.getId()+"_'+iteration; ");
            ret.append("\n        removeCheckInput.id = 'remove_"+base.getId()+"_'+iteration; ");
            ret.append("\n    }");
            ret.append("\n    removeCheckInput.alt = '"+paramRequest.getLocaleString("usrmsg_ImageGallery_doAdmin_altRemove")+"'; ");
            ret.append("\n    if(filename && img) { ");
            ret.append("\n        removeCheckInput.disabled = false; ");
            ret.append("\n    }else { ");
            ret.append("\n        removeCheckInput.disabled = true; ");
            ret.append("\n    } ");
            ret.append("\n    removeCheckInput.value = '1'; ");
            ret.append("\n    removeCheckCell.appendChild(removeCheckInput); ");
            ret.append("\n ");
                        
            ret.append("\n    // celda nombre de archivo ");
            ret.append("\n    var filenameCell = row.insertCell(3); ");
            ret.append("\n    if(filename) { ");
            ret.append("\n        var fnTxt = document.createTextNode(filename); ");
            ret.append("\n        filenameCell.appendChild(fnTxt); ");
            ret.append("\n    } ");
            ret.append("\n    filenameCell.style.textAlign = 'left'; ");
            ret.append("\n ");
            
            ret.append("\n    var imgCell = row.insertCell(4); ");
            ret.append("\n    if(img) { ");
            ret.append("\n        imgCell.style.textAlign = 'center'; ");
            ret.append("\n        imgCell.innerHTML = img; ");
            ret.append("\n            editCheckInput.disabled = false; ");
            ret.append("\n    }else { ");
            ret.append("\n        // file uploader ");
            ret.append("\n        imgCell.style.textAlign = 'right'; ");
            ret.append("\n        var fileInput = document.createElement('input'); ");
            ret.append("\n        fileInput.type = 'file'; ");
            ret.append("\n        fileInput.name = 'imggallery_"+base.getId()+"_'+iteration; ");
            ret.append("\n        fileInput.id = 'imggallery_"+base.getId()+"_'+iteration; ");
            ret.append("\n        fileInput.size = 40; ");
            ret.append("\n        imgCell.appendChild(fileInput); ");
            ret.append("\n    } ");
            ret.append("\n} ");
            
            ret.append("\n ");
            ret.append("\nfunction removeRowFromTable(tblId) { ");
            ret.append("\n    var tbl = document.getElementById(tblId); ");
            ret.append("\n    var lastRow = tbl.rows.length; ");
            ret.append("\n    if(lastRow >= 2) { ");
            ret.append("\n        tbl.deleteRow(lastRow - 1); ");
            ret.append("\n    } ");
            ret.append("\n} ");
            
            Iterator<String> it = base.getAttributeNames();
            while(it.hasNext()) {
                String attname = it.next();
                String attval = base.getAttribute(attname);
                if(attval!=null && attname.startsWith("imggallery_" + base.getId())) {
                    ret.append("\naddRowToTable('igtbl_"+base.getId()+"', '"+base.getAttribute(attname)+"', '"+admResUtils.displayImage(base, base.getAttribute(attname), attname)+"', '"+attname.substring(11)+"'); ");
                }
            }
            
            ret.append("\n</script>");
            ret.append(getScript(request, paramRequest));
        }catch(Exception e) {
            log.error(e);
        }
        return ret.toString();
    }
    
    private String getScript(HttpServletRequest request, SWBParamRequest paramsRequest)
    {
        StringBuffer ret = new StringBuffer();
        try
        {
            ret.append("\n<script type=\"text/javascript\">");

            ret.append("\nfunction jsValida(pForm) {");
            ret.append("\n    if(!isNumber(pForm.imgwidth)) return false;");
            ret.append("\n    if(!isNumber(pForm.imgheight)) return false;");
            ret.append("\n    if(!isNumber(pForm.fullwidth)) return false;");
            ret.append("\n    if(!isNumber(pForm.fullheight)) return false;");
            ret.append("\n    if(!isNumber(pForm.pause)) return false;");
            ret.append("\n    if(!isNumber(pForm.fadetime)) return false;");
            ret.append("\n    return true;");
            ret.append("\n }");
            ret.append(admResUtils.loadIsNumber());
            ret.append("\n</script>");
        }
        catch(Exception e) {
            log.error(e); 
        }
        return ret.toString();
    }
}