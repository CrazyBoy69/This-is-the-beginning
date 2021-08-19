package com.example.demo.controller;

import com.example.demo.entity.Entourage;
import com.example.demo.entity.PlayerEntourage;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class GameController {

    static Map<String,Map<String,Integer>> PlayerInformation = new HashMap<>();
    static List<Entourage> FirstAttackPlayer,BackAttackPlayer,Container;
    static int let,t,k;
    static Map<String,Integer> player1 = new HashMap<>();
    static Map<String,Integer> player2 = new HashMap<>();
    static Map<String,Integer> draw = new HashMap<>();
    static {
        PlayerInformation.put("player1",player1);
        PlayerInformation.put("player2",player2);
        PlayerInformation.put("draw",draw);
    }

    @ResponseBody
    @PostMapping("/entourage")
    public Map<String,Map<String,Integer>> playerEntourage(String entourages,Integer totalTout) {
        player1.put("Wins",0);
        player1.put("WinningProbability",0);
        player2.put("Wins",0);
        player2.put("WinningProbability",0);
        draw.put("DrawNumber",0);
        draw.put("WinningProbability",0);
        Gson gson = new Gson();
        Random rand = new Random();
        for (int w = 0; w < totalTout; w++) {
            PlayerEntourage playerEntourages = gson.fromJson(entourages,PlayerEntourage.class);
            boolean start = rand.nextBoolean();
            if(start){
                FirstAttackPlayer = playerEntourages.getPlayer1Entourage();
                BackAttackPlayer = playerEntourages.getPlayer2Entourage();
            }else {
                FirstAttackPlayer = playerEntourages.getPlayer2Entourage();
                BackAttackPlayer = playerEntourages.getPlayer1Entourage();
            }
            for (int i = 0; i < 200; i++) {
                t = 0;
                k = 0;
                if(t >= FirstAttackPlayer.size()){
                    t = 0;
                }
                if(k >= BackAttackPlayer.size()){
                    k = 0;
                }
                let = (i%2 != 1) ? t++ : k++;
                int num = rand.nextInt(BackAttackPlayer.size());
                fight(FirstAttackPlayer, let, BackAttackPlayer, num);
                if(FirstAttackPlayer.size() == 0){
                    break;
                }else if(BackAttackPlayer.size() == 0){
                    break;
                }
                Container = FirstAttackPlayer;
                FirstAttackPlayer = BackAttackPlayer;
                BackAttackPlayer = Container;
            }
            if(playerEntourages.getPlayer1Entourage().size() != 0 && playerEntourages.getPlayer2Entourage().size() == 0){
                player1.put("Wins",(player1.get("Wins")+1));
                System.out.println("player1 Win");
            }else if(playerEntourages.getPlayer1Entourage().size() == 0 && playerEntourages.getPlayer2Entourage().size() != 0){
                player2.put("Wins",(player2.get("Wins")+1));
                System.out.println("player1 Win");
            }else{
                draw.put("DrawNumber",(draw.get("DrawNumber")+1));
                System.out.println("DRAW");
            }
        }
        player1.put("WinningProbability",(int)((double)player1.get("Wins")/totalTout*100));
        player2.put("WinningProbability",(int)((double)player2.get("Wins")/totalTout*100));
        draw.put("WinningProbability",(int)((double)draw.get("DrawNumber")/totalTout*100));
        return PlayerInformation;
    }

    public void fight(List<Entourage> attacker, int let, List<Entourage> attacked, int num) {
        if(attacked.get(num).getHolyShield() == 1 && attacked.get(num).getPoison() == 0){
            attacked.get(num).setHolyShield(0);
            if(attacker.get(let).getHolyShield() == 1){
                attacker.get(let).setHolyShield(0);
            }else {
                hurt(attacker,let,attacked,num);
            }
        }else if(attacked.get(num).getHolyShield() == 1 && attacked.get(num).getPoison() == 1){
            attacked.get(num).setHolyShield(0);
            if(attacker.get(let).getHolyShield() == 1){
                attacker.get(let).setHolyShield(0);
            }else {
                attacker.remove(let);
            }
        }else if(attacked.get(num).getHolyShield() == 0 && attacked.get(num).getPoison() == 1){
            if(attacker.get(let).getHolyShield() == 1 && attacker.get(let).getPoison() == 0){
                attacker.get(let).setHolyShield(0);
                hurt(attacked,num,attacker,let);
            }else if(attacker.get(let).getHolyShield() == 0 && attacker.get(let).getPoison() == 1){
                attacker.remove(let);
                attacked.remove(num);
            }else if(attacker.get(let).getHolyShield() == 0 && attacker.get(let).getPoison() == 0){
                hurt(attacked,num,attacker,let);
                attacker.remove(let);
            }else{
                attacker.get(let).setHolyShield(0);
                attacked.remove(num);
            }
        }else{
            if(attacker.get(let).getHolyShield() == 1 && attacker.get(let).getPoison() == 0){
                attacker.get(let).setHolyShield(0);
                hurt(attacked,num,attacker,let);
            }else if(attacker.get(let).getHolyShield() == 0 && attacker.get(let).getPoison() == 1){
                hurt(attacker,let,attacked,num);
                attacked.remove(num);
            }else if(attacker.get(let).getHolyShield() == 0 && attacker.get(let).getPoison() == 0){
                hurt1(attacker,let,attacked,num);
            }else{
                attacker.get(let).setHolyShield(0);
                attacked.remove(num);
            }
        }
    }

    public void hurt(List<Entourage> attacker ,int let ,List<Entourage> attacked, int num){
        int hurt = attacker.get(let).getBloodVolume() - attacked.get(num).getAggressivity();
        if(hurt > 0){
            attacker.get(let).setBloodVolume(hurt);
        }else{
            attacker.remove(let);
        }
    }

    public void hurt1(List<Entourage> attacker ,int let ,List<Entourage> attacked, int num){
        int firstHurt = attacker.get(let).getBloodVolume() - attacked.get(num).getAggressivity();
        int lastHurt = attacked.get(num).getBloodVolume() - attacker.get(let).getAggressivity();
        if(firstHurt > 0){
            attacker.get(let).setBloodVolume(firstHurt);
        }else{
            attacker.remove(let);
        }
        if(lastHurt > 0){
            attacked.get(num).setBloodVolume(lastHurt);
        }else{
            attacked.remove(num);
        }
    }
}


