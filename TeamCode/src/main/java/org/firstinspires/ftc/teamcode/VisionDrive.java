package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.holonomicdrive.DriveMotors;
import org.firstinspires.ftc.teamcode.holonomicdrive.HoloXDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.firstinspires.ftc.teamcode.opencv.*;

@TeleOp(name = "Controlled Vision", group = "Iterative Opmode")
public class VisionDrive extends OpMode {
    private DriveMotors motors;

    private HoloXDrive drive;
    private OpenCvCamera camera;

    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK, cameraMonitorViewId);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                camera.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);
                camera.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
                camera.setPipeline(new ColourFilterPipeline());
            }
        });

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
