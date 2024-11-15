package gay.menkissing.skisca.impl

import java.util.Locale
import java.util.regex.{Matcher, Pattern}

enum Architecture extends Enum[Architecture] {
  case X86, X64, IA64, ARM32, ARM64, SPARC, SPARCV9, MIPS, MIPS64, MIPSEL, MIPS64EL, PPC, PPC64, PPCLE, PPC64LE, S390, S390X, RISCV32, RISCV64, LOONGARCH32, LOONGARCH64, LOONGARCH64_OW, UNKNOWN
}

object Architecture {
  val CURRENT: Architecture = {
    val littleEndian = "little".equalsIgnoreCase(System.getProperty("sun.cpu.endian"))
    System.getProperty("os.arch") match {
      case "x8664" | "x86-64" | "x86_64" | "amd64" | "ia32e" | "em64t" | "x64" => X64
      case "x8632" | "x86-32" | "x86_32" | "x86" | "i86pc" | "i386" | "i486" | "i586" | "i686" | "ia32" | "x32" => X86
      case "arm64" | "aarch64" | s"armv8$_" | s"armv9$_" => ARM64
      case "arm" | "arm32" | s"armv7$_" => ARM32
      case "mips64" => if (littleEndian) MIPS64EL else MIPS64
      case "mips64el" => MIPS64EL
      case "mips" | "mips32" => if (littleEndian) MIPSEL else MIPS
      case "mipsel" | "mips32el" => MIPSEL
      case "riscv" | "risc-v" | "riscv64" => RISCV64
      case "ia64" | "ia64w" | "itanium64" => IA64
      case "sparcv9" | "sparc64" => SPARCV9
      case "sparc" | "sparc32" => SPARC
      case "ppc64" | "powerpc64" => if (littleEndian) PPC64LE else PPC64
      case "ppc64le" | "powerpc64le" => PPC64LE
      case "ppc" | "ppc32" | "powerpc" | "powerpc32" => if (littleEndian) PPCLE else PPC
      case "ppcle" | "ppc32le" | "powerpcle" | "powerpc32le" => PPCLE
      case "s390" => S390
      case "s390x" => S390X
      case "loong32" | "loongarch32" => LOONGARCH32
      case "loong64" | "loongarch64" => {
        var oldWorld = false
        if (OperatingSystem.CURRENT == OperatingSystem.LINUX) {
          val osVersion = System.getProperty("os.version")
          if (osVersion.startsWith("4.")) {
            oldWorld = true
          } else if (osVersion.startsWith("5.")) {
            val matcher = Pattern.compile("^5\\.(?<minor>[0-9]+)").matcher(osVersion)
            if (matcher.find() && Integer.parseInt(matcher.group("minor")) < 19) {
              oldWorld = true
            }
          }
        }
        if (oldWorld) LOONGARCH64_OW else LOONGARCH64
      }
      case _ => UNKNOWN
    }
  }
}