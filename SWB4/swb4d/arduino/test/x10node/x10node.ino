// Inicializacion de constantes
byte CERO=5;      //Pin para cruce por cero
byte READ=4;      //Pin para lectura 
byte WRITE=3;     //Pin para escritura
byte LED=0;      //Pin del led de estatus

byte _cero=0;     //Ultimo valor de curce por cero

void setup()
{
  //Inicializacion de pines 
  pinMode(READ, INPUT);
  pinMode(WRITE, OUTPUT);
  digitalWrite(WRITE, LOW);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);   // turn the LED on (HIGH is the voltage level)
  //delay(500);               // wait for a second
  //digitalWrite(LED, HIGH);    // turn the LED off by making the voltage LOW
  //delay(500);    
  //digitalWrite(LED, LOW);   // turn the LED on (HIGH is the voltage level)
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
                      
                      //change status
                      
                  }
              }
          }
      }
      
}




