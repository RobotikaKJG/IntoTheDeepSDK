package org.firstinspires.ftc.teamcode.HardwareInterface;

public class SlideLogic {


    private final MotorControl motorControl;
    private final SensorControl sensorControl;
    private final SlideControl slideControl;
    private int slideExtensionTarget = 0;
    private int prevSlideExtensionTarget = 0;
    private final SlideProperties slideProperties;
    private int slideMaxExtension;
    private int slideExtensionStep;

    public SlideLogic(SlideControl slideControl, MotorControl motorControl, SensorControl sensorControl, SlideProperties slideProperties) {
        this.motorControl = motorControl;
        this.sensorControl = sensorControl;
        this.slideControl = slideControl;
        this.slideProperties = slideProperties;
        setSlideProperties();
        resetEncoders();
    }

    public void setSlideProperties()
    {
        this.slideMaxExtension = slideProperties.getSlideMaxExtension();
        this.slideExtensionStep = slideProperties.getSlideExtensionStep();
    }

    public boolean slidesBottomReached() {
        if (!sensorControl.isLimitSwitchPressed())
            return false;
        motorControl.setMotorSpeed(MotorConstants.bothSlides, 0);
        motorControl.resetMotorEncoders(MotorConstants.bothSlides);
        return true;
    }

    public int getSlideExtensionTarget() {
        return slideExtensionTarget;
    }

    public void setSlideExtensionTarget(int slideExtensionTarget) {
        if (isExtensionTargetNotInBounds(slideExtensionTarget)) return;
        this.slideExtensionTarget = slideExtensionTarget;
        slideControl.setSlidePosition(slideExtensionTarget);
    }

    public void addSlideExtension(int addSlideExtension) {
        if (isExtensionTargetNotInBounds(slideExtensionTarget + addSlideExtension)) return;
        this.slideExtensionTarget += addSlideExtension;
        slideControl.setSlidePosition(slideExtensionTarget);
    }

    public void stepUp()
    {
        addSlideExtension(slideExtensionStep);
    }

    public void stepDown()
    {
        addSlideExtension(-slideExtensionStep);
    }

    public boolean isExtensionTargetNotInBounds(int slideExtensionTarget) {
        return slideExtensionTarget < 0 || slideExtensionTarget > slideMaxExtension;
    }

    public void resetEncoders() {
        slideControl.resetEncoders();
    }

    public void setMaxSpeed(double maxSpeed){
        slideProperties.setSlideMaxSpeed(maxSpeed);
    }
}