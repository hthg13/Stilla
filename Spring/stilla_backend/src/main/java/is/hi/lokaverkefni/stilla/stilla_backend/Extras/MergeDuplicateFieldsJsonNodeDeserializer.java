package is.hi.lokaverkefni.stilla.stilla_backend.Extras;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

class MergeDuplicateFieldsJsonNodeDeserializer extends JsonNodeDeserializer {

    @Override
    protected void _handleDuplicateField(JsonParser p, DeserializationContext ctxt,
                                         JsonNodeFactory nodeFactory, String fieldName, ObjectNode objectNode,
                                         JsonNode oldValue, JsonNode newValue) throws JsonProcessingException {
        super._handleDuplicateField(p, ctxt, nodeFactory, fieldName, objectNode, oldValue, newValue);

        ArrayNode array;
        if (oldValue instanceof ArrayNode) {
            // Merge 3-rd, 4-th, ..., n-th element to already existed array
            array = (ArrayNode) oldValue;
            array.add(newValue);
        } else {
            // Merge first two elements
            array = nodeFactory.arrayNode();
            array.add(oldValue);
            array.add(newValue);
        }
        objectNode.set(fieldName, array);
    }
}
