package com.techon.login.service;

import com.techon.login.entity.Authority;
import com.techon.login.entity.Member;
import com.techon.login.entity.Role;
import com.techon.login.repository.AuthorityRepository;
import com.techon.login.repository.MemberRepository;
import com.techon.login.repository.RoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final MemberRepository memberRepository;

    public List<Role> getAll() {
      return roleRepository.findAll();
    }

    public Authority insertAuthority(String role, Long memberId) throws Exception {
      Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
          .orElseThrow(() -> new RuntimeException("Member not found"));

      List<Authority> authorities = authorityRepository.findByMemberAndRole(member, role);
      if(!authorities.isEmpty()) {
        authorityRepository.deleteAll(authorities);
      }

      Authority authority = new Authority(member, role);

      return authorityRepository.save(authority);
    }

}
