package com.clearpay.demo.RestRepository;

import com.clearpay.demo.Document.Transfer;
import com.clearpay.demo.Document.Wallet;
import com.clearpay.demo.Document.User;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRep {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User save(User user) {
        return mongoTemplate.save(user);
    }

    public UpdateResult addWallet(String idUser, Wallet wallet){
        User user = find(idUser);
        double sum = user.addBalance(wallet.getCurrency());

        return mongoTemplate.updateFirst(
                new Query().addCriteria(Criteria.where("_id").is(idUser)),
                new Update().addToSet( "wallets", wallet).set("balance", sum),
                User.class
        );
    }

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User find(String idUser){
        return mongoTemplate.find(
                new Query().addCriteria(Criteria.where("_id").is(idUser)),
                User.class)
                .get(0);
    }

    public List<User> search(String search){

        return mongoTemplate.aggregate(Aggregation.newAggregation(

                Aggregation.match(new Criteria().orOperator(

                        Criteria.where("name").regex("(?i).*" + search + ".*")
                ))
        ),"User", User.class).getMappedResults();
    }
}
