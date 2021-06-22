package org.firstinspires.ftc.teamcode.opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
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
    Mat hierarchy = new Mat();

    List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
    List<MatOfPoint> approxList = new ArrayList<MatOfPoint>();

    MatOfPoint approx = new MatOfPoint();

    public double epsilonMod = 1 * Math.pow(10,-7);

    @Override
    public Mat processFrame(Mat input)
    {
        Imgproc.cvtColor(input,grey, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(grey, limited,127,255, Imgproc.ADAPTIVE_THRESH_MEAN_C);


        Mat hierarchy = new Mat();
        Imgproc.findContours(limited, cnts, hierarchy, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);



        for (MatOfPoint temp : cnts) {
            // Convert contour to MapOfPoint2f
            MatOfPoint2f c = new MatOfPoint2f(temp.toArray());

            // Run approxPolyDP on each
            double epsilon = epsilonMod * Imgproc.arcLength(c,true);

            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(c,approx,epsilon,true);

            approxList.add(new MatOfPoint(approx.toArray()));
        }

        Imgproc.drawContours(input, approxList,-1 ,new Scalar(255,255,255),3);

        hierarchy.release();
        return input;

    }
}
