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
package org.semanticwb.domotic.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.semanticwb.domotic.model.DomContext;
import org.semanticwb.domotic.model.DomDevice;
import org.semanticwb.domotic.model.DomGroup;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;

/**
 * Example web socket servlet for chat.
 */
public class WebSocketServlet extends org.apache.catalina.websocket.WebSocketServlet
{

    private static final long serialVersionUID = 1L;
    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Set<ChatMessageInbound> connections = new CopyOnWriteArraySet();
    private static final LinkedList<Message> messages=new LinkedList();

    public static void broadcast(String message)
    {
        System.out.println("broadcast:" + message);
        for (ChatMessageInbound connection : connections)
        {
            try
            {
                CharBuffer buffer = CharBuffer.wrap(message);
                connection.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore)
            {
                // Ignore
            }
        }
        
        messages.add(new Message(System.currentTimeMillis(), message));
    }
    
    public static List<String> getLastMessages()
    {
        long time=System.currentTimeMillis()-4000;
        ArrayList arr=new ArrayList();
        Iterator<Message> it=messages.listIterator();
        while (it.hasNext())
        {
            Message message = it.next();
            if(message.getTime()>time)
            {
                arr.add(message.getMessage());
            }else
            {
                it.remove();
            }
        }
        return arr;
    }
            
            
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request)
    {
        return new ChatMessageInbound(connectionIds.incrementAndGet());
    }

    private final class ChatMessageInbound extends MessageInbound
    {

        private final String nickname;

        private ChatMessageInbound(int id)
        {
            this.nickname = GUEST_PREFIX + id;
        }

        @Override
        protected void onOpen(WsOutbound outbound)
        {
            connections.add(this);
            String message = String.format("* %s %s",nickname, "has joined.");
            //broadcast(message);
            System.out.println("onOpen:" + message);
        }

        @Override
        protected void onClose(int status)
        {
            connections.remove(this);
            String message = String.format("* %s %s",nickname, "has disconnected.");
            //broadcast(message);
            System.out.println("onClose:" + message);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException
        {
            //System.out.println("onBinaryMessage:" + message.toString());
            throw new UnsupportedOperationException(
                    "Binary message not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException
        {
            // Never trust the client
            String filteredMessage = String.format("%s: %s",nickname, message.toString());
            //broadcast(filteredMessage);
            System.out.println("onTextMessage:" + filteredMessage);
            
            String txt=message.toString();
            StringTokenizer st=new StringTokenizer(txt," ");
            if (st.hasMoreTokens())
            {
                 String suri=st.nextToken();
                 String sstat=st.nextToken();
                 
                String uri=SemanticObject.shortToFullURI(suri);
                GenericObject obj=SemanticObject.createSemanticObject(uri).createGenericInstance();
                if(obj instanceof DomDevice)
                {        
                    DomDevice dev = (DomDevice)obj;
                    dev.setStatus(Integer.parseInt(sstat));
                }else if(obj instanceof DomGroup)
                {
                    DomGroup grp=(DomGroup)obj;
                    grp.setStatus(Integer.parseInt(sstat));
                }else if(obj instanceof DomContext)
                {
                    DomContext cnt=(DomContext)obj;
                    cnt.setActive(Boolean.parseBoolean(sstat));
                }
            }
            
        }
    }
}

class Message
{
    private long time;
    private String message=null;

    public Message(long time, String message)
    {
        this.time = time;
        this.message=message;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }
    
    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}
