package org.firstinspires.ftc.teamcode.opencv;

import android.os.strictmode.ImplicitDirectBootViolation;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class EdgeDetectPipeline extends OpenCvPipeline
{
    // Notice this is declared as an instance variable (and re-used), not a local variable
    Mat grey = new Mat();

    @Override
    public Mat processFrame(Mat input)
    {
        Mat edges = new Mat();
        Imgproc.Canny(input, edges, 100, 200);
        return edges;
    }
}
