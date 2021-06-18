package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.holonomicdrive.DriveMotors;
import org.firstinspires.ftc.teamcode.holonomicdrive.HoloXDrive;

@TeleOp(name = "Controlled Vision", group = "Iterative Opmode")
public class VisionDrive extends OpMode {
    private DriveMotors motors;

    private HoloXDrive drive;

    @Override
    public void init() {
        motors = new DriveMotors(
                hardwareMap.get(DcMotor.class, "frontLeft"),
                hardwareMap.get(DcMotor.class, "frontRight"),
                hardwareMap.get(DcMotor.class, "backLeft"),
                hardwareMap.get(DcMotor.class, "backRight")
        );

        drive = new HoloXDrive(motors);
    }

    @Override
    public void loop() {
        drive.drive(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.left_bumper,
                gamepad1.right_bumper
        );
        telemetry.update();
    }
}
