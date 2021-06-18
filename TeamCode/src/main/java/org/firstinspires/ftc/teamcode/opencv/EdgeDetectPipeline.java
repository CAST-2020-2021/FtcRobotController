package org.firstinspires.ftc.teamcode.opencv;

import android.os.strictmode.ImplicitDirectBootViolation;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class EdgeDetectPipeline extends OpenCvPipeline
{
    Mat edges = new Mat();

    @Override
    public Mat processFrame(Mat input)
    {
        Imgproc.Canny(input, edges, 100, 200);
        return edges;
    }
}
