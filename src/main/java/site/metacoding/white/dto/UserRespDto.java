package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class UserRespDto {

    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;

        // 응답의 DTO는 생성자로 처리한다.
        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }

    @Getter
    @Setter
    public static class UserDetailRespDto {
        private Long id;
        private String username;

        public UserDetailRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }

    @Getter
    @Setter
    public static class UpdateRespDto {
        private String username;
        private String password;
        private Long id;

        public UpdateRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.password = user.getPassword();
        }
    }

}
