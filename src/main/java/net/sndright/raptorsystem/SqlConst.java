package net.sndright.raptorsystem;

public class SqlConst {
    public static final String SQL ="insert into race_data(" +
            "race_date," +
            "place," +
            "race_number," +
            "race_name," +
            "dist," +
            "race_condition," +
            "race_url," +
            "horse_name_1," +
            "horse_name_url_1," +
            "father1_1," +
            "father11_1," +
            "father12_1," +
            "jockey_1," +
            "horse_name_2," +
            "horse_name_url_2," +
            "father1_2," +
            "father11_2," +
            "father12_2," +
            "jockey_2," +
            "horse_name_3," +
            "horse_name_url_3," +
            "father1_3," +
            "father11_3," +
            "father12_3," +
            "jockey_3" +
            ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String SQL2 ="insert into race_data(" +
            "race_date," +
            "place," +
            ") values (?,?);";
}
