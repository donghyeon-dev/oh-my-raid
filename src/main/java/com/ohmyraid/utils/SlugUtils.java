package com.ohmyraid.utils;

import org.springframework.stereotype.Component;

@Component
public class SlugUtils {

    public String krSlugToEng(String krSlugName) {
        String engValue;
        switch (krSlugName) {
            case "불타는 군단":
                engValue = "burning-legion";
                break;
            case "아즈샤라":
                engValue = "azshara";
                break;
            case "달라란":
                engValue = "dalaran";
                break;
            case "듀로탄":
                engValue = "durotan";
                break;
            case "노르간논":
                engValue = "norgannon";
                break;
            case "가로나":
                engValue = "garona";
                break;
            case "윈드러너":
                engValue = "windrunner";
                break;
            case "굴단":
                engValue = "guldan";
                break;
            case "알렉스트라자":
                engValue = "alexstrasza";
                break;
            case "말퓨리온":
                engValue = "malfurion";
                break;
            case "헬스크림":
                engValue = "hellscream";
                break;
            case "와일드해머":
                engValue = "wildhammer";
                break;
            case "렉사르":
                engValue = "rexxar";
                break;
            case "하이잘":
                engValue = "hyjal";
                break;
            case "데스윙":
                engValue = "deathwing";
                break;
            case "세나리우스":
                engValue = "cenarius";
                break;
            case "스톰레이지":
                engValue = "stormrage";
                break;
            case "줄진":
                engValue = "zuljin";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + krSlugName);
        }
        return engValue;
    }
}
