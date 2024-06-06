package com.example.gsmoa.Community.repository;

import com.example.gsmoa.Community.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPostOwnerIdAndCheckedFalse(Long userId);
}
