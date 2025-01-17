package org.example.url_shortener.refresher;

import org.example.url_shortener.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LinksRefresher {
    private final UserRepository usersRepository;

    @Autowired
    public LinksRefresher(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // раз в 60 секунд удалять записи у которых expired_at < текущего времени
    @Scheduled(fixedDelay = 60_000)
    public void refreshPricingParameters() {
        usersRepository.deleteExpiredUsers();

        System.out.println("All old link deleted");
    }
}
