/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.EventListener;

/**
 * A generic listener for any object that changes (for ready state in this project)
 * @author Harlan Sang
 */
public interface IObjectListener extends EventListener {
  void update(Object source);
}
