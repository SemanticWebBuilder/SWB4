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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author serch
 */
public class LongFileUploadUtils {
    private static Logger log = SWBUtils.getLogger(LongFileUploadUtils.class);
    private static LongFileUploadUtils instance = null;

    public static LongFileUploadUtils getFileManager(File tmpplace) 
            throws IOException {
        if (instance==null) {
            instance = new LongFileUploadUtils();
        } 
        instance.init(tmpplace);
        return instance;
    }
    private File workingDirectory;
    private ArrayList<PendingFile> pending=new ArrayList<PendingFile>();
    private LongFileUploadUtils(){
        
    }
    private void init(final File wrkDir) throws IOException{
        if (!wrkDir.exists()){
            wrkDir.mkdirs();
        } 
        workingDirectory = wrkDir;
        File config = new File(workingDirectory,"uploadLongFiles.config");
        if (config.exists()){
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    new FileInputStream(config)));
            String pf = null;
            while ((pf = bf.readLine())!=null){ 
                String[] parts = pf.split("\\|"); 
                if (parts.length==7 && parts[5].equals("false")){
                    PendingFile tpf = new PendingFile(parts[0], parts[1], 
                            Long.parseLong(parts[2]), parts[3], parts[4], 
                            parts[6]);
                    pending.add(tpf);
                }
            }
            bf.close();
        }
    }
    
    public PendingFile haveWorkingFile(String name, long size, String crc, String user){
        PendingFile pf = null;
        for (PendingFile lpf:pending){
            if (lpf.getsUser().equals(user) && lpf.getFilename().equalsIgnoreCase(name) && lpf.getSize()==size 
                    && lpf.getIniCRC().equalsIgnoreCase(crc)){
                pf = lpf;
                break;
            }
        }
        return pf;
    }
    
    public boolean addWorkingFile(String name, long size, String crc, 
            String date, String user){
        if (null==haveWorkingFile(name, size, crc, user)) {
            PendingFile pf = new PendingFile(UUID.randomUUID().toString(), name, 
                    size, date, crc, user);
            pending.add(pf);
            new Thread(){
                public void run(){
                    saveData();
                }
            }.start();
            return true;
        }
        return false;
    }
    
    private synchronized void saveData(){
        try {
            File config = new File(workingDirectory,"uploadLongFiles.config");
            FileOutputStream osw = new FileOutputStream(config);
            for (PendingFile pf:pending){
                osw.write(pf.toString().getBytes("ISO8859-1"));
            }
            osw.close();
        } catch (Exception e){
            log.error("Error Saving Config data of LargeUpload", e);
        }
    }
    
    public ArrayList<PendingFile> getListOfPendingFiles(String strUser){
        ArrayList<PendingFile> ret = new ArrayList<PendingFile>();
        for (PendingFile pf: pending){
            if (strUser.equals(pf.getsUser()))
            {
                ret.add(pf);
            }
        }
        return ret;
    }
    
    public PendingFile getPendingFileFromId(String id){
        PendingFile ret = null;
        for (PendingFile pf: pending){
            if (id.equals(pf.getId())){
                ret = pf;
                break;
            }
        }
        return ret;
    }
    
    public boolean removePendingFile(String id){
        boolean ret =false;
        PendingFile pf = getPendingFileFromId(id);
        if (null!=pf){
            File dir = new File(workingDirectory, id);
            for (File file:dir.listFiles()){
                if (file.exists()){
                    file.delete();
                }
            }
            ret = dir.delete();
            pending.remove(pf);
        }
        return ret;
    }
    
    public void updateChanges(){
        new Thread(){
                public void run(){
                    saveData();
                }
            }.start();
    }
}
