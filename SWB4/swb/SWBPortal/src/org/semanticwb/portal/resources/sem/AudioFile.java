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
package org.semanticwb.portal.resources.sem;

import java.io.File;
import java.util.Comparator;
import java.util.GregorianCalendar;
import org.semanticwb.SWBPortal;

public class AudioFile extends org.semanticwb.portal.resources.sem.base.AudioFileBase 
{
    public AudioFile(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
     public static class SortByReviews implements Comparator<AudioFile>{
        @Override
        public int compare(AudioFile audio1, AudioFile audio2) {
            return new Long(audio1.getReviews()).compareTo(new Long(audio2.getReviews()));
        }
    }
     
    public static class SortByRanking implements Comparator<AudioFile>{
        @Override
        public int compare(AudioFile audio1, AudioFile audio2) {
            return new Double(audio1.getRank()).compareTo(new Double(audio2.getRank()));
        }
    }
    
    public File getFile() {
        File file = null;
        file = new File(SWBPortal.getWorkPath()+this.getWorkPath()+"/"+this.getFilename());
        return file;
    }
    
    public String getExtension() {
        return this.getFilename().substring(this.getFilename().lastIndexOf(".")+1);
    }

    @Override
    public void setYear(String value) {
        super.setYear(value);
    }

    @Override
    public String getYear() {
        String year = super.getYear();
        if(year==null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(getCreated());
            year = Integer.toString(gc.get(GregorianCalendar.YEAR));
            setYear(year);
        }
        return year;
    }
    /*
     * String prefix = super.getPrefix();
        if(prefix==null) {
            try {
                prefix = getTitle().trim().substring(0, 1).toUpperCase();
            }catch(Exception e) {
                prefix = "Untitled";
            }
            setPrefix(prefix);
        }
        return prefix;
     */
}
