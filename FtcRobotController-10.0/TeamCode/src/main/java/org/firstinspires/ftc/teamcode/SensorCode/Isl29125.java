package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.I2cWaitControl;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;


@I2cDeviceType
@DeviceProperties(name = "Isl29125 color sensor", description = "Renesas Isl29125 color sensor", xmlTag = "Isl29125")
public class Isl29125 extends I2cDeviceSynchDevice<I2cDeviceSynch> //implements ColorSensor
{
    @Override
    public Manufacturer getManufacturer() {

        return Manufacturer.Other;
    }

    @Override
    protected synchronized boolean doInitialize() {
        System.out.println("doInitialize");
        //byte readValue = readByte(Isl29125Register.CONFIGURATION_1);
        byte configInfo = (byte) 0b00010101;
        System.out.printf("configInfo value: %02X%n", configInfo);

        writeByte(Isl29125Register.CONFIGURATION_1, configInfo);
        byte readValue = readByte(Isl29125Register.CONFIGURATION_1);
        System.out.println("Configuration 1 register value: " + Integer.toBinaryString(readValue));
        System.out.println("I2C address: " + deviceClient.getI2cAddress());
        return true;
    }

    @Override
    public String getDeviceName() {

        return "Renesas Isl29125 color sensor";
    }

    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x44);


    protected void writeShort(final Isl29125Register reg, short value) {
        deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(value));
    }

    protected void writeByte(final Isl29125Register reg, byte value) {
        System.out.println("WriteByte");
        System.out.printf("Writing to register %02X, value: %02X%n", reg.bVal, value);
        this.deviceClient.write8(reg.bVal, value, I2cWaitControl.NONE);
    }

    protected short readShort(Isl29125Register reg) {
        return TypeConversion.byteArrayToShort(deviceClient.read(reg.bVal, 2));
    }

    protected byte readByte(Isl29125Register reg) {
        return this.deviceClient.read8(reg.bVal);
    }

    public int getManufacturerIDRaw() {
        int address = ADDRESS_I2C_DEFAULT.get7Bit();
        return readByte(Isl29125Register.DEVICE_ID);
    }

    public int getRed() {
        byte highByte = readByte(Isl29125Register.RED_DATA_HIGH_BYTE);
        int highValue = Byte.toUnsignedInt(highByte);
        byte lowByte = readByte(Isl29125Register.RED_DATA_LOW_BYTE);
        int lowValue = Byte.toUnsignedInt(lowByte);

        return highValue * 256 + lowValue;
    }

    public int getGreen() {
        byte highByte = readByte(Isl29125Register.GREEN_DATA_HIGH_BYTE);
        int highValue = Byte.toUnsignedInt(highByte);
        byte lowByte = readByte(Isl29125Register.GREEN_DATA_LOW_BYTE);
        int lowValue = Byte.toUnsignedInt(lowByte);

        return highValue * 256 + lowValue;
    }

    public int getBlue() {
        byte highByte = readByte(Isl29125Register.BLUE_DATA_HIGH_BYTE);
        int highValue = Byte.toUnsignedInt(highByte);
        byte lowByte = readByte(Isl29125Register.BLUE_DATA_LOW_BYTE);
        int lowValue = Byte.toUnsignedInt(lowByte);

        return highValue * 256 + lowValue;
    }


    public Isl29125(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {

        super(deviceClient, deviceClientIsOwned);

        this.setOptimalReadWindow();

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false); // Deals with USB cables getting unplugged
        // Sensor starts off disengaged so we can change things like I2C address. Need to engage

        this.deviceClient.engage();

    }

    private I2cDeviceSynch.ReadWindow readWindow;


    protected void setOptimalReadWindow() {
        // Sensor registers are read repeatedly and stored in a register. This method specifies the
        // registers and repeat read mode
        readWindow = new I2cDeviceSynch.ReadWindow(
                Isl29125Register.FIRST.bVal,
                Isl29125Register.LAST.bVal - Isl29125Register.FIRST.bVal + 1,
                I2cDeviceSynch.ReadMode.REPEAT);
        this.deviceClient.setReadWindow(readWindow);
    }


}
