package tdt4140.gr1812.app.backend;

import static com.sun.corba.se.impl.logging.POASystemException.get;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.misc.Version.print;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tdt4140.gr1812.app.backend.server.Server;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Server.class)
@AutoConfigureMockMvc
public class ServerTest{

    @Autowired // 
    private MockMvc mvc;

    @Test //Testing that endpoint responds properly
    public void welcomeMessage() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("").accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Welcome. I am your server.")));
    }
    //Have to figure out how to test for login and signup
    @Test //Testing that endpoint responds properly
    public void testMainMethod() throws Exception {
        Server.main(new String[] {});

    }

    @Test
    public void testMysqlConnection() throws Exception{




    }

//    private MockHttpServletRequestBuilder get(String s) {
//    }

    //Have to figure out how to test for login and signup
}
