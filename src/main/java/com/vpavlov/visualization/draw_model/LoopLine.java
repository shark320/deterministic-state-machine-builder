package com.vpavlov.visualization.draw_model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;

public class LoopLine extends ATitledLine {

    private class CoordinatesChangedListener implements ChangeListener<Number>{

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
            drawLoop();
            calculateTitle();
        }
    }

    private static final double LOOP_ANGLE = Math.PI/4;

    private static final double LOOP_STROKE_WIDTH = 1;

    private static final Color LOOP_STROKE_COLOR = Color.BLACK;

    private static final double LOOP_RADIUS = 20;

    private final DoubleProperty nodeX;

    private final DoubleProperty nodeY;

    private final DoubleProperty nodeRadius;

    private final CubicCurve cubicCurve = new CubicCurve();

    private boolean isOnTop = true;

    private double cxCenter;

    private double cyCenter;


    public LoopLine(MachineNode node, String title){
        super(title);
        this.nodeX = node.getCenterXProperty();
        this.nodeY = node.getCenterYProperty();
        this.nodeRadius = node.getRadiusProperty();

        CoordinatesChangedListener updater = new CoordinatesChangedListener();

        nodeX.addListener(updater);
        nodeY.addListener(updater);

        cubicCurve.setFill(null);
        cubicCurve.setStrokeWidth(LOOP_STROKE_WIDTH);
        cubicCurve.setStroke(LOOP_STROKE_COLOR);
        this.getChildren().add(cubicCurve);

        drawLoop();
        calculateTitle();
    }

    private void drawLoop(){
        double x = nodeX.getValue();
        double y = nodeY.getValue();
        double radius = nodeRadius.getValue();

        double sx,ex,cx1,cx2;
        double sey,cy;

        sx = x - radius*Math.sin(LOOP_ANGLE/2);
        ex = x + radius*Math.sin(LOOP_ANGLE/2);

        cx1 = sx - LOOP_RADIUS;
        cx2 = ex + LOOP_RADIUS;

        if (y-radius-LOOP_RADIUS*2 < 0){
            //loop oriented down
            isOnTop = false;
            cy = y+(radius+2*LOOP_RADIUS);
            sey = y + radius*Math.cos(LOOP_ANGLE/2);

        }else{
            //loop oriented up
            isOnTop = true;
            cy = y-(radius+2*LOOP_RADIUS);
            sey = y - radius*Math.cos(LOOP_ANGLE/2);
        }

        cxCenter = (cx1+cx2)/2;
        cyCenter = cy;

        cubicCurve.setStartX(sx);
        cubicCurve.setStartY(sey);
        cubicCurve.setControlX1(cx1);
        cubicCurve.setControlY1(cy);
        cubicCurve.setControlX2(cx2);
        cubicCurve.setControlY2(cy);
        cubicCurve.setEndX(ex);
        cubicCurve.setEndY(sey);
    }

    @Override
    protected void calculateTitle() {
        title.setX(cxCenter - title.getLayoutBounds().getWidth()/2);
        if (isOnTop){
            title.setY(cyCenter-TITLE_PADDING);
        }else{
            title.setY(cyCenter+TITLE_PADDING);
        }
    }
}
