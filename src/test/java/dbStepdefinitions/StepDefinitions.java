package dbStepdefinitions;

import io.cucumber.java.en.Given;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {
    String url="jdbc:sqlserver://184.168.194.58:1433;databaseName=hotelmycamp ; user=techproed;password=P2s@rt65";
    String username="techproed";
    String password="P2s@rt65";
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Given("kullanici HMC veri tabanina baglanir")
    public void kullanici_hmc_veri_tabanina_baglanir() throws SQLException {
        //database e baglanti kuruyoruz
        connection= DriverManager.getConnection(url,username,password);
        statement=statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

    }
    @Given("kullanici {string} tablosundaki {string} verilerini alir")
    public void kullanici_tablosundaki_verilerini_alir(String table, String field) throws SQLException {
        //Query calistiriyoruz
        //select Price from tHOTELROOM
        String readQuery="select "+field+" from "+table;
        resultSet=statement.executeQuery(readQuery);

    }

    @Given("kullanici {string} sutunundaki verileri okur")
    public void kullanici_sutunundaki_verileri_okur(String field) throws SQLException {
        // resultSet bizim su ana kadar kullandigimiz set yapisinda degildir
        //resultSet iterator ile calisir

        //resultsetteki bilgileri okumak istiyorsaniz
        // once iterator i istediginiz yere gondermelisiniz

        resultSet.first(); //bu komut iteratoru ilk elemente goturur gitti ise true, gidemezse false doner
        //iterator istenen konuma gidince artik elementi yazdirabiliriz

        System.out.println(resultSet.getString(field));
        //ikinci oda fiyatini gormek istersek
        // iterator'i yollamamiz lazim
        resultSet.next();
        System.out.println(resultSet.getString(field));
        System.out.println(resultSet.next());//true dondurur
        System.out.println(resultSet.getString(field)); //resulSet.next() nerede olursa olsun iteratoru bir sonrakine gecirir

        System.out.println("==========================");
        //resultSet ile calisirken iterator konumunu kontrol etmek zorundayiz
        //eger birinci elemana donmeden listeyi yazdirmaya devam edersem
        // kalsigi yerden devam edip 4. element ve sonrasini yazdirir
        resultSet.absolute(0);
       while(resultSet.next()){
           System.out.println(resultSet.getString(field));
       }
       //price sutununda kac data oldugunu bulalim
        //while loop ile resultset.next() false donunceye kadar gittik
        //dolayisi ile artik iterator sonda

       resultSet.last();
        System.out.println(resultSet.getRow());

        //su anda tum price bilgileri uzerinde
        // eger bu bilgilerle birden fazla test yapacaksak
        //bu bilgileri Java ortamina almakta fayda var
        //ornegin bir List<Double> olusturup
        //tum price verilerini bu listeye ekleyebiliriz
        //boylece bir java objesi olan List sayesinde
        //price degerleri uzerinde istedigimiz testleri yapabiliriz

        resultSet.absolute(0);
        List<Double> priceList=new ArrayList<>();
        while(resultSet.next()){
            priceList.add(resultSet.getDouble(field));
        }
        System.out.println(priceList);
    }
}
