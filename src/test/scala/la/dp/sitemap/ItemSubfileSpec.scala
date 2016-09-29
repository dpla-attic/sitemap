package la.dp.sitemap

import java.io.{File, FileInputStream}
import java.net.URL
import java.nio.file.Files

import org.scalatest.BeforeAndAfter

import scala.xml.{Elem, XML}

class ItemSubfileSpec extends SitemapSpec with BeforeAndAfter with ItemSubfile {

  var subfiles: List[File] = _
  var xml: Elem = _

  before {

    val tempDir = Files.createTempDirectory("ItemSubfileSpec").toFile
    subfiles = createItemSubfiles(loadSomeDotJson, timestamp, tempDir).toList
    xml = XML.loadFile(subfiles.head)
  }

  "This much JSON" should "produce one subfile" in {
    assert(subfiles.size == 1)
  }

  "An ItemSubfile" should "be a well-formed xml file" in {
     assert(xml.nonEmpty)
  }

  it should "have a root element <urlset> in the correct namespace" in {
    assert("urlset".equals(xml.label))
    assert(sitemapNS.equals(xml.namespace))
  }

  it should "have children called <url>" in {
    assert((xml \ "url").nonEmpty)
  }

  it should "have <loc> <lastmod> and <changefreq> elements inside each <url>" in {
    (xml \\ "url").foreach(sitemap => {
      assert((sitemap \ "loc").size == 1)
      assert((sitemap \ "lastmod").size == 1)
      assert((sitemap \ "changefreq").size == 1)
    })
  }

  it should "have <loc> elements that contain dp.la urls" in {
    (xml \\ "loc").foreach(loc => {
      val url = new URL(loc.text)
      assert(url.getHost.equals("dp.la"))
    })
  }

  it should "have <lastmod> elements that have timestamps" in {
    (xml \\ "lastmod").foreach(lastmod => {
      val lastmodTimestamp = lastmod.text
      assert(timestamp.equals(lastmodTimestamp.trim))
    })
  }

  it should "have <changefreq> elements that say 'monthly'" in {
    (xml \\ "changefreq").foreach(changefreq => {
      assert("monthly".equals(changefreq.text.trim))
    })
  }

}
