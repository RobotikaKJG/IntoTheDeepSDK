package org.firstinspires.ftc.teamcode.Subsystems.Intake.Old.OldNew;

public enum IntakeStateCombinations { //kein juzen dis
    //extendo idle:
    retractedIntaking, //motor and servo forward
    retractedExtraEjecting, //motor backward, servo idle
    retractedAllEjecting, //motor and servo backward
    retractedSampleSecuring, //motor idle, servo forward
    retractedSampleSecuringWhileEjecting, //motor backward, servo forward

    //extendo active
    extendedIntaking, //motor and servo forward
    extendedExtraEjecting, //motor backward, servo idle
    extendedAllEjecting, //motor and servo backward
    extendedSampleSecuring, //motor idle, servo forward
    extendedSampleSecuringWhileEjecting, //motor backward, servo forward

    idle //all idle
}
