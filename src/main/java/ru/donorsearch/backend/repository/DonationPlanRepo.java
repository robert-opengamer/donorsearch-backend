package ru.donorsearch.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.donorsearch.backend.entity.DonationPlan;

public interface DonationPlanRepo extends JpaRepository<DonationPlan, Long> {

}
