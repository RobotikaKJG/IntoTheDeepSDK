package org.firstinspires.ftc.teamcode.HardwareInterface.Slide;

import org.firstinspires.ftc.teamcode.Subsystems.Intake.IntakeStates;

public class SlideLogic {


    private final SlideControl slideControl;
    private int slideExtensionTarget = 0;
    private final SlideProperties slideProperties;
    private int slideMaxExtension;
    private int slideExtensionStep;

    public SlideLogic(SlideControl slideControl, SlideProperties slideProperties) {
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
        if (!slideControl.isLimitSwitchPressed()) return false;
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
        IntakeStates.extensionTarget = this.slideExtensionTarget;
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
    public int getSlidePosition() {
        return slideControl.getSlidePosition();
    }
}