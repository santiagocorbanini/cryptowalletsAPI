package com.clearpay.demo.RestRepository;

import com.clearpay.demo.Document.Transfer;
import com.clearpay.demo.Document.User;
import com.clearpay.demo.Document.Wallet;
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
public class WalletRep {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRep userRep;

    public Wallet save(Wallet wallet) {
        return mongoTemplate.save(wallet);
    }

    public UpdateResult addWallet(String idUser, Wallet wallet){
        User user = userRep.find(idUser);
        double sum = user.addBalance(wallet.getCurrency());

        return mongoTemplate.updateFirst(
                new Query().addCriteria(Criteria.where("_id").is(idUser)),
                new Update().set("balance", sum),
                User.class
        );
    }

    public List<Wallet> findAll() {
        return mongoTemplate.findAll(Wallet.class);
    }

    public Wallet find(String idWallet){
        return mongoTemplate.find(
                new Query().addCriteria(Criteria.where("_id").is(idWallet)),
                Wallet.class)
                .get(0);
    }

    public List<Wallet> findIdUser(String idUser){
        return mongoTemplate.aggregate(Aggregation.newAggregation(
                Aggregation.match(new Criteria().orOperator(
                        Criteria.where("idUser").regex(idUser)
                ))
        ),"wallet", Wallet.class).getMappedResults();
    }

    public void transferMoney(Transfer transfer){

        Wallet sender =  find(transfer.getSender());
        Wallet receiver =  find(transfer.getReceiver());

        mongoTemplate.updateFirst(
                new Query().addCriteria(Criteria.where("_id").is(sender.getId())),
                new Update().set("currency", (sender.getCurrency() - transfer.getMoney())),
                Wallet.class
        );

        mongoTemplate.updateFirst(
                new Query().addCriteria(Criteria.where("_id").is(receiver.getId())),
                new Update().set("currency", (receiver.getCurrency() + transfer.getMoney())),
                Wallet.class
        );

    }
}
