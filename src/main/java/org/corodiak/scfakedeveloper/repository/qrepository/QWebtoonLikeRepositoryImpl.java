package org.corodiak.scfakedeveloper.repository.qrepository;

import java.util.List;

import org.corodiak.scfakedeveloper.type.entity.QUser;
import org.corodiak.scfakedeveloper.type.entity.QWebtoon;
import org.corodiak.scfakedeveloper.type.entity.QWebtoonLike;
import org.corodiak.scfakedeveloper.type.entity.WebtoonLike;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QWebtoonLikeRepositoryImpl implements QWebtoonLikeRepository {

	private final JPAQueryFactory queryFactory;
	private QWebtoonLike qWebtoonLike = QWebtoonLike.webtoonLike;
	private QWebtoon qWebtoon = QWebtoon.webtoon;
	private QUser qUser = QUser.user;

	@Override
	public List<WebtoonLike> findByUserSeq(Long seq, Long start, Long display) {
		List<WebtoonLike> results = queryFactory.selectFrom(qWebtoonLike)
			.where(qWebtoonLike.user.seq.eq(seq))
			.innerJoin(qWebtoonLike.user, qUser)
			.innerJoin(qWebtoonLike.webtoon, qWebtoon)
			.fetchJoin()
			.orderBy(qWebtoonLike.createdDate.desc())
			.offset(start).limit(display)
			.fetch();
		return results;
	}

}
