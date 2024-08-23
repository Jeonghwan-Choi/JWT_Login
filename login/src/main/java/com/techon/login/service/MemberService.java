package com.techon.login.service;

import com.techon.login.entity.Member;
import com.techon.login.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public Optional<Member> findById(Long id) {
    return memberRepository.findByIdAndDeletedAtIsNull(id);
  }

}
