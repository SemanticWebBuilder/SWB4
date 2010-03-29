package org.semanticwb.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.SWBRuntimeException;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.ImageResizer;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.util.UploadFileRequest;
import org.semanticwb.util.UploadedFile;
import org.semanticwb.util.UploaderFileCacheUtils;

public class FlashImageUpload extends org.semanticwb.model.base.FlashImageUploadBase
{

    public FlashImageUpload(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop)
    {
        if (request.getParameter(prop.getName() + "_delFile") != null)
        {
            if (prop.getName().startsWith("has"))
            {
                Iterator<SemanticLiteral> list = obj.listLiteralProperties(prop);

                Set<String> grupo = new TreeSet<String>();
                while (list.hasNext())
                {
                    grupo.add(list.next().getString());
                }
                String[] params = request.getParameterValues(prop.getName() + "_delFile");
                for (String valor : params)
                {
                    grupo.remove(valor);
                    delfile(obj, valor);
                }
                obj.removeProperty(prop);
                for (String valor : grupo)
                {
                    obj.addLiteralProperty(prop, new SemanticLiteral(valor));
                }
            } else
            {
                delfile(obj, request.getParameter(prop.getName() + "_delFile"));
                obj.removeProperty(prop);
            }
        }
        String destpath = UploaderFileCacheUtils.getHomepath() + "/" + obj.getWorkPath();
        File dir = new File(destpath);
        if (!dir.exists() && !dir.mkdirs())
        {
            throw new SWBRuntimeException("Can't create work directory " + dir);
        }
        String cad = request.getParameter(prop.getName());
        List<UploadedFile> lista = UploaderFileCacheUtils.get(cad);
        for (UploadedFile arch : lista)
        {
            File orig = new File(arch.getTmpuploadedCanonicalFileName());
            String webpath = obj.getWorkPath() + arch.getOriginalName();
            File dest = new File(dir, arch.getOriginalName());
            if (!orig.renameTo(dest))
            {
                try
                {
                    SWBUtils.IO.copy(orig.getCanonicalPath(), dest.getCanonicalPath(), false, null, null);
                } catch (IOException ex)
                {
                    throw new SWBRuntimeException("Can't copy files", ex);
                }
            }
            try{
            imgPrpcess(dest);
            if (prop.getName().startsWith("has"))
            {
                obj.addLiteralProperty(prop, new SemanticLiteral(arch.getOriginalName()));
            } else
            {
                obj.setProperty(prop, arch.getOriginalName());
            }
            }catch (IOException IOE){
                //TODO Logg error
            }
        }
        UploaderFileCacheUtils.clean(cad);
    }

    private void imgPrpcess(File dest) throws IOException
    {
        //TODO: ImageResizer.
        String name = dest.getName();
        File path = dest.getParentFile();
        dest.renameTo(new File(path, "tmping_" + name));
        if (isImgCrop())
        {
            ImageResizer.resizeCrop(dest, getImgMaxWidth(), getImgMaxHeight(), new File(path, name), name.substring(name.lastIndexOf(".") + 1));
        } else
        {
            ImageResizer.resize(dest, getImgMaxWidth(), getImgMaxHeight(), true, new File(path, name), name.substring(name.lastIndexOf(".") + 1));
        }
        if (isImgThumbnail())
        {
            if (isImgCrop())
            {
                ImageResizer.resizeCrop(dest, getImgThumbnailWidth(), getImgThumbnailHeight(), new File(path, "thmb_"+name), name.substring(name.lastIndexOf(".") + 1));
            } else
            {
                ImageResizer.resize(dest, getImgThumbnailWidth(), getImgThumbnailHeight(), true, new File(path, "thmb_"+name), name.substring(name.lastIndexOf(".") + 1));
            }
        }
        dest.delete();
    }

    private UploadFileRequest configFileRequest(SemanticProperty prop)
    {
        boolean multiple = prop.getName().startsWith("has");
//        System.out.println("filter:"+getFileFilter());
        HashMap<String, String> filtros = new HashMap<String, String>();
        if (null == getFileFilter() || "".equals(getFileFilter()))
        {
            filtros.put("Jpeg File", "*.jpg;*.jpeg");
            filtros.put("GIF File", "*.gif");
            filtros.put("PNG File", "*.png");
            filtros.put("All Images", "*.jpg;*.jpeg;*.gif;*.png");
        } else
        {
            String[] cads = getFileFilter().split("\\|");
            for (String line : cads)
            {
//                System.out.println("cadena:"+line);
                String[] parts = line.split(":");
                filtros.put(parts[0], parts[1]);
            }
        }
        return new UploadFileRequest(filtros, multiple, getFileMaxSize());
    }
}
