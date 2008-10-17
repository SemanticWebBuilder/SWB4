/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.services;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hpsf.*;
import org.apache.poi.poifs.eventfilesystem.*;
import org.apache.poi.util.LittleEndian;
import java.io.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author jorge.jimenez
 */
public class DocumentExtractorSrv implements HSSFListener
{
    private static Logger log = SWBUtils.getLogger(DocumentExtractorSrv.class);
    
    private SSTRecord sstrec;
    private static StringBuffer excelBuf=new StringBuffer();
    
    
    
    /**
     * Extrae el string de un archivo de excel
     *
     * Extract the string of a excel document
     */    
    public String excelExtractor(File file){
        try{
            FileInputStream fin = new FileInputStream(file);
            POIFSFileSystem poifs = new POIFSFileSystem(fin);
            InputStream din = poifs.createDocumentInputStream("Workbook");
            HSSFRequest req = new HSSFRequest();
            req.addListenerForAllRecords(new DocumentExtractorSrv());
            HSSFEventFactory factory = new HSSFEventFactory();
            factory.processEvents(req, din);
            fin.close();
            din.close();
        }catch(Exception e){
            log.error("Error while reading excel file in DocumentExtractorSrv/ExcelExtractor:"+e.getMessage());
        }
        return excelBuf.toString();
    }
    
    /**
     * Extrae el string de un archivo de word
     *
     * Extract the string of a word document
     */    
    public String wordExtractor(File file){
        try{
            FileInputStream in = new FileInputStream(file);
            HWPFDocument doc = new HWPFDocument(in);
            if(doc!=null && doc.getRange()!=null){
                return doc.getRange().text();
            }
        }catch(Exception e){
            log.error("Error while reading word file in DocumentExtractorSrv/ExcelExtractor:"+e.getMessage());
        }
        return "";
    }
    
    
    /**
     * Extrae el string de un archivo de power point
     *
     * Extract the string of a power point document
     */    
    public static String pptExtractor(File file) throws IOException, FileNotFoundException {
        POIFSReader r = new POIFSReader();
        /* Register a listener for *all* documents. */
        StringBuffer pptBuf=new StringBuffer();
        MyPOIFSReaderListener listener = new MyPOIFSReaderListener(pptBuf);
        //MyPOIFSReaderListener listener = new MyPOIFSReaderListener(new BufferedWriter(new FileWriter(dest)));
        r.registerListener(listener);
        r.read(new FileInputStream(file));
        return listener.getString();
    }
    
    /**
     * Extrae el string de un archivo pdf
     *
     * Extract the string of a pdf document
     */    
    public String pdfExtractor(File file) throws java.io.IOException {
        FileInputStream is=new FileInputStream(file);
        org.pdfbox.pdmodel.PDDocument pdfDocument = null;
        try {
            pdfDocument = org.pdfbox.pdmodel.PDDocument.load( is );
            
            
            if( pdfDocument.isEncrypted() ) {
                //Just try using the default password and move on
                pdfDocument.decrypt( "" );
            }
            
            //create a writer where to append the text content.
            StringWriter writer = new StringWriter();
            org.pdfbox.util.PDFTextStripper stripper = new org.pdfbox.util.PDFTextStripper();
            stripper.writeText( pdfDocument, writer );
            
            // Note: the buffer to string operation is costless;
            // the char array value of the writer buffer and the content string
            // is shared as long as the buffer content is not modified, which will
            // not occur here.
            String contents = writer.getBuffer().toString();
            return contents;
        }
        catch( org.pdfbox.exceptions.CryptographyException e ) {
            throw new IOException( "Error decrypting document(" + file.getPath() + "): " + e );
        }
        catch( org.pdfbox.exceptions.InvalidPasswordException e ) {
            //they didn't suppply a password and the default of "" was wrong.
            throw new IOException( "Error: The document(" + file.getPath() + ") is encrypted and will not be indexed." );
        }
        finally {
            if( pdfDocument != null ) {
                pdfDocument.close();
            }
        }
    }
    
    
    /**
     * This method listens for incoming records and handles them as required.
     * @param record    The record that was found while reading.
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
                excelBuf.append(sstrec.getString(lrec.getSSTIndex())+" ");
                break;
        }
    }
    
}


class MyPOIFSReaderListener implements POIFSReaderListener{
    private static StringBuffer pptBuf = new StringBuffer();
    SavePPTString saveStrig=null;
    
    public MyPOIFSReaderListener(StringBuffer pptBuf){
        this.pptBuf = pptBuf;
        saveStrig=new SavePPTString();
    }
    
    public void processPOIFSReaderEvent(POIFSReaderEvent event) {
        PropertySet ps = null;
        
        try{
            
            org.apache.poi.poifs.filesystem.DocumentInputStream dis=null;
            if(!event.getName().equalsIgnoreCase("PowerPoint Document"))
                return;
            
            dis=event.getStream();
            
            byte btoWrite[]= new byte[12];
            dis.read(btoWrite);
            
            btoWrite = new byte[dis.available()];
            dis.read(btoWrite, 0, dis.available());
            
            String strBytes="";
            for(int i=0; i<btoWrite.length-20; i++){
                long type=LittleEndian.getUShort(btoWrite,i+2);
                long size=LittleEndian.getUInt(btoWrite,i+4);
                if (type==4008){
                    int offset = i+4+1;
                    int length = (int)size+3;
                    int end = offset + length;
                    
                    byte[] textBytes = new byte[length];
                    
                    for (int j = offset; j < end; j++) {
                        byte b = btoWrite[j];
                        pptBuf.append(String.valueOf((char)b));
                    }
                    if(i < (end -1)){
                        i = end -1;
                    }
                }
            }
            if(pptBuf!=null && pptBuf.toString().length()>0){
                saveStrig.setSavePPTString(pptBuf.toString());
            }
            PropertySetFactory.create(event.getStream());
        }catch (Exception e){
            if(pptBuf.toString().length()>0){
                saveStrig.setSavePPTString(pptBuf.toString());
            }
            //AFUtils.log(e,"No property set stream: \"" + event.getPath() + event.getName());
        }
    }
    
    public String getString(){
        return saveStrig.getPPTStr();
    }
    
}

class SavePPTString{
    String str=null;
    public void setSavePPTString(String str){
        this.str=str;
    }
    public String getPPTStr(){
        return str;
    }

}
