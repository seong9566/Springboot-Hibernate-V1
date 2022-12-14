package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserReqDto.UpdateReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.dto.UserRespDto.UpdateRespDto;
import site.metacoding.white.dto.UserRespDto.UserDetailRespDto;
import site.metacoding.white.util.SHA256;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SHA256 sha256;

    // 응답의 DTO는 서비스에서 만든다.
    @Transactional // 트랜잭션이 붙이지 않으면 영속화 되어 있는 객체가 flush가 안됨.
    public JoinRespDto save(JoinReqDto joinReqDto) {
        // 비밀번호 해시
        String encPassword = sha256.encrypt(joinReqDto.getPassword());
        joinReqDto.setPassword(encPassword);

        // 회원정보 저장
        User userPS = userRepository.save(joinReqDto.toEntity());

        // DTO 리턴
        return new JoinRespDto(userPS);
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginReqDto loginReqDto) {

        String encPassword = sha256.encrypt(loginReqDto.getPassword());
        User userPS = userRepository.findByUsername(loginReqDto.getUsername());

        if (userPS.getPassword().equals(encPassword)) {
            return new SessionUser(userPS);
        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
        }
    }

    @Transactional(readOnly = true)
    public UserDetailRespDto findById(Long id) {
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isPresent()) {
            UserDetailRespDto userDetailRespDto = new UserDetailRespDto(userOP.get());
            return userDetailRespDto;
        } else {
            throw new RuntimeException("해당 " + id + "로 상세보기를 할 수 없습니다.");
        }
    } // 트랜잭션 종료

    @Transactional
    public UpdateRespDto update(UpdateReqDto updateReqDto) {
        Long id = updateReqDto.getId();
        Optional<User> userOP = userRepository.findById(id);
        if (userOP.isPresent()) {
            User userPS = userOP.get();
            userPS.update(updateReqDto.getUsername(), updateReqDto.getPassword());
            return new UpdateRespDto(userPS);
        } else {
            throw new RuntimeException("해당 " + id + "로 수정을 할 수 없습니다.");
        }
    }
}
