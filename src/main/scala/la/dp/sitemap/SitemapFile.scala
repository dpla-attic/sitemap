package la.dp.sitemap

import java.io.File

/**
  * Given a list of subfiles and a URL fragment where they will eventually reside, and a parent directory,
  * creates a top-level sitemap file in that parent directory.
  *
  */

trait SitemapFile extends FileWriter {

  def createSitemap(baseUrl: String, subfiles: Iterator[File], timestamp: String, parentDirectory: File): File  = {

    val sitemapElements = subfiles.map(
      subfile =>
        {<sitemap><loc>{baseUrl + subfile.getName}</loc><lastmod>{timestamp}</lastmod></sitemap>}
    )

    val xmlData =
      <sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">{sitemapElements}</sitemapindex>

    write(parentDirectory, "all_item_urls.xml", xmlData.toString)
  }
}
