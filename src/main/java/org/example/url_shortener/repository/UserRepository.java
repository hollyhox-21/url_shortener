package org.example.url_shortener.repository;

import jakarta.transaction.Transactional;
import org.example.url_shortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserUuidAndShortUrl(UUID userUuid, String shortUrl);
    Optional<User> findOneByUserUuid(UUID userUuid);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.countRedirect = :newCountRedirect WHERE u.userUuid = :userId AND u.shortUrl = :shortUrl")
    void updateFieldByUserIdAndShortUrl(
            @Param("userId") UUID userId,
            @Param("shortUrl") String shortUrl,
            @Param("newCountRedirect") int newCountRedirect
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.expiredAt < CURRENT_TIMESTAMP")
    void deleteExpiredUsers();
}
