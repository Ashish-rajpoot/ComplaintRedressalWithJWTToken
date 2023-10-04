package com.spring.security.servicesimp;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateServiceImp {


   public Date getCurrentDate(){
//       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)");
//       Date date = simpleDateFormat.parse(complaintDto.getDate());
       Date currentDate = new Date();
       System.out.println(currentDate);
        return currentDate;
    }
}
