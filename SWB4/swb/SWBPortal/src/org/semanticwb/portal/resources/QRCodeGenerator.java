/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources;

import com.google.zxing.WriterException;
import java.io.IOException;
import com.google.zxing.common.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import java.io.File;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.PrintWriter;
import org.semanticwb.SWBPortal;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author gabriela.rosales
 */
public class QRCodeGenerator extends GenericAdmResource {

    private static Logger log = SWBUtils.getLogger(QRCodeGenerator.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("image/png; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        String text = base.getAttribute("text", "no data");
        out.println("<img alt=\""+text+"\" src=\""+SWBPortal.getWebWorkPath()+base.getWorkPath()+"/Qr.png" +"\"/>");
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        String resourceUpdatedMessage = paramRequest.getLocaleString("msgOkUpdateResource");
        String fieldsetText = paramRequest.getLocaleString("lblAdmin_Settings");

        String action = paramRequest.getAction();
        if(SWBParamRequest.Action_ADD.equals(action)) {
            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("   alert('" + resourceUpdatedMessage + " " + base.getId() + "');");
            out.println("   window.location.href='" + paramRequest.getRenderUrl().setAction(SWBParamRequest.Action_EDIT).toString() + "';");
            out.println("-->");
            out.println("</script>");
        }
        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setAction(SWBParamRequest.Action_EDIT);
        
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("  dojo.require('dijit.layout.ContentPane');");
        out.println("  dojo.require('dijit.form.Form');");
        out.println("  dojo.require('dijit.form.SimpleTextarea');");
        out.println("  dojo.require('dijit.form.ValidationTextBox');");
        out.println("  dojo.provide('ValidationTextarea');");

        out.println("function enviar() {");
        out.println("  var objd=dijit.byId('gQR');");
        out.println("  if(objd.validate()) {");
        out.println("    return true;");
        out.println("  }else {");
        out.println("    alert('Datos incompletos o erroneos');");
        out.println("  }");
        out.println("  return false;");
        out.println("}");

        out.println("dojo.declare( ");
        out.println("  'ValidationTextarea',");
        out.println("  [dijit.form.ValidationTextBox, dijit.form.SimpleTextarea],");
        out.println("  {");
        out.println("    invalidMessage: 'Este dato es requerido',");
        out.println("    promptMessage: 'Ingresa',");
        out.println("    postCreate: function() {");
        out.println("    this.inherited(arguments);");
        out.println("  },");
        out.println("  validate: function() {");
        out.println("        if(arguments.length==0)");
        out.println("          return this.validate(false);");
        out.println("        return this.inherited(arguments);");
        out.println("      },");
        out.println("  onFocus: function() {");
        out.println("        if(!this.isValid()) {");
        out.println("          this.displayMessage(this.getErrorMessage());");
        out.println("        }");
        out.println("  },"); 
        out.println("  onBlur: function() {");
        out.println("          this.validate(false);");
        out.println("        }");
        out.println("  }");
        out.println(");");
        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"swbform\">");
        out.println("  <form id=\"gQR\" action=\"" + urlAction + "\" dojoType=\"dijit.form.Form\"  method=\"post\" >");
        out.println("    <fieldset>");
        out.println("      <legend>");
        out.println(fieldsetText);
        out.println("      </legend>");
        out.println("      <ul class=\"swbform-ul\">");
        out.println("        <li class=\"swbform-li\">\n");
        out.println("          <label for=\"text\" class=\"swbform-label\">Texto*</label>\n");
        out.println("          <textarea  id=\"text\" name=\"text\" dojoType=\"ValidationTextarea\" required=\"true\" promptMessage=\"Campo de texto requerido\" invalidMessage=\"Campo de texto requerido\" trim=\"true\" rows=\"4\" cols=\"25\" >" + base.getAttribute("text", "") + "</textarea>\n");
        out.println("        </li>\n");
        out.println("        <li class=\"swbform-li\">\n");
        out.println("          <label for=\"size\" class=\"swbform-label\">TamaÃ±o imagen pixeles</label>\n");
        out.println("          <input type=\"text\" id=\"size\" name=\"size\" value=\"" + base.getAttribute("size", "100") + "\" maxlength =\"4\" dojoType=\"dijit.form.ValidationTextBox\"  promptMessage=\"TamaÃ±o de la imagen en pixeles\" invalidMessage=\"Campo de texto requerido\" regExp=\"\\d{1,4}\" trim=\"true\" />\n");
        out.println("        </li>\n");
        out.println("      </ul>");
        out.println("    </fieldset>");
        out.println("    <fieldset>");
        out.println("      <button dojoType=\"dijit.form.Button\" type=\"reset\">Restablecer</button>");
        out.println("      <button dojoType=\"dijit.form.Button\" type=\"submit\" onclick=\"return enviar()\" >Guardar</button>");
        out.println("    </fieldset>");
        out.println("  </form>");
        out.println("</div>");
    }

    private void generateImage(final String text, final int size, final String filePath) throws WriterException {

        try {
            Charset charset = Charset.forName("ISO-8859-1");
            CharsetEncoder encoder = charset.newEncoder();
            byte[] b = null;

            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(text));
            b = bbuf.array();

            String data = null;
            data = new String(b, "ISO-8859-1");
            BitMatrix matrix = null;
            com.google.zxing.Writer writer = new QRCodeWriter();
            matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, size, size);
            File fi = new File(filePath);
            if (!fi.exists()) {
                fi.mkdir();
            }
            String fPath = filePath + "Qr.png";
            File file = new File(fPath);

            MatrixToImageWriter.writeToFile(matrix, "PNG", file);

        } catch (IOException ex) {
            log.error(ex);
        }
    }
}
