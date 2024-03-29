package com.vpavlov.visualization.machineBuilder.handlers;

import com.vpavlov.visualization.draw_model.MachineNode;
import com.vpavlov.visualization.machineBuilder.controller.MachineBuilderController;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 * Mouse click handler for machine builder
 *
 * @author vpavlov
 */
public class MouseClickHandler implements EventHandler<MouseEvent> {

    /**
     * Machine builder window controller
     */
    private final MachineBuilderController controller;

    /**
     * Constructor
     *
     * @param controller machine builder window controller
     */
    public MouseClickHandler(MachineBuilderController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        controller.getCanvasPane().requestFocus();
        if (!controller.isDrag) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            Point2D point = new Point2D(x, y);
            switch (mouseEvent.getButton()) {
                case PRIMARY -> {
                    MachineNode node = controller.getClickedMachineNode(point);
                    if (node != null) {
                        controller.selectNode(node);
                    } else {
                        controller.createNode(point);
                    }
                }
            }
        } else {
            controller.isDrag = false;
        }
        mouseEvent.consume();
    }
}
