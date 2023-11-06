package univ.lab.problem1.client;

import org.junit.jupiter.api.Test;
import univ.lab.problem1.model.Model;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void constructClient() {
        Client client1 = new Client();
        assertNotNull(client1.myModel);

        Model model = new Model();
        model.setId(1);
        model.setName("Name");
        model.setDescription("Description");
        Client client2 = new Client(model);
        assertEquals(client2.myModel, model);
    }
}