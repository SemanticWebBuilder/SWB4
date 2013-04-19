#include <Ethernet.h>
#include <SPI.h>
#include <RFMXX.h>
#include <avr/wdt.h> 

// include the X10 library files:
#include <x10.h>

const int rxPin = 6;    // data receive pin
const int txPin = 4;    // data transmit pin
const int zcPin = 5;    // zero crossing pin

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
    byte val=string2Byte(msg.substring(lsp+1));
    
    if(grp==1)grp=A;
    else if(grp==2)grp=B;
    else if(grp==3)grp=C;
    else if(grp==4)grp=D;
    else if(grp==5)grp=E;
    else if(grp==6)grp=F;
    else if(grp==7)grp=G;
    else if(grp==8)grp=H;
    else if(grp==9)grp=I;
    else if(grp==10)grp=J;
    else if(grp==11)grp=K;
    else if(grp==12)grp=L;
    else if(grp==13)grp=M;
    else if(grp==14)grp=N;
    else if(grp==16)grp=O;
    else if(grp==16)grp=P;
    
    if(id==1)id=UNIT_1;
    else if(id==2)id=UNIT_2;
    else if(id==3)id=UNIT_3;
    else if(id==4)id=UNIT_4;
    else if(id==5)id=UNIT_5;
    else if(id==6)id=UNIT_6;
    else if(id==7)id=UNIT_7;
    else if(id==8)id=UNIT_8;
    else if(id==9)id=UNIT_9;
    else if(id==10)id=UNIT_10;
    else if(id==11)id=UNIT_11;
    else if(id==12)id=UNIT_12;
    else if(id==13)id=UNIT_13;
    else if(id==14)id=UNIT_14;
    else if(id==15)id=UNIT_15;
    else if(id==16)id=UNIT_16;
    
    x10.beginTransmission(grp);
    x10.write(id);
    if(val==0)x10.write(OFF);    
    else x10.write(ON);      
    x10.endTransmission();        
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
  
  x10.begin(rxPin, txPin, zcPin);
  
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
  /*
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
     */
      
     checkEthernetConnection();
     wdt_reset(); 
}

