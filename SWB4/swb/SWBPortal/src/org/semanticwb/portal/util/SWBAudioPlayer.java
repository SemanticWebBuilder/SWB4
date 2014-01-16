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
package org.semanticwb.portal.util;

import com.sun.speech.freetts.audio.AudioPlayer;
import java.io.IOException;
import java.io.OutputStream;
import javax.sound.sampled.AudioFormat;

/**
 *
 * @author serch
 */
public class SWBAudioPlayer implements AudioPlayer {

    private AudioFormat audioFormat;
    private float volume;
    private OutputStream os;

    public SWBAudioPlayer(OutputStream os) {
        //this.audioFormat = audioFormat;
        this.os = os;
        //System.out.println("Player created.");
    }

    
    
    @Override
    public void setAudioFormat(AudioFormat audioFormat) {
        //System.out.println("p.setAudioFormat:"+audioFormat);
        this.audioFormat = audioFormat;
    }

    @Override
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    @Override
    public void pause() {
        //System.out.println("p.pause");
    }

    @Override
    public void resume() {
        //System.out.println("p.resume");
    }

    @Override
    public void reset() {
        //System.out.println("p.reset");
    }

    @Override
    public boolean drain() {
        //System.out.println("p.drain");
        return true;
    }

    @Override
    public void begin(int i) {
        //System.out.println("p.begin:"+i);
    }

    @Override
    public boolean end() {
        //System.out.println("p.end");
        return true;
    }

    @Override
    public void cancel() {
        //System.out.println("p.cancel");
    }

    @Override
    public void close() {
        //System.out.println("p.close");
        try {
            os.flush();
            //System.out.println("format: "+audioFormat);
            //os.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public float getVolume() {
        //System.out.println("p.getVolume");
        return volume;
    }

    @Override
    public void setVolume(float volume) {
        //System.out.println("p.setVolume:"+volume);
        this.volume = volume;
    }

    @Override
    public long getTime() {
        //System.out.println("p.getTime");
        return -1L;
    }

    @Override
    public void resetTime() {
        //System.out.println("p.resetTime");
    }

    @Override
    public void startFirstSampleTimer() {
        //System.out.println("p.startFirstSampleTimer");
    }

    @Override
    public boolean write(byte[] audioData) {
        //System.out.println("p.write b[]");
        return write(audioData, 0, audioData.length);
    }

    @Override
    public boolean write(byte[] bytes, int offset, int size) {
        //System.out.println("p.write b[] o s");
        try {
            os.write(bytes, offset, size);
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    @Override
    public void showMetrics() {
        //System.out.println("p.showMetrics");
    }

}
