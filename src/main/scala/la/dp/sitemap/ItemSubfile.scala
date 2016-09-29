package la.dp.sitemap

import java.io.{File, InputStream}

trait ItemSubfile extends FileWriter {

  def buildSubfile(timestamp: String, ids: Iterable[String]): String = {

    val urls = ids.map(
      id => {
        <url>
          <loc>
            {"https://dp.la/item/" + id}
          </loc> <lastmod>
          {timestamp}
        </lastmod> <changefreq>monthly</changefreq>
        </url>
      }
    )

    val xml = <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
      {urls}
    </urlset>

    xml.buildString(true)
  }

  def createItemSubfiles(json: InputStream, timestamp: String, outputPath: File): Iterator[File] = {

    def traversable = new DplaJsonTraversable(json).view

    traversable.filter(
      jsonNode => "item".equals(jsonNode.path("_type").asText())

    ).map(
      jsonNode => jsonNode.path("_source").path("id").asText()

    ).toIterable.grouped(10000).zipWithIndex.map(
      pair => {
        val ids = pair._1
        val sequence = pair._2
        val data = buildSubfile(timestamp, ids)
        val filename = "all_item_urls_" + sequence + ".xml"
        val subfile = write(outputPath, filename , data)
        subfile
      }
    )
  }
}
