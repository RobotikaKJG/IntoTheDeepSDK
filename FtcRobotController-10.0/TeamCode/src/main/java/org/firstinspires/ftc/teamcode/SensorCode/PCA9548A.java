package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;


@I2cDeviceType
@DeviceProperties(name = "PCA9548A Multiplexer", description = "PCA9548A I2C Multiplexer", xmlTag = "PCA9548A")
public class PCA9548A extends I2cDeviceSynchDevice<I2cDeviceSynch> //implements ColorSensor
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

        return "PCA9548A I2C Multiplexer";
    }

    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x70);


    protected byte readByte(TCS3472Register reg) {
        return this.deviceClient.read8(reg.bVal);
    }

    public void writeNoAddress(byte value) {
        System.out.println("Value:" + value);
        deviceClient.write8(value);
        short read = TypeConversion.byteArrayToShort(deviceClient.read(value, 2));
        System.out.println("Read:" + read);
    }


    public PCA9548A(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {

        super(deviceClient, deviceClientIsOwned);

        //this.setOptimalReadWindow();

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false); // Deals with USB cables getting unplugged
        // Sensor starts off disengaged so we can change things like I2C address. Need to engage

        this.deviceClient.engage();

    }
}
