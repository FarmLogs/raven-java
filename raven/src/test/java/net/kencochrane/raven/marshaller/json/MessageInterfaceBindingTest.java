package net.kencochrane.raven.marshaller.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.kencochrane.raven.event.interfaces.MessageInterface;
import org.hamcrest.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class MessageInterfaceBindingTest extends AbstractInterfaceBindingTest {
    private MessageInterfaceBinding interfaceBinding;
    @Mock
    private MessageInterface mockMessageInterface;

    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        interfaceBinding = new MessageInterfaceBinding();
    }

    @Test
    public void testSimpleMessage() throws Exception {
        String message = UUID.randomUUID().toString();
        List<String> parameters = Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        when(mockMessageInterface.getMessage()).thenReturn(message);
        when(mockMessageInterface.getParameters()).thenReturn(parameters);

        JsonGenerator jSonGenerator = getJsonGenerator();
        interfaceBinding.writeInterface(jSonGenerator, mockMessageInterface);
        jSonGenerator.close();

        JsonNode rootNode = getMapper().readValue(getJsonParser(), JsonNode.class);
        assertThat(rootNode.get("message").asText(), is(message));
        assertThat(getMapper().convertValue(rootNode.get("params"), List.class), Matchers.<List>is(parameters));
    }
}
