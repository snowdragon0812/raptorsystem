package net.sndright.raptorsystem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Raptor {


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, ParseException {
        Scanner stdIn = new Scanner(System.in);
        String dateText = "";
        String[] dateTextArray;
//        while(true){
//            System.out.print("日付を入れる（8桁※yyyy-MM-dd形式で）＞＞");
//            dateText = stdIn.next();
//            if(dateText.length() != 10){
//                System.out.println("不正な日付");
//            }else{
//                break;
//            }
//        }
        while(true) {
            System.out.print("日付を入れる（8桁※yyyy-MM-dd形式で）＞＞");
            dateText = stdIn.next();
            dateTextArray = dateText.split(",");
            //入力したテキストが問題ないか確認
            boolean flg = true;
            for (int i = 0; i < dateTextArray.length; i++) {
                if (dateTextArray[i].length() != 10){
                    System.out.println("不正な日付");
                    flg = false;
                }
            }
            if(flg){
                break;
            }
        }

        for(int i = 0;i<dateTextArray.length;i++){
            getData(dateTextArray[i]);
        }


//        String urlDate = dateText.replace("-","");
//
//        String URL = "https://db.netkeiba.com/race/list/"+urlDate+"/";
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Date formatDate = Date.valueOf(dateText);
//
//        List<RaceData> raceDataList = new ArrayList<RaceData>();
//        Elements elements = Jsoup.connect(URL).get().select("dl.race_top_hold_list");
//        for(Element element : elements){
//            Thread.sleep(500);
//            Document document1 = Jsoup.parse(String.valueOf(element));
//            //System.out.println(document1.html());
//            Elements elements1 = document1.select("dt p");
//            String place = elements1.text();
//            //raceのURL取得
//            Elements elements2 = document1.select("dl.race_top_data_info a");
//            List<String> raceUrlList = new ArrayList<String>();
//            int index1 = 0;
//            for(Element element1 : elements2){
//                if(index1 % 2 == 0){
//                    String raceUrl = "https://db.netkeiba.com"+element1.attr("href");
//                    raceUrlList.add(raceUrl);
//                }
//                index1++;
//            }
//            //System.out.println(raceUrlList);
//            //System.out.println("-----------------");
//            Elements elements11 = document1.select("dl.race_top_data_info");
//            int index2 = 0;
//            for(Element element11 : elements11){
//                String eleText = element11.text();
//                String[] data = eleText.split(" ");
//                RaceData raceData = new RaceData();
//                raceData.setDate(formatDate);
//                raceData.setPlace(place);
//                raceData.setRaceNumber(data[0]);
//                raceData.setRaceName(data[1]);
//                raceData.setDist(data[2]);
//                raceData.setRaceUrl(raceUrlList.get(index2));
//                raceData.setCondition(getConditionInfo(raceData.getRaceUrl()));
//
//                BulletinBoardInfo bulletinBoardInfo = getBulletBoardInfo(raceData.getRaceUrl());
//                raceData.setBulletinBoardInfo(bulletinBoardInfo);
//
//                raceDataList.add(raceData);
//                index2++;
//                System.out.println(index2);
//
//
//            }
//
//
//
//        }
//        insertRaceData(raceDataList);
//
//

    }

    public static void getData(String targetDate) throws ClassNotFoundException, IOException, InterruptedException {

        String urlDate = targetDate.replace("-","");

        String URL = "https://db.netkeiba.com/race/list/"+urlDate+"/";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date formatDate = Date.valueOf(targetDate);

        List<RaceData> raceDataList = new ArrayList<RaceData>();
        Elements elements = Jsoup.connect(URL).get().select("dl.race_top_hold_list");
        for(Element element : elements){
            Thread.sleep(500);
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
                raceData.setDate(formatDate);
                raceData.setPlace(place);
                raceData.setRaceNumber(data[0]);
                raceData.setRaceName(data[1]);
                raceData.setDist(data[2]);
                raceData.setRaceUrl(raceUrlList.get(index2));
                raceData.setCondition(getConditionInfo(raceData.getRaceUrl()));

                BulletinBoardInfo bulletinBoardInfo = getBulletBoardInfo(raceData.getRaceUrl());
                raceData.setBulletinBoardInfo(bulletinBoardInfo);

                raceDataList.add(raceData);
                index2++;
                System.out.println(index2);


            }



        }
        insertRaceData(raceDataList);
    }


    public static String getConditionInfo(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String text = document.select("dl.racedata").text();
        //System.out.println(text);
        String[] textArray = text.split("/");
        //System.out.println(textArray[2]);
        String[] textArray2 = textArray[2].split(":");
        //System.out.println(textArray2[1]);
        String conditionText = textArray2[1].replace(" ","");
        //System.out.println(conditionText);


        return conditionText;
    }

    public static BulletinBoardInfo getBulletBoardInfo(String raceUrl) throws IOException, InterruptedException {
        BulletinBoardInfo bulletinBoardInfo = new BulletinBoardInfo();
        //Document document = Jsoup.connect(raceUrl).get();
        //Elements elements = document.select("table.race_table_01 td.txt_l a");

        Elements elements = Jsoup.connect(raceUrl).get().select("table.race_table_01 td.txt_l a");

        int index = 0;
        for(Element element : elements){
            if(index == 0){
                bulletinBoardInfo.setHorseName_1(element.text());
                bulletinBoardInfo.setHorseNameUrl_1("https://db.netkeiba.com"+element.attr("href"));
                //ここで親の血糖取得する
                Thread.sleep(500);
                //Document document1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_1()).get();
                //Elements elements1 = document1.select("table.blood_table a");
                Elements elements1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_1()).get().select("table.blood_table a");
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
                Thread.sleep(500);
                //Document document1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_2()).get();
                //Elements elements1 = document1.select("table.blood_table a");
                Elements elements1 =  Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_2()).get().select("table.blood_table a");
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
                Thread.sleep(500);
//                Document document1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_3()).get();
//                Elements elements1 = document1.select("table.blood_table a");
                Elements elements1 = Jsoup.connect(bulletinBoardInfo.getHorseNameUrl_3()).get().select("table.blood_table a");
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

    public static void insertRaceData(List<RaceData> raceDataList) throws ClassNotFoundException {
        //System.out.println(SqlConst.SQL);

        ResourceBundle rb = ResourceBundle.getBundle("setting");
        String USER = rb.getString("user");
        String PASS = rb.getString("pass");

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost/raptor?user="+USER+"&password="+PASS+"&characterEncoding=UTF-8&serverTimezone=JST");
            con.setAutoCommit(false);
            System.out.println("MySQLに接続できました。");
            int index = 1;
            for(RaceData raceData : raceDataList){
                try(PreparedStatement ps = con.prepareStatement(SqlConst.SQL)){
                    ps.setDate(1,raceData.getDate());
                    ps.setString(2,raceData.getPlace());
                    ps.setString(3,raceData.getRaceNumber());
                    ps.setString(4,raceData.getRaceName());
                    ps.setString(5,raceData.getDist());
                    ps.setString(6,raceData.getCondition());
                    ps.setString(7,raceData.getRaceUrl());
                    ps.setString(8,raceData.getBulletinBoardInfo().getHorseName_1());
                    ps.setString(9,raceData.getBulletinBoardInfo().getHorseNameUrl_1());
                    ps.setString(10,raceData.getBulletinBoardInfo().getFather1_1());
                    ps.setString(11,raceData.getBulletinBoardInfo().getFather11_1());
                    ps.setString(12,raceData.getBulletinBoardInfo().getFather12_1());
                    ps.setString(13,raceData.getBulletinBoardInfo().getJockey_1());

                    ps.setString(14,raceData.getBulletinBoardInfo().getHorseName_2());
                    ps.setString(15,raceData.getBulletinBoardInfo().getHorseNameUrl_2());
                    ps.setString(16,raceData.getBulletinBoardInfo().getFather1_2());
                    ps.setString(17,raceData.getBulletinBoardInfo().getFather11_2());
                    ps.setString(18,raceData.getBulletinBoardInfo().getFather12_2());
                    ps.setString(19,raceData.getBulletinBoardInfo().getJockey_2());

                    ps.setString(20,raceData.getBulletinBoardInfo().getHorseName_3());
                    ps.setString(21,raceData.getBulletinBoardInfo().getHorseNameUrl_3());
                    ps.setString(22,raceData.getBulletinBoardInfo().getFather1_3());
                    ps.setString(23,raceData.getBulletinBoardInfo().getFather11_3());
                    ps.setString(24,raceData.getBulletinBoardInfo().getFather12_3());
                    ps.setString(25,raceData.getBulletinBoardInfo().getJockey_3());

                    ps.executeUpdate();

                    System.out.println(index+"OK");
                    index++;
                } catch (Exception e){
                    e.printStackTrace();
                    con.rollback();
                    System.out.println("rollback");
                    throw e;
                }

            }
            con.commit();
            con.setAutoCommit(true);





        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("MySQLに接続できませんでした。");
        } catch (Exception e){
            e.printStackTrace();

        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("MySQLのクローズに失敗しました。");
                }
            }

        }
    }

}
