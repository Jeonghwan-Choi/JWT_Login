package com.techon.login.repository;

import com.techon.login.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByNameidAndDeletedAtIsNull(String nameid); // Example query method
  Optional<Member> findByIdAndDeletedAtIsNull(Long id);
}
