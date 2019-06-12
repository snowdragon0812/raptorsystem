package net.sndright.raptorsystem;


import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class RaceData {

    public Date date;

    public String place;

    public String raceNumber;

    public String raceName;

    public String dist;

    public String condition;

    public String raceUrl;

    public BulletinBoardInfo bulletinBoardInfo;

}
