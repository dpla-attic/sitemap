package la.dp.sitemap

import java.io.InputStream
import java.time.{LocalDateTime, ZoneOffset}
import java.time.format.DateTimeFormatter

import org.scalatest.{FlatSpec, Matchers}

abstract class SitemapSpec extends FlatSpec with Matchers {
  val dateFormat = DateTimeFormatter.ISO_INSTANT
  val timestamp = LocalDateTime.now().atZone(ZoneOffset.UTC).format(dateFormat)
  val sitemapNS = "http://www.sitemaps.org/schemas/sitemap/0.9"

  def someDotJson: InputStream = getClass.getResourceAsStream("/some.json")
}

