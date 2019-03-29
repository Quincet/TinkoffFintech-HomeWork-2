package com.fintechtinkoff.homework.Generateusers.database;


import com.fintechtinkoff.homework.Generateusers.Human;
import lombok.Value;
import lombok.var;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Value
public class DataBaseUtils {
    private String login;
    private String pass;
    private String stringConnection;
    private SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("ru"));

    public DataBaseUtils() throws IOException {
        var property = new Properties();
        try(FileInputStream filProp = new FileInputStream("src/main/resources/config.properties")) {
            property.load(filProp);
            this.login = property.getProperty("db.login");
            this.pass = property.getProperty("db.pass");
            this.stringConnection = property.getProperty("db.host");
        }
    }
    public List<Human> selectHumans()throws SQLException, ParseException {
        List<Human> humans = new ArrayList<>();
        try(Connection connection = getConnect(); Statement stmnt = connection.createStatement()){
            String query = "SELECT * FROM persons JOIN address on persons.address_id = address.id;";
            ResultSet resultSet = stmnt.executeQuery(query);
            resultSet.last();
            System.out.println("В базе данных содержится " + resultSet.getRow() + " пользователей");
            resultSet.beforeFirst();
            Calendar data = Calendar.getInstance();
            while(resultSet.next()){
                data.setTime(dataFormat.parse(resultSet.getString("birthday")));
                Human user = Human.builder().name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .patronymic(resultSet.getString("middlename"))
                        .dataBirth(data)
                        .gender(resultSet.getString("gender"))
                        .inn(resultSet.getString("inn"))
                        .index(resultSet.getString("postcode"))
                        .country(resultSet.getString("country"))
                        .region(resultSet.getString("region"))
                        .city(resultSet.getString("city"))
                        .street(resultSet.getString("street"))
                        .house(resultSet.getInt("house"))
                        .apartment(resultSet.getInt("flat"))
                        .build();
                humans.add(user);
            }
        }
        return humans;
    }
    public void insertHumans(List<Human> users)throws SQLException {
        try(Connection connection = getConnect()){
            for(Human user:users){
                int idIfExist = getIdIfUserExist(user,connection);
                if (idIfExist > 0) {
                    getQueryAdrssUp(user,idIfExist,connection).executeUpdate();
                    getQueryPersUp(user,idIfExist,connection).executeUpdate();
                } else {
                    getQueryAdrsIn(user,connection).executeUpdate();
                    getQueryPersIn(user,connection).executeUpdate();
                }
            }
        }
        System.out.println("Пользователи были добавлены в базу данных, всего было добавлено = " + users.size() + " пользователей");
    }
    public int checkDataDB()throws SQLException{
        Integer haveDBData;
        try(Connection connection = getConnect(); Statement stmnt = connection.createStatement()) {
            String query = "SELECT COUNT(*) FROM persons JOIN address WHERE persons.address_id=address.id;";
            ResultSet resultSet = stmnt.executeQuery(query);
            resultSet.next();
            haveDBData = resultSet.getInt(1);
        }
        return haveDBData;
    }
    private PreparedStatement getQueryAdrssUp(Human user,Integer idIfExist,Connection conn)throws SQLException{
        PreparedStatement prpStat = conn.prepareStatement(String.format("UPDATE address SET postcode='%d',country=?,region=?,city=?,street=?,house= %d ,flat= %d WHERE id = %d",user.getIndex(),user.getHouse(),user.getApartment(),idIfExist));
        prpStat.setString(1,user.getCountry());
        prpStat.setString(2,user.getRegion());
        prpStat.setString(3,user.getCity());
        prpStat.setString(4,user.getStreet());
        return prpStat;
    }
    private PreparedStatement getQueryPersUp(Human user,Integer idIfExist,Connection conn)throws SQLException{
        PreparedStatement prpStat = conn.prepareStatement(String.format("UPDATE persons SET birthday='%s',gender='%s',inn='%s' WHERE id= %d;",
                dataFormat.format(user.getDataBirth().getTime()),user.getGender().substring(0,1).toUpperCase(),user.getInn(),idIfExist));
        return prpStat;
    }
    private PreparedStatement getQueryPersIn(Human user,Connection conn)throws SQLException{
        PreparedStatement prpStat = conn.prepareStatement(String.format("INSERT INTO persons(name,surname,middlename,birthday,gender,inn,address_id)" +
                " values(?,?,?,'%s','%s','%s',last_insert_id());",dataFormat.format(user.getDataBirth().getTime()),user.getGender().substring(0,1).toUpperCase(),user.getInn()));
        prpStat.setString(1,user.getName());
        prpStat.setString(2,user.getSurname());
        prpStat.setString(3,user.getPatronymic());
        return prpStat;
    }
    private PreparedStatement getQueryAdrsIn(Human user,Connection conn) throws SQLException{
        PreparedStatement prpStat = conn.prepareStatement(String.format("INSERT INTO address(postcode,country,region,city,street,house,flat) " +
                "values('%d',?,?,?,?,%d,%d);",user.getIndex(),user.getHouse(),user.getApartment()));
        prpStat.setString(1,user.getCountry());
        prpStat.setString(2,user.getRegion());
        prpStat.setString(3,user.getCity());
        prpStat.setString(4,user.getStreet());
        return prpStat;
    }
    private int getIdIfUserExist(Human user,Connection conn){
        try(Statement stmt = conn.createStatement()) {
            String query = String.format("SELECT id FROM persons WHERE name = '%s' && surname = '%s' && middlename = '%s';",user.getName(),user.getSurname(),user.getPatronymic());
            ResultSet result = stmt.executeQuery(query);
            result.next();
            return result.getInt(1);
        } catch (SQLException e){
            return 0;
        }
    }
    private Connection getConnect()throws SQLException{
        return DriverManager.getConnection(stringConnection,login,pass);
    }
}
