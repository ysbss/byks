package com.wyw.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import com.wyw.utils.FinalStaticValue;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 鱼酥不是叔
 */
@Component
public class RandomStr {


    private final SecureRandom secureRandom=new SecureRandom();

    public char getRandomChar(String str){
        return str.charAt(secureRandom.nextInt(str.length()));
    }

    public char getLowerChar(){
        return getRandomChar(FinalStaticValue.LOW_STR);
    }

    public char getUpperChar(){
        return Character.toUpperCase(getLowerChar());
    }

    public char getNumChar(){
        return getRandomChar(FinalStaticValue.NUM_STR);
    }

    public char getSpecialChar(){
        return getRandomChar(FinalStaticValue.SPECIAL_STR);
    }

    public char getRandomChar(int funNum) {
        switch (funNum) {
            case 0:
                return getLowerChar();
            case 1:
                return getUpperChar();
            case 2:
                return getNumChar();
            case 3:
                return getSpecialChar();
            default:
        }
        return getLowerChar();
    }

    public String getRandomString(int stringLength){
        List<Character> listCharacter = new ArrayList<Character>(stringLength);

        for (int i = 0; i < stringLength; i++) {
            listCharacter.add(getRandomChar(secureRandom.nextInt(4)));
        }
        Collections.shuffle(listCharacter);
        StringBuilder sb=new StringBuilder(listCharacter.size());
        for (Character c :
                listCharacter) {
            sb.append(c);
        }


        return sb.toString();
    }

}
