//package org.firstinspires.ftc.teamcode.Subsystems.Outtake;
//
//import org.firstinspires.ftc.teamcode.Control.MotorConstants;
//import org.firstinspires.ftc.teamcode.Control.MotorControl;
//import org.firstinspires.ftc.teamcode.Control.SensorControl;
//import org.firstinspires.ftc.teamcode.Control.ServoConstants;
//import org.firstinspires.ftc.teamcode.Control.ServoControl;
//import org.firstinspires.ftc.teamcode.Control.StateSwitchLogic;
//import org.firstinspires.ftc.teamcode.Enums.ReleaseServoState;
//
//public class OldOuttake {
//
//    private ReleaseServoState releaseState = ReleaseServoState.openSecond; //start with both open
//    private final MotorControl motorControl;
//    private final ServoControl servoControl;
//    private final SensorControl sensorControl;
//    private int slideExtensionTarget;
//    private int prevSlideExtensionTarget;
//
//    public OldOuttake(MotorControl motorControl, ServoControl servoControl, SensorControl sensorControl)
//    {
//        this.motorControl = motorControl;
//        this.servoControl = servoControl;
//        this.sensorControl = sensorControl;
//    }
//
//    public void servo(StateSwitchLogic.StateSwitch stateSwitch)
//    {
//        switch (stateSwitch) {
//            case downOn:
//                // Servo starts at max position, which is fully lowered
//                servoControl.increasePos(ServoConstants.outtake);
//                break;
//
//            case upOn:
//                //decreasing the position raises the servo, as default state is maxPos
//                servoControl.decreasePos(ServoConstants.outtake);
//                break;
//        }
//    }
//
////    public void releaseServo(boolean risingEdge)
////    {
////        if(!risingEdge)
////            return;
////
////        iterateReleaseServoState();
////        releaseServoControl(releaseState);
////
////    }
//
//    public void controlReleaseServo()
//    {
//        iterateReleaseServoState();
//        releaseServoControl(releaseState);
//
//    }
//
//    //move to the next servo state
//    public void iterateReleaseServoState()
//    {
//        switch (releaseState) {
//            case openFirst: // closed, releases first one
//                releaseState = ReleaseServoState.openSecond;
//                break;
//
//            case openSecond: // first one open, releases second one
//                releaseState = ReleaseServoState.closeBoth;
//                break;
//
//            case closeBoth: // closes both
//                releaseState = ReleaseServoState.openFirst;
//                break;
//        }
//    }
//
//    // state variable needed due to intake stopping servo close logic
//    public void releaseServoControl(ReleaseServoState state)
//    {
//        switch (state) {
//            case openFirst: // closed, releases first one
//                servoControl.decreasePos(ServoConstants.release1);
//                break;
//
//            case openSecond: // first one open, releases second one
//                servoControl.decreasePos(ServoConstants.release2);
//                break;
//
//            case closeBoth: // closes both
//                servoControl.increasePos(ServoConstants.release1);
//                servoControl.increasePos(ServoConstants.release2);
//                break;
//        }
//    }
//
//
//    private Boolean slideBoundsLogic()
//    {
//        slidesBottomReached();
//        return slidesTopReached();
//    }
//
//    //Should be extracted to "OuttakeAutomation" ?
////    public void toggleableSlides(Boolean upRisingEdge, Boolean downRisingEdge)
////    {
////        if(slideBoundsLogic())
////            return;
////
////        if (upRisingEdge)
////            upToggle();
////        else if (downRisingEdge)
////            downToggle();
////        servoLogic();
////        moveSlidesTarget(slideExtensionTarget);
////    }
//
////    private void upToggle()
////    {
////        if(slideBoundsLogic())
////            return;
////
////        if(slideExtensionTarget < OuttakeConstants.slideCurrentMinExtension)
////        {
////            slideExtensionTarget = OuttakeConstants.slideCurrentMinExtension;
////        }
////        else
////            slideExtensionTarget += OuttakeConstants.slideExtensionStep;
////    }
////
////    private void downToggle()
////    {
////        if(slideBoundsLogic())
////            return;
////
////        if(slideExtensionTarget <= OuttakeConstants.slideCurrentMinExtension) {
////            slideExtensionTarget = 0;
////        } else
////            slideExtensionTarget -= OuttakeConstants.slideExtensionStep;
////    }
////
////    private void servoLogic()
////    {
////        if(prevSlideExtensionTarget == slideExtensionTarget) return;
////
////        if (slideExtensionTarget < OuttakeConstants.slideCurrentMinExtension)
////            servo(StateSwitchLogic.StateSwitch.downOn);
////        else servo(StateSwitchLogic.StateSwitch.upOn);
////
////        prevSlideExtensionTarget = slideExtensionTarget;
////    }
//
//    private Boolean slidesTopReached() {
//        if (motorControl.getMotorPosition(MotorConstants.bothSlides) > OuttakeConstants.slideMaxExtension) {
//            motorControl.setMotorSpeed(MotorConstants.bothSlides, 0);
//            slideExtensionTarget = OuttakeConstants.slideMaxExtension;
//            return true;
//        }
//        return false;
//    }
//
//    private void slidesBottomReached() {
//        if(sensorControl.isLimitSwitchPressed())
//        {
//            motorControl.setMotorSpeed(MotorConstants.bothSlides,0);
//            motorControl.resetMotorEncoders(MotorConstants.bothSlides);
//        }
//    }
//
//    public void moveSlidesTarget()
//    {
//        moveSlidesTarget(slideExtensionTarget);
//    }
//    public void moveSlidesTarget(int targetPPR)
//    {
//        // There is no such thing as too many checks for slide safety
//        if(slideBoundsLogic())
//            return;
//
//        int currentPosition = motorControl.getMotorPosition(MotorConstants.bothSlides);
//        int positionDifference = Math.abs(targetPPR - currentPosition);
//
//
//        if (positionDifference > OuttakeConstants.slideHoldThreshold && !sensorControl.isLimitSwitchPressed()) {
//            moveSlides(targetPPR);
//        } else {
//            holdSlidesToggle(targetPPR);
//        }
//        motorControl.limitSpeed(MotorConstants.bothSlides, OuttakeConstants.slideMovementMaxSpeed);
//    }
//
//    private void holdSlidesToggle(int target)
//    {
//        holdSlidesControl(target);
//    }
//
//    private void moveSlides(int targetPos) //slide movement proportional using extension avg
//    {
//        int error = targetPos - motorControl.getMotorPosition(MotorConstants.bothSlides);
//        double control = error * OuttakeConstants.SKP;
//        //if going down and below slideMinSpeedThreshold
//        control = retractionControlLogic(control);
//
//        setMoveSlidesMotorSpeed(control);
//    }
//
//    void setMoveSlidesMotorSpeed(double control)
//    {
//        motorControl.setMotorSpeed(MotorConstants.slideLeft,-control);
//        motorControl.setMotorSpeed(MotorConstants.slideRight,control);
//    }
//
////    public void closeSlides(Boolean closeRisingEdge)
////    {
////        if (!closeRisingEdge) return;
////        slideExtensionTarget = 0;
////        servo(StateSwitchLogic.StateSwitch.downOn);
////    }
//
//    private double retractionControlLogic (double control)
//    {
//        // set speed to min speed below a certain threshold to be as an extra safeguard from breaking the servo
//        if(isInMinSpeedThreshold() && control < 0)
//            control = -OuttakeConstants.slideRetractionMinSpeed;
//        return control;
//    }
//
//    boolean isInMinSpeedThreshold()
//        {
//        return motorControl.getMotorPosition(MotorConstants.bothSlides) < OuttakeConstants.slideMinSpeedThreshold;
//    }
//
//    private void holdSlidesControl(int targetPos)
//    {
//        int leftError = targetPos - motorControl.getMotorPosition(MotorConstants.slideLeft);
//        int rightError = targetPos - motorControl.getMotorPosition(MotorConstants.slideRight);
//        double leftControl = leftError * OuttakeConstants.SHKP;
//        double rightControl = rightError * OuttakeConstants.SHKP;
//        motorControl.setMotorSpeed(MotorConstants.slideLeft,-leftControl);
//        motorControl.setMotorSpeed(MotorConstants.slideRight,rightControl);
//    }
//
//    public int getSlideExtensionTarget() {
//        return slideExtensionTarget;
//    }
//
//    public void setSlideExtensionTarget(int slideExtensionTarget) {
//        if(isExtensionTargetInBounds(slideExtensionTarget)) return;
//        this.slideExtensionTarget = slideExtensionTarget;
//    }
//
//    public void addSlideExtension(int addSlideExtension) {
//        if(isExtensionTargetInBounds(slideExtensionTarget + addSlideExtension)) return;
//        slideExtensionTarget += addSlideExtension;
//    }
//
//    public boolean isExtensionTargetInBounds(int slideExtensionTarget)
//    {
//        return slideExtensionTarget >= 0 && slideExtensionTarget <= OuttakeConstants.slideMaxExtension;
//    }
//
////    public void setReleaseState(ReleaseServoState releaseState) {
////        this.releaseState = releaseState;
////    }
//}