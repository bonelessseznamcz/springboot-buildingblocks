package com.flem.resthello.repository;

import com.flem.resthello.entity.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteById(@NonNull Long id);

    User getUSerByUserName(String userName);
}
