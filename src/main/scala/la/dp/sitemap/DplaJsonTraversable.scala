package la.dp.sitemap

import java.io.InputStream

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.JsonToken._
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

/**
  * Consumes an InputStream that has JSON data.
  *
  * Uses this data to present a consumer with a feed of individual JSON objects represented as Jackson JsonNodes.
  *
  * This stream uses SAX-style events to detect the objects and the feed start and end, callers are presented with a
  * DOM-ish interface to project properties out of the record. Doing it this way means you don't need a cluster or
  * 60-ish GB of RAM.
  */
class DplaJsonTraversable(inputStream: InputStream) extends Traversable[JsonNode] {

  override def hasDefiniteSize = false

  override def foreach[U](f: (JsonNode) => U): Unit = {

    try {
      val parser = DplaJsonTraversable.jsonFactory.createParser(inputStream)
      assume(parser.nextToken() == JsonToken.START_ARRAY)

      while (parser.nextToken() != END_ARRAY) {
        if (parser.currentToken() == START_OBJECT) {
          val node = parser.readValueAsTree[JsonNode]
          f(node)
        }
      }

    } finally {
      inputStream.close()
    }
  }

  override def toString(): String = "DplaJsonTraversable: " + inputStream.toString
}

object DplaJsonTraversable {
  private val objectMapper = new ObjectMapper()
  private val jsonFactory = objectMapper.getFactory
}
