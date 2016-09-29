package la.dp.sitemap

import java.io.{File, FileInputStream}
import java.time.{LocalDateTime, ZoneOffset}
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.zip.GZIPInputStream

object SitemapApp extends App with ItemSubfile with SitemapFile {

  val start = new Date().getTime
  val inputFile = new File(args(0))
  val sitemapUrlPrefix = args(1)
  val parentDirectory = new File(args(2))
  val timestamp = LocalDateTime.now().atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)

  val inputStream =
    if (inputFile.getName.endsWith(".gz")) new GZIPInputStream(new FileInputStream(inputFile))
    else new FileInputStream(inputFile)

  val itemSubfiles = createItemSubfiles(inputStream, timestamp, parentDirectory)
  val sitemapFile = createSitemap(sitemapUrlPrefix, itemSubfiles, timestamp, parentDirectory)

  val end = new Date().getTime

  println((end - start) + "ms")
}
