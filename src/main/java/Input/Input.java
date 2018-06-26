package Input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input extends GLFWKeyCallback{

    public static int[] keys = new int[65536];

    // The GLFWKeyCallback class is an abstract method that
    // can't be instantiated by itself and must instead be extended
    //
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        keys[key] = action;
    }
    /**
     *
     * @param keycode
     * @return A boolean if the given key is down.
     */
    public static boolean isKeyPressed(int keycode) {
        boolean check = false;
        if (keys[keycode] == GLFW_PRESS) {
            check = true;
            keys[keycode] = -1;
        }
        return check;
    }
    public static boolean isKeyDown(int keycode) {
        boolean check = false;
        if (keys[keycode] == GLFW_REPEAT || keys[keycode] == GLFW_PRESS || keys[keycode] == GLFW_REPEAT) {
            check = true;
        }
        return check;
    }
    public static boolean isKeyRelease(int keycode) {
        boolean check = false;
        if (keys[keycode] == GLFW_RELEASE) {
            check = true;
            keys[keycode] = -1;
        }
        return check;
    }

}
