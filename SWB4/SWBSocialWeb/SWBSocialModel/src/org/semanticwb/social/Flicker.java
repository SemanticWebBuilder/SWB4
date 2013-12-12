package org.semanticwb.social;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;


public class Flicker extends org.semanticwb.social.base.FlickerBase 
{
    Logger log = SWBUtils.getLogger(Flicker.class);
    SWBFlickrOauth oauthConnection = new SWBFlickrOauth();
    public static final String UPLOAD_Photo = "upld";

    // Utilizado para loguearse con OAuth
    public String getCredentials(String uri, WebPage wp, String scheme) throws IOException{
        String url2 = null;
        Flicker flicker = (Flicker)SemanticObject.createSemanticObject(uri).createGenericInstance();
        String url = scheme + wp.getUrl() + "";
        oauthConnection.setKey(flicker.getAppKey());
        oauthConnection.setSecret(flicker.getSecretKey());
        oauthConnection.setUrlCallBack(url);
        oauthConnection.sendRequestToken(flicker);
        url2 = oauthConnection.getAuthorize(flicker);
        return url2;
    }

    public Flicker(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    
    @Override
    public void postPhoto(org.semanticwb.social.Photo photo) {
        /*
        //String action = response.getAction();
        Flickr flickr = null;
        
        //if(UPLOAD_Photo.equals(action) && photo != null && photo.getPhoto() != null) {
        
        if(photo != null && photo.getPhoto() != null) {
           String photoSend = SWBPortal.getWorkPath() + photo.getWorkPath() + "/" + //org.semanticwb.social.Photo.social_photo.getName() +
                     "_" + photo.getId() + "_" + photo.getPhoto();

           String oauth_token = this.getAccessToken();//flicker.getProperty("oauth_token");
           String oauth_token_secret = this.getAccessTokenSecret();//flicker.getProperty("oauth_token_secret");
           if(oauth_token != null && oauth_token_secret != null && (this.getAppKey() != null && this.getAppKey().trim().length() > 1) && (this.getSecretKey() != null && this.getSecretKey().trim().length() > 1)) {
               REST rest = new REST();

               flickr = new Flickr(this.getAppKey(), this.getSecretKey(), rest);
               Auth auth = new Auth();
               auth.setPermission(Permission.DELETE);
               auth.setToken(oauth_token);
               auth.setTokenSecret(oauth_token_secret);

               RequestContext requestContext = RequestContext.getRequestContext();
               requestContext.setAuth(auth);
               flickr.setAuth(auth);
               SWBFile file = new SWBFile(photoSend);//path + photo.getPhoto();
               String description = photo.getMsg_Text()!= null ? photo.getMsg_Text() : "";
               InputStream fileInputStream = null;
               Uploader uploader = flickr.getUploader();
               List<String> list = new ArrayList();
               String tags1 = photo.getTags();
               if(tags1 != null && tags1.trim().length() > 1) {
                   String[] tags2 = tags1.split(",");
                   for(int i=0; i<tags2.length; i++) {
                       list.add(tags2[i]);
                   }
               }
               try {
                   fileInputStream = new FileInputStream(file);
                   UploadMetaData uploadMetaData = new UploadMetaData();
                   uploadMetaData.setTitle("SWBSocial");    //TODO:En este momento envío "SWBSocial", ver como aparece este título en la Red Social
                   uploadMetaData.setPublicFlag(true);
                   uploadMetaData.setDescription(description);
                   uploadMetaData.setTags(list);
                   try {
                       String id = uploader.upload(fileInputStream, uploadMetaData);

                   } catch(FlickrException e1){
                       log.error(e1);
                   }
               } catch(IOException e){
                   log.error("Exception: " + e);
               } finally {
                   IOUtilities.close(fileInputStream);
               }
           }
       }
       * */
    }
    public void listen(Stream stream)
    {
         WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());

        final String key="48a5f73b83d8c736dc2c752fe7c1958d";//flicker.getAppKey()
        final String shared = "3120b55a3337460c";//flicker.getSecretKey()
        final String svr="www.flickr.com";
        REST rest=new REST();
        rest.setHost(svr);

        Flickr flickr=new Flickr(key,shared,rest);
        Flickr.debugStream=false;

        SearchParameters searchParams=new SearchParameters();
        searchParams.setSort(SearchParameters.INTERESTINGNESS_DESC);
        /*
        Iterator<WordsToMonitor> words = WordsToMonitor.ClassMgr.listWordsToMonitors(wsite);
        if(words.hasNext()) {
            StringBuilder tagLst = new StringBuilder();
            while(words.hasNext()) {
                WordsToMonitor word = words.next();
                tagLst.append(word.getCompany());tagLst.append(";");
                tagLst.append(word.getCompetition());tagLst.append(";");
                tagLst.append(word.getProductsAndServices());tagLst.append(";");
                tagLst.append(word.getOtherWords());tagLst.append(";");
            }
            searchParams.setTagMode("any");
            searchParams.setTags(tagLst.toString().split(";"));
            searchParams.setText(tagLst.toString());
        }
        * */

        PhotosInterface photosInterface=flickr.getPhotosInterface();
        PhotoList photoList=null;
        try {
            photoList=photosInterface.search(searchParams,20,1);
        }catch(Exception e) {
            e.printStackTrace(System.out);
        }
        if(photoList!=null) {
            com.flickr4java.flickr.photos.Photo photo;
            org.semanticwb.social.PhotoIn photoIn;
            StringBuilder strBuf=new StringBuilder();
            for(int i=0;i<photoList.size();i++) {
                photo=(com.flickr4java.flickr.photos.Photo)photoList.get(i);
                photoIn = PhotoIn.ClassMgr.createPhotoIn(photo.getId(), wsite);
                photoIn.setMsg_Text(photo.getTitle());
                photoIn.setTags(Arrays.toString(searchParams.getTags()));
                photoIn.setPostInSocialNetwork(this);
                //photoIn.setPostInSocialNetworkUser(photo.getOwner());
                //              strBuf.append("<a href=\"\">");
                //              strBuf.append("<img border=\"0\" src=\""+photo.getSmallSquareUrl()+"\" />");
                //              strBuf.append("</a>\n");
                //new Classifier(photoIn);
            }
//           System.out.println(strBuf.toString());
    }
  }
    
