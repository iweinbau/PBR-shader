package Input;

import display.Display;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;

public class MouseCursor extends GLFWCursorPosCallback {

    double newX = Display.getWidth()/2;
    double newY = Display.getHeight()/2;

    double prevX = 0;
    double prevY = 0;

    static double deltaX;
    static double deltaY;

    static boolean rotX = false;
    static boolean rotY = false;
    static boolean mouseIsLocked = true;

    public MouseCursor() {

    }

    @Override
    public void invoke(long window, double xpos, double ypos) {

        if(mouseIsLocked) {
            newX = xpos;
            newY = ypos;

            deltaX = newX - Display.getWidth() / 2;
            deltaY = newY - Display.getHeight() / 2;

            rotX = newX != prevX;
            rotY = newY != prevY;

            prevX = newX;
            prevY = newY;

            glfwSetCursorPos(Display.getWindow(), Display.getWidth() / 2, Display.getHeight() / 2);
        }else{
            newX = xpos;
            newY = ypos;
            deltaX = 0;
            deltaY = 0;
            rotX = false;
            rotY = false;
        }
    }

    public static double getDeltaX() {
        double tmp = deltaX;
        deltaX = 0;
        return tmp;
    }

    public static double getDeltaY() {
        double tmp = deltaY;
        deltaY = 0;
        return tmp;
    }

    public static boolean isRotX() {
        return rotX;
    }

    public static boolean isRotY() {
        return rotY;
    }

    public static boolean isMouseIsLocked() {
        return mouseIsLocked;
    }

    public static void setMouseIsLocked(boolean bool) {
        mouseIsLocked = bool;
    }

    public static void disableMouseCursor(){
        glfwSetInputMode(Display.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public static void normalMouseCursor(){
        glfwSetInputMode(Display.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public static void hideMouseCursor(){
        glfwSetInputMode(Display.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }


}
