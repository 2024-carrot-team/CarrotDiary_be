package com.example.carrotdiary.diary.repository;

import static com.example.carrotdiary.diary.entity.QDiary.diary;
import static com.example.carrotdiary.member.entity.QMember.member;
import static com.example.carrotdiary.post.entity.QPost.post;
import static com.example.carrotdiary.postdiary.entity.QPostDiary.postDiary;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.diary.entity.QDiary;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Diary> findByPostDiaryId(Long postDiaryId) {
        return jpaQueryFactory
                .selectFrom(diary)
                .where(diary.postDiary.id.eq(postDiaryId))
                .fetch();
    }

    @Override
    public Page<Diary> findPostDiaryAndDiaryByPostIdPaging(Long postId, Pageable pageable) {
        // 서브 쿼리로 각 PostDiaryId에 대한 최소 createDate 찾기
        QDiary diarySub = new QDiary("diarySub");

        // 메인 쿼리: 서브 쿼리 결과를 사용하여 조건 적용
        List<Diary> content = jpaQueryFactory
                .selectFrom(diary)
                .join(diary.postDiary, postDiary).fetchJoin()
                .where(diary.postDiary.post.id.eq(postId)
                        .and(diary.createDate.in(
                                JPAExpressions
                                        .select(diarySub.createDate.min())
                                        .from(diarySub)
                                        .where(diarySub.postDiary.id.eq(diary.postDiary.id))
                        )))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트 쿼리: 필터링 된 결과의 총 수를 계산
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(diary.count())
                .from(diary)
                .where(diary.postDiary.post.id.eq(postId)
                        .and(diary.createDate.in(
                                JPAExpressions
                                        .select(diarySub.createDate.min())
                                        .from(diarySub)
                                        .where(diarySub.postDiary.id.eq(diary.postDiary.id))
                        )));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public String findMemberEmailByDiaryId(Long diaryId) {
        return jpaQueryFactory
                .select(member.email)
                .from(diary)
                .join(diary.postDiary, postDiary)
                .join(postDiary.post, post)
                .join(post.member, member)
                .where(diary.id.eq(diaryId))
                .fetchOne();
    }
}
