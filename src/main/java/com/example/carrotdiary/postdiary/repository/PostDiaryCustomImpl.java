package com.example.carrotdiary.postdiary.repository;

import static com.example.carrotdiary.diary.entity.QDiary.diary;
import static com.example.carrotdiary.image.entity.QImage.image;
import static com.example.carrotdiary.member.entity.QMember.member;
import static com.example.carrotdiary.post.entity.QPost.post;
import static com.example.carrotdiary.postdiary.entity.QPostDiary.postDiary;

import com.example.carrotdiary.diary.dto.DiaryMainFlatDto;
import com.example.carrotdiary.diary.entity.DiarySearch;
import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.follow.repository.FollowRepository;
import com.example.carrotdiary.image.dto.ImageInfo;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.postdiary.dto.PostDiaryFlatDto;
import com.example.carrotdiary.postdiary.dto.QPostDiaryFlatDto;
import com.example.carrotdiary.postdiary.entity.Visibility;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class PostDiaryCustomImpl implements PostDiaryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final FollowRepository followRepository;

    @Override
    public Page<PostDiaryFlatDto> findAllPostDiariesPaging(Pageable pageable, Member currentMember) {
        List<PostDiaryFlatDto> postDiaries = fetchPostDiaryFlatDtos(null, null, pageable, currentMember);

        setRelatedDiariesAndImages(postDiaries);

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(postDiary.count())
                .from(postDiary);

        return PageableExecutionUtils.getPage(postDiaries, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<PostDiaryFlatDto> findPostDiaryBySearch(DiarySearch diarySearch, String searchContent,
                                                        Pageable pageable, Member currentMember) {
        List<PostDiaryFlatDto> postDiaries = fetchPostDiaryFlatDtos(diarySearch, searchContent,
                pageable, currentMember);

        setRelatedDiariesAndImages(postDiaries);

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(postDiary.count())
                .from(postDiary)
                .where(getSearchPredicate(diarySearch, searchContent));

        return PageableExecutionUtils.getPage(postDiaries, pageable, countQuery::fetchOne);
    }

    @Override
    public String findMemberEmailByPostDiaryId(Long postDiaryId) {
        return jpaQueryFactory
                .select(member.email)
                .from(postDiary)
                .join(postDiary.post, post)
                .join(post.member, member)
                .where(postDiary.id.eq(postDiaryId))
                .fetchOne();
    }

    private List<PostDiaryFlatDto> fetchPostDiaryFlatDtos(DiarySearch diarySearch, String searchContent,
                                                          Pageable pageable, Member currentMember) {
        JPAQuery<PostDiaryFlatDto> query = jpaQueryFactory
                .select(new QPostDiaryFlatDto(
                        member.id,
                        member.nickname,
                        image.imageUrl,
                        post.id,
                        postDiary.id,
                        postDiary.visibility
                ))
                .from(postDiary)
                .join(postDiary.post, post)
                .join(post.member, member)
                .join(member.image, image)
                .where(isVisibleByUser(currentMember))
                .orderBy(postDiary.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (diarySearch != null && searchContent != null) {
            query.where(getSearchPredicate(diarySearch, searchContent));
        }

        return query.fetch();
    }

    private void setRelatedDiariesAndImages(List<PostDiaryFlatDto> postDiaries) {
        if (postDiaries.isEmpty()) {
            return;
        }

        List<Long> postDiaryIds = postDiaries.stream()
                .map(PostDiaryFlatDto::getPostDiaryId)
                .collect(Collectors.toList());

        List<Tuple> diariesWithImages = jpaQueryFactory
                .select(diary.id, diary.content, diary.postDiary.id, image.id, image.imageUrl)
                .from(diary)
                .leftJoin(diary.images, image)
                .where(diary.postDiary.id.in(postDiaryIds))
                .fetch();

        Map<Long, Map<Long, DiaryMainFlatDto>> diaryMap = new HashMap<>();

        for (Tuple tuple : diariesWithImages) {
            Long postDiaryId = tuple.get(diary.postDiary.id);
            Long diaryId = tuple.get(diary.id);

            if (!diaryMap.containsKey(postDiaryId)) {
                diaryMap.put(postDiaryId, new HashMap<>());
            }

            Map<Long, DiaryMainFlatDto> diarySubMap = diaryMap.get(postDiaryId);

            if (!diarySubMap.containsKey(diaryId)) {
                DiaryMainFlatDto diaryDto = new DiaryMainFlatDto(diaryId, tuple.get(diary.content));
                diarySubMap.put(diaryId, diaryDto);
            }

            DiaryMainFlatDto diaryDto = diarySubMap.get(diaryId);
            ImageInfo imageInfo = new ImageInfo(tuple.get(image.id), tuple.get(image.imageUrl));
            diaryDto.getImageInfo().add(imageInfo);
        }

        postDiaries.forEach(dto -> {
            Map<Long, DiaryMainFlatDto> diarySubMap = diaryMap.get(dto.getPostDiaryId());
            List<DiaryMainFlatDto> diaries = diarySubMap != null
                    ? new ArrayList<>(diarySubMap.values())
                    : new ArrayList<>();
            dto.setDiaries(diaries);
        });
    }

    private BooleanExpression getSearchPredicate(DiarySearch diarySearch, String content) {
        return switch (diarySearch) {
            case USER -> searchByUser(content);
            case CONTENT -> searchByContent(content);
            case USER_CONTENT -> searchByUserOrContent(content);
        };
    }

    private BooleanExpression searchByUser(String content) {
        return postDiary.post.member.nickname.containsIgnoreCase(content);
    }

    private BooleanExpression searchByContent(String content) {
        return postDiary.diaries.any().content.containsIgnoreCase(content);
    }

    private BooleanExpression searchByUserOrContent(String content) {
        return searchByUser(content).or(searchByContent(content));
    }

    private BooleanExpression isVisibleByUser(Member currentMember) {
        BooleanExpression isPublic = postDiary.visibility.eq(Visibility.PUBLIC);
        BooleanExpression isOwnPostDiary = postDiary.post.member.email.eq(currentMember.getEmail());

        List<Long> followedUserIds = getFollowedUserIds(currentMember);
        BooleanExpression isFollowerPostDiary = postDiary.post.member.id.in(followedUserIds)
                .and(postDiary.visibility.eq(Visibility.FOLLOWERS));

        return isPublic.or(isOwnPostDiary).or(isFollowerPostDiary);
    }

    private List<Long> getFollowedUserIds(Member currentMember) {
        List<Follow> follows = followRepository.findByFollower(currentMember);
        return follows.stream()
                .map(f -> f.getFollowee().getId())
                .collect(Collectors.toList());
    }

}
