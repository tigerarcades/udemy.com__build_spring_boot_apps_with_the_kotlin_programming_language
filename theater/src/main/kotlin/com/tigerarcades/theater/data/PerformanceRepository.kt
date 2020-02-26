package com.tigerarcades.theater.data

import com.tigerarcades.theater.domain.Performance
import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceRepository : JpaRepository<Performance, Long> 