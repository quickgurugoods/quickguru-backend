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

	@Query("select status, count(*) from Question group by status") 
	List<Object[]> mapCountOfStatus();
	
	@Query("select t.name, count(q.id) from Question q JOIN q.tag t ON q.tag=t group by t.id") 
	List<Object[]> mapCountOfTag();
	
	@Query("select l.name, count(q.id) from Question q JOIN q.language l ON q.language=l group by l.id") 
	List<Object[]> mapCountOfLanguage();
	
	//List<Question> findAllByStatusInAndTagInAndLanguageInOrderByUpdatedOnDesc(Set<QStatus> status, Set<Tag> tags, Set<Language> languages);
	List<Question> findAllByStatusAndTagInAndLanguageInOrderByUpdatedOnDesc(QStatus approved, Set<Tag> tags, Set<Language> languages);

	List<Question> findAllByCreatedByOrderByUpdatedOnDesc(User user);

	List<Question> findAllByStatusOrderByUpdatedOnDesc(QStatus submitted);
	
	@Query("Select q from Answer a JOIN a.question q ON a.question=q WHERE a.createdBy=:expertUser order by q.updatedOn desc")
	List<Question> fetchExpertAnsweredQuestions(@Param("expertUser") User expertUser);
	
	@Modifying
	@Query("update Question set status=:qStatus, approvedBy=:approvedBy, approvedOn=:approvedOn  where id=:questionId")
	void adminActionToQuestion(@Param("questionId") long questionId, @Param("approvedBy") User approvedBy, 
			@Param("approvedOn") Timestamp approvedOn, @Param("qStatus") QStatus qStatus);

	@Query("Select q from Question q WHERE q.status in (:status) and q.tag in (:tags) and "
			+ "q.language in (:languages) and (q.restricted=false or q.createdBy=:createdBy) order by q.updatedOn desc")
	List<Question> findAllQuestionsByUserPreferences(Set<QStatus> status, Set<Tag> tags, Set<Language> languages, User createdBy);
	

}