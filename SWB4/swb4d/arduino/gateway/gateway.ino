#include <Ethernet.h>
#include <SPI.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
//byte ip[] = { 192, 168, 5, 116};
byte ip[] = { 192, 168, 2, 2};
//byte gate[] = { 192, 168, 5, 254 };
byte gate[] = { 192, 168, 2, 1  };
byte mask[] = { 255, 255, 255, 0 };
//byte server[] = { 192, 168, 2, 1 }; // Google
//byte server[] = { 108,161,130,57 }; // Google
byte server[] = { 192,168,2,1 }; // Google

char serverName[] = "domotic.infotec.com";  // twitter URL

// Inicializacion de constantes
byte CERO=8;      //Pin para cruce por cero
byte READ=9;      //Pin para lectura 
byte WRITE=7;     //Pin para escritura
byte LED=13;      //Pin del led de estatus

byte _cero=0;     //Ultimo valor de curce por cero

EthernetClient client;

String currentLine = "";            // string to hold the text from server

void setup()
{
  //Inicializacion de pines 
  pinMode(READ, INPUT);
  pinMode(WRITE, OUTPUT);
  digitalWrite(WRITE, LOW);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);   // turn the LED on (HIGH is the voltage level)
  delay(500);               // wait for a second
  digitalWrite(LED, HIGH);    // turn the LED off by making the voltage LOW
  delay(500);    
  digitalWrite(LED, LOW);   // turn the LED on (HIGH is the voltage level)

 // initialize the serial communication:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }
  
  // attempt a DHCP connection:
  Serial.println("Attempting to get an IP address using DHCP:");
  //if (!Ethernet.begin(mac)) 
  {
    // if DHCP fails, start with a hard-coded address:
    Serial.println("failed to get an IP address using DHCP, trying manually");
    Ethernet.begin(mac, ip, gate, mask);
  }
  
  conect();
}

/** 
 *  espera al siguiente cruce por cero con el valor de c por linea electrica
 */
void waitUntil(int c)
{
  while(c!=digitalRead(CERO));
}

/** 
 *  Lee estatus de un segmento bit por linea electrica
 */
boolean readBitPart(unsigned long time)
{
  int r=0;
  while(micros()<time)
  {
     if(digitalRead(READ)>0)r++;
     else r--;     
  }
  return r>0;
}

/** 
 *  lee la informacion de un semiciclo ciclo (lee informacion de un bit) por linea electrica
 *  no espera a que cambie de ciclo 
 */
int readBitData()
{ 
  unsigned long time=micros();    
  int x=0;
  int r=0;  
  while(x<8)
  {
    time+=500;
    if(readBitPart(time))
    {
      r++;
    }else
    {
      r--;
    }
    time+=500;
    if(readBitPart(time))
    {
      r--;
    }else
    {
      r++;
    }
    x++;
  }
  return r;
}

/** 
 *  espera a leer el sigueinte bit por linea electrica
 */
int getBit()
{
  waitNextCicle();
  int d=readBitData();
  //if(r>3 || r<-3)digitalWrite(LED, HIGH);
  //else digitalWrite(LED, LOW);
  if(d>4)return 1;
  if(d<-4)return -1;
  return 0;
}

/** 
 *  Espera a leer el siguente byte por linea electrica
 */
byte getByte()
{
  byte r=0;
  for(int x=0;x<8;x++)
  {    
    int b=getBit();
    if(b<0)b=0;
    r=(r<<1);
    r=r+b;
  }
  return r;
}

/** 
 *  Espera al siguiente cruce por cero en linea electrica
 */
void waitNextCicle()
{
  _cero=digitalRead(CERO);  
  if(_cero==0)_cero=1;else _cero=0;
  waitUntil(_cero);
}

/** 
 *  Ciclo de envio de bin 1 por linea electrica
 */
void cicleData1()
{
    byte x=0;
    //unsigned long time=micros();   
    for(x=0;x<8;x++)
    {
      digitalWrite(WRITE, HIGH);
      delayMicroseconds(490);
      digitalWrite(WRITE, LOW);
      delayMicroseconds(496);
    }
    //time=micros()-time;
    //Serial.println(time);
}

/** 
 *  Cilo de envio de bit -1 por linea electrica
 */
void cicleData0()
{
    byte x=0;
    for(x=0;x<8;x++)
    {
      digitalWrite(WRITE, LOW);
      delayMicroseconds(496);
      digitalWrite(WRITE, HIGH);
      delayMicroseconds(490);
    }
    digitalWrite(WRITE, LOW);
}

/** 
 *  Ciclo de envio de bit 0 (espera 8 milisegundos)
 */
void cicleNoData()
{
   delayMicroseconds(8000);
}

/** 
 *  Manda bin 1 por linea electrica
 */
void send1()
{
   waitNextCicle();
   cicleData1();
}

/** 
 *  Manda bit inverso (-1) por linea electrica
 */
void sendInv()
{
   waitNextCicle();
   cicleData0();
}

/** 
 *  Manda bit cero por linea electrica
 */
void send0()
{
   waitNextCicle();
   cicleNoData();
}

/** 
 *  Manda bits de inicio por linea electrica
 */
void sendIni()
{
   sendInv();
   send1();
   send0();
   sendInv();
   send1();
   send0();
   sendInv();
   send1();
   sendInv();
   send1();
}

/** 
 *  Manda un byte por linea electrica
 */
void writeByte(byte b)
{
   byte x=0;
   for(x=0;x<8;x++)
   {
       if(bitRead(b, 7))send1();
       else send0();
       b=b<<1;
   }
}

/** 
 *  Establece conexion con el servidor
 */
void conect()
{
  Serial.println("connecting...");
  if (client.connect(server, 9494)) {
    Serial.println("connected");
    client.println("ini 0000000002");
  } else {
    Serial.println("connection failed");
    delay(2000);
  }
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
    Serial.println("disconnecting.");
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
    byte b1=string2Byte(msg.substring(4,fsp));
    byte b2=string2Byte(msg.substring(fsp+1,lsp));
    byte b3=string2Byte(msg.substring(lsp+1));
    
    sendIni();
    writeByte(b1);
    writeByte(b2);
    writeByte(b3);
  }
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

void loop()
{
      int b=getBit();
      if(b==-1)  //Lectura de mensage inicial
      {
          b=getBit();
          if(b==1)
          {
              b=getBit();
              if(b==-1)
              {
                  b=getBit();
                  if(b==1)
                  {
                      unsigned char zona=getByte();
                      unsigned char iden=getByte();
                      unsigned char comm=getByte();
                      
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
              }
          }
      }
      
      checkEthernetConnection();
/*      
      waitNextCicle();
      sendIni();
      writeByte(1);
      writeByte(1);
      writeByte(1);
      delay(500);

      waitNextCicle();
      sendIni();
      writeByte(1);
      writeByte(1);
      writeByte(2);
      delay(500);
*/  
}




