/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.util.UploaderFileCacheUtils;
import org.w3c.dom.Document;

/**
 *
 * @author ana.garcias
 */
public class GanttChartTest {
    
    public GanttChartTest() {
    }
    
    @BeforeClass
     public static void setUpClass() {
        String base = SWBUtils.getApplicationPath();
        SWBPlatform plat = SWBPlatform.createInstance();
        plat.setPersistenceType(SWBPlatform.PRESIST_TYPE_SWBTRIPLESTOREEXT);
        SWBPlatform.getSemanticMgr().initializeDB();
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/ana.garcias/Documents/Proyectos5/SWBBSCom/build/web/WEB-INF/owl/swb.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/ana.garcias/Documents/Proyectos5/SWBBSCom/build/web/WEB-INF/owl/swb_rep.owl");
        SWBPlatform.getSemanticMgr().addBaseOntology("C:/Users/ana.garcias/Documents/Proyectos5/SWBBSCom/build/web/WEB-INF/owl/ext/bscom.owl");
        SWBPlatform.getSemanticMgr().loadBaseVocabulary();
        SWBPlatform.getSemanticMgr().loadDBModels();
        SWBPlatform.getSemanticMgr().getOntology().rebind();
    }

    /**
     * Test of doGetPNGImage method, of class GanttChart.
     */
    @Test
    public void testDoGetPNGImage() throws Exception {
        System.out.println("doGetPNGImage");
        
         String data = "<svg xmlns=\"http://www.w3.org/2000/svg\" class=\"chart\" width=\"1222\" height=\"205\">\n" +
"<g class=\"gantt-chart\" width=\"1222\" height=\"205\" transform=\"translate(150, 20)\">\n" +
"<rect rx=\"5\" ry=\"5\" style=\"fill: #33B5E5;\" y=\"0\" transform=\"translate(0,6)\" height=\"53\" width=\"1032\"></rect>\n" +
"<rect rx=\"5\" ry=\"5\" style=\"fill: #CCCCCC;\" y=\"0\" transform=\"translate(33.94736842105264,65)\" height=\"53\" width=\"52.537592525584806\"></rect>\n" +
"<g class=\"x axis\" transform=\"translate(0, 125)\"><line class=\"tick minor\" y2=\"8\" transform=\"translate(105.23684210526316,0)\" x2=\"0\" style=\"opacity:1;\"></line>\n" +
"<line class=\"tick minor\" y2=\"8\" transform=\"translate(308.92105263157896,0)\" x2=\"0\" style=\"opacity: 1;\"></line>\n" +
"<line class=\"tick minor\" y2=\"8\" transform=\"translate(519.3947368421052,0)\" x2=\"0\" style=\"opacity: 1;\"></line>\n" +
"<line class=\"tick minor\" y2=\"8\" transform=\"translate(729.8684210526316,0)\" x2=\"0\" style=\"opacity: 1;\"></line>\n" +
"<line class=\"tick minor\" y2=\"8\" transform=\"translate(940.3421052631579,0)\" x2=\"0\" style=\"opacity: 1;\"></line>\n" +
"<g class=\"tick major\" transform=\"translate(0,0)\" style=\"opacity: 1;\"><line y2=\"8\" x2=\"0\"></line>\n" +
"<text y=\"16\" dy=\".71em\" x=\"0\" style=\"text-anchor: middle;\">01/05/2014</text></g>\n" +
"<g class=\"tick major\" transform=\"translate(210.47368421052633,0)\" style=\"opacity: 1;\"><line y2=\"8\" x2=\"0\"></line>\n" +
"<text y=\"16\" dy=\".71em\" x=\"0\" style=\"text-anchor: middle;\">01/06/2014</text></g>\n" +
"<g class=\"tick major\" transform=\"translate(414.15789473684214,0)\" style=\"opacity: 1;\"><line y2=\"8\" x2=\"0\"></line>\n" +
"<text y=\"16\" dy=\".71em\" x=\"0\"style=\"text-anchor: middle;\">01/07/2014</text></g>\n" +
"<g class=\"tick major\" transform=\"translate(624.6315789473684,0)\" style=\"opacity: 1;\"><line y2=\"8\" x2=\"0\"></line>\n" +
"<text y=\"16\" dy=\".71em\" x=\"0\" style=\"text-anchor: middle;\">01/08/2014</text></g>\n" +
"<g class=\"tick major\" transform=\"translate(835.1052631578948,0)\" style=\"opacity: 1;\"><line y2=\"8\" x2=\"0\"></line>\n" +
"<text y=\"16\" dy=\".71em\" x=\"0\" style=\"text-anchor: middle;\">01/09/2014</text></g><path class=\"domain\" d=\"M0,8V0H1032V8\"></path>\n" +
"</g><g class=\"y axis\"><g class=\"tick major\" transform=\"translate(0,32.5)\" style=\"opacity: 1;\"><line x2=\"0\" y2=\"0\"></line>\n" +
"<text x=\"-3\" dy=\".32em\" y=\"0\" style=\"text-anchor: end;\">Implementaci├│n de modelos de mejores pr├ícticas de TIC - Planeado</text></g>\n" +
"<g class=\"tick major\" transform=\"translate(0,91.5)\" style=\"opacity: 1;\"><line x2=\"0\" y2=\"0\"></line>\n" +
"<text x=\"-3\" dy=\".32em\" y=\"0\" style=\"text-anchor: end;\">Implementaci├│n de modelos de mejores pr├ícticas de TIC - Real</text></g>\n" +
"<path class=\"domain\" d=\"M0,0H0V125H0\"></path></g></g></svg>";
         Document svg = SWBUtils.XML.xmlToDom(data);   
         String destpath = "C:\\Users\\ana.garcias\\Documents\\Proyectos5\\SWBBSCom\\build\\web\\work\\models\\InfotecPEMP";
            JPEGTranscoder t = new JPEGTranscoder();
            t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
            // Set the transcoder input and output.
            TranscoderInput input = new TranscoderInput(svg);
            OutputStream ostream = new FileOutputStream(destpath + "/graphics.jpg");
            TranscoderOutput output = new TranscoderOutput(ostream);
            try {
                // Perform the transcoding.
                t.transcode(input, output);
            } catch (TranscoderException ex) {
                java.util.logging.Logger.getLogger(GanttChart.class.getName()).log(Level.SEVERE, null, ex);
            }
            ostream.flush();
            ostream.close();
    }
}