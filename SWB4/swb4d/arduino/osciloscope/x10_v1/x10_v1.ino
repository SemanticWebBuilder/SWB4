int CERO=8;
int READ=12;
int WRITE=7;

int _cero=0;

void setup() {
  // initialize the serial communication:
  Serial.begin(9600);
}

void waitUntil(int c)
{
  while(c!=digitalRead(CERO));
}

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

int readBit()
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

void loop() {
  if(_cero==0)_cero=1;else _cero=0;
  //Serial.println(_cero);
  waitUntil(_cero);
  int data=_cero+(readBit()<<2);
  //int data=readBit();
  Serial.println(data);
}
