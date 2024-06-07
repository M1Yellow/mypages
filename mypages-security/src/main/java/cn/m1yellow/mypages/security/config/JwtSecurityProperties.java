package cn.m1yellow.mypages.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtSecurityProperties {

    /** Request Headers: Authorization */
    private String tokenHeader;

    /** 令牌前缀，最后留个空格 Bearer */
    private String tokenStart;

    /** JWT 加解密使用的密钥 */
    private String base64Secret;

    /** 令牌过期时间，单位：秒 */
    private Long expiration;

    /** 返回令牌前缀 */
    public String getTokenStart() {
        return tokenStart + " ";
    }
}
