package net.sndright.raptorsystem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Raptor {

    public final static String URL = "https://db.netkeiba.com/race/list/20190602/";

    public static void main(String[] args) throws IOException, InterruptedException {
        List<RaceData> raceDataList = new ArrayList<RaceData>();
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.select("dl.race_top_hold_list");
        for(Element element : elements){
            Thread.sleep(1000);
            Document document1 = Jsoup.parse(String.valueOf(element));
            //System.out.println(document1.html());
            Elements elements1 = document1.select("dt p");
            String place = elements1.text();
            //raceのURL取得
            Elements elements2 = document1.select("dl.race_top_data_info a");
            List<String> raceUrlList = new ArrayList<String>();
            int index1 = 0;
            for(Element element1 : elements2){
                if(index1 % 2 == 0){
                    String raceUrl = "https://db.netkeiba.com"+element1.attr("href");
                    raceUrlList.add(raceUrl);
                }
                index1++;
            }
            //System.out.println(raceUrlList);
            //System.out.println("-----------------");
            Elements elements11 = document1.select("dl.race_top_data_info");
            int index2 = 0;
            for(Element element11 : elements11){
                String eleText = element11.text();
                String[] data = eleText.split(" ");
                RaceData raceData = new RaceData();
                raceData.setDate("20190602");
                raceData.setPlace(place);
                raceData.setRaceNumber(data[0]);
                raceData.setRaceName(data[1]);
                raceData.setDist(data[2]);
                raceData.setRaceUrl(raceUrlList.get(index2));

                BulletinBoardInfo bulletinBoardInfo = getBulletBoardInfo(raceData.getRaceUrl());
                raceData.setBulletinBoardInfo(bulletinBoardInfo);

                raceDataList.add(raceData);
                index2++;
                System.out.println(raceData);

            }





        }

        for(RaceData raceData : raceDataList){
            //System.out.println(raceData);
        }


    }

    public static BulletinBoardInfo getBulletBoardInfo(String raceUrl) throws IOException, InterruptedException {
        BulletinBoardInfo bulletinBoardInfo = new BulletinBoardInfo();
        //System.out.println(raceUrl);
        Document document = Jsoup.connect(raceUrl).get();
        Elements elements = document.select("table.race_table_01 td.txt_l a");
        int index = 0;
        for(Element element : elements){
            if(index == 0){
                bulletinBoardInfo.setHorseName_1(element.text());
                bulletinBoardInfo.setHorseNameUrl_1("https://db.netkeiba.com"+element.attr("href"));
                //ここで親の血糖取得する
                Thread.sleep(1000);
                Document document1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_1()).get();
                Elements elements1 = document1.select("table.blood_table a");
                int index1 = 0;
                for(Element element1 : elements1){
                    if(index1 == 0){
                        bulletinBoardInfo.setFather1_1(element1.text());
                    }else if(index1 == 1){
                        bulletinBoardInfo.setFather11_1(element1.text());
                    }else if(index1 == 4){
                        bulletinBoardInfo.setFather12_1(element1.text());
                    }
                    index1++;
                }
            }else if(index == 1){
                bulletinBoardInfo.setJockey_1(element.text());
            }else if(index == 4){
                bulletinBoardInfo.setHorseName_2(element.text());
                bulletinBoardInfo.setHorseNameUrl_2("https://db.netkeiba.com"+element.attr("href"));
                //ここで親の血糖取得する
                Thread.sleep(1000);
                Document document1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_2()).get();
                Elements elements1 = document1.select("table.blood_table a");
                int index1 = 0;
                for(Element element1 : elements1){
                    if(index1 == 0){
                        bulletinBoardInfo.setFather1_2(element1.text());
                    }else if(index1 == 1){
                        bulletinBoardInfo.setFather11_2(element1.text());
                    }else if(index1 == 4){
                        bulletinBoardInfo.setFather12_2(element1.text());
                    }
                    index1++;
                }
            }else if(index == 5){
                bulletinBoardInfo.setJockey_2(element.text());
            }else if(index == 8){
                bulletinBoardInfo.setHorseName_3(element.text());
                bulletinBoardInfo.setHorseNameUrl_3("https://db.netkeiba.com"+element.attr("href"));
                //ここで親の血糖取得する
                Thread.sleep(1000);
                Document document1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_3()).get();
                Elements elements1 = document1.select("table.blood_table a");
                int index1 = 0;
                for(Element element1 : elements1){
                    if(index1 == 0){
                        bulletinBoardInfo.setFather1_3(element1.text());
                    }else if(index1 == 1){
                        bulletinBoardInfo.setFather11_3(element1.text());
                    }else if(index1 == 4){
                        bulletinBoardInfo.setFather12_3(element1.text());
                    }
                    index1++;
                }
            }else if(index == 9){
                bulletinBoardInfo.setJockey_3(element.text());
                break;
            }

            index++;

        }

        return bulletinBoardInfo;

    }

}
