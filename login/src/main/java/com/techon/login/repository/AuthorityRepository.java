package com.techon.login.repository;

import com.techon.login.entity.Authority;
import com.techon.login.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

  List<Authority> findByMember(Member member);
}
