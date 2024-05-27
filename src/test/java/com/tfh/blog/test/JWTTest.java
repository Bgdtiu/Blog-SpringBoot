package com.tfh.blog.test;

import com.tfh.blog.utils.jwt.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * @author tang
 * @version 1.0
 * @since 2024/5/19 13:30
 * TODO: test
 */
@SpringBootTest
public class JWTTest {

    @Autowired
    private Token token;


    @Test
    public void test01() {

//        StringBuilder stringBuilder = new StringBuilder();
//
//        for (int i = 0; i < 513; i++) {
//            stringBuilder.append((int) (Math.random() * 1000 + 1));
//        }
//
//        System.out.println(stringBuilder);
//
//
//        SecretKey secretKey = Keys.hmacShaKeyFor(stringBuilder.toString().getBytes());
//        String token = Jwts.builder()
//                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
//                .signWith(secretKey)
//                .claim("userId", "123321")
//                .compact();
//        System.out.println(token);

    }

    @Test
    public void test02() {
//        String key = "375200855389463723517126791802284255853172797552745281860265789875298795876277457806355147514476829973817825486927288082134669083087946768663313514658733287833885956025071337084039499863269616168636599235110811835268977146307693376209348269815469240667446197321850712595285211726606542351464246800775391647138877525699457127298988451535778148297365510546861859475584454582222623381383607568327207636486250950932993375628696720643959631676253085182309351147019255874182495576333564152994827376126599693478708154890827208558414937454692372188657617549040190247975405863135478983414339774529102826468594134494135321378265308581358308265225387958152547607578142912703407745881000509943539421695827884410848476383417196385826325806924807947561978197992648557943595538415047113578277885538996593488051498639751994783573261566948176032660317393753412641761022529266678811494918172018495101786698361356894011833654461529518584253233767530721785621382938077037874344006348412549894086933883466916345967627630526671860484623160459669760927453498425562025344843908234905587367623630364777324125195124351504658756786158259923418341891970870166856524085343374150891729215917276934755186027360542835751868467658865951483260690927716426498032292960080332666934680979137024868437413845672626483328958425605388332268165698072067993357602291082396659521342931854324810176260241140212683296361362361412702159436116517456977482574740724888948630347279472434108566456799478442190968414388557863767890929245";
//        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());
//        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTYxMDUyMjksInVzZXJJZCI6IjEyMzMyMSJ9.0bZz5GRoeMjuBPJuut_HNN-OLbN43177Vb3dOhe3mJUDP5wCorYRZneklOxCbAjiSzewtCRDgcVcV70DcRKOig";
//
//        Jws<Claims> claimsJws = Jwts.parser()
//                .verifyWith(secretKey)
//                .build()
//                .parseSignedClaims(token);
//
//        Claims payload = claimsJws.getPayload();
//
//        String userId = (String) payload.get("userId");
//        System.out.println(userId);
    }

    @Test
    public void test03() {
//        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3MTYxMDUyMjksInVzZXJJZCI6IjEyMzMyMSJ9.0bZz5GRoeMjuBPJuut_HNN-OLbN43177Vb3dOhe3mJUDP5wCorYRZneklOxCbAjiSzewtCRDgcVcV70DcRKOig";
//        String[] s = token.split("Bearer ");
//        System.out.println(s[0]);
//        System.out.println(s[1]);
    }
}
