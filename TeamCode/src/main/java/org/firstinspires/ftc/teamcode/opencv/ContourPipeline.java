package org.firstinspires.ftc.teamcode.opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ContourPipeline extends OpenCvPipeline
{
    // Notice this is declared as an instance variable (and re-used), not a local variable
    Mat grey = new Mat();
    Mat limited = new Mat();


    @Override
    public Mat processFrame(Mat input)
    {
        Imgproc.cvtColor(input,grey, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(grey, limited,127,255, Imgproc.ADAPTIVE_THRESH_MEAN_C);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(limited, contours, hierarchy, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(input, contours,-1 ,new Scalar(255,255,255),3);
        hierarchy.release();
        return input;

    }
}
