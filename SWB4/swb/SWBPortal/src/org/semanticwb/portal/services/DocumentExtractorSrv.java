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
package org.semanticwb.portal.services;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hpsf.*;
import org.apache.poi.poifs.eventfilesystem.*;
import org.apache.poi.util.LittleEndian;
import java.io.*;
import org.apache.poi.POIDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * Utils to find in ms documents (word, excel, power point, pdf) via poi api.
 *
 * @author jorge.jimenez
 * @since version 3.0
 */
public class DocumentExtractorSrv implements HSSFListener {

    /**
     * The log.
     */
    private static Logger log = SWBUtils.getLogger(DocumentExtractorSrv.class);
    /**
     * The sstrec.
     */
    private SSTRecord sstrec;
    /**
     * The excel buf.
     */
    private static StringBuffer excelBuf = new StringBuffer();

    /**
     * Extrae el string de un archivo pdf
     *
     * Extract the string of a pdf document.
     *
     * @param file the file
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String pdfExtractor(File file) throws java.io.IOException {
        FileInputStream is = new FileInputStream(file);
        org.pdfbox.pdmodel.PDDocument pdfDocument = null;
        try {
            pdfDocument = org.pdfbox.pdmodel.PDDocument.load(is);


            if (pdfDocument.isEncrypted()) {
                //Just try using the default password and move on
                pdfDocument.decrypt("");
            }

            //create a writer where to append the text content.
            StringWriter writer = new StringWriter();
            org.pdfbox.util.PDFTextStripper stripper = new org.pdfbox.util.PDFTextStripper();
            stripper.writeText(pdfDocument, writer);

            // Note: the buffer to string operation is costless;
            // the char array value of the writer buffer and the content string
            // is shared as long as the buffer content is not modified, which will
            // not occur here.
            String contents = writer.getBuffer().toString();
            return contents;
        } catch (org.pdfbox.exceptions.CryptographyException e) {
            throw new IOException("Error decrypting document(" + file.getPath() + "): " + e);
        } catch (org.pdfbox.exceptions.InvalidPasswordException e) {
            //they didn't suppply a password and the default of "" was wrong.
            throw new IOException("Error: The document(" + file.getPath() + ") is encrypted and will not be indexed.");
        } finally {
            if (pdfDocument != null) {
                pdfDocument.close();
            }
        }
    }

    /**
     * This method listens for incoming records and handles them as required.
     *
     * @param record The record that was found while reading.
     */
    public void processRecord(Record record) {
        switch (record.getSid()) {
            // the BOFRecord can represent either the beginning of a sheet or the workbook
            case BOFRecord.sid:
                BOFRecord bof = (BOFRecord) record;
                break;
            case BoundSheetRecord.sid:
                BoundSheetRecord bsr = (BoundSheetRecord) record;
                break;
            case RowRecord.sid:
                RowRecord rowrec = (RowRecord) record;
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                break;
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                //System.out.println("String cell found with value " + sstrec.getString(lrec.getSSTIndex()));
                excelBuf.append(sstrec.getString(lrec.getSSTIndex()) + " ");
                break;
        }
    }

