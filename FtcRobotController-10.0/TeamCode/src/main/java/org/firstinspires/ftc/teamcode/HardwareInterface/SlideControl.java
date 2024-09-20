package org.firstinspires.ftc.teamcode.HardwareInterface;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class SlideControl {


    private final MotorControl motorControl;
    private final SensorControl sensorControl;
    private int slideExtensionTarget;
    private int currentLeftMotorPosition;
    private int currentRightMotorPosition;
    private int currentMotorPositionAvg;
    private double control;
    private double slideKP;
    private double slideHoldKP;
    private int slideMaxExtension;
    private double slideMovementMaxSpeed;
    private int slideHoldThreshold;
    private int slideEncoderMaxSeparation;

    public SlideControl(MotorControl motorControl, SensorControl sensorControl) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
    }

    public void setSlideProperties(SlideProperties slideProperties)
    {
        this.slideKP = slideProperties.getSlideKP();
        this.slideHoldKP = slideProperties.getSlideHoldKP();
        this.slideMaxExtension = slideProperties.getSlideMaxExtension();
        this.slideMovementMaxSpeed = slideProperties.getSlideMovementMaxSpeed();
        this.slideHoldThreshold = slideProperties.getSlideHoldThreshold();
        this.slideEncoderMaxSeparation = slideProperties.getSlideEncoderMaxSeparation();
    }

    public void getMotorPos()
    {
       currentLeftMotorPosition = motorControl.getMotorPosition(MotorConstants.slideLeft);
       currentRightMotorPosition = motorControl.getMotorPosition(MotorConstants.slideRight);
       currentMotorPositionAvg = (currentLeftMotorPosition + currentRightMotorPosition)/2;
    }

    private boolean slideBoundsLogic() {
        slidesBottomReached();
        if(encoderIssues())
            return true;
        return slidesTopReached();
    }

    private boolean slidesTopReached() {
        if (currentMotorPositionAvg <= slideMaxExtension)
            return false;

        motorControl.setMotorSpeed(MotorConstants.bothSlides, 0);
        slideExtensionTarget = slideMaxExtension;
        return true;
    }

    public boolean slidesBottomReached() {
        if (!sensorControl.isLimitSwitchPressed())
            return false;
        motorControl.setMotorSpeed(MotorConstants.bothSlides, 0);
        motorControl.resetMotorEncoders(MotorConstants.bothSlides);
        return true;
    }

    private boolean encoderIssues(){
        int slidePositionDifference = Math.abs(currentLeftMotorPosition - currentRightMotorPosition);
        if(slidePositionDifference > slideEncoderMaxSeparation) {
            GlobalVariables.emergencyMode = true;
            return true;
        }
        return false;
    }

    public void updateSlides() {
        getMotorPos();
        moveSlidesTarget(slideExtensionTarget);
    }

    public void moveSlidesTarget(int targetPPR) {
        // There is no such thing as too many checks for slide safety
        if (slideBoundsLogic())
            return;

        selectMovementType(targetPPR);
    }

    private void selectMovementType(int targetPPR) {
        int positionDifference = Math.abs(targetPPR - currentMotorPositionAvg);

        if (positionDifference > slideHoldThreshold){// && !sensorControl.isLimitSwitchPressed()) {
            moveSlides(targetPPR);
        } else {
            holdSlidesControl(targetPPR);
        }
        motorControl.limitSpeed(MotorConstants.bothSlides, slideMovementMaxSpeed);
    }

    private void moveSlides(int targetPos) //slide movement proportional using extension avg
    {
        int error = targetPos - currentMotorPositionAvg;
        control = error * slideKP;

        setMoveSlidesMotorSpeed(control);
    }

    void setMoveSlidesMotorSpeed(double control) {
        motorControl.setMotorSpeed(MotorConstants.slideLeft, -control);
        motorControl.setMotorSpeed(MotorConstants.slideRight, control);
    }

    private void holdSlidesControl(int targetPos) {
        int leftError = targetPos - currentLeftMotorPosition;
        int rightError = targetPos - currentRightMotorPosition;
        double leftControl = leftError * slideHoldKP;
        double rightControl = rightError * slideHoldKP;
        motorControl.setMotorSpeed(MotorConstants.slideLeft, -leftControl);
        motorControl.setMotorSpeed(MotorConstants.slideRight, rightControl);
    }

    public int getSlideExtensionTarget() {
        return slideExtensionTarget;
    }

    public void setSlideExtensionTarget(int slideExtensionTarget) {
        if (isExtensionTargetNotInBounds(slideExtensionTarget)) return;
        this.slideExtensionTarget = slideExtensionTarget;
    }

    public void addSlideExtension(int addSlideExtension) {
        if (isExtensionTargetNotInBounds(slideExtensionTarget + addSlideExtension)) return;
        slideExtensionTarget += addSlideExtension;
    }

    public boolean isExtensionTargetNotInBounds(int slideExtensionTarget) {
        return slideExtensionTarget < 0 || slideExtensionTarget > slideMaxExtension;
    }

    public int getCurrentMotorPositionAvg() {
        return currentMotorPositionAvg;
    }

    public double getSpeed() {
        return control;
    }
}