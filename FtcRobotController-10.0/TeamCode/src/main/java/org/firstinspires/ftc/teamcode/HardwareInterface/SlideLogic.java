package org.firstinspires.ftc.teamcode.HardwareInterface;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class SlideLogic {


    private final MotorControl motorControl;
    private final SensorControl sensorControl;
    private final SlideControl slideControl;
    private int slideExtensionTarget;
    private int currentMotorPositionAvg;
    private final SlideProperties slideProperties;
    private double control;
    private double slideKP;
    private double slideHoldKP;
    private int slideMaxExtension;
    private int slideHoldThreshold;

    public SlideLogic(SlideControl slideControl, MotorControl motorControl, SensorControl sensorControl, SlideProperties slideProperties) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
        this.slideControl = slideControl;
        this.slideProperties = slideProperties;
    }

    public void setSlideProperties()
    {
        this.slideKP = slideProperties.getSlideKP();
        this.slideHoldKP = slideProperties.getSlideHoldKP();
        this.slideMaxExtension = slideProperties.getSlideMaxExtension();
        this.slideHoldThreshold = slideProperties.getSlideHoldThreshold();
    }

    public void getMotorPos()
    {
       currentMotorPositionAvg = slideControl.getSlidePosition();
    }

    private boolean slideBoundsLogic() {
        slidesBottomReached();
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
        slideControl.limitSpeed(slideProperties.getSlideMovementMaxSpeed()); // separate call as it is dynamic
    }

    private void moveSlides(int targetPos) //slide movement proportional using extension avg
    {
        int error = targetPos - currentMotorPositionAvg;
        control = error * slideKP;

        slideControl.setSlides(control);
    }

    private void holdSlidesControl(int targetPos) {
        int error = targetPos - currentMotorPositionAvg;;
        double control = error * slideHoldKP;
        slideControl.setSlides(control);
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