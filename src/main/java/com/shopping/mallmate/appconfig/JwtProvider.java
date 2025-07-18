package com.shopping.mallmate.appconfig;

import com.shopping.mallmate.entity.User;
import com.shopping.mallmate.entity.enums.USER_ROLE;
import com.shopping.mallmate.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    private SecretKey key = Keys.hmacShaKeyFor(JwtConstants.JWT_SECRET_KEY.getBytes());

    @Autowired
    private UserRepository userRepository;

    public String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        if (roles.equals(USER_ROLE.OWNER.name())) {
            User user = userRepository.findUserByEmail(auth.getName());
            if (user != null && !user.getStores().isEmpty()) {
                String storeId = user.getStores().get(0).getId();
                String jwt = Jwts.builder()
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(new Date().getTime() + 86400000))
                        .claim("email", auth.getName())
                        .claim("authorities", roles)
                        .claim("storeId", storeId)
                        .signWith(key)
                        .compact();
                return jwt;
            } else {
                String jwt = Jwts.builder()
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(new Date().getTime() + 86400000))
                        .claim("email", auth.getName())
                        .claim("authorities", roles)
                        .signWith(key)
                        .compact();
                return jwt;
            }
        }

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email", auth.getName())
                .claim("authorities", roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromJwtToken(String jwt) {
        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email = String.valueOf(claims.get("email"));

        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : collection) {
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}
