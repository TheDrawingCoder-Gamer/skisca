package gay.menkissing.skisca


case class ColorMatrix(mat: Array[Float]) {
  assert(mat.length == 20, if ("Expected 20 elements, got " + mat == null) {
    null
  } else {
    mat.length
  })

  def this(r0: Float, r1: Float, r2: Float, r3: Float, r4: Float,
       g0: Float, g1: Float, g2: Float, g3: Float, g4: Float,
       b0: Float, b1: Float, b2: Float, b3: Float, b4: Float,
       a0: Float, a1: Float, a2: Float, a3: Float, a4: Float) = {
    this(Array(r0, r1, r2, r3, r4, g0, g1, g2, g3, g4, b0, b1, b2, b3, b4, a0, a1, a2, a3, a4))
  }
}