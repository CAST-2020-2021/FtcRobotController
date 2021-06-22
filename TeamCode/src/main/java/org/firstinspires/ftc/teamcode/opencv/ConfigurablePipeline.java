package org.firstinspires.ftc.teamcode.opencv;

import android.util.Log;

import org.firstinspires.ftc.teamcode.lib.CircularLinkedList;
import org.firstinspires.ftc.teamcode.lib.Node;
import org.openftc.easyopencv.OpenCvPipeline;

import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Field;
import java.util.Objects;

public class ConfigurablePipeline {
    public OpenCvPipeline pipeline;
    private final boolean configurable;
    CircularLinkedList<Field> configurableElementsMap = new CircularLinkedList<>();
    private final String TAG = "ConfigurablePipeline";

    public ConfigurablePipeline(OpenCvPipeline pipeline) throws Exception {

        this.pipeline = pipeline;

        Class<?> clazz = pipeline.getClass();
        configurable = clazz.isAnnotationPresent(Configurable.class);

        if (configurable) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ConfigurableElement.class)) {
                    configurableElementsMap.addNode(field);
                    if (field.getType() != Double.class) {
                        throw new Exception("Configurable type not a double.");
                    }
                }
            }
        }
    }

    public void StepForwards() {
        if (!configurable) {
            return;
        }
        configurableElementsMap.stepForwards();

    }


    public void StepBackwards() {
        if (!configurable) {
            return;
        }
        configurableElementsMap.stepForwards();

    }

    public void SetConfigurationValue(double value) {
        if (!configurable) {
            return;
        }

        try {
            this.configurableElementsMap.GetHead().value.set(this.pipeline, value);
        } catch (Exception e) {
            Log.e(TAG,(e.getMessage() != null) ? e.getMessage() : "Could not set configuration value.");
        }

    }

    public double GetConfigurationValue() {
        if (!configurable) {
            return -1;
        }

        try {
            return this.configurableElementsMap.GetHead().value.getDouble(this.pipeline);
        } catch (Exception e) {
            Log.e(TAG,(e.getMessage() != null) ? e.getMessage() : "Could not get configuration value.");
        }

        return -1;
    }

    public String toString() {
        if (!configurable){return "";}

        Node<Field> currentNode = configurableElementsMap.head;
        StringBuilder output = new StringBuilder();

        do {
            try {
                currentNode = currentNode.nextNode;
                output.append(currentNode.value.getName()).append(": ").append(currentNode.value.getDouble(pipeline));
            } catch (Exception e) {
                Log.wtf(TAG,e.getMessage());
            }
        } while (currentNode != configurableElementsMap.head);
        output.append("(*)");
        return output.toString();
    }
}
