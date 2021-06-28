package org.firstinspires.ftc.teamcode.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

@Configurable
public class ColourCountourPipeline extends OpenCvPipeline {

    // Colour filter Declarations
    Mat hsv = new Mat();
    Mat mask = new Mat();
    Mat colourMask = new Mat();

    @ConfigurableElement
    public double minHue = 120;

    @ConfigurableElement
    public double maxHue = 170;

    @ConfigurableElement
    public double minSat = 0;

    @ConfigurableElement
    public double maxSat = 255;

    @ConfigurableElement
    public double minVal = 0;

    @ConfigurableElement
    public double maxVal = 255;

    // Contour Declarations
    Mat grey = new Mat();
    Mat limited = new Mat();
    Mat hierarchy = new Mat();

    List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
    List<MatOfPoint> approxList = new ArrayList<MatOfPoint>();

    MatOfPoint2f approx = new MatOfPoint2f();

    @ConfigurableElement
    public double epsilonMod = 1 * Math.pow(10, -7);

    @Override
    public Mat processFrame(Mat input) {
        // Filter
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);
        Core.inRange(hsv, new Scalar(minHue, minSat, minVal), new Scalar(maxHue, maxSat, maxVal), mask);
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(3, 3)));
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(9, 9)));

        colourMask.release();
        colourMask = new Mat();
        Core.bitwise_and(input, input, colourMask, mask);

        // Contour
        Imgproc.cvtColor(colourMask, grey, Imgproc.COLOR_RGB2GRAY);
        Imgproc.threshold(grey, limited, 127, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C);

        cnts.clear();

        Imgproc.findContours(limited, cnts, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        approxList.clear();

        for (MatOfPoint temp : cnts) {
            // Convert contour to MapOfPoint2f
            MatOfPoint2f c = new MatOfPoint2f(temp.toArray());

            // Run approxPolyDP on each
            double epsilon = epsilonMod * Imgproc.arcLength(c, true);
            Imgproc.approxPolyDP(c, approx, epsilon, true);

            approxList.add(new MatOfPoint(approx.toArray()));
        }

        Imgproc.drawContours(input, approxList, -1, new Scalar(255, 255, 255), 3);

        Core.addWeighted(input, 0.8, colourMask, 0.2, 0, input);
        return input;
    }
}


