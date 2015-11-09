import javax.swing.JFrame;


public class StarMorphFrame extends JFrame implements IObjectListener {
  private static final long serialVersionUID = 1L;
  private static final double RANGE_FROM_ORIGIN = 10;
  private static final int INITIAL_X_SIZE = 500;
  private static final int INITIAL_Y_SIZE = 500;
  
  public static void main(String[] args) {
    StarMorphFrame frame = new StarMorphFrame();
  }
  
  private StarMorphInputPanel inputPanel;
  
  public StarMorphFrame() {
    super();
    
    CoordinateMap2D map = new IsotropicCoordinateMap2D
      (-RANGE_FROM_ORIGIN, RANGE_FROM_ORIGIN, -RANGE_FROM_ORIGIN, RANGE_FROM_ORIGIN);
    
    inputPanel = new StarMorphInputPanel(map);
    inputPanel.setReadyListener(this);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(inputPanel);
    setSize(INITIAL_X_SIZE, INITIAL_Y_SIZE);
    setVisible(true);
  }

  @Override
  public void update(Object source) {
    Class<?> type = source.getClass();
    if(type == StarMorphInputPanel.class) {
      // Call critical point calculator ADT
    }
  }
}
