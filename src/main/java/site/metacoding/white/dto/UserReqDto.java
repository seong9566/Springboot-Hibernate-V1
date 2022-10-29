package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class UserReqDto {

    @Setter
    @Getter
    public static class JoinReqDto { // 로그인 전 로직들 전부다 앞에 엔티티 안붙임. POST /user -> /join
        private String username;
        private String password;

        public User toEntity() {
            return User.builder().username(username).password(password).build();
        }
    }

    @Setter
    @Getter
    public static class LoginReqDto {
        private String username;
        private String password;
        private Long id;// 서비스 로직
    }

    @Getter
    @Setter
    public static class UpdateReqDto {
        private String username;
        private String password;
        private Long id;

        public User toEntity() {
            return User.builder().username(username).password(password).id(id).build();
        }
    }

}
