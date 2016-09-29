package la.dp.sitemap

import java.io.InputStream

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.JsonToken._
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

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
