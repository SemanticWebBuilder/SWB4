/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.domotic.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.semanticwb.SWBPlatform;
import org.semanticwb.domotic.model.DomDevice;
import org.semanticwb.domotic.model.base.DomDeviceBase;
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
            System.out.println("onBinaryMessage:" + message.toString());
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
            while (st.hasMoreTokens())
            {
                 String devid=st.nextToken();
                 String val=st.nextToken();
                 DomDevice dev=(DomDevice)SemanticObject.getSemanticObject(SemanticObject.shortToFullURI(devid)).createGenericInstance();
                 dev.setStatus(Integer.parseInt(val));
            }
            
        }
    }
}
