package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.HardwareInterface.EdgeDetection;
import org.firstinspires.ftc.teamcode.Enums.Alliance;
import org.firstinspires.ftc.teamcode.Enums.GamepadIndexValues;
import org.firstinspires.ftc.teamcode.Enums.StartPos;
import org.firstinspires.ftc.teamcode.Main.GlobalVariables;

public class SelectStartVariables {
    Gamepad gamepad1;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad prevGamepad1 = new Gamepad();
    Telemetry telemetry;
    EdgeDetection edgeDetection;
    private boolean risingTriangleEdge;
    private boolean risingSquareEdge;

    public SelectStartVariables(Gamepad gamepad1, Telemetry telemetry) {
        this.gamepad1 = gamepad1;
        this.telemetry = telemetry;
        currentGamepad1.copy(this.gamepad1);
        edgeDetection = new EdgeDetection();
        selectAlliance();
        //selectStartPos();
    }

    private void selectAlliance() {
        while (!risingTriangleEdge && !risingSquareEdge) {
            calculateGamepadValues();

            telemetry.addLine("Press triangle for RED, press square for BLUE");
            telemetry.update();
            if (risingTriangleEdge)
                GlobalVariables.alliance = Alliance.Red;
            if (risingSquareEdge)
                GlobalVariables.alliance = Alliance.Blue;

        }
    }

    private void selectStartPos() {
        do {
            calculateGamepadValues();

            telemetry.addLine("Press triangle for BACKSIDE, press square for AUDIENCE SIDE");
            telemetry.update();
            if (risingTriangleEdge)
                GlobalVariables.startPos = StartPos.Backside;
            if (risingSquareEdge)
                GlobalVariables.startPos = StartPos.AudienceSide;
        } while (!risingTriangleEdge && !risingSquareEdge);
    }

    private void calculateGamepadValues() {
        prevGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        edgeDetection.refreshGamepadIndex(gamepad1, prevGamepad1);
        risingTriangleEdge = edgeDetection.rising(GamepadIndexValues.triangle);
        risingSquareEdge = edgeDetection.rising(GamepadIndexValues.square);
    }
}
