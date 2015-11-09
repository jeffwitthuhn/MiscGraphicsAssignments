/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;

/**
 *
 * @author jsart
 */
public class StarMorph {

    static public class MyFrameMorph extends JFrame implements IObjectListener {

        private static final double RANGE_FROM_ORIGIN = 10;
        private static final int INITIAL_X_SIZE = 500;
        private static final int INITIAL_Y_SIZE = 500;
        private final StarMorphInputPanel inputPanel;
        private StarMorphOutputPanel outputPanel;
        private final CoordinateMap2D map;
		private final int totalSteps;

        public MyFrameMorph(int s) {
            super();
            setSize(INITIAL_X_SIZE, INITIAL_Y_SIZE);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            map = new IsotropicCoordinateMap2D(-RANGE_FROM_ORIGIN, RANGE_FROM_ORIGIN, -RANGE_FROM_ORIGIN, RANGE_FROM_ORIGIN);

			totalSteps = s;
            inputPanel = new StarMorphInputPanel(map);
            inputPanel.setReadyListener(this);

            add(inputPanel);
        }

        @Override
        public void update(Object source) {
            Class<?> type = source.getClass();
            if (type == StarMorphInputPanel.class) {
                getContentPane().removeAll();
                outputPanel = new StarMorphOutputPanel(Morphing.calcMorph(
                        inputPanel.getOriginalShape(),
                        inputPanel.getMorphShape(),
                        inputPanel.getOriginalCenter(),
                        inputPanel.getMorphCenter()), // ADT HashMap
                        inputPanel.getOriginalCenter(),//source center
                        inputPanel.getMorphCenter(),//target center
                        totalSteps, // total steps
                        map);                           // Coordinate Map
                add(outputPanel);
                validate();
                repaint();

            }
        }
    }

    public static void main(String[] args) {
		final int steps;
		
		if(args.length == 0)
			steps = 120;
		else
			steps = Integer.parseInt(args[0]);
		
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrameMorph(steps).setVisible(true);
            }
        });

    }

}
