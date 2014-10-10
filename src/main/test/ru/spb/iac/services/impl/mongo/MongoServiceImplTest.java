package ru.spb.iac.services.impl.mongo;

import com.google.gson.*;
import com.mongodb.util.*;
import lombok.extern.log4j.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.model.*;

import java.util.*;

/**
 * Created by manaev on 10.10.14.
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test/application-context.xml"})
public class MongoServiceImplTest extends MongoServiceImpl {
    @Autowired
    @Qualifier("mongoTemplateFactory")
    MongoTemplateFactory mongoFactory;

    @Test
    public void test() throws Exception{
        Gson gson = new Gson();
        MongoAddress address = new MongoAddress("192.168.42.146",27017,"wcTest","test");
        mongoFactory.initMap(address);
//        Map<String, List<String>> upda = mongoFactory.getMongoInfo(address);
        List<Object> data = find(address, new Query());
        String dddd = JSON.serialize(data);
        List<Object> fffff = (List<Object>)JSON.parse(dddd);
//        String dataa = gson.toJson(data);
//        List<Object> obj = (List<Object>)gson.fromJson(dataa, List.class);
        save(address,fffff.get(0));
        log.debug("bdgfb");
    }
}
