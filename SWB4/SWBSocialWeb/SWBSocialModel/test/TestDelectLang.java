/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.Language;
import net.arnx.jsonic.JSONException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.semanticwb.SWBPortal;
 

/**
 *
 * @author jorge.jimenez
 */
public class TestDelectLang {
    
    public static void main(String args[])
    {
        File profileDir=new File("c://profiles/");
        if(profileDir.exists() && profileDir.isDirectory())
        {
            try
            {
                DetectorFactory.loadProfile(profileDir); 
                Detector detector = DetectorFactory.create();
                System.out.println("Lang:"+detector.detect());
                detector.append("EE.UU. expulsa a tres diplomáticos #venezolanos en respuesta a la expulsión de tres estadounidenses la semana pasada http://cnn.it/1jxkEke");
                System.out.println("Lang:"+detector.detect());
                /*
                ArrayList<Language> langlist = detector.getProbabilities();
                if(!langlist.isEmpty())
                {
                    Iterator<Language> itLangs=langlist.iterator(); 
                    while(itLangs.hasNext())
                    {
                        Language lang=itLangs.next();
                        System.out.println("Lang:"+lang.lang);
                    }
                }*/
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
