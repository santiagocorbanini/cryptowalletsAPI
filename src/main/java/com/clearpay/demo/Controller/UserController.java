package com.clearpay.demo.Controller;

import com.clearpay.demo.Document.Wallet;
import com.clearpay.demo.Document.User;
import com.clearpay.demo.RestRepository.UserRep;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin()
public class UserController {

    @Autowired
    private UserRep userRep;

    @GetMapping("/users/")
    public List<User> findAll(){
        return userRep.findAll();
    }

    @PostMapping("/users")
    public User save(@RequestBody User user){
        return userRep.save(user);
    }

    @PostMapping("/users/{idUser}/addwallet")
    public UpdateResult addWallet(@PathVariable("idUser") String idUser, @RequestBody Wallet wallet){
        return  userRep.addWallet(idUser, wallet);
    }

    @GetMapping("/user/{idUser}")
    public User find(@PathVariable("idUser") String idUser){
        return userRep.find(idUser);
    }

    @GetMapping("/user/search/{searchUser}")
    public List<User> search(@PathVariable("searchUser") String searchUser){
        return userRep.search(searchUser);
    }
}
