package la.dp.sitemap

import java.nio.charset.StandardCharsets
import java.nio.file.Files

class FileWriterTest extends SitemapSpec with FileWriter {

  "A FileWriter" should "let you write data to a file" in {
    val tempFileName = "foo"
    val data = "what if a much of a which of a wind"
    val tempDirectory = Files.createTempDirectory("filewritertest").toFile
    val outputFile = write(tempDirectory, tempFileName, data)
    val outputData = new String(Files.readAllBytes(outputFile.toPath), StandardCharsets.UTF_8)
    assert(data.equals(outputData))
  }

}
