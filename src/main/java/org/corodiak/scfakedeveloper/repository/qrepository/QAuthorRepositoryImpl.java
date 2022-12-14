package org.corodiak.scfakedeveloper.repository.qrepository;

import java.util.List;

import org.corodiak.scfakedeveloper.type.entity.Author;
import org.corodiak.scfakedeveloper.type.entity.QAuthor;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QAuthorRepositoryImpl implements QAuthorRepository {
	private final JPAQueryFactory queryFactory;
	private QAuthor qAuthor = QAuthor.author;

	@Override
	public List<Author> search(String keyword) {
		List<Author> results = queryFactory.selectFrom(qAuthor)
			.where(qAuthor.name.contains(keyword).or(qAuthor.description.contains(keyword)))
			.fetch();
		return results;
	}
}
