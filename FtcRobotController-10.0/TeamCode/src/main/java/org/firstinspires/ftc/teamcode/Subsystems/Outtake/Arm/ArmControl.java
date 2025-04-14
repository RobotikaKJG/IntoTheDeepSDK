package org.firstinspires.ftc.teamcode.Subsystems.Outtake.Arm;

import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoConstants;
import org.firstinspires.ftc.teamcode.HardwareInterface.Servo.ServoControl;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.Subsystems.Outtake.OuttakeStates;

public class ArmControl {
    private final ServoControl servoControl;
    private ArmStates prevArmState;

    public ArmControl(ServoControl servoControl) {
        this.servoControl = servoControl;
    }

    public void update() {
        if (prevArmState != OuttakeStates.getArmState()) {
            updateStates();
            prevArmState = OuttakeStates.getArmState();
        }
    }

    private void updateStates() {
        switch (OuttakeStates.getArmState()) {
            case up:
                up();
                break;
            case down:
                down();
                break;
            case drop:
                drop();
                break;
        }
    }


    private void up() {
//        CountDownLatch readyLatch = new CountDownLatch(1);
//
//        Thread rightServoThread = new Thread(() -> {
//            try {
//                readyLatch.await(); // wait for signal to start
//                servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMinPos);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        Thread leftServoThread = new Thread(() -> {
//            try {
//                readyLatch.await(); // wait for signal to start
//                servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });
//
//        // Start both threads (they'll wait at latch)
//        rightServoThread.start();
//        leftServoThread.start();
//
//        // Release both threads at the same time
//        readyLatch.countDown();
//
//
//        try {
//            rightServoThread.join();
//            leftServoThread.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoSamplePos);
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoSamplePos);
    }



    private void down() {
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMaxPos);
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMaxPos);
    }

    private void drop() {
        servoControl.setServoPos(ServoConstants.outtakeRight, OuttakeConstants.outtakeRightServoMinPos);
        servoControl.setServoPos(ServoConstants.outtakeLeft, OuttakeConstants.outtakeLeftServoMinPos);
    }
}
