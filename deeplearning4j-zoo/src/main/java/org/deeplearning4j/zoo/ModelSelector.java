package org.deeplearning4j.zoo;

import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.zoo.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for selecting multiple models from the zoo.
 *
 * @author Justin Long (crockpotveggies)
 */
public class ModelSelector {

    public static Map<ZooType, ZooModel> select(ZooType zooType, int numLabels) {
        return select(zooType, numLabels, 123, 1, WorkspaceMode.SEPARATE);
    }

    public static Map<ZooType, ZooModel> select(ZooType zooType, int numLabels, WorkspaceMode workspaceMode) {
        return select(zooType, numLabels, 123, 1, workspaceMode);
    }

    public static Map<ZooType, ZooModel> select(ZooType zooType, int numLabels, int seed, int iterations) {
        return select(zooType, numLabels, 123, 1, WorkspaceMode.SEPARATE);
    }

    /**
     * Select multiple models from the zoo according to type.
     *
     * @param zooType
     * @param numLabels
     * @param seed
     * @param iterations
     * @return A hashmap of zoo types and models.
     */
    public static Map<ZooType, ZooModel> select(ZooType zooType, int numLabels, int seed, int iterations,
                    WorkspaceMode workspaceMode) {
        Map<ZooType, ZooModel> netmap = new HashMap<>();

        switch (zooType) {
            case ALL:
                netmap.putAll(ModelSelector.select(ZooType.CNN, numLabels, seed, iterations, workspaceMode));
                netmap.putAll(ModelSelector.select(ZooType.RNN, numLabels, seed, iterations, workspaceMode));
                break;
            // CNN models
            case CNN:
                netmap.putAll(ModelSelector.select(ZooType.ALEXNET, numLabels, seed, iterations, workspaceMode));
                netmap.putAll(ModelSelector.select(ZooType.LENET, numLabels, seed, iterations, workspaceMode));
                netmap.putAll(ModelSelector.select(ZooType.GOOGLENET, numLabels, seed, iterations, workspaceMode));
                netmap.putAll(ModelSelector.select(ZooType.RESNET50, numLabels, seed, iterations, workspaceMode));
                netmap.putAll(ModelSelector.select(ZooType.VGG16, numLabels, seed, iterations, workspaceMode));
                netmap.putAll(ModelSelector.select(ZooType.VGG19, numLabels, seed, iterations, workspaceMode));
                break;
            case ALEXNET:
                netmap.put(ZooType.ALEXNET, new AlexNet(numLabels, seed, iterations, workspaceMode));
                break;
            case LENET:
                netmap.put(ZooType.LENET, new LeNet(numLabels, seed, iterations, workspaceMode));
                break;
            case INCEPTIONRESNETV1:
                netmap.put(ZooType.INCEPTIONRESNETV1,
                                new InceptionResNetV1(numLabels, seed, iterations, workspaceMode));
                break;
            case FACENETNN4SMALL2:
                netmap.put(ZooType.FACENETNN4SMALL2, new FaceNetNN4Small2(numLabels, seed, iterations, workspaceMode));
                break;
            case GOOGLENET:
                netmap.put(ZooType.LENET, new GoogLeNet(numLabels, seed, iterations, workspaceMode));
                break;
            case RESNET50:
                netmap.put(ZooType.RESNET50, new ResNet50(numLabels, seed, iterations, workspaceMode));
                break;
            case VGG16:
                netmap.put(ZooType.VGG16, new VGG16(numLabels, seed, iterations, workspaceMode));
                break;
            case VGG19:
                netmap.put(ZooType.VGG16, new VGG19(numLabels, seed, iterations, workspaceMode));
                break;
            default:
                // do nothing
        }

        if (netmap.size() == 0)
            throw new IllegalArgumentException("Zero models have been selected for benchmarking.");

        return netmap;
    }
}
