package org.semanticwb.process.model;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.process.resources.ProcessFileRepository;
import org.semanticwb.process.utils.XDocReport;

public class StoreArtifact extends org.semanticwb.process.model.base.StoreArtifactBase 
{
    private static Logger log=SWBUtils.getLogger(StoreArtifact.class);

    public StoreArtifact(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);

        String filePath=SWBPortal.getWorkPath()+getProcessFileTemplate().getWorkPath()+"/"+getProcessFileTemplate().getFileName();
        XDocReport rep=new XDocReport(getProcessFileName(), filePath);

        //rep.addContextList("", null, null);

        rep.addContextObject("instance", instance);
        rep.addContextObject("user", user);

        List<ItemAwareReference> list=instance.listHeraquicalItemAwareReference();
        for(ItemAwareReference item : list)
        {
            String varname=item.getItemAware().getName();
            SemanticObject object=item.getProcessObject().getSemanticObject();
            try
            {
                //System.out.println("Cargando clase "+className+" ...");
                Class clazz=SWBPClassMgr.getClassDefinition(object.getSemanticClass());
                //System.out.println("Obteniendo constructor...");
                Constructor c=clazz.getConstructor(SemanticObject.class);
                //System.out.println("Instanciando objeto...");
                Object instanceObject=c.newInstance(object);
                //System.out.println("Agregando variable "+varname+"="+instanceObject+" de tipo "+instanceObject.getClass());
                rep.addContextObject(varname, instanceObject);
                //System.out.println("Variable "+ varname +" agregada");
            }
            catch(Exception cnfe)
            {
                log.error("No se agrego variable "+varname+" a script relacionada con el objeto "+object.getURI()+" en la instancia de proceso "+instance.getURI(),cnfe);
            }
        }


        RepositoryFile file=null;
        String id=getRepositoryFileId();
        if(id!=null)
        {
            file=RepositoryFile.ClassMgr.getRepositoryFile(id, getProcessSite());
        }
             
        if(file==null)
        {
            file=RepositoryFile.ClassMgr.createRepositoryFile(getProcessSite());
        }
        file.setRepositoryDirectory(getProcessDirectory());
        file.setTitle(getProcessFileName());

        try
        {
            rep.generateReport(file.storeFile(getProcessFileName(), null, false));
        }catch(Exception e){log.error(e);}

//        String code=getScript();

//        try
//        {
//            Map map=null;
//            if(code!=null)
//            {
//                Interpreter i = SWBPClassMgr.getInterpreter(instance, user);
//                Object ret=i.eval(code);
//                if(ret!=null && ret instanceof Map)
//                {
//                    map=(Map)ret;
//                }
//            }
//
//            String filePath=SWBPortal.getWorkPath()+getProcessFileTemplate().getWorkPath()+"/"+getProcessFileTemplate().getFileName();
//            System.out.println(filePath);
//            XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
//
//            if(map!=null)
//            {
//                Iterator<XWPFParagraph> paragraphs = document.getParagraphsIterator();
//                while (paragraphs.hasNext()) {
//                    XWPFParagraph paragraph = paragraphs.next();
//                    getParagraphBookmarks(paragraph, map);
//                }
//            }
//
//            document.write(new FileOutputStream("e:/"+getProcessFileName()));
//
//        }catch(Exception e)
//        {
//            log.error(e);
//        }
    }

//    public void getParagraphBookmarks(XWPFParagraph paragraph, Map<String, String> map) {
//        CTP ctp = paragraph.getCTP();
//        Iterator<CTBookmark> bookmarks = ctp.getBookmarkStartList().iterator();
//
//        //System.out.println("c:"+C);
//        int i=0;
//        while (bookmarks.hasNext()) {
//            CTBookmark bookmark = bookmarks.next();
//            if (map.containsKey(bookmark.getName())) {
//                CTR ctr = ctp.addNewR();
//                CTText text = ctr.addNewT();
//                text.setStringValue(map.get(bookmark.getName()));
//            }
//            bookmark.setNil();
//        }
//    }


}
