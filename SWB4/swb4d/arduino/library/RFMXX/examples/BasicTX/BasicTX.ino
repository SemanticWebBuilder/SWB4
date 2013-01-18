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
#include <avr/wdt.h> 
#include <RFMXX.h>

#define LED 9
  
  Rfm rfm;
  
  void setup()
  {    
    pinMode(LED, OUTPUT);
    digitalWrite(LED, LOW);
    
    //*
    Serial.begin(9600);
    while (!Serial) {
      ; // wait for serial port to connect. Needed for Leonardo only
    }
    //*/
    
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);        
    delay(100);
    //433MH, Group 1, ID 1
    rfm.initRFM(RFM_433,1,1);   
    
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);    
   
    wdt_enable(WDTO_250MS);   
  }  
  
  
  //unsigned char data[]={1,2,3,4,5};
  unsigned char data[]="Hello World!";
  void loop()
  { 
     Serial.print("SendData:");
     rfm.sendData(1,2,data, sizeof(data));
     wdt_reset(); 
     delay(200);
     wdt_reset(); 
     delay(200);
     wdt_reset(); 
     delay(200);
     wdt_reset(); 
  }