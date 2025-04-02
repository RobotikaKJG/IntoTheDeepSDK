package org.firstinspires.ftc.teamcode.HardwareInterface.Motor;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorUtilities {

    public int getMotorPosition(DcMotor[] motors, int index) {
        int sum = 0;
        for (int i = 0; i < configLength(index); i++)
            sum += getPos(motors, index, i);
        return avg(sum, configLength(index));
    }


    //extracted for readability
    public int configLength(int index) {
        return MotorConstants.motorConfig[index].length;
    }

    public int motorIndex(int index, int i) {
        return MotorConstants.motorConfig[index][i];
    }

    //more readable version instead of doing it directly in straightAvgCalc
    private int getPos(DcMotor[] motors, int index, int i) {
        int motorIndex = motorIndex(index, i);
        return motors[motorIndex].getCurrentPosition();
    }



    public int avg(int sum, int count) {
        return sum / count;
    }

    public void resetMotorEncoders(DcMotor[] motors, int index) {
        for (int i = 0; i < configLength(index); i++) {
            int motorIndex = motorIndex(index, i);
            motors[motorIndex].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors[motorIndex].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * @noinspection unused
     */
    public double setMinValue(double value, double limit) {
        if (Math.abs(value) < limit)
            value = Math.signum(value) * limit;
        return value;
    }
}