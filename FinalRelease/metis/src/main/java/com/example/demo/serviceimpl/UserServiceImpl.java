package com.example.demo.serviceimpl;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    public String checkLogin(String usn, String psw){
        System.out.println("login");
        User user = userRepository.getUserByUsername(usn);
        if(user==null){
            return "{\"log\" : \"" + "false" + "\"}";
        }
        else if (user.getPassword().equals(psw)){
            System.out.println("log" + user.getWeak());
            return (
                    "{\"nickname\" : \"" + user.getNickname() +
                            "\", \"game_score\" : \""+ user.getGame_score()  +
                            "\", \"log\" : \""+ "true"  +
                            "\", \"id\" : \""+ user.getId()  +
                            "\", \"username\" : \""+ user.getUsername()  +
                            "\", \"image_id\" : \""+ user.getImage_id() +
                            "\", \"weak\" : \""+ user.getWeak() +
                            "\", \"brave\" : \""+ user.getBrave() +
                            "\", \"money\" : \""+ user.getMoney() +"\"}"
            );
        }
        else {
            return "{\"log\" : \"" + "false" + "\"}";
        }
    }



    public String getInfo( String usn){
        if(userRepository.getUserByUsername(usn)==null){return"Not logged in";}
        else {
            User user = userRepository.getUserByUsername(usn);
            return(
                    "{\"nickname\" : \"" + user.getNickname() +
                            "\", \"game_score\" : \""+ user.getGame_score() +"\"}"
            );
        }
    }
    public String updateProfile( String usn, String nickname){

        //if(user==null)
        // return "Not logged in";
        User user = userRepository.getUserByUsername(usn);
        user.setNickname(nickname);
        userRepository.save(user);
        return "Succeed";
    }

    public void updateScore(String usn, int chan)
    {
        User user = userRepository.getUserByUsername(usn);
        int sco = user.getGame_score();
        if(sco + chan < 0)
            user.setGame_score(0);
        else
            user.setGame_score(sco+chan);
        userRepository.save(user);
    }

    public void updateMoney(String usn, int chan)
    {
        User user = userRepository.getUserByUsername(usn);
        int sco = user.getMoney();
        user.setMoney(sco+chan);
        userRepository.save(user);
    }

    public void updateWeak(String usn, int chan)
    {
        User user = userRepository.getUserByUsername(usn);
        int sco = user.getWeak();
        user.setWeak(sco+chan);
        userRepository.save(user);
    }

    public void updateBrave(String usn, int chan)
    {
        User user = userRepository.getUserByUsername(usn);
        int sco = user.getBrave();
        user.setBrave(sco+chan);
        userRepository.save(user);
    }

    public void updateHead(String usn, int chan)
    {
        User user = userRepository.getUserByUsername(usn);
        user.setImage_id(chan);
        userRepository.save(user);
    }

    public String signUp( String username, String password, String nickname)
    {
        User user = userRepository.getUserByUsername(username);
        if (user!=null)
            return "Username used";
        User newUser = new User();
        newUser.setNickname(nickname);
        newUser.setPassword(password);
        newUser.setUsername(username);
        newUser.setGame_score(0);
        Random random = new Random();
        int image_order = random.nextInt(6);
        newUser.setImage_id(image_order);
        userRepository.save(newUser);
        //if(httpSession.getAttribute("user")==null)return"Username used";
        return "Succeed";
    }
    public String getall(){
        List<User> users = userRepository.getAll();
        StringBuffer buf = new StringBuffer("[");
        for (User user:users){
            buf.append(
                    "{\"Username\" : \"" + user.getUsername() +
                            "\", \"Nickname\" : \"" + user.getNickname() +
                            "\", \"image_id\" : \"" +user.getImage_id() +
                            "\", \"game_score\" : \""+ user.getGame_score() +"\"}");
            buf.append(',');
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("]");
        return buf.toString();
    }


}

