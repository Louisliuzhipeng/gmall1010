package com.chirping.gmall.payment.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 15211
 */
@Configuration
public class AlipayConfig {

    public static String app_id = "2016101300677129";

    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoPhncK6xGcCdlb7j+vfQ59qZOFS1sVXDMV/mYo61nDv0zLY4q4kWTBgtNGMB/c0NT5zUMhkrzgud74WM122IbQrWf6u2wWs1T7IIDxaR1Bq9aUlIAFP1Pl8ZqwvtMrfD0kU4bLa4piz/AbbKmRYiR8sV6gIKLC9/kzeAaBLrDdqTVvTCpgdi029DRQ4ThtdWBxX5fEjfHu8E+A84+tjNmC1ZusreezChs/98wEkxZB6y1PtkPxXZ3RZjy+FjQhAe/V05H6OnTotQkjK01a4X6lsDQvJmhFvdawN7V0sSoc7k4isEOMuq1XxDESd3nB/Pdg71Lcc7R16jgXBW5l5bxAgMBAAECggEAVEOGQDe/eSgDE1L5nN0WzVEzyjfhIDlK3Y3SfRi9aLihKFjQs7nQjuqgDmUvRKY+Om+6qiM4tu4BcpJYVkkiwfSPcyD/GyzfWgFCvRDoZZSLjvlcCJ56oLqrrh0qMCWOYW/FZudiK3yK2Z4XtB9vX2nwboKe33V7FeXnMCDeSbb6SYCnLI7PUSM2WmEC55wNaez9IGO53JB7nfXIZuEjeyecYadWVjyM+1y0X2GQ7muCNmiwadZzBlbUESQvyddb4r0cYPeaLaoEIHE2bu3yTOC6p3132O8Beelk0+iiiwTguI4mewVxbMZ16+NsZmlmCitW06yncOetEzyfHSd1YQKBgQDeIzdrag9rmHOXdCCKGz8XqXj4N3TSWIF3jgfWt/31sfUc5KVT9Swnf7gC2d4nl5++Czv9W6E5P9vChGCGgHmLpZNaD8sF1a+ONt4fvewzyQOPwaLBbaQPCCwS+TCEY788xHP0EyVpLCWUDxuzL4HeY5z38r1sLqh1KENXIPnuGwKBgQDB46pj36BhQbLZu8rWxPl6mcMULriVCdH290n4rOR1oweA6Ey3+Zc+UpgpvCun2BUhn+F8qKuITPyxXt4xqkS3K7oaS02I20oMYfECo/MdEypv0zPOKwjh44+gpO+xe92DBlkR6ozg6w7p8LFwtTt9NWH9ciJMUdSPaidzde+v4wKBgQCbjsHSgLVHvbzExNe8nxPxuAY0nHE8lCol1Np8bOB1XCewEhYM01YWWwQ7SuKdizqcVTWvWKlwc92rPBR0XCfBuTOVEBY2gcuspZOrWs67/Pp3gzw8hFEtDaX/q21kaI2VOmWQ+I9sNTQjmOjm9E/Xsp5LrDsmNQ5W7NkrfLZJHQKBgCegMeirgYme9cCOIsx2AU7PMhFj4xGCqkjBDXsnSqHIKfZsg5FuDz0LgFG3RWzOOrNei6UAi94VbeeKLfsUg2q0IxrQnQ4FU0LomWsiusMMcbXdAk92eCBCc84vJ1WsrP7kQzvoJdap86hYKsuQHpJeYKJ9uwjCCKEu3tU4Gz5lAoGAVDg8wdp+6mQ4qPOoOIqbxEYLA/PqYq9wtWUOgvclilTOzEiqqaRBTSPkZmlr42kA5lDZDZc6gEnFrJLXJsKhr7rsvoaokjeIS3QOyYUpL33RRy+CoMwfVMRU9msHbFQRy/a/CKqTAsdy1Ko72Zz3p4EDuxVZmwPF+VTAMoS9s1s=";

    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAokVquJKJ3CVN3K9qgcZ8wVMRx2P1kF4Xmm0QFTNOhHD0v+sj7S6gjj3XUZqtPdp+X3bTRB0J4z26ng6X2ODwui4hJ3qOOPSAd9g9NjgcbMBHKU7bL7hGSN/Uoj0U09Aa+eYpi68RW5V9j6Q2jy30EvwtH5/DpsTA8c/0EPSLRp3RrTEnLcnwGlTQM1Dv/ChhdrfPf5lWq0D5Eh2C/wdVmknmMC2ideZyOSUsnHWNx+aRbSicYL1CkxR6spoBjFIpDdCCnke5qa28f+W+BicIjfJZcrh6nKjbdcS1IPOSM0MwZVO8LGjSWQDhH4CSemCdX5wHaODL+YVHfuvlQIrLoQIDAQAB";

    public static String notify_url = "http://chihpeng.zicp.vip//callback/notifyUrl";

    public static String return_url = "http://chihpeng.zicp.vip//callback/returnUrl";

    public static String sign_type = "RSA2";

    public static String charset = "utf-8";

    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public static String log_path = "D:\\";

    @Bean
    public AlipayClient getAlipayClient() {
        System.out.println(app_id);
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id,
                merchant_private_key, "json", charset, alipay_public_key, sign_type);
        return alipayClient;
    }
}

