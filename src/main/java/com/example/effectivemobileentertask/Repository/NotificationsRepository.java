package com.example.effectivemobileentertask.Repository;

import com.example.effectivemobileentertask.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {

}
