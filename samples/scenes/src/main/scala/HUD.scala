package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import gay.menkissing.skisca.impl.*
import io.github.humbleui.types.*
import scala.jdk.CollectionConverters.{given, *}
import scala.util.boundary

import java.util.*

object HUD {
  var extras = collection.mutable.Buffer[Pair[String, String]]()
}

class HUD {
  var t0: Long = System.nanoTime
  var times = new Array[Double](155)
  var timesIdx = 0

  def tick(): Unit = {
    val t1 = System.nanoTime
    times(timesIdx) = (t1 - t0) / 1000000.0
    t0 = t1
    timesIdx = (timesIdx + 1) % times.length
  }

  def log(): Unit = {
    if (timesIdx % 100 == 0) {
      System.out.println(String
        .format("%.0f fps", 1000.0 / java.util.Arrays.stream(times).takeWhile((t: Double) => t > 0).average.getAsDouble))
    }
  }

  def draw(canvas: Canvas, scene: Scene, width: Int, height: Int): Unit = {
    val nativeCalls = Stats.nativeCalls
    Stats.nativeCalls = 0
    val allocated = Stats.allocated.values.stream.reduce(0, Integer.sum)
    val bg = Paint().setColor(0x90000000)
    val fg = Paint().setColor(0xFFFFFFFF)
    val graph = Paint().setColor(0xFF00FF00).setStrokeWidth(1)
    val graphPast = Paint().setColor(0x9000FF00).setStrokeWidth(1)
    val graphLimit = Paint().setColor(0xFFcc3333).setStrokeWidth(1)
    val metrics = Scene.inter13.getMetrics
    val variantsHeight = if (scene._variants.length > 1) {
      25
    } else {
      0
    } // Background
    canvas.translate(width - 230, height - 160 - variantsHeight - HUD.extras.size * 25)
    canvas.drawRRect(RRect.makeLTRB(0, 0, 225, 180 + variantsHeight, 7), bg)
    canvas.translate(5, 5)
    // Scene
    var buttonBounds = RRect.makeLTRB(0, 0, 30, 20, 2)
    var labelBounds = Rect.makeLTRB(35, 0, 225, 20)
    canvas.drawRRect(buttonBounds, bg)
    Scene.drawStringCentered(canvas, "←→", 15, 10, Scene.inter13, fg)
    var sceneIdx = 1
    import scala.jdk.CollectionConverters.*
    boundary {
      for (sceneKey <- Scenes.scenes.keySet.asScala) {
        if (sceneKey == Scenes.currentScene) boundary.break()
        sceneIdx += 1
      }
    }
    Scene.drawStringLeft(canvas, sceneIdx + "/" + Scenes.scenes.size + " " + Scenes.currentScene, labelBounds, Scene
      .inter13, fg)
    canvas.translate(0, 25)
    // Variants
    if (scene._variants.length > 1) {
      labelBounds = Rect.makeLTRB(35, 0, 225, 20)
      canvas.drawRRect(buttonBounds, bg)
      Scene.drawStringCentered(canvas, "↑↓", 15, 10, Scene.inter13, fg)
      Scene.drawStringLeft(canvas, (scene._variantIdx + 1) + "/" + scene._variants.length + " " + scene
        .variantTitle, labelBounds, Scene.inter13, fg)
      canvas.translate(0, 25)
    }
    // Extras
    for (pair <- HUD.extras) {
      val key = pair.getFirst
      val label = pair.getSecond
      buttonBounds = RRect.makeXYWH(5, 0, 20, 20, 2)
      canvas.drawRRect(buttonBounds, bg)
      Scene.drawStringCentered(canvas, key, 15, 10, Scene.inter13, fg)
      Scene.drawStringLeft(canvas, label, labelBounds, Scene.inter13, fg)
      canvas.translate(0, 25)
    }
    // Stats
    canvas.drawRRect(buttonBounds, bg)
    Scene.drawStringCentered(canvas, "S", 15, 10, Scene.inter13, fg)
    Scene.drawStringLeft(canvas, "Stats: " + (if (Scenes.stats) {
      "ON"
    } else {
      "OFF"
    }), labelBounds, Scene.inter13, fg)
    canvas.translate(0, 25)
    // GC
    canvas.drawRRect(buttonBounds, bg)
    Scene.drawStringCentered(canvas, "G", 15, 10, Scene.inter13, fg)
    Scene.drawStringLeft(canvas, "GC objects: " + allocated, labelBounds, Scene.inter13, fg)
    canvas.translate(0, 25)
    // Native calls
    Scene.drawStringLeft(canvas, "Native calls: " + nativeCalls, labelBounds, Scene.inter13, fg)
    canvas.translate(0, 25)
    // fps
    canvas.drawRRect(RRect.makeLTRB(0, 0, times.length, 45, 2), bg)
    for (i <- 0 until times.length) {
      canvas.drawLine(i, 45, i, 45 - times(i).toFloat, if (i > timesIdx) {
        graphPast
      } else {
        graph
      })
    }
    for (refreshRate <- Array[Int](30, 60, 120)) {
      val frameTime = 1000f / refreshRate
      canvas.drawLine(0, 45 - frameTime, times.length, 45 - frameTime, graphLimit)
    }
    val time = String.format("%.1fms", java.util.Arrays.stream(times).takeWhile((t: Double) => t > 0).average.getAsDouble)
    Scene.drawStringLeft(canvas, time, Rect.makeLTRB(times.length + 5, 0, 225, 20), Scene.inter13, fg)
    val fps = String
      .format("%.0f fps", 1000.0 / java.util.Arrays.stream(times).takeWhile((t: Double) => t > 0).average.getAsDouble)
    Scene.drawStringLeft(canvas, fps, Rect.makeLTRB(times.length + 5, 25, 225, 40), Scene.inter13, fg)
    canvas.translate(0, 25)
    bg.close()
    fg.close()
    graph.close()
    graphPast.close()
    graphLimit.close()
  }
}