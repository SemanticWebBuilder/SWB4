/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import mx.gob.sat.sgi.SgiCripto.SgiCertificado;
import mx.gob.sat.sgi.SgiCripto.SgiCriptoException;
import mx.gob.sat.sgi.SgiCripto.SgiFirma;
import mx.gob.sat.sgi.SgiCripto.SgiLlavePrivada;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author serch
 */
public class SigTest {

  SgiCertificado certificado = new SgiCertificado();
  SgiLlavePrivada llavePrivada = new SgiLlavePrivada();
  SgiFirma firma = new SgiFirma();
  SgiFirma sgiFirma = new SgiFirma();
  String rfcCert;
  String serialNumber = null;
  String sign = null;
  String lsFechaCertificado = "*";
  PrivateKey pkey;

  private String stringToSign = "*";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SigTest firma = new SigTest();
        firma.inicia(args);
    }
  
  public void inicia(String[] args) {
      
   /*   try
      {
        this.certificado.inicia(1, new FileInputStream(args[0]));
        this.rfcCert = this.certificado.getTitular(1);
        this.serialNumber = this.certificado.getNumSerie().trim();
        this.lsFechaCertificado = this.certificado.getVigenciaFinal();
        String tipoCertificado = tipoDeCertificado(this.certificado);
        if (tipoCertificado != "FIEL") {
          System.out.println("ERROR: El certificado que está usando no es un certificado tipo FIEL.");
          return;
        }
      } catch (SgiCriptoException sce) {
        System.out.println("ERROR: No fue posible leer el archivo que contiene el certificado.");
        return;
      } catch (FileNotFoundException fnfe) {
        System.out.println("ERROR: No fue posible leer el archivo que contiene el certificado.");
        return;
      }
      //this.rfcCert = this.rfcCert.substring(0, this.tfrfc.getText().trim().length());
      if (!"MAPS740508UD7".equals(this.rfcCert)) {
        System.out.println("ERROR: El RFC del certificado no corresponde al RFC proporcionado por usted.");
        return;
      }
      */
  Security.addProvider(new BouncyCastleProvider());

      boolean correspondencia = false;
      try {
        llavePrivada.inicia(new FileInputStream(args[1]), args[2].getBytes());
        pkey = llavePrivada.getLlave();
       /* correspondencia = this.llavePrivada
          .correspondenciaConCertificado(this.certificado);*/
      }
      catch (FileNotFoundException fnfe) {
          System.out.println("ERROR: No fue posible leer el archivo que contiene la llave.");
        return;
      }
      catch (SgiCriptoException sce) {
        System.out.println("ERROR: La contraseña de su llave privada es incorrecta.");
        return;
      }
      System.out.println("correspondencia: "+correspondencia);
  
//      if (!correspondencia) {
//        System.out.println("ERROR: El certificado y la llave no corresponden");
//        return;
//      }

      this.stringToSign = ("|" + "MAPS740508UD7" + "|" + this.serialNumber + "|");
      try {
//        byte[] abyte0 = this.firma.genFirma(this.llavePrivada, 9, 
//          this.stringToSign.getBytes(), this.stringToSign.length());
//        this.sign = new String(Base64.encode(abyte0));
//        System.out.println("Datos correctos, puede Continuar.");
        
        Signature sig = Signature.getInstance("SHA1withRSA");
        
        sig.initSign(pkey);
        sig.update(stringToSign.getBytes());
        byte[] firma= sig.sign();
        
        sig = Signature.getInstance("SHA1withRSA");
        FileInputStream fis = new FileInputStream(args[0]);
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert=null;
        if (bis.available() > 0) {
            cert = cf.generateCertificate(bis);
            X509Certificate x509 = (X509Certificate)cert;
            System.out.println("SerialNumber:"+x509.getSerialNumber());
            System.out.println("DN:"+x509.getSubjectDN().getName());
            System.out.println("Subject UniqueID:"+x509.getSubjectUniqueID());
            System.out.println("X500P:"+x509.getSubjectX500Principal().getName());
        }
        bis.close();
        fis.close();
        
        sig.initVerify(cert);
        sig.update(stringToSign.getBytes());
        System.out.println("La firma regresa: "+sig.verify(firma));
        System.out.println("\n\n");
        
//      } catch (SgiCriptoException sce) {
//        System.out.println("ERROR: " + sce.getMensaje());
//        return;
      } catch (GeneralSecurityException gse){
          System.out.println("GSE:"+gse.getMessage());
      }catch (IOException ioe){
          System.out.println("IOE:"+ioe.getMessage());
      }

      this.lsFechaCertificado = formatUtcToLocalDate(this.lsFechaCertificado);
      if (this.lsFechaCertificado == null) {
        System.out.println("ERROR: El certificado contiene una fecha inválida");
        return;
      }
      
      
      
    }
  
  
  private String formatUtcToLocalDate(String fechaUTC) {
    SimpleDateFormat sdfUTC = new SimpleDateFormat("yyMMddHHmmss");
    SimpleDateFormat sdfLocal = new SimpleDateFormat("dd/MM/yyyy");
    sdfUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date localDate = new Date();
    try {
      localDate = sdfUTC.parse(fechaUTC);
    } catch (ParseException e) {
      return null;
    }
    sdfLocal.setTimeZone(TimeZone.getTimeZone("CST"));
    return sdfLocal.format(localDate);
  }
  
  
  private String tipoDeCertificado(SgiCertificado elCert) {
    String sTipCert = null;
    try {
      if (elCert != null) {
        int tipoCert = elCert.getType(); System.out.println("tipo: "+tipoCert);
        if (tipoCert == 1) {
          sTipCert = "FIEL";
        } else if (tipoCert == 2) {
          sTipCert = "SELLO";
        } else if (tipoCert == 3) {
          List uso = elCert.getKeyUsageExt();
          if (uso != null) {
            if ((uso.contains("digitalSignature")) && 
              (uso
              .contains("nonRepudiation"))) {
              if ((uso.contains("keyEncipherment")) || 
                (uso.contains("dataEncipherment"))) {
                if ((uso.contains("keyAgreement")) && 
                  (elCert.getNetscapeTypeCert() != null)) {
                  sTipCert = "FIEL";
                }

              }
              else if (elCert.getTitular(24) != null) {
                try {
                  elCert.getNetscapeTypeCert();
                } catch (SgiCriptoException ex) {
                  sTipCert = "SELLO";
                }
              }
              else
                sTipCert = "DESCONOCIDO";
            }
            else
              sTipCert = "DESCONOCIDO";
          }
          else
            sTipCert = "DESCONOCIDO";
        }
      }
    }
    catch (SgiCriptoException localSgiCriptoException1) {
    }
    return sTipCert;
  }
  
}
