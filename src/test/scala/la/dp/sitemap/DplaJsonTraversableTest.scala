package la.dp.sitemap

import com.fasterxml.jackson.core.JsonToken

class DplaJsonTraversableTest extends SitemapSpec {

  "A DplaJsonTraversable" should "find all the objects in a DPLA JSON file" in {
    assert(new DplaJsonTraversable(loadSomeDotJson).size == 49)
  }

  it should "present the caller with a stream of JSON objects containing DPLA metadata" in {
    new DplaJsonTraversable(loadSomeDotJson).foreach(
      obj => {
        assert(obj.asToken() == JsonToken.START_OBJECT)
        List(obj.get("_id"), obj.get("_type"), obj.get("_index")).foreach( value => {
          assert(value.asToken() != JsonToken.VALUE_NULL)
        })
      })
  }
}
