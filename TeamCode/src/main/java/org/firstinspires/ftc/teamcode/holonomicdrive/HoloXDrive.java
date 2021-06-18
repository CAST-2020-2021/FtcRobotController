package org.firstinspires.ftc.teamcode.holonomicdrive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.CommonMethods;
import org.firstinspires.ftc.teamcode.PIDController;
import org.firstinspires.ftc.teamcode.holonomicdrive.DriveMotors;

import static org.firstinspires.ftc.teamcode.CommonMethods.*;

public class HoloXDrive {
    private DriveMotors motors;

    private double frontLeftPower, frontRightPower, backLeftPower, backRightPower, max;
    // For PID
    private boolean pidMode = false;
    private BNO055IMU imu;
    private PIDController pid;

    public float turnSpeed = 0.5f;
    public float drivePower = 0.5f;
    private boolean lastTurning = false;
    private boolean turning = false;
    private float correction = 0;
    public Telemetry telemetry;

    public HoloXDrive(DriveMotors motors) {
        this.motors = motors;
        this.motors.frontLeft.setDirection(DcMotor.Direction.FORWARD);
        this.motors.frontRight.setDirection(DcMotor.Direction.REVERSE);
        this.motors.backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        this.motors.backRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public HoloXDrive(DriveMotors motors, BNO055IMU imu, PIDController pid) {
        this.pidMode = true;
        this.imu = imu;
        this.pid = pid;
    }

    public void drive(double x, double y, boolean right, boolean left) {
        frontLeftPower = y - x;
        frontRightPower = y + x;
        backLeftPower =  y + x;
        backRightPower = y - x;

        if (left) {
            turning = true;
            frontLeftPower += -turnSpeed;
            frontRightPower += turnSpeed;
            backLeftPower += -turnSpeed;
            backRightPower += turnSpeed;
        } else if (right) {
            turning = true;
            frontLeftPower += turnSpeed;
            frontRightPower += -turnSpeed;
            backLeftPower += turnSpeed;
            backRightPower += -turnSpeed;
        } else {
            turning = false;
        }

        // if not turning and wasn't turning before
        // use pid if pid needs to be used

        double max = Math.abs(findMax(frontLeftPower, frontRightPower, backLeftPower, backRightPower));

        if (max != 0) {
            frontLeftPower = (frontLeftPower / max) * drivePower;
            frontRightPower = (frontRightPower / max) * drivePower;
            backLeftPower = (backLeftPower / max) * drivePower;
            backRightPower = (backRightPower / max) * drivePower;
        }

        if (!pidMode) {
            correction = 0;
        }

        if (turning) {
            backLeftPower = backLeftPower / drivePower;
            backRightPower = backRightPower / drivePower;
            frontLeftPower = frontLeftPower / drivePower;
            frontRightPower = frontRightPower / drivePower;
        }

        motors.frontLeft.setPower(frontLeftPower + correction);
        motors.frontRight.setPower(frontRightPower - correction);
        motors.backLeft.setPower(backLeftPower + correction);
        motors.backRight.setPower(backRightPower - correction);

        lastTurning = turning;

        if (telemetry != null) {
            telemetry.addData("Front Motor", " left:(%.2f) right:(%.2f)", frontLeftPower, frontRightPower);
            telemetry.addData("Back Motor", " left:(%.2f) right:(%.2f)", backLeftPower, backRightPower);
        }
    }
}
