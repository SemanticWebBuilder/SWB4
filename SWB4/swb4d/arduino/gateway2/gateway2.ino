#include <Ethernet.h>
#include <SPI.h>
#include <RFMXX.h>
#include <avr/wdt.h> 

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
//byte ip[] = { 192, 168, 5, 116};
byte ip[] = { 192, 168, 2, 2};
//byte gate[] = { 192, 168, 5, 254 };
byte gate[] = { 192, 168, 2, 1  };
byte mask[] = { 255, 255, 255, 0 };
byte server[] = { 108,161,130,57 }; // Google
//byte server[] = { 192,168,2,1 }; // Google

char serverName[] = "swb4d.semanticbuilder.com";  // twitter URL

EthernetClient client;
Rfm rfm;

String currentLine = "";            // string to hold the text from server

/** 
 *  Establece conexion con el servidor
 */
void conect()
{
  wdt_disable();  
  Serial.println("connecting...");
  if (client.connect(server, 9494)) {
    Serial.println("connected...");
    client.println("ini 0000000002");
  } else {
    Serial.println("connection failed...");
    delay(1000);
  }
  wdt_enable(WDTO_1S); 
}

void checkEthernetConnection()
{
  
  if (client.connected()) 
  {
    if (client.available()) {
      char c = client.read();
      //Serial.print(c);
      
      // if you get a newline, clear the line:
      if (c == '\n') 
      {
        processMsg(currentLine);
        currentLine = "";
      }else
      {
        // add incoming byte to end of line:
        currentLine += c; 
      } 
    }
  }else
  {  
    Serial.println();
    Serial.println("disconnecting...");
    client.stop();
    conect();
  }  
}


unsigned char data[1];
void processMsg(String msg)
{
  Serial.println("Mensaje recibido:"+msg);
  if(msg.startsWith("upd"))
  {
    //upd 01 01 255
    int fsp=msg.indexOf(' ',4);
    int lsp=msg.lastIndexOf(' ');
    
    
    byte grp=string2Byte(msg.substring(4,fsp));
    byte id=string2Byte(msg.substring(fsp+1,lsp));
    data[0]=string2Byte(msg.substring(lsp+1));
    
    rfm.sendData(grp,id,data, sizeof(data));
    //Esperar a procesar por linea electrica
    //O talvez agregar un buffer del lado del transiever
    //delay(250);
  }
  wdt_reset(); 
}

void sendMsg(String msg)
{
  Serial.println("Mensaje enviado:"+msg);
  client.println(msg);
}

byte expo(byte val)
{
  byte ret=1;
  for(int x=0;x<val;x++)
  {
    ret=ret*10;
  }
  return ret;
}

byte string2Byte(String str)
{
  byte ret=0;
  int len=str.length();
  for(byte x=0;x<len;x++)
  {
    ret+=(str.charAt(len-x-1)-48)*expo(x);
  }
  //Serial.println("String2byte:"+str);
  //Serial.println(ret);
  return ret;
}

void setup()
{
  wdt_disable();  
 // initialize the serial communication:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }
  
  rfm.initRFM(RFM_433,255,255);   
  
  // attempt a DHCP connection:
  Serial.println("Attempting to get an IP address using DHCP:");
  if (!Ethernet.begin(mac)) 
  {
    // if DHCP fails, start with a hard-coded address:
    Serial.println("failed to get an IP address using DHCP, trying manually");
    Ethernet.begin(mac, ip, gate, mask);
  }
  //Serial.println("Local IP:"+Ethernet.localIP());
  conect();
}

void loop()
{
     if(rfm.hasData())
     {
          unsigned char zona=rfm.RF_RXBUF[3];
          unsigned char iden=rfm.RF_RXBUF[4];
          unsigned char comm=rfm.RF_RXBUF[5];     
        
          String str="rep";
          str+=" ";
          str+=zona;
          str+=" ";
          str+=iden;
          str+=" ";
          str+=comm;
          
          sendMsg(str);

          Serial.print(zona);
          Serial.print(" ");
          Serial.print(iden);
          Serial.print(" ");
          Serial.println(comm);          
     }
      
     checkEthernetConnection();
     wdt_reset(); 
}
