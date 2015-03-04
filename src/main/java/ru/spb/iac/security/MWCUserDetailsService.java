package ru.spb.iac.security;

import com.google.common.base.*;
import lombok.extern.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import ru.spb.iac.exceptions.*;
import ru.spb.iac.security.oo.*;
import ru.spb.iac.services.*;
import ru.spb.iac.ui.models.*;

import javax.annotation.*;

/**
 * Created by manaev on 04.03.15.
 */
@Log4j
@Service("mwcDetailsService")
public class MWCUserDetailsService implements UserDetailsService {
    @Autowired
    @Qualifier("mongoTemplateFactory")
    private MongoTemplateFactory mongoTemplateFactory;

    @Value("${mongo.users.db}")
    private String mongoDbName;

    @Value("${mongo.users.host}")
    private String mongoDbHost;

    @Value("${mongo.users.port}")
    private String mongoDbPort;

    private MongoAddress mongoHere;

    public MongoAddress getMongoHere() {
        return mongoHere;
    }

    public void setMongoHere(MongoAddress mongoHere) {
        this.mongoHere = mongoHere;
    }

    public MongoTemplateFactory getMongoTemplateFactory() {
        return mongoTemplateFactory;
    }

    public void setMongoTemplateFactory(MongoTemplateFactory mongoTemplateFactory) {
        this.mongoTemplateFactory = mongoTemplateFactory;
    }

    @PostConstruct
    public void doInit(){
        try {
            mongoHere = new MongoAddress(mongoDbHost, Integer.valueOf(mongoDbPort), mongoDbName, MWCUser.COLLECTION_NAME);
            mongoTemplateFactory.initMap(mongoHere);
        } catch (MongoException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        if(!Strings.isNullOrEmpty(username)){
            MWCUser user = null;
            try {
                user = mongoTemplateFactory.getOperationsTemplate(mongoHere).findOne(new Query(Criteria.where("username").is(username)), MWCUser.class, MWCUser.COLLECTION_NAME);
            } catch (MongoException e) {
                log.error(e.getMessage(),e);
            }
            if(user == null){
                return null;
            }else{
                return user;
            }
        }else{
            return null;
        }
    }
}
