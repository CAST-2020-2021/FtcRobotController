package org.firstinspires.ftc.teamcode.holonomicdrive;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveMotors {
    public DcMotor frontLeft, frontRight, backLeft, backRight;

    public DriveMotors(DcMotor frontLeft, DcMotor frontRight, DcMotor backLeft, DcMotor backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }
}