    public String WordExtractor(File file, String ext) throws java.io.IOException {

        String returnData = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            if ("doc".equals(ext.toLowerCase())) {
                org.apache.poi.hwpf.extractor.WordExtractor wext = new org.apache.poi.hwpf.extractor.WordExtractor(fis);
                returnData = wext.getText();
            } else if ("docx".equals(ext.toLowerCase())) {
                XWPFDocument doc = new XWPFDocument(fis);
                org.apache.poi.xwpf.extractor.XWPFWordExtractor wext = new org.apache.poi.xwpf.extractor.XWPFWordExtractor(doc);
                returnData = wext.getText();
            }
            return returnData;
        } catch (Exception e) {
            log.error("Error al extraer el texto del documento Word. ", e);
            throw new IOException("Error al extraer el texto del documento Word (" + file.getPath() + "): " + e);
        }
    }

    public String ExcelExtractor(File file, String ext) throws java.io.IOException {

        String returnData = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            if ("xls".equals(ext.toLowerCase())) {
                HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fis));
                org.apache.poi.hssf.extractor.ExcelExtractor wext = new org.apache.poi.hssf.extractor.ExcelExtractor(wb);
                wext.setFormulasNotResults(true);
                wext.setIncludeSheetNames(false);
                returnData = wext.getText();
            } else if ("xlsx".equals(ext.toLowerCase())) {
                XSSFWorkbook exls = new XSSFWorkbook(fis);
                org.apache.poi.xssf.extractor.XSSFExcelExtractor wext = new org.apache.poi.xssf.extractor.XSSFExcelExtractor(exls);
                returnData = wext.getText();          }
            return returnData;
        } catch (Exception e) {
            log.error("Error al extraer el texto del documento Excel. ", e);
            throw new IOException("Error al extraer el texto del documento Excel (" + file.getPath() + "): " + e);
        }
    }

    public String PPTExtractor(File file, String ext) throws java.io.IOException {

        String returnData = null;
        FileInputStream fis = null;
        if (ext == null) {
            ext = "";
        }
        try {
            fis = new FileInputStream(file);
            if ("ppt".equals(ext.toLowerCase())) {
                org.apache.poi.hslf.extractor.PowerPointExtractor wext = new org.apache.poi.hslf.extractor.PowerPointExtractor(fis);
                returnData = wext.getText() + " " + wext.getNotes();
            } else if ("pptx".equals(ext.toLowerCase())) {
                XSLFSlideShow fppt = new XSLFSlideShow(file.getPath());
                org.apache.poi.xslf.extractor.XSLFPowerPointExtractor wext = new org.apache.poi.xslf.extractor.XSLFPowerPointExtractor(fppt);
                returnData = wext.getText(true,true);
            }
            return returnData;
        } catch (Exception e) {
            log.error("Error al extraer el texto del documento Excel. ", e);
            throw new IOException("Error al extraer el texto del documento Excel (" + file.getPath() + "): " + e);
        }
    }

    public String TxtExtractor(File file) throws java.io.IOException {

        String returnData = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            returnData = SWBUtils.IO.readInputStream(fis);
            //System.out.println("Text extractor: " + returnData);
            return returnData;
        } catch (Exception e) {
            log.error("Error al extraer el texto del documento de Text. ", e);
            throw new IOException("Error al extraer el texto del documento de Texto (" + file.getPath() + "): " + e);
        }
    }
}

class MyPOIFSReaderListener implements POIFSReaderListener {

    private static StringBuffer pptBuf = new StringBuffer();
    SavePPTString saveStrig = null;

    public MyPOIFSReaderListener(StringBuffer pptBuf) {
        this.pptBuf = pptBuf;
        saveStrig = new SavePPTString();
    }

    public void processPOIFSReaderEvent(POIFSReaderEvent event) {
        PropertySet ps = null;

        try {

            org.apache.poi.poifs.filesystem.DocumentInputStream dis = null;
            if (!event.getName().equalsIgnoreCase("PowerPoint Document")) {
                return;
            }

            dis = event.getStream();

            byte btoWrite[] = new byte[12];
            dis.read(btoWrite);

            btoWrite = new byte[dis.available()];
            dis.read(btoWrite, 0, dis.available());

            String strBytes = "";
            for (int i = 0; i < btoWrite.length - 20; i++) {
                long type = LittleEndian.getUShort(btoWrite, i + 2);
                long size = LittleEndian.getUInt(btoWrite, i + 4);
                if (type == 4008) {
                    int offset = i + 4 + 1;
                    int length = (int) size + 3;
                    int end = offset + length;

                    byte[] textBytes = new byte[length];

                    for (int j = offset; j < end; j++) {
                        byte b = btoWrite[j];
                        pptBuf.append(String.valueOf((char) b));
                    }
                    if (i < (end - 1)) {
                        i = end - 1;
                    }
                }
            }
            if (pptBuf != null && pptBuf.toString().length() > 0) {
                saveStrig.setSavePPTString(pptBuf.toString());
            }
            PropertySetFactory.create(event.getStream());
        } catch (Exception e) {
            if (pptBuf.toString().length() > 0) {
                saveStrig.setSavePPTString(pptBuf.toString());
            }
            //AFUtils.log(e,"No property set stream: \"" + event.getPath() + event.getName());
        }
    }

    public String getString() {
        return saveStrig.getPPTStr();
    }
}

class SavePPTString {

    String str = null;

    public void setSavePPTString(String str) {
        this.str = str;
    }

    public String getPPTStr() {
        return str;
    }
}
