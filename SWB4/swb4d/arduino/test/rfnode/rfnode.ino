/**
* Very small library for family RFM12 devices...
* You can use this library with the Attiny chips 
* Libreria muy simple de comunicación inalámbrica utilizando la familia de dispositivos RFM12
* El tamaño es muy pequeño para poder ser utilizo con dispositivos de poca memoria como los Attiny
* Javier Solís González
* softjei@gmail.com.mx
* 01/01/2013
* version 0.1
*/
/*
  #define _SCK 13		//Digital pin of SCK
  #define _SDO 12		//Digital pin of SDO
  #define _SDI 11		//Digital pin of SDI
  #define _nSEL 10		//Digital pin of SEL
  #define _nIRQ 2       //Digital pin of IRQ
  #define _iIRQ 0  		//Internal Interruption 0 of pin digital pin 2
*/
#include <RFMXX.h>
#include <avr/wdt.h> 

#if defined(__AVR_ATtiny4313__) || defined(__AVR_ATtiny2313__) 
  #define LED 7
  #define SENS 6
#else 
  #define LED 9
  #define SENS 8
#endif

  
  Rfm rfm;
  
  
  void setup()
  {    
    wdt_disable();  
    pinMode(LED, OUTPUT);
    digitalWrite(LED, LOW);
    pinMode(SENS, INPUT);
    
    /*
    Serial.begin(9600);
    while (!Serial) {
      ; // wait for serial port to connect. Needed for Leonardo only
    }
    Serial.println("init");
    //*/
    
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);        
    delay(100);
    
    rfm.initRFM(RFM_433,1,2);   
    
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);     
    wdt_enable(WDTO_1S); 
  }  
  
  
  byte data[1];
  byte old_sens=0;
  byte led=0;
  
  void setLed()
  {
      if(led==16)
        digitalWrite(LED, HIGH);
      else if(led==0)
        digitalWrite(LED, LOW);
      else
        analogWrite(LED, led*15);    
  }
  
  void loop()
  { 
      wdt_reset(); 
      
      byte sens=digitalRead(SENS);
      
      if(old_sens!=sens)
      {
        old_sens=sens;
        if(sens==1)
        {
          if(led)led=0;
          else led=16;
          setLed();
          //Serial.print("SendData: ");
          //Serial.print(led);
          data[0]=led;
          rfm.sendData(255,255,data, sizeof(data));
          delay(100);
        }
      }
      
      if(rfm.hasData())
      {  
        led=rfm.RF_RXBUF[5];
        //Serial.println(b);
        setLed();
      }
  }
