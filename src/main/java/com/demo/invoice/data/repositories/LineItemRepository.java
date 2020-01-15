package com.demo.invoice.data.repositories;

import com.demo.invoice.data.entities.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItem, Long> {
}
