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
package org.semanticwb.portal.community;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;
import org.semanticwb.base.util.ImageResizer;

/** @author Hasdai Pacheco {haxdai@gmail.com}
 *  @author Carlos Ramos
 *
 * Events manager resource.
 * <p>
 * Recurso administrador de eventos.
 */
public class EventResource extends org.semanticwb.portal.community.base.EventResourceBase
{

    private static Logger log = SWBUtils.getLogger(EventResource.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Empty constructor.
     * <p>
     * Constructor vacío.
     */
    public EventResource()
    {
    }

    /**
     * Default constructor.
     * <p>
     * Constructor por defecto.
     */
    public EventResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {

        String action = request.getParameter("act");
        WebPage page = response.getWebPage();
        final String realpath = SWBPortal.getWorkPath();
        Member mem = Member.getMember(response.getUser(), page);
        User user = response.getUser();
        boolean isAdministrator = false;
        if (user != null)
        {
            GenericIterator<UserGroup> groups = user.listUserGroups();
            while (groups.hasNext())
            {
                UserGroup group = groups.next();
                if (group != null && group.getId().equals("admin"))
                {
                    isAdministrator = true;
                    break;
                }
            }
        }
        if ("remove".equals(action))
        {
            if (!isAdministrator)
            {
                if (!mem.canView())
                {
                    return;                                      
                }
            }
        } else
        {
            if (!mem.canView())
            {
                return;                                       //si el usuario no pertenece a la red sale;
            }
        }


        if (action == null)
        {
            HashMap<String, String> params = upload(request);
            if (mem.canAdd() && params.containsValue("add"))
            {
                EventElement rec = EventElement.ClassMgr.createEventElement(getResourceBase().getWebSite());
                if (params.containsKey("filename"))
                {

                    File file = new File(realpath + params.get("filename"));
                    if (file.exists())
                    {
                        FileInputStream in = new FileInputStream(file);
                        String filename = file.getName();
                        String finalpath = rec.getWorkPath() + "/";
                        String target = realpath + finalpath + filename;
                        File ftarget = new File(target);
                        ftarget.getParentFile().mkdirs();
                        FileOutputStream out = new FileOutputStream(ftarget);
                        SWBUtils.IO.copyStream(in, out);
                        file.delete();
                        params.put("filename", finalpath + filename);
                    }
                    rec.setEventImage(file.getName());
                } else
                {
                    rec.setEventImage(null);
                }
                if (params.containsKey("thumbnail"))
                {
                    File file = new File(realpath + params.get("thumbnail"));
                    if (file.exists())
                    {
                        FileInputStream in = new FileInputStream(file);
                        String filename = file.getName();
                        String finalpath = rec.getWorkPath() + "/";
                        String target = realpath + finalpath + filename;
                        File ftarget = new File(target);
                        ftarget.getParentFile().mkdirs();
                        FileOutputStream out = new FileOutputStream(ftarget);
                        SWBUtils.IO.copyStream(in, out);
                        file.delete();
                        params.put("thumbnail", finalpath + filename);
                    }
                    rec.setEventThumbnail(file.getName());
                } else
                {
                    rec.setEventThumbnail(null);
                }
                rec.setTitle(SWBUtils.XML.replaceXMLChars(params.get("event_title")));
                rec.setDescription(SWBUtils.XML.replaceXMLChars(params.get("event_description")));
                rec.setAudienceType(SWBUtils.XML.replaceXMLChars(params.get("event_audience")));
                try
                {
                    String startDate = params.get("event_startDate");
                    startDate = (startDate == null ? "" : startDate);
                    String endDate = params.get("event_endDate");
                    endDate = (endDate == null ? "" : endDate);
                    String startTime = params.get("event_startTime");
                    startTime = (startTime == null ? "" : startTime.replace("T", "").trim());
                    String endTime = params.get("event_endTime");
                    endTime = (endTime == null ? "" : endTime.replace("T", "").trim());
                    rec.setStartDate(dateFormat.parse(startDate.trim()));
                    rec.setEndDate(dateFormat.parse(endDate.trim()));
                    rec.setStartTime(new Timestamp(timeFormat.parse(startTime).getTime()));
                    rec.setEndTime(new Timestamp(timeFormat.parse(endTime).getTime()));
                } catch (Exception e)
                {
                    log.error(e);
                }
                rec.setPlace(SWBUtils.XML.replaceXMLChars(params.get("event_place")));
                rec.setTags(SWBUtils.XML.replaceXMLChars(params.get("event_tags")));
                rec.setEventWebPage(page);
                try
                {
                    response.setRenderParameter("act", "edit");
                    response.setRenderParameter("uri", rec.getURI());
                } catch (Exception e)
                {
                    log.error(e);
                    response.setRenderParameter("act", "add");
                    response.setRenderParameter("err", "true");
                }
            } else if (params.containsValue("edit"))
            {
                String uri = params.get("uri");
                EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
                if (rec != null && rec.canModify(mem))
                {
                    if (params.get("filename") != null)
                    {
                        File file = new File(realpath + params.get("filename"));
                        if (file.exists())
                        {
                            FileInputStream in = new FileInputStream(file);
                            String filename = file.getName();
                            String finalpath = rec.getWorkPath() + "/";
                            String target = realpath + finalpath + filename;
                            File ftarget = new File(target);
                            ftarget.getParentFile().mkdirs();
                            FileOutputStream out = new FileOutputStream(ftarget);
                            SWBUtils.IO.copyStream(in, out);
                            file.delete();
                            params.put("filename", finalpath + filename);
                        }
                        rec.setEventImage(file.getName());
                    }
                    if (params.get("thumbnail") != null)
                    {
                        File file = new File(realpath + params.get("thumbnail"));
                        if (file.exists())
                        {
                            FileInputStream in = new FileInputStream(file);
                            String filename = file.getName();
                            String finalpath = rec.getWorkPath() + "/";
                            String target = realpath + finalpath + filename;
                            File ftarget = new File(target);
                            ftarget.getParentFile().mkdirs();
                            FileOutputStream out = new FileOutputStream(ftarget);
                            SWBUtils.IO.copyStream(in, out);
                            file.delete();
                            params.put("thumbnail", finalpath + filename);
                        }
                        rec.setEventThumbnail(file.getName());
                    }
                    rec.setTitle(SWBUtils.XML.replaceXMLChars(params.get("event_title")));
                    rec.setDescription(SWBUtils.XML.replaceXMLChars(params.get("event_description")));
                    rec.setAudienceType(SWBUtils.XML.replaceXMLChars(params.get("event_audience")));
                    try
                    {
                        String startDate = params.get("event_startDate");
                        startDate = (startDate == null ? "" : startDate);
                        String endDate = params.get("event_endDate");
                        endDate = (endDate == null ? "" : endDate);
                        String startTime = params.get("event_startTime");
                        startTime = (startTime == null ? "" : startTime.replace("T", "").trim());
                        String endTime = params.get("event_endTime");
                        endTime = (endTime == null ? "" : endTime.replace("T", "").trim());
                        rec.setStartDate(dateFormat.parse(startDate.trim()));
                        rec.setEndDate(dateFormat.parse(endDate.trim()));
                        rec.setStartTime(new Timestamp(timeFormat.parse(startTime).getTime()));
                        rec.setEndTime(new Timestamp(timeFormat.parse(endTime).getTime()));
                    } catch (Exception e)
                    {
                        log.error(e);
                    }
                    rec.setPlace(SWBUtils.XML.replaceXMLChars(params.get("event_place")));
                    rec.setTags(SWBUtils.XML.replaceXMLChars(params.get("event_tags")));
                    rec.setEventWebPage(page);
                }
            }
        } else if (action.equals("remove"))
        {
            //Get event object

            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            //Remove event object
            if (rec != null && (rec.canModify(mem) || isAdministrator))
            {
                rec.remove();                                       //elimina el registro
            }
        } else if (action.equals("attend"))
        {
            //Get event object
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            //Add attendant member
            if (rec != null && rec.canView(mem))
            {
                boolean found = false;
                Iterator<User> uit = rec.listAttendants();
                while (uit.hasNext() && !found)
                {
                    User u = uit.next();
                    if (u.getFullName().equals(response.getUser().getFullName()))
                    {
                        found = true;
                    }
                }
                if (!found)
                {
                    rec.addAttendant(response.getUser());
                }
            }
            response.setRenderParameter("uri", uri);
            response.setRenderParameter("act", "detail");
        } else
        {
            super.processAction(request, response);
        }
    }

    private HashMap<String, String> upload(HttpServletRequest request)
    {
        final String realpath = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/";
        final String path = getResourceBase().getWorkPath() + "/";

        HashMap<String, String> params = new HashMap<String, String>();
        try
        {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart)
            {
                File tmpwrk = new File(SWBPortal.getWorkPath() + "/tmp");
                if (!tmpwrk.exists())
                {
                    tmpwrk.mkdirs();
                }
                FileItemFactory factory = new DiskFileItemFactory(1 * 1024 * 1024, tmpwrk);
                ServletFileUpload upload = new ServletFileUpload(factory);
                ProgressListener progressListener = new ProgressListener()
                {

                    private long kBytes = -1;

                    public void update(long pBytesRead, long pContentLength, int pItems)
                    {
                        long mBytes = pBytesRead / 10000;
                        if (kBytes == mBytes)
                        {
                            return;
                        }
                        kBytes = mBytes;
                        int percent = (int) (pBytesRead * 100 / pContentLength);
                    }
                };
                upload.setProgressListener(progressListener);
                List items = upload.parseRequest(request); /* FileItem */
                FileItem currentFile = null;
                Iterator iter = items.iterator();
                while (iter.hasNext())
                {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField())
                    {
                        String name = item.getFieldName();
                        String value = item.getString();
                        params.put(name, value);
                    } else
                    {
                        currentFile = item;
                        File file = new File(path);
                        if (!file.exists())
                        {
                            file.mkdirs();
                        }

                        try
                        {
                            long serial = (new Date()).getTime();
                            String filename = serial + "_" + currentFile.getFieldName() + currentFile.getName().substring(currentFile.getName().lastIndexOf("."));

                            File image = new File(realpath);
                            if (!image.exists())
                            {
                                image.mkdir();
                            }
                            image = new File(realpath + filename);
                            File thumbnail = new File(realpath + "thumbn_" + filename);
                            currentFile.write(image);
                            //ImageResizer.resize(image, 150, true, thumbnail, "jpeg" );
                            ImageResizer.resizeCrop(image, 150, thumbnail, "jpeg");
                            params.put("filename", path + filename);
                            params.put("thumbnail", path + "thumbn_" + filename);
                        } catch (StringIndexOutOfBoundsException iobe)
                        {
                        }
                    }
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return params;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/microsite/EventResource/eventView.jsp";
        String action = request.getParameter("act");


        if (action == null)
        {
            action = "view";
        }

        if (action.equals("calendar"))
        {
            path = "/swbadmin/jsp/microsite/EventResource/eventsCalendar.jsp";
        } else if (action.equals("add"))
        {
            path = "/swbadmin/jsp/microsite/EventResource/eventAdd.jsp";
        } else if (action.equals("edit"))
        {
            path = "/swbadmin/jsp/microsite/EventResource/eventEdit.jsp";
        } else if (action.equals("detail"))
        {
            path = "/swbadmin/jsp/microsite/EventResource/eventDetail.jsp";
        } else if (action.equals("daily"))
        {
            path = "/swbadmin/jsp/microsite/EventResource/eventDailyView.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try
        {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e)
        {
            log.error(e);
        }
    }
}
