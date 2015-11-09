import java.util.EventListener;

/**
 * A generic listener for any object that changes (for ready state in this project)
 * @author Harlan Sang
 */
public interface IObjectListener extends EventListener {
  void update(Object source);
}
