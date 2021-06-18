package org.firstinspires.ftc.teamcode.opencv;

import android.os.strictmode.ImplicitDirectBootViolation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.imgproc.Imgproc;

public class ColourFilterPipeline extends OpenCvPipeline {
    // Notice this is declared as an instance variable (and re-used), not a local variable
    Mat hsv = new Mat();
    Mat mask = new Mat();
    Mat colourMask = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsv, new Scalar(70, 27, 15), new Scalar(160, 100, 100), mask);
        colourMask.release();
        colourMask = new Mat();
        Core.bitwise_and(input, input, colourMask, mask);
        return colourMask;
    }
}