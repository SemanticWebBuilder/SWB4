/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author martha.jimenez
 * Esta clase contiene los metodos para conectarse al sitio de Flickr y obtener
 * el permiso de alguna cuenta de Flickr.
 */
public class SWBFlickrOauth {

    Logger log = SWBUtils.getLogger(SWBFlickrOauth.class);

    public String KEY = "";//52ce7d7f07b7b9524283655dfef74302
    public String SECRET = "";//b06660c30664e88c
    protected static final String HMAC_SHA1 = "HmacSHA1";
    protected static final String ENC = "UTF-8";
    protected static Base64 base64 = new Base64();
    protected static final String methodGet = "GET";
    protected static final String methodPost = "POST";
    protected String urlCallback = "";
    protected String oauthVerifier="";
    protected String oauthToken="";
    protected String versionOauth = "1.0";
    String uriFlicker = "";

    public String getSignature(String url, String params, String access_token) throws UnsupportedEncodingException, NoSuchAlgorithmException,InvalidKeyException {
        StringBuilder base = new StringBuilder();
        base.append(methodGet);
        base.append("&");
        base.append(url);
        base.append("&");
        base.append(params);
        byte[] keyBytes = (SECRET + "&" + access_token).getBytes(ENC);
        SecretKey key = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(key);
        return new String(base64.encode(mac.doFinal(base.toString().getBytes(ENC))), ENC).trim();
    }

    public List<NameValuePair> asignParameters(String peticion) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(peticion.equals("requestToken")){
            params.add(new BasicNameValuePair("oauth_callback", urlCallback));
        }
        params.add(new BasicNameValuePair("oauth_consumer_key", KEY));
        params.add(new BasicNameValuePair("oauth_nonce", "" + (int) (Math.random() * 100000000)));
        params.add(new BasicNameValuePair("oauth_signature_method", "HMAC-SHA1"));
        params.add(new BasicNameValuePair("oauth_timestamp", "" + (System.currentTimeMillis() / 1000)));
        if(peticion.equals("accessToken")){
            params.add(new BasicNameValuePair("oauth_token", oauthToken));
            params.add(new BasicNameValuePair("oauth_verifier", oauthVerifier));
        }
        params.add(new BasicNameValuePair("oauth_version", versionOauth));
        return params;
    }

    public List<NameValuePair> configRequestToken(String access_token, String peticion, String urlPeticion){
        String signature = null;
        List<NameValuePair> params = asignParameters(peticion);
        try {
            signature = getSignature(URLEncoder.encode(urlPeticion, ENC),
            URLEncoder.encode(URLEncodedUtils.format(params, ENC), ENC),access_token);
        }catch(NoSuchAlgorithmException e){
            log.error(e);
        } catch(InvalidKeyException e1){
            log.error(e1);
        } catch(UnsupportedEncodingException e2){
            log.error(e2);
        }

        params.add(new BasicNameValuePair("oauth_signature", signature));
        return params;
    }

    public void sendRequestToken(Flicker flicker) throws IOException{
        URI uri = null;
        String at = "";
        String ats = "";
        List<NameValuePair> params = configRequestToken("","requestToken", "http://www.flickr.com/services/oauth/request_token");
        try {
            uri = URIUtils.createURI("http", "www.flickr.com", -1, "/services/oauth/request_token",
                URLEncodedUtils.format(params, ENC), null);
        } catch(URISyntaxException e2){
            log.error(e2);
        }
        String[] all = executePeticion(uri);
        if(all.toString().length() > 1) {
            at = all[1].substring(all[1].indexOf("=") + 1, all[1].length());
            ats = all[2].substring(all[2].indexOf("=") + 1, all[2].length());
        }
         if(flicker != null) {
            flicker.setProperty("oauth_token_authorize", at);
            flicker.setProperty("token_secret", ats);
         }
    }

    public void sendAccessToken(Flicker flicker) throws IOException{
        URI uri = null;
        String at = "";
        String ats = "";
        if(flicker != null) {
            try {
                uri = URIUtils.createURI("http", "www.flickr.com", -1, "/services/oauth/access_token",
                    URLEncodedUtils.format(configRequestToken(flicker.getProperty("token_secret"),"accessToken", "http://www.flickr.com/services/oauth/access_token" ), ENC), null);
            } catch(URISyntaxException e2) {
                log.error(e2);
            }
            String[] all = executePeticion(uri);
            at = all[1].substring(all[1].indexOf("=") + 1, all[1].length());
            ats = all[2].substring(all[2].indexOf("=") + 1, all[2].length());
            //flicker.setProperty("oauth_token", at);
            flicker.setAccessToken(at);
            //flicker.setProperty("oauth_token_secret", ats);
            flicker.setAccessTokenSecret(ats);
         }
    }

    protected String [] executePeticion(URI uri) throws IOException{
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(uri);
        String[] all = {};

        HttpResponse response1 = httpclient.execute(httpget);
        HttpEntity entity = response1.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            int len;
            byte[] tmp = new byte[2048];
            while ((len = instream.read(tmp)) != -1) {
                String tmp1 = new String(tmp, 0, len, ENC);
                if(tmp1.contains("&")) {
                    all = tmp1.split("&");
                } else {
                    log.error("Error in: " + tmp1);
                }
            }
        }
        return all;
    }
    public String getAuthorize(Flicker flicker){
        String aut = "";
        if(flicker != null) {
            aut = flicker.getProperty("oauth_token_authorize");
        }
        URI uri = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("oauth_token", aut));
        params.add(new BasicNameValuePair("perms", "delete"));
        params.add(new BasicNameValuePair("uri", flicker.getURI()));
        try {
            uri = URIUtils.createURI("http", "www.flickr.com", -1, "/services/oauth/authorize",
                URLEncodedUtils.format(params, ENC), null);
        } catch(URISyntaxException e2){
            log.error(e2);
        }
        return uri.toString();
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }
    public String getOauthVerifier() {
        return oauthVerifier;
    }

    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }

    public String getUrlCallback() {
        return urlCallback;
    }

    public void setUrlCallBack(String urlCallBack) {
        this.urlCallback = urlCallBack;
    }

    public void setKey(String key) {
        this.KEY = key;
    }

    public void setSecret(String secret) {
        this.SECRET = secret;
    }

    public String getUriFlicker() {
        return uriFlicker;
    }

    public void setUriFlicker(String uriFlicker) {
        this.uriFlicker = uriFlicker;
    }

}
