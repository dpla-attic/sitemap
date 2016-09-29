package la.dp.sitemap

import java.io.File
import java.net.URL

import org.scalatest.BeforeAndAfter

import scala.xml.{Elem, XML}

class SitemapFileSpec extends SitemapSpec with BeforeAndAfter with SitemapFile {

  val exampleDotCom = "http://example.com/"
  val subfileName = "sitemapfilespec"
  var xml: Elem = _

  before {
    val tempFile = File.createTempFile(subfileName, "")
    val sitemap = createSitemap(exampleDotCom, List(tempFile).iterator, timestamp, tempFile.getParentFile)
    xml = XML.loadFile(sitemap)
  }

  "A Sitemap" should "be a well-formed xml file" in {
    assert(xml.nonEmpty)
  }

  it should "have a root element <sitemapindex> in the correct namespace" in {
    assert("sitemapindex".equals(xml.label))
    assert(sitemapNS.equals(xml.namespace))
  }

  it should "have children elements <sitemap>" in {
    assert((xml \ "sitemap").nonEmpty)
  }

  it should "have <loc> and <lastmod> elements inside each <sitemap>" in {
    (xml \\ "sitemap").foreach(sitemap => {
      assert((sitemap \ "loc").size == 1)
      assert((sitemap \ "lastmod").size == 1)
    })
  }

  it should "have <lastmod> elements with valid timestamps" in {
    (xml \\ "lastmod").foreach(lastmod => {
      assert(timestamp.equals(lastmod.text))
    })
  }

  it should "have <loc> elements with valid URLs" in {
    (xml \\ "loc").foreach(loc => {
      assert("example.com".equals(new URL(loc.text).getHost))
    })
  }
}
