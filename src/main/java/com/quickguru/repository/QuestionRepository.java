package com.quickguru.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quickguru.model.Language;
import com.quickguru.model.Question;
import com.quickguru.model.Question.QStatus;
import com.quickguru.model.Tag;
import com.quickguru.model.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findAllByStatusInAndTagInAndLanguageInOrderByUpdatedOnDesc(Set<QStatus> status, Set<Tag> tags, Set<Language> languages);
	List<Question> findAllByStatusAndTagInAndLanguageInOrderByUpdatedOnDesc(QStatus approved, Set<Tag> tags, Set<Language> languages);

	List<Question> findAllByCreatedByOrderByUpdatedOnDesc(User user);

	List<Question> findAllByStatusOrderByUpdatedOnDesc(QStatus submitted);
	
	@Query("Select q from Answer a JOIN a.question q ON a.question=q WHERE a.createdBy=:expertUser order by q.updatedOn desc")
	public List<Question> fetchExpertAnsweredQuestions(@Param("expertUser") User expertUser);
	
	@Modifying
	@Query("update Question set status=:qStatus, approvedBy=:approvedBy, approvedOn=:approvedOn  where id=:questionId")
	public void adminActionToQuestion(@Param("questionId") long questionId, @Param("approvedBy") User approvedBy, 
			@Param("approvedOn") Timestamp approvedOn, @Param("qStatus") QStatus qStatus);

}