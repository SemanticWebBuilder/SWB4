//Gateway
//*
  #define LED 53
  #define _SCK 7
  #define _SDO 6
  #define _SDI 5
  #define _nSEL 4
  #define _nIRQ 3   
//*/
//UNO
/*
  #define LED 9
  #define _SCK 13
  #define _SDO 12
  #define _SDI 11
  #define _nSEL 10
  #define _nIRQ 2   
//*/
//MEGA
/*
  #define LED 13
  #define _SCK 52
  #define _SDO 50
  #define _SDI 51
  #define _nSEL 53
  #define _nIRQ 2 
//*/
//Attiny4313  
/*
  #define LED 1
  #define _SCK 16
  #define _SDO 15
  #define _SDI 14
  #define _nSEL 13
  #define _nIRQ 4
//*/  
  void initRFM()
  {
    //Inicializacion de pines 
    pinMode(_SCK, OUTPUT);
    pinMode(_SDO, INPUT);
    pinMode(_SDI, OUTPUT);
    pinMode(_nSEL, OUTPUT);
    pinMode(_nIRQ, INPUT);
  
    digitalWrite(_nSEL, HIGH);
    digitalWrite(_SDI, HIGH);
    digitalWrite(_SCK, LOW);
    
    writeCMD(0x0000);
    writeCMD(0x8205);  // DC (disable clk pin), enable lbd
    writeCMD(0xB800);  // in case we're still in OOK mode
    while (digitalRead(_nIRQ) == 0)writeCMD(0x0000);    
    
    writeCMD(0x80D7);//EL,EF,433band,12.0pF
    //writeCMD(0x80F7);//EL,EF,915band,12.0pF    
    writeCMD(0x82D9);//!er,!ebb,ET,ES,EX,!eb,!ew,DC
    writeCMD(0xA640);//A140=430.8MHz
    //writeCMD(0xA7D0);//2000=915.0MHz
    //writeCMD(0xC647);//4.8kbps
    writeCMD(0xC623);//9.579kbps
    //writeCMD(0x94A0);//VDI,FAST,134kHz,0dBm,-103dBm 
    writeCMD(0x94A2);//VDI,FAST,134kHz,0dBm,-91dBm 
    writeCMD(0xC2AC);//AL,!ml,DIG,DQD4
    writeCMD(0xCA80);//FIFO8,SYNC,
    writeCMD(0xCA83);//FIFO8,SYNC,
    writeCMD(0xC483); //@PWR,NO RSTRIC,!st,!fi,OE,EN
    writeCMD(0xCED4);//SYNC=2DD4;
    writeCMD(0x9850);//!mp,9810=30kHz,MAX OUT 
    writeCMD(0xCC77);//OB1,OB0,!lpx,!ddy,DDIT,BW0
    writeCMD(0xE000);//NOT USE 
    writeCMD(0xC800);//NOT USE 
    writeCMD(0xC040);//1.66MHz,2.2V    
    
    if(_nIRQ==2)
      attachInterrupt(0, getByte, LOW);
    else 
      attachInterrupt(1, getByte, LOW);
  }
  
  unsigned int writeCMD(unsigned int cmd)
  {
    byte x=0;
    digitalWrite(_SCK, LOW);
    digitalWrite(_nSEL, LOW);
    for(x=0;x<16;x++)
    {
      if(bitRead(cmd, 15))digitalWrite(_SDI, HIGH);
      else digitalWrite(_SDI, LOW);
      digitalWrite(_SCK, HIGH);
      //delayMicroseconds(1);
      cmd=cmd<<1;
      digitalWrite(_SCK, LOW);
    }
    digitalWrite(_nSEL, HIGH);
  }
  
  void writeByte( byte data ) 
  {
    byte rgit=0;
    unsigned int temp=0xB800;
    temp|=data;
    Loop: 
    digitalWrite(_SCK, LOW);
    digitalWrite(_nSEL, LOW);
    digitalWrite(_SDI, LOW);
    digitalWrite(_SCK, HIGH);
    if(digitalRead(_SDO)>0)//Polling SDO
    {
      rgit=1;
    } else
    {
      rgit=0;
    }
    digitalWrite(_SCK, LOW);
    digitalWrite(_SDI, HIGH);
    digitalWrite(_nSEL, HIGH);
    if(rgit==0)
    {
      goto Loop;
    } else
    {
       rgit=0;
       writeCMD(temp);
    }
  }
  
  byte readFIFO()
  {
    byte i, ret;
    digitalWrite(_SCK, LOW);
    digitalWrite(_SDI, LOW);
    digitalWrite(_nSEL, LOW);
    for(i=0;i<16;i++)
    {                     
      //skip status bits
      digitalWrite(_SCK, HIGH);
      //delayMicroseconds(1);
      digitalWrite(_SCK, LOW);
    }
    ret=0;
    for(i=0;i<8;i++)
    {
      //read fifo data byte
      ret=ret<<1; 
      if(digitalRead(_SDO))
      {
        ret|=1; 
      }
      digitalWrite(_SCK, HIGH);
      //delayMicroseconds(1);
      digitalWrite(_SCK, LOW);
    }
    digitalWrite(_nSEL, HIGH);
    return(ret);
  }
  
  byte RF_RXI=0;
  boolean RF_RXDATA=false;
  byte RF_RXBUF[64];
  
  void resetRXBuffer()
  {
        writeCMD(0xCA80);
        delayMicroseconds(1);
        writeCMD(0xCA83);
        //delayMicroseconds(1);
        RF_RXI=0;
  }  
  
  void getByte()
  {
      digitalWrite(LED, HIGH);    
      //Serial.println("getByte");
      RF_RXBUF[RF_RXI++]=readFIFO();
      if(RF_RXI>(RF_RXBUF[0]+1))
      {
        resetRXBuffer();
        unsigned int chksum=0;
        byte j=0;
        for(;j<RF_RXBUF[0];j++)
        {
          chksum+=RF_RXBUF[j+1]; //add 0x30-----0x3F
        }
        chksum&=0x0FF;
        if(chksum==RF_RXBUF[j+1])
        { 
          RF_RXDATA=true;
        }
        /*
        Serial.print(" recv NO: ");
        for(j=0;j<RF_RXBUF[0]+2;j++)
        {
          Serial.print(RF_RXBUF[j]);
          Serial.print(" ");
        }
        Serial.println();
        */
      }    
      digitalWrite(LED, LOW);              
  }  
  
  byte sendData(byte str[], byte len)
  {  
    digitalWrite(LED, HIGH);        
    while(RF_RXI>0)delayMicroseconds(1000);
    noInterrupts();
    //Serial.println("sendData ");    
    unsigned int chksum=0;
    byte ret=0;
    
    //writeCMD(0x82F9);
    //writeCMD(0x8229);
    //delayMicroseconds(4);
    writeCMD(0x8239);
    
    writeByte(0xAA);
    writeByte(0xAA);
    writeByte(0xAA);
    writeByte(0x2d);
    writeByte(0xd4);
    writeByte(len);
    for(byte x=0;x<len;x++)
    {
      writeByte(str[x]);
      chksum+=str[x];
    }
    chksum&=0xff;
    writeByte(chksum);
    writeByte(0xAA);
    writeByte(0xAA);
    writeCMD(0x82D9);
    //Serial.println("end send "); 
    interrupts();
    digitalWrite(LED, LOW);            
  }
  
  boolean readData()
  {
    if(RF_RXDATA)
    {
      RF_RXDATA=false;
      return true;
    }
    return false;
  }
  
  void setup()
  {
    
    pinMode(LED, OUTPUT);
    digitalWrite(LED, LOW);
    /*
    Serial.begin(9600);
    while (!Serial) {
      ; // wait for serial port to connect. Needed for Leonardo only
    }
    //*/
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);        
    delay(100);
    
    initRFM();   
    
    digitalWrite(LED, HIGH);
    delay(100);
    digitalWrite(LED, LOW);     
 
  }  
  
  
  byte data[]={10,11,12,13};
  void loop()
  { 
     //Serial.println("SendData:");
     sendData(data, sizeof(data));
     delay(500);
     if(readData())
     {  
        digitalWrite(LED, HIGH);
        delay(100);
        digitalWrite(LED, LOW);
        /*
        Serial.print("recv: ");
        for(byte j=0;j<RF_RXBUF[0];j++)
        {
          Serial.print(RF_RXBUF[j+1]);Serial.print(" ");
        }
        Serial.println();
        */
     }
  }

