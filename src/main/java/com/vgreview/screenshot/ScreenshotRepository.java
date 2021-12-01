package com.vgreview.screenshot;

import com.vgreview.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {
    List<Screenshot> findAllByApiIdIn(List<Long> screenshots);
}
