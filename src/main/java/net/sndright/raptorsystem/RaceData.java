package net.sndright.raptorsystem;


import lombok.Data;

import java.util.List;

@Data
public class RaceData {

    public String date;

    public String place;

    public String raceNumber;

    public String raceName;

    public String dist;

    public String raceUrl;

    public BulletinBoardInfo bulletinBoardInfo;

}
