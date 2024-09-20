package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class AutonomousConstants {

    public static double propPixelPlaceWait()
    {
        switch (GlobalVariables.alliance) {
            case Red:
                switch (GlobalVariables.propPos) {
                    case left:
                        return 1.45;
                    case front:
                        return 2;
                    case right:
                        return 3;
                }
            case Blue:
                switch (GlobalVariables.propPos) {
                    case left:
                        return 3;
                    case front:
                        return 2;
                    case right:
                        return 1.45;
                }
        }
        return 0.0;
    }

    public static final double activateIntakeWait = 1.5;
    public static final double pixelStackWait = 0.3;//1.3; // only needed until color sensors are implemented
    public static final double pixelStackPushoutWait = 0.8;//0.4;
    public static final double whitePixelPlaceWait = 0.5;//1;
    public static final double yellowPixelPlaceWait = 1.05;//1;
    public static final double closeSlidesWait = 0.5;
    public static final double whitePixelsPlaceWait = 0.7;
    public static final double slideOpenWait = 3.0;
    public static final double intakeSecondStartWait = 2.7;


    public static final double maxPixelPlacementSpeed = 30;
    public static final double maxParkSpeed = 15;
    public static final double maxPixelTakeSpeed = 20;
    public static final double maxStackHitSpeed = 30;

    public static final double maxAngularPixelPlacementSpeed = Math.toRadians(250);

    public static final int audienceSideWhitePixelExtension = 950;
    public static final int audienceSideYellowPixelExtension = 1400;
    public static final int audienceSideSecondCycleExtension = 1550;
}