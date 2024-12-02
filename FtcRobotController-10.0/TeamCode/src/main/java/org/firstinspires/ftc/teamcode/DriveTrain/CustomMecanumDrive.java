package org.firstinspires.ftc.teamcode.DriveTrain;

import com.acmerobotics.roadrunner.AccelConstraint;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.MecanumKinematics;
//import com.acmerobotics.roadrunner.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.ProfileParams;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilderParams;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.Arrays;
import java.util.List;

public class CustomMecanumDrive extends MecanumDrive {
    private final List<DcMotorEx> motors;
    private Pose2d poseEstimate = new Pose2d(0, 0, 0); // Default starting pose

    public CustomMecanumDrive(HardwareMap hardwareMap) {
        // Initialize MecanumDrive with the default pose
        super(hardwareMap, new Pose2d(0, 0, 0));

        // Initialize motors
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        motors = Arrays.asList(frontLeft, frontRight, backLeft, backRight);

        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        }

        // Reverse directions for the left-side motors if necessary
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
    }

    @Override
    public void setDrivePower(Pose2d drivePower) {
        // Extract x, y, and heading from drivePower
        double x = drivePower.position.x; // Get x from position
        double y = drivePower.position.y; // Get y from position
        double heading = drivePower.heading.toDouble(); // Convert Rotation2d to double

        // Calculate motor powers
        double frontLeftPower = x + y + heading;
        double frontRightPower = x - y - heading;
        double backLeftPower = x - y + heading;
        double backRightPower = x + y - heading;

        // Normalize motor powers
        double maxPower = Math.max(1.0, Math.max(
                Math.abs(frontLeftPower),
                Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)))));

        frontLeftPower /= maxPower;
        frontRightPower /= maxPower;
        backLeftPower /= maxPower;
        backRightPower /= maxPower;

        // Set motor powers
        motors.get(0).setPower(frontLeftPower);
        motors.get(1).setPower(frontRightPower);
        motors.get(2).setPower(backLeftPower);
        motors.get(3).setPower(backRightPower);
    }

    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                (double) motors.get(0).getCurrentPosition(),
                (double) motors.get(1).getCurrentPosition(),
                (double) motors.get(2).getCurrentPosition(),
                (double) motors.get(3).getCurrentPosition()
        );
    }

    /**
     * Sets the robot's pose estimate for localization.
     */
    public void setPoseEstimate(Pose2d pose) {
        this.poseEstimate = pose;
    }

    /**
     * Returns the current pose estimate.
     */
    public Pose2d getPoseEstimate() {
        return poseEstimate;
    }

    /**
     * Follows a trajectory and updates the pose estimate.
     */
    @Override
    public void followTrajectory(Trajectory trajectory) {
        // Calculate the total time of the trajectory
        double totalTime = trajectory.path.length() / 30.0; // Assuming 30 inches/second velocity

        // Initialize a timer
        ElapsedTime timer = new ElapsedTime();

        // Start the timer
        timer.reset();

        // Follow the trajectory in real-time
        while (timer.seconds() < totalTime) {
            double t = timer.seconds(); // Get elapsed time

            // Get the pose at the current time
            poseEstimate = trajectory.path.get(t, 0).value();

            // Simulate motor control here (use calculated poseEstimate)
            System.out.println("Current pose: " + poseEstimate);

            // Simulate a short delay for real-time trajectory following
            try {
                Thread.sleep(10); // 10ms delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Final pose (optional, to verify end point)
        poseEstimate = trajectory.path.get(trajectory.path.length(), 0).value();
        System.out.println("Final pose: " + poseEstimate);
    }

    public VelConstraint getVelocityConstraint(double maxVel, double maxAngularVel, double trackWidth) {
        return new MinVelConstraint(Arrays.asList(
                new AngularVelConstraint(maxAngularVel),
                new MecanumKinematics(trackWidth).new WheelVelConstraint(maxVel)
        ));
    }


    public AccelConstraint getAccelerationConstraint(double maxAccel) {
        return new ProfileAccelConstraint(-maxAccel, maxAccel);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose) {
        return new TrajectoryBuilder(
                new TrajectoryBuilderParams(1e-6, new ProfileParams(0.25, 0.1, 1e-2)),
                startPose,
                0.0,
                getVelocityConstraint(30, Math.toRadians(180), 15),
                getAccelerationConstraint(30)
        );
    }


    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, boolean reversed) {
        return trajectoryBuilder(startPose).setReversed(reversed); // Use method chaining to set reversed
    }


}
