import serial
import struct


PORT = "/dev/ttyUSB0"

ser = serial.Serial(PORT, 9600, 8, "N", 1, timeout = None)
rxData = None

while rxData==None:
	ser.flushInput()
	rxData = ser.read(11)


ser.close()

print(rxData)
