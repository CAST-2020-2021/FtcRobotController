package org.firstinspires.ftc.teamcode.opencv;

import android.os.strictmode.ImplicitDirectBootViolation;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.imgproc.Imgproc;

@Configurable
public class ColourFilterPipeline extends OpenCvPipeline {
    // Notice this is declared as an instance variable (and re-used), not a local variable
    Mat hsv = new Mat();
    Mat mask = new Mat();
    Mat colourMask = new Mat();

    @ConfigurableElement
    public double hue1 = 70;

    @ConfigurableElement
    public double hue2 = 70;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsv, new Scalar(hue1, 27, 15), new Scalar(hue2, 100, 100), mask);
        colourMask.release();
        colourMask = new Mat();
        Core.bitwise_and(input, input, colourMask, mask);
        return colourMask;
    }
}