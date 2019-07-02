package com.fintechtinkoff.homework.generateusers;


import com.fintechtinkoff.homework.generateusers.factory.*;
import com.fintechtinkoff.homework.generateusers.human.Human;
import com.fintechtinkoff.homework.generateusers.writers.*;

import java.util.*;

public class Main  {
    public static void main(String[] args)
            throws Exception{
        IHumanFactory humanFactory;
        List<Human> humans;
        DataBaseWriter dataBaseWriter = new DataBaseWriter();
        ExcelWriter excelWriter = new ExcelWriter();
        Pdf_Writer pdfwriter = new Pdf_Writer();
        Random rnd = new Random();
        try {
            humanFactory = new HumanWebFactory();
            humans = humanFactory.createHumans(rnd.nextInt(300));
            dataBaseWriter.writeHumans(humans);
        } catch (Exception ex){
            System.out.println("Не удалось скачать файлы с пользователями с веб сайт по ап. \n Приступаем к генерации пользователей из бд");
            try {
                humanFactory = new HumanDataBaseFactory();
                humans = humanFactory.createHumans(rnd.nextInt(300));
            } catch (Exception e){
                System.out.println("Не удалось скачать файлы с пользователями с базы данных. \n Приступаем к генерации пользователей из оффлайн");
                humanFactory = new HumanManualFactory();
                humans = humanFactory.createHumans(rnd.nextInt(300));
            }
        }
        excelWriter.writeHumans(humans);
        pdfwriter.writeHumans(humans);
    }
}
