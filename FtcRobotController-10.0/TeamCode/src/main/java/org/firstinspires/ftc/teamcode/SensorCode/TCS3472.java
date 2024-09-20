package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;


@I2cDeviceType
@DeviceProperties(name = "TCS3472 color sensor", description = "Taos TCS3472 color sensor", xmlTag = "TCS3472")
public class TCS3472 extends I2cDeviceSynchDevice<I2cDeviceSynch> //implements ColorSensor
{
    @Override
    public Manufacturer getManufacturer() {

        return Manufacturer.Other;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {

        return "Taos TCS3472 color sensor";
    }

    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x29);


    protected void writeShort(final TCS3472Register reg, short value) {
        deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(value));
    }

    protected void writeByte(TCS3472Register reg, byte value) {
        System.out.println("WriteByte");
        System.out.printf("Writing to register %02X, value: %02X%n", reg.bVal, value);
        this.deviceClient.write8(reg.bVal, value);
    }

    protected short readShort(TCS3472Register reg) {
        return TypeConversion.byteArrayToShort(deviceClient.read(reg.bVal, 2));
    }

    protected byte readByte(TCS3472Register reg) {
        return this.deviceClient.read8(reg.bVal);
    }

    public int getManufacturerIDRaw() {
        return readByte(TCS3472Register.ID);
    }

    public short getRed() {
//        byte highByte = readByte(TCS3472Register.RDATAH);
//        int highValue = Byte.toUnsignedInt(highByte);
//        byte lowByte = readByte(TCS3472Register.RDATAL);
//        int lowValue = Byte.toUnsignedInt(lowByte);
        return readShort(TCS3472Register.RDATAH);
        //return TypeConversion.byteArrayToShort(deviceClient.read(TCS3472Register.RDATAH.bVal, 2));
//        return highValue*256 + lowValue;
    }

    public int getGreen() {
//        byte highByte = readByte(TCS3472Register.GDATAH);
//        int highValue = Byte.toUnsignedInt(highByte);
//        byte lowByte = readByte(TCS3472Register.GDATAL);
//        int lowValue = Byte.toUnsignedInt(lowByte);
//
//        return highValue*256 + lowValue;
        return readShort(TCS3472Register.GDATAH);
    }

    public int getBlue() {
//        byte highByte = readByte(TCS3472Register.BDATAH);
//        int highValue = Byte.toUnsignedInt(highByte);
//        byte lowByte = readByte(TCS3472Register.BDATAL);
//        int lowValue = Byte.toUnsignedInt(lowByte);
//
//        return highValue*256 + lowValue;
        return readShort(TCS3472Register.BDATAH);
    }

    public Boolean isColor(int targetR, int targetG, int targetB, int accuracy) {
        int deltaR = Math.abs(getRed() - targetR);
        int deltaG = Math.abs(getGreen() - targetG);
        int deltaB = Math.abs(getBlue() - targetB);

        return (deltaR <= accuracy) && (deltaG <= accuracy) && (deltaB <= accuracy);
    }


    public TCS3472(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {

        super(deviceClient, deviceClientIsOwned);

        //this.setOptimalReadWindow();
        this.writeByte(TCS3472Register.ENABLE, (byte) 0x3);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false); // Deals with USB cables getting unplugged
        // Sensor starts off disengaged so we can change things like I2C address. Need to engage

        this.deviceClient.engage();

    }

    //private I2cDeviceSynch.ReadWindow readWindow;


//    protected void setOptimalReadWindow()
//    {
//        // Sensor registers are read repeatedly and stored in a register. This method specifies the
//        // registers and repeat read mode
//        readWindow = new I2cDeviceSynch.ReadWindow(
//                TCS3472Register.FIRST.bVal,
//                TCS3472Register.LAST.bVal - TCS3472Register.FIRST.bVal + 1,
//                I2cDeviceSynch.ReadMode.REPEAT);
//        this.deviceClient.setReadWindow(readWindow);
//    }


}
