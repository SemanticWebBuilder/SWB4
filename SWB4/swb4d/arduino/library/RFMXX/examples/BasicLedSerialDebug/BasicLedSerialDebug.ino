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
    
    rfm.initRFM(RFM_433,1,1);   
    
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);     
 
  }  
  
  
  byte data[]="Hello World!";
  void loop()
  { 
     Serial.println("SendData:");
     rfm.sendData(0,0,data, sizeof(data));
     delay(500);
     if(rfm.hasData())
     {  
        digitalWrite(LED, HIGH);
        delay(100);
        digitalWrite(LED, LOW);
        
        Serial.print("msg size: ");
        Serial.print(rfm.RF_RXBUF[0]);
        Serial.print(" grp_to: ");
        Serial.print(rfm.RF_RXBUF[1]);
        Serial.print(" id_to: ");
        Serial.print(rfm.RF_RXBUF[2]);
        Serial.print(" grp_src: ");
        Serial.print(rfm.RF_RXBUF[3]);
        Serial.print(" id_src: ");
        Serial.print(rfm.RF_RXBUF[4]);
        
        Serial.print(" data: ");        
        for(byte j=0;j<rfm.RF_RXBUF[0];j++)
        {
          Serial.print(rfm.RF_RXBUF[j+5]);
          Serial.print(" ");
        }
        
        //This is only if you send text
        Serial.print(" txt: ");
        Serial.print(rfm.getMsg());
        
        Serial.println();
     }
  }