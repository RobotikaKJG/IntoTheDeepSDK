package org.firstinspires.ftc.teamcode.SensorCode;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;


@I2cDeviceType
@DeviceProperties(name = "VL53L1X ToF Distance Sensor", description = "STMicroelectronics VL53L1X Time-of-Flight Distance Sensor", xmlTag = "VL53L1X")
public class VL53L1X extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    @Override
    public Manufacturer getManufacturer() {

        return Manufacturer.STMicroelectronics;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {

        return "STMicroelectronics VL53L1X Time-of-Flight Distance Sensor";
    }

    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create7bit(0x52);


    protected void writeShort(final VL53L1XRegister reg, short value) {
        deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(value));
    }

    protected short readShort(VL53L1XRegister reg) {
        return TypeConversion.byteArrayToShort(deviceClient.read(reg.bVal, 2));
    }

    // public int getManufacturerIDRaw()
    // {
    //     return readShort(VL53L1XRegister.VL53L1_IDENTIFICATION__MODEL_ID);

    // }

    //@Override
    // public byte read8(int ireg) {
    //     return deviceClient.read(ireg, 1)[0];
    // }

    public VL53L1X(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {


        super(deviceClient, deviceClientIsOwned);
        //this.setOptimalReadWindow();
        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();

    }


}