    @Override
    public String doRequestAccess()
    {
        return null;
    }

    @Override
    public String doRequestPermissions()
    {
        return null;
    }


    /* Funcionando para el protoripo
    @Override
    public void listen(SWBModel model) {
        final String key="48a5f73b83d8c736dc2c752fe7c1958d";//flicker.getAppKey()
        final String shared = "3120b55a3337460c";//flicker.getSecretKey()
        final String svr="www.flickr.com";
        REST rest=new REST();
        rest.setHost(svr);

        Flickr flickr=new Flickr(key,shared,rest);
        Flickr.debugStream=false;

        SearchParameters searchParams=new SearchParameters();
        searchParams.setSort(SearchParameters.INTERESTINGNESS_DESC);
        
        Iterator<WordsToMonitor> words = WordsToMonitor.ClassMgr.listWordsToMonitors(model);
        if(words.hasNext()) {
            StringBuilder tagLst = new StringBuilder();
            while(words.hasNext()) {
                WordsToMonitor word = words.next();
                tagLst.append(word.getCompany());tagLst.append(";");
                tagLst.append(word.getCompetition());tagLst.append(";");
                tagLst.append(word.getProductsAndServices());tagLst.append(";");
                tagLst.append(word.getOtherWords());tagLst.append(";");
            }
            searchParams.setTagMode("any");
            searchParams.setTags(tagLst.toString().split(";"));
            searchParams.setText(tagLst.toString());
        }


        PhotosInterface photosInterface=flickr.getPhotosInterface();
        PhotoList photoList=null;
        try {
            photoList=photosInterface.search(searchParams,20,1);
        }catch(Exception e) {
            e.printStackTrace(System.out);
        }
        if(photoList!=null) {
            com.flickr4java.flickr.photos.Photo photo;
            org.semanticwb.social.PhotoIn photoIn;
            StringBuilder strBuf=new StringBuilder();
            for(int i=0;i<photoList.size();i++) {
                photo=(com.flickr4java.flickr.photos.Photo)photoList.get(i);
                photoIn = PhotoIn.ClassMgr.createPhotoIn(photo.getId(), model);
                photoIn.setMsg_Text(photo.getTitle());
                photoIn.setTags(Arrays.toString(searchParams.getTags()));
                photoIn.setPostInSocialNetwork(this);
                //photoIn.setPostInSocialNetworkUser(photo.getOwner());
                //              strBuf.append("<a href=\"\">");
                //              strBuf.append("<img border=\"0\" src=\""+photo.getSmallSquareUrl()+"\" />");
                //              strBuf.append("</a>\n");
                new Classifier(photoIn);
            }
//           System.out.println(strBuf.toString());
        }
    }
     *
     */
    
    @Override
    public JSONObject getUserInfobyId(String userId) {
        return null;
    }

    @Override
    public int monitorPostOutResponses(PostOut postOut) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
