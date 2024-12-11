package org.firstinspires.ftc.teamcode.HardwareInterface;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorControl {

    private static class MotorNames {
        public static final String frontLeft = "frontLeftMotor";
        public static final String backLeft = "backLeftMotor";
        public static final String frontRight = "frontRightMotor";
        public static final String backRight = "backRightMotor";
        public static final String intake = "intakeMotor";
        public static final String slideLeft = "slideLeftMotor";
        public static final String slideRight = "slideRightMotor";
        public static final String extendo = "extendoMotor";
    }

    private final HardwareMap hardwareMap;
    private DcMotor[] motors;
    private final Utilities utilities = new Utilities();


    private final double[] motorSpeeds = new double[8];

    public MotorControl(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        getMotors();
    }

    private void getMotors() {
        motors = new DcMotor[]{
                hardwareMap.dcMotor.get(MotorNames.frontLeft),
                hardwareMap.dcMotor.get(MotorNames.backLeft),
                hardwareMap.dcMotor.get(MotorNames.frontRight),
                hardwareMap.dcMotor.get(MotorNames.backRight),
                hardwareMap.dcMotor.get(MotorNames.intake),
                hardwareMap.dcMotor.get(MotorNames.slideLeft),
                hardwareMap.dcMotor.get(MotorNames.slideRight),
                hardwareMap.dcMotor.get(MotorNames.extendo),
        };

        motors[MotorConstants.frontLeft].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[MotorConstants.backLeft].setDirection(DcMotorSimple.Direction.REVERSE);
        motors[MotorConstants.slideLeft].setDirection(DcMotorSimple.Direction.REVERSE);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        setMotorMode(MotorConstants.all, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorMode(MotorConstants.all, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        for (DcMotor i : motors)
            i.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void setMotorSpeed(int index, double speed) {
        for (int i = 0; i < Utilities.configLength(index); i++)
            motorSpeeds[Utilities.motorIndex(index, i)] = speed;
    }


    public void addMotorSpeed(int index, double speed) {
        for (int i = 0; i < Utilities.configLength(index); i++)
            motorSpeeds[Utilities.motorIndex(index, i)] += speed;
    }

    public void multiplyMotorSpeed(int index, double multiplier) {
        for (int i = 0; i < Utilities.configLength(index); i++)
            motorSpeeds[Utilities.motorIndex(index, i)] *= multiplier;
    }

    public void divideMotorSpeed(int index, double divisor) {
        for (int i = 0; i < Utilities.configLength(index); i++)
            motorSpeeds[Utilities.motorIndex(index, i)] /= divisor;
    }

    public void setMotors()
    {
        setMotors(MotorConstants.all);
    }

    //when setting specific combinations is needed
    public void setMotors(int index) {
        for (int i = 0; i < Utilities.configLength(index); i++)
            motors[Utilities.motorIndex(index, i)].setPower(motorSpeeds[Utilities.motorIndex(index, i)]);
    }

    public void limitSpeed(int index, double maxSpeed) {
        double max = utilities.getMaxDouble(motorSpeeds, org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants.motorConfig[index].length);
        if (Math.abs(max) > maxSpeed)
            for (int i = 0; i < org.firstinspires.ftc.teamcode.HardwareInterface.MotorConstants.motorConfig[index].length; i++) {
                motorSpeeds[Utilities.motorIndex(index, i)] /= Math.abs(max);
                motorSpeeds[Utilities.motorIndex(index, i)] *= maxSpeed;
            }
    }

    public void setMotorMode(int index, DcMotor.RunMode mode)
    {
        for (int i = 0; i < Utilities.configLength(index); i++)
            motors[Utilities.motorIndex(index, i)].setMode(mode);
    }

    public void setMotorPos(int index, int position){
        for (int i = 0; i < Utilities.configLength(index); i++)
            motors[Utilities.motorIndex(index, i)].setTargetPosition(position);
    }

    /**
     * @noinspection unused
     */
    public void resetMotors() {
        for (DcMotor i : motors)
            i.setPower(0);
    }

    public int getMotorPosition(int index) {
        return Utilities.getMotorPosition(motors, index);
    }

    public void resetMotorEncoders(int index) {
        utilities.resetMotorEncoders(motors, index);
    }
}
