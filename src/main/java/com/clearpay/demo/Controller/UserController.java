package com.clearpay.demo.Controller;

import com.clearpay.demo.Document.Wallet;
import com.clearpay.demo.Document.User;
import com.clearpay.demo.Document.Transfer;
import com.clearpay.demo.RestRepository.UserRep;
import com.clearpay.demo.RestRepository.WalletRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin()
public class UserController {

    @Autowired
    private UserRep userRep;

    @Autowired
    private WalletRep walletRep;

    @GetMapping("/users/")
    public List<User> findAll(){
        return userRep.findAll();
    }

    @PostMapping("/users")
    public User save(@RequestBody User user){
        return userRep.save(user);
    }

    @PostMapping("/user/addwallet")
    public Wallet addWallet(@RequestBody Wallet wallet){
        walletRep.addWallet(wallet.getIdUser(), wallet);
        return walletRep.save(wallet);
    }

    @GetMapping("/user/{idUser}")
    public User find(@PathVariable("idUser") String idUser){
        return userRep.find(idUser);
    }

    @GetMapping("/user/search/{searchUser}")
    public List<User> search(@PathVariable("searchUser") String searchUser){
        return userRep.search(searchUser);
    }

    @PostMapping("/transfer")
    public List<User> transferMoney(@RequestBody Transfer transfer){
        walletRep.transferMoney(transfer);

        return userRep.findAll();
    }

    @GetMapping("/user/{idUser}/wallets")
    public List<Wallet> findWallets(@PathVariable("idUser") String idUser){
        return walletRep.findIdUser(idUser);
    }

    @GetMapping("/wallets/")
    public List<Wallet> findAllWallets(){
        return walletRep.findAll();
    }

}
