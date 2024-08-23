package com.techon.login.service;

import com.techon.login.common.Sha256Util;
import com.techon.login.config.exception.CustomException;
import com.techon.login.dto.RegistrationRequest;
import com.techon.login.entity.Authority;
import com.techon.login.entity.Member;
import com.techon.login.repository.AuthorityRepository;
import com.techon.login.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationService {

  private final MemberRepository memberRepository;
  private final AuthorityRepository authorityRepository;

  @Transactional
  public Member register(RegistrationRequest request) {
    // Hash the password using SHA-256
    String hashedPassword = Sha256Util.hash(request.getPassword());

    Optional<Member> findMember = memberRepository.findByNameidAndDeletedAtIsNull(request.getNameid());

    if(findMember.isPresent()) {
      throw new CustomException("Already Exist User");
    }

    // Create a new Member entity
    Member member = Member.builder()
        .name(request.getName())
        .nameid(request.getNameid())
        .password(hashedPassword)
        .part(request.getPart())
        .birth(request.getBirth())
        .gender(request.getGender())
        .email(request.getEmail())
        .phone(request.getPhone())
        .salary(request.getSalary())
        .build();

    // Save the member to the database
    member = memberRepository.save(member);

    List<String> role = request.getRole();
    if (role.isEmpty()) {
      throw new CustomException("Role cannot be null");
    }

    List<Authority> authority = new ArrayList<>();


    for (String a: role) {
      authority.add(Authority.builder()
          .role(a)
          .member(member)
          .build());
    }

    member.setAuthorities(authorityRepository.saveAll(authority));

    return member;
  }
}
