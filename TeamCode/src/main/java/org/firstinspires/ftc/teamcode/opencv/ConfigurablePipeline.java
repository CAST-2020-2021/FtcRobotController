package org.firstinspires.ftc.teamcode.opencv;

import org.firstinspires.ftc.teamcode.lib.CircularLinkedList;
import org.firstinspires.ftc.teamcode.lib.Node;
import org.openftc.easyopencv.OpenCvPipeline;

import java.lang.reflect.Field;

public class ConfigurablePipeline {
    public OpenCvPipeline pipeline;
    private boolean configurable;
    CircularLinkedList<Field> configurableElementsMap = new CircularLinkedList<>();

    public ConfigurablePipeline(OpenCvPipeline pipeline) {
        this.pipeline = pipeline;

        Class<?> clazz = pipeline.getClass();
        configurable = clazz.isAnnotationPresent(Configurable.class);

        if (configurable) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(ConfigurableElement.class)) {
                    configurableElementsMap.addNode(field);

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
            int a = 1;
        }

    }

    public double GetConfigurationValue() {
        if (!configurable) {
            return -1;
        }

        try {
            return this.configurableElementsMap.GetHead().value.getDouble(this.pipeline);
        } catch (Exception e) {
            int a = 1;
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
                int a = 1;
            }
        } while (currentNode != configurableElementsMap.head);
        output.append("(*)");
        return output.toString();
    }


}
