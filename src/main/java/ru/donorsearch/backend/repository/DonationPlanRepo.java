package ru.donorsearch.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donorsearch.backend.entity.DonationPlan;

import java.util.List;

public interface DonationPlanRepo extends JpaRepository<DonationPlan, Long> {
    List<DonationPlan> findDonationPlansByPlanDate(String planDate);

}
