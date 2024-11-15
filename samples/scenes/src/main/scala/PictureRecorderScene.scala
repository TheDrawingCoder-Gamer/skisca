package gay.menkissing.skisca.examples.scenes

import gay.menkissing.skisca.*
import io.github.humbleui.types.*

import java.util.*
import java.util.concurrent.atomic.*

class PictureRecorderScene extends Scene {
  override def draw(canvas: Canvas, width: Int, height: Int, dpi: Float, xpos: Int, ypos: Int): Unit = {
    canvas.translate(30, 30)
    var id1 = -1
    {
      val recorder = new PictureRecorder
      val paint1 = Paint().setColor(0xFF264653)
      val paint2 = Paint().setColor(0xFF2a9d8f)
      val paint3 = Paint().setColor(0xFFe9c46a)
      val paint4 = Paint().setColor(0xFFf4a261)
      val paint5 = Paint().setColor(0xFFe76f51)
      val font = new Font
      try {
        for (bounds <- Array[Rect](Rect.makeXYWH(0, 0, 40, 40), Rect.makeXYWH(0, 0, 20, 20))) {
          val rc = recorder.beginRecording(bounds)
          rc.drawRect(Rect.makeXYWH(0, 0, 10, 10), paint1)
          rc.drawRect(Rect.makeXYWH(20, 0, 10, 10), paint2)
          rc.drawRect(Rect.makeXYWH(5, 5, 20, 20), paint3)
          rc.translate(0, 20)
          rc.drawRect(Rect.makeXYWH(0, 0, 10, 10), paint4)
          rc.drawRect(Rect.makeXYWH(20, 0, 10, 10), paint5)
          assert(Objects.equals(rc, recorder.getRecordingCanvas))
          try {
            val picture = recorder.finishRecordingAsPicture
            val transparent = Paint().setColor(0x80000000)
            try {
              assert(Objects.equals(bounds, picture.getCullRect))
              id1 = picture.getUniqueId
              canvas.save
              canvas.drawPicture(picture)
              canvas.drawPicture(picture, Matrix33.makeTranslate(40, 0), null)
              canvas.translate(80, 0)
              canvas.drawPicture(picture, null, transparent)
              canvas.translate(40, 0)
              try {
                val transparentCanvas = new AlmostTransparentFilterCanvas(canvas)
                try {
                  transparentCanvas.drawPicture(picture, null, null)
                } finally {
                  if (transparentCanvas != null) transparentCanvas.close()
                }
              }
              canvas.translate(40, 0)
              picture.playback(canvas)
              canvas.translate(40, 0)
              val counter = new AtomicInteger(0)
              picture.playback(canvas, () => counter.addAndGet(1) > 3)
              canvas.restore
            } finally {
              if (picture != null) picture.close()
              if (transparent != null) transparent.close()
            }
          }
          canvas.translate(0, 40)
        }
        val rc = recorder.beginRecording(Rect.makeXYWH(0, 0, width, height))
        rc.drawRect(Rect.makeXYWH(0, 0, 10, 10), paint1)
        rc.drawRect(Rect.makeXYWH(20, 0, 10, 10), paint2)
        rc.drawRect(Rect.makeXYWH(5, 5, 20, 20), paint3)
        rc.translate(0, 20)
        rc.drawRect(Rect.makeXYWH(0, 0, 10, 10), paint4)
        rc.drawRect(Rect.makeXYWH(20, 0, 10, 10), paint5)
        try {
          val picture = recorder.finishRecordingAsPicture(Rect.makeXYWH(0, 0, 20, 20))
          val transparent = Paint().setColor(0x80000000)
          val black = Paint().setColor(0xFF000000)
          try {
            assert(id1 != picture.getUniqueId)
            canvas.save
            canvas.drawPicture(picture)
            canvas.drawPicture(picture, Matrix33.makeTranslate(40, 0), null)
            canvas.translate(80, 0)
            canvas.drawPicture(picture, null, transparent)
            canvas.translate(40, 0)
            picture.playback(canvas)
            canvas.translate(40, 0)
            val counter = new AtomicInteger(0)
            picture.playback(canvas, () => counter.addAndGet(1) > 3)
            canvas.translate(40, 0)
            try {
              val data = picture.serializeToData
              val picture2 = Picture.makeFromData(data)
              try {
                picture2.playback(canvas)
                canvas.translate(40, 0)
              } finally {
                if (data != null) data.close()
                if (picture2 != null) picture2.close()
              }
            }
            {
              val placeholder = Picture.makePlaceholder(Rect.makeXYWH(0, 0, 40, 40))
              try {
                canvas.drawPicture(placeholder, null, black)
                canvas.translate(40, 0)
              } finally {
                if (placeholder != null) placeholder.close()
              }
            }
            try {
              val s = picture.makeShader(FilterTileMode.CLAMP, FilterTileMode.REPEAT, FilterMode.LINEAR)
              try {
                black.setShader(s)
                canvas.drawRect(Rect.makeXYWH(0, 0, 30, 30), black)
                canvas.translate(40, 0)
              } finally {
                if (s != null) s.close()
              }
            }
            try {
              val s = picture.makeShader(FilterTileMode.CLAMP, FilterTileMode.REPEAT, FilterMode.LINEAR, Matrix33
                .makeTranslate(5, 5))
              try {
                black.setShader(s)
                canvas.drawRect(Rect.makeXYWH(0, 0, 30, 30), black)
                canvas.translate(40, 0)
              } finally {
                if (s != null) s.close()
              }
            }
            try {
              val s = picture.makeShader(FilterTileMode.CLAMP, FilterTileMode.REPEAT, FilterMode.LINEAR, null, Rect
                .makeXYWH(5, 5, 20, 20))
              try {
                black.setShader(s)
                canvas.drawRect(Rect.makeXYWH(0, 0, 30, 30), black)
                canvas.translate(40, 0)
              } finally {
                if (s != null) s.close()
              }
            }
            black.setShader(null)
            canvas.drawString("approximateOpCount = " + picture.getApproximateOpCount, 0, 10, font, black)
            canvas.drawString("approximateBytesUsed = " + picture.getApproximateBytesUsed, 0, 25, font, black)
            canvas.restore
          } finally {
            if (picture != null) picture.close()
            if (transparent != null) transparent.close()
            if (black != null) black.close()
          }
        }
        canvas.translate(0, 40)
      } finally {
        if (recorder != null) recorder.close()
        if (paint1 != null) paint1.close()
        if (paint2 != null) paint2.close()
        if (paint3 != null) paint3.close()
        if (paint4 != null) paint4.close()
        if (paint5 != null) paint5.close()
        if (font != null) font.close()
      }
    }
  }
}

class AlmostTransparentFilterCanvas(canvas: Canvas) extends PaintFilterCanvas(canvas, true) {
  override def onFilter(paint: Paint): Boolean = {
    paint.setColor(Color.withA(paint.getColor, 32))
    true
  }
}