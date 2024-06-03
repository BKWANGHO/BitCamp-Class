package com.jsggun.api.user.service;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SubstringDemo {


    @Test
    public void 문자열_분할() throws Exception {

        String str = "a,d,c";
        str = new StringBuilder(str).append(",d,e,f").toString();
        System.out.println(str);
        StringTokenizer arr = new StringTokenizer(str, ",");
        System.out.println(arr.toString());
        System.out.println(arr.hasMoreTokens());
        assertThat(arr.hasMoreTokens()).isEqualTo(true);


    }

    @Test
    public void 주민번호로_성별_구분()throws Exception{
        String human1 = "970301-1";
        String human2 = "950201-2";
        String human3 = "020301-3";
        String human4 = "090301-4";
        String human5 = "730301-5";
        String human6 = "820301-6";
        String human7 = "120301-7";
        String human8 = "050301-8";

        String[] arr = {human1,human2,human3,human4,human5};
//      주민번호를 통해 나이와 성별을 출력하시오, 단 나이는 만나이로 표기하시오.
        for(int i=0; i<arr.length;i++){
            StringTokenizer st = new StringTokenizer(arr[i],"-");
            String a = st.nextToken();
            String b = st.nextToken();
            System.out.println("나이 : " +  ", 성별 : " +getGender(b) );

//            Long tmp = Long.parseLong(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString()) - Long.parseLong(a);
//            tmp += tmp < 0 ? 1000000L : 0;
//            Integer age = Integer.parseInt(String.valueOf(tmp).substring(0, 3));
        }
    }
    private String getGender(String ssn){
        return switch (ssn){
            case "1","3","5" -> "남자";
            case "2","4","6" -> "여자";
            default -> "Error";
        };
    }

    @Test
    public void getAge(String ssn){
        LocalDate date = LocalDate.now();

        assertThat(date.getYear()).isEqualTo(2024);
        assertThat(date.getMonthValue()).isEqualTo(4);
        assertThat(date.getDayOfMonth()).isEqualTo(24);

        ssn = new StringBuilder(ssn)
                .insert(0,ssn.charAt(0)=='9' ? "19" : "20").toString();

        long y = Long.parseLong(new StringBuilder(ssn).delete(4,8).toString());
        long m = Long.parseLong(new StringBuilder(ssn).delete(0,4).delete(2,4).toString());
        long d = Long.parseLong(new StringBuilder(ssn).delete(0,6).toString());

        String age = String.valueOf(date.minusYears(y).minusMonths(m).minusDays(d).getYear());

//        return age;
    }
    @Test
    public void birthYear(){
        String ssn = "970301-1";

        int birthYear = Integer.parseInt(ssn.substring(0,2));

        birthYear += Integer.parseInt(ssn.substring(7))<=2 ? 1900 : 2000;

        assertThat(birthYear).isEqualTo(1997);


        String ssn2 = "020101-4";

        int birthYear2 = Integer.parseInt(ssn2.substring(0,2));

//        assertThat(birthYear2).isEqualTo(2002);
    }


    @Test
    public void oldAge(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        String ssn = "950401-1";

        int birthYear = Integer.parseInt(ssn.substring(0,2));

        birthYear = switch (ssn.charAt(7)){
            case '1','2','5','6' -> birthYear+1900;
            case '3','4','7','8' -> birthYear+2000;
            default -> birthYear+1800;};

        int age = year - birthYear;

        int birthMonth = Integer.parseInt(ssn.substring(2,4));
        int birthDay = Integer.parseInt(ssn.substring(4,6));

        if(birthMonth > month){
            age--;
        }else if(birthMonth == month){
            if(birthDay > day){
                age--;
            }
        }
        System.out.println(age);
        assertThat(age).isEqualTo(29);
    }
    @Test
    public void getAgeUsingLambda(){
        String ssn = "920401-1";
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int birthMonth = Integer.parseInt(ssn.substring(2,4));
        int birthDay = Integer.parseInt(ssn.substring(4,6));
        Long fullYear = Long.parseLong(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        var age = Stream.of(ssn)
                .map(i->Integer.parseInt(i.substring(0,6)))
                .map(i->switch (ssn.charAt(7)){
                    case '1','2','5','6' -> "19"+i;
                    case '3','4','7','8' -> "20"+i;
                    default -> "18"+i;})
                .peek(System.out::println)
                .map(i-> Long.parseLong(i))
                .map(i->(fullYear -i)/10000)
                .peek(System.out::println)
                .findFirst().get();



    }

}
