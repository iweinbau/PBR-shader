package display;

import Input.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;
    private static final int FPS_CAP = 120;

    private static long window;

    /**
     *
     * @return the current window.
     */
    public static long getWindow() {
        return window;
    }

    /**
     * This create a new display.
     */
    public static void createDisplay(String displayTitle){
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, displayTitle, NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();

        setupKeyCallBack();
    }

    private static void setupKeyCallBack(){
        //set up key callbacks for input
        glfwSetKeyCallback(Display.getWindow(), new Input());
        glfwSetCursorPosCallback(Display.getWindow(), new MouseCursor());
    }

    /**
     * This update the current display.
     */
    public static void updateDisplay(){
        glfwSwapBuffers(window); // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glViewport(	0, 0, Display.getWidth(), Display.getHeight());
        glfwPollEvents();
    }

    public static void setDisplaySize( int width, int height){
        glfwSetWindowSize(window, width, height);
    }

    public static void setDisplaySize(ScreenResolution resolution){
        glfwSetWindowSize(window, resolution.getWidth(), resolution.getHeight());
    }


    private static ScreenResolution getDisplaySize(){
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(window, w, h);
        int width = w.get(0);
        int height = h.get(0);
        return new ScreenResolution(width,height);
    }
    /**
     *
     * This close the current display.
     */
    public static void closeDisplay(){
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     *
     * @return Boolean whether the current display should close.
     */
    public static boolean isCloseRequest(){
        return glfwWindowShouldClose(window);
    }

    /**
     *
     * @return The width of the display.
     */
    public static int getWidth() {
        return getDisplaySize().getWidth();
    }

    /**
     *
     * @return The height of the display.
     */
    public static int getHeight() {
        return getDisplaySize().getHeight();
    }

    /**
     * @return return the FPS cap.
     */
    public static int getFpsCap() {
        return FPS_CAP;
    }

}
