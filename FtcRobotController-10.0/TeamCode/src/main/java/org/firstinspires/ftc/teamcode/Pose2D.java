package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Pose2D {
    private final double x;
    private final double y;
    private final double heading;

    public Pose2D(DistanceUnit distanceUnit, double x, double y, AngleUnit headingUnit, double heading) {
        this.x = distanceUnit.toMm(x);
        this.y = distanceUnit.toMm(y);
        this.heading = headingUnit.toRadians(heading);
    }

    public double getX(DistanceUnit unit) {
        return unit.fromMm(x);
    }

    public double getY(DistanceUnit unit) {
        return unit.fromMm(y);
    }

    public double getHeading(AngleUnit unit) {
        return unit.fromRadians(heading);
    }
}