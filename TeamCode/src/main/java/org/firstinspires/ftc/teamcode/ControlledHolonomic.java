package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.holonomicdrive.DriveMotors;
import org.firstinspires.ftc.teamcode.holonomicdrive.HoloXDrive;

@TeleOp(name = "Controlled Dev", group = "Iterative Opmode")
public class ControlledHolonomic extends OpMode {
    private DriveMotors motors;

    private HoloXDrive drive;

    private DcMotor shooterTilt;
    private DcMotor shooterFire;

    @Override
    public void init() {
        motors = new DriveMotors(
                hardwareMap.get(DcMotor.class, "frontLeft"),
                hardwareMap.get(DcMotor.class, "frontRight"),
                hardwareMap.get(DcMotor.class, "backLeft"),
                hardwareMap.get(DcMotor.class, "backRight")
        );

        drive = new HoloXDrive(motors);

        shooterTilt = hardwareMap.get(DcMotor.class, "shooterTilt");
        shooterTilt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shooterFire = hardwareMap.get(DcMotor.class, "shooterFire");
    }

    @Override
    public void loop() {
        drive.drive(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.left_bumper,
                gamepad1.right_bumper
        );

        double shooterTiltPower = (gamepad2.left_bumper ? 1 : (gamepad2.right_bumper ? -1: 0)) * 0.5;

        shooterTiltPower = shooterTiltPower == 0 ? 0.1 : shooterTiltPower;

        telemetry.addData("tiltPower", shooterTiltPower);
        shooterTilt.setPower(shooterTiltPower);

        // Fire up shooter
        if (gamepad2.a) {
            shooterFire.setPower(1);
        }
        else {
            shooterFire.setPower(0);
        }


        telemetry.update();
    }
}
