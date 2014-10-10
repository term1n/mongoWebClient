package ru.spb.iac.services.impl;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.spb.iac.services.impl.templates.MongoTemplateFactoryImpl;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test/application-context.xml"})
public class MongoTemplateFactoryImplTest extends MongoTemplateFactoryImpl {

    @Test
    public void test() throws Exception{
      log.debug("bdgfb");
    }

}
