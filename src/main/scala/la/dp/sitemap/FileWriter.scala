package la.dp.sitemap

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files

trait FileWriter {

  def write(dir: File, name: String, data: String): File = {
    val outputFile = new File(dir, name)
    Files.write(outputFile.toPath, data.getBytes(StandardCharsets.UTF_8))
    outputFile
  }

}
