package engine.repository;

import engine.domain.QuizCompletedInfo;
import engine.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletedInfoRepository extends JpaRepository<QuizCompletedInfo, Integer> {

    Page<QuizCompletedInfo> findAllByUser(User user, Pageable pageable);
}
