/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bigdata.test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author javier.solis.g
 */
public class CleanNT
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //http://admon.mapas.gob.mx/work/models/vgn/vgn_RecursoDigital/11583-1495/
        //<http://www.vgn.swb#vgn_RecursoDigital:11583-1495> <http://www.sfp.gob.mx/vgn#hrefVistaPrevia> "hrefVistaPrevia_11583-1495_11583.jpg" .
        //<http://www.vgn.swb#vgn_RecursoDigital:11583-1495> <http://www.sfp.gob.mx/vgn#pathFileSystem> "pathFileSystem_11583-1495_11583.jpg" .

        
        //Clean VGN
        /*
        try
        {
            FileInputStream in=new FileInputStream("/programming/proys/hackaton/vgn_db/vgn.nt");
            FileOutputStream ot=new FileOutputStream("/programming/proys/hackaton/vgn_db/vgn2.nt");
            PrintWriter out=new PrintWriter(ot);
            DataInputStream din=new DataInputStream(in);

            int x=0;
            int y=0;
            String line=null;
            while((line=din.readLine())!=null)
            {
                x++;if(x%10000==0)System.out.println(x+" "+y+" "+line);
                if(line.indexOf("<http://www.vgn.swb#vgn_RecursoGeograficoBusqueda:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#vgn_Bitacora:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#vgn_ConsolaEventoCapa:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#swb_")>-1)continue;
                if(line.indexOf("<http://user.vgn.swb#vgn_Usuario:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#WebPage:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#Resource:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#catalog_SWBCatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#catalogvgn_CatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#counter:")>-1)continue;
                if(line.indexOf("<http://www.vgn.swb#Template:")>-1)continue;
                if(line.indexOf("<http://www.semanticwb.org/uradm#")>-1)continue;
                out.println(line);
                y++;
            }
            out.flush();
            out.close();
            ot.close();
        }catch(Exception e){e.printStackTrace();}
        */
        
        try
        {
            FileInputStream in=new FileInputStream("/programming/proys/hackaton/vgng_db/vgng.nt");
            FileOutputStream ot=new FileOutputStream("/programming/proys/hackaton/vgng_db/vgng2.nt");
            PrintWriter out=new PrintWriter(ot);
            DataInputStream din=new DataInputStream(in);

            int x=0;
            int y=0;
            String line=null;
            while((line=din.readLine())!=null)
            {
                x++;if(x%10000==0)System.out.println(x+" "+y+" "+line);
                if(line.indexOf("<http://www.vgng.swb#vgn_RecursoGeograficoBusqueda:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#swb_")>-1)continue;
                if(line.indexOf("<http://user.vgng.swb#vgn_Usuario:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#WebPage:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#Resource:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#catalog_SWBCatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#catalogvgn_CatalogResource:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#counter:")>-1)continue;
                if(line.indexOf("<http://www.vgng.swb#Template:")>-1)continue;
                if(line.indexOf("<http://www.semanticwb.org/uradm#")>-1)continue;
                out.println(line);
                y++;
            }
            out.flush();
            out.close();
            ot.close();
        }catch(Exception e){e.printStackTrace();}
        
        
    }
}
