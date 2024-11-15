package gay.menkissing.skisca.examples.lwjgl

import java.nio.IntBuffer
import java.util._
import org.lwjgl.glfw._
import org.lwjgl.opengl._
import org.lwjgl.system.MemoryStack
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.system.MemoryUtil._
import gay.menkissing.skisca._
import gay.menkissing.skisca.examples.scenes._
import gay.menkissing.skisca.impl._
import io.github.humbleui.types._

object Main {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    GLFWErrorCallback.createPrint(System.err).set
    if (!glfwInit) throw new RuntimeException("Unable to initialize GLFW")
    val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor)
    val width = (vidmode.width * 0.75).asInstanceOf[Int]
    val height = (vidmode.height * 0.75).asInstanceOf[Int]
    val bounds = IRect
      .makeXYWH(Math.max(0, (vidmode.width - width) / 2), Math.max(0, (vidmode.height - height) / 2), width, height)
    new Window().run(bounds)
  }
}

class Window {
  var window = 0L
  var width = 0
  var height = 0
  var dpi = 1f
  var xpos = 0
  var ypos = 0
  var vsync = true
  var stats = true
  private var refreshRates: Array[Int] = null
  private val os = System.getProperty("os.name").toLowerCase

  private def getRefreshRates = {
    val monitors = glfwGetMonitors
    val res = new Array[Int](monitors.capacity)
    var i = 0
    while (i < monitors.capacity) {
      res(i) = glfwGetVideoMode(monitors.get(i)).refreshRate
      i += 1
    }
    res
  }

  def run(bounds: IRect): Unit = {
    refreshRates = getRefreshRates
    createWindow(bounds)
    loop()
    glfwFreeCallbacks(window)
    glfwDestroyWindow(window)
    glfwTerminate
    glfwSetErrorCallback(null).free
  }

  private def updateDimensions(): Unit = {
    val width = new Array[Int](1)
    val height = new Array[Int](1)
    glfwGetFramebufferSize(window, width, height)
    val xscale = new Array[Float](1)
    val yscale = new Array[Float](1)
    glfwGetWindowContentScale(window, xscale, yscale)
    assert(xscale(0) == yscale(0), "Horizontal dpi=" + xscale(0) + ", vertical dpi=" + yscale(0))
    this.width = (width(0) / xscale(0)).toInt
    this.height = (height(0) / yscale(0)).toInt
    this.dpi = xscale(0)
    System.out.println("FramebufferSize " + width(0) + "x" + height(0) + ", scale " + this.dpi + ", window " + this
      .width + "x" + this.height)
  }

  private def createWindow(bounds: IRect): Unit = {
    glfwDefaultWindowHints() // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable
    window = glfwCreateWindow(bounds.getWidth, bounds.getHeight, "Skija LWJGL Demo", NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window")
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) => {
      if ((key eq GLFW_KEY_ESCAPE) && (action eq GLFW_RELEASE)) glfwSetWindowShouldClose(window, true)
    })
    glfwSetWindowPos(window, bounds.getLeft, bounds.getTop)
    updateDimensions()
    xpos = width / 2
    ypos = height / 2
    glfwMakeContextCurrent(window)
    glfwSwapInterval(if (vsync) {
      1
    } else {
      0
    }) // Enable v-sync
    glfwShowWindow(window)
  }

  private var context: DirectContext = null
  private var renderTarget: BackendRenderTarget = null
  private var surface: Surface = null
  private var canvas: Canvas = null

  private def initSkia(): Unit = {
    Stats.enabled = true
    if (surface != null) surface.close
    if (renderTarget != null) renderTarget.close
    renderTarget = BackendRenderTarget
      .makeGL((width * dpi).toInt, (height * dpi).toInt, /*samples*/ 0, /*stencil*/ 8, /*fbId*/ 0, FramebufferFormat
        .GR_GL_RGBA8)
    surface = Surface.makeFromBackendRenderTarget(context, renderTarget, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat
      .RGBA_8888, ColorSpace.getDisplayP3, // TODO load monitor profile
      SurfaceProps(pixelGeometry = PixelGeometry.RGB_H))
    canvas = surface.getCanvas
  }

  private def draw(): Unit = {
    Scenes.draw(canvas, width, height, dpi, xpos, ypos)
    context.flush
    glfwSwapBuffers(window)
  }

  private def loop(): Unit = {
    GL.createCapabilities
    if ("false".equals(System.getProperty("skija.staticLoad"))) Library.load()
    context = DirectContext.makeGL
    GLFW.glfwSetWindowSizeCallback(window, (window, width, height) => {
      updateDimensions()
      initSkia()
      draw()
    })
    glfwSetCursorPosCallback(window, (window, xpos, ypos) => {
      if (os.contains("mac") || os.contains("darwin")) {
        this.xpos = xpos.asInstanceOf[Int]
        this.ypos = ypos.asInstanceOf[Int]
      }
      else {
        this.xpos = (xpos / dpi).toInt
        this.ypos = (ypos / dpi).toInt
      }
    })
    glfwSetMouseButtonCallback(window, (window, button, action, mods) => {
    })
    glfwSetScrollCallback(window, (window, xoffset, yoffset) => {
      Scenes.currentScene.onScroll(xoffset.asInstanceOf[Float] * dpi, yoffset.asInstanceOf[Float] * dpi)
    })
    glfwSetKeyCallback(window, (window, key, scancode, action, mods) => {
      if (action eq GLFW_PRESS) {
        key match {
          case GLFW_KEY_LEFT => {
            Scenes.prevScene
          }
          case GLFW_KEY_RIGHT => {
            Scenes.nextScene
          }
          case GLFW_KEY_UP => {
            Scenes.currentScene.changeVariant(-1)
          }
          case GLFW_KEY_DOWN => {
            Scenes.currentScene.changeVariant(1)
          }
          case GLFW_KEY_V => {
            vsync = !vsync
            glfwSwapInterval(if (vsync) {
              1
            } else {
              0
            })
            HUD.extras.update(0, new Pair("V", "VSync: " + (if (vsync) {
              "ON"
            } else {
              "OFF"
            })))
          }
          case GLFW_KEY_S => {
            Scenes.stats = !Scenes.stats
            Stats.enabled = Scenes.stats
          }
          case GLFW_KEY_G => {
            System.out.println("Before GC " + Stats.allocated)
            System.gc()
          }
          case _ => ()
        }
      }
    })
    HUD.extras.append(new Pair("V", "VSync: " + (if (vsync) {
      "ON"
    } else {
      "OFF"
    })))
    initSkia()
    while (!glfwWindowShouldClose(window)) {
      draw()
      glfwPollEvents()
    }
  }
}