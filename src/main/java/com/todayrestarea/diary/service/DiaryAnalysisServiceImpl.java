package com.todayrestarea.diary.service;

import com.todayrestarea.admin.model.dto.MusicRequest;
import com.todayrestarea.admin.model.entity.Emotion;
import com.todayrestarea.admin.model.entity.Movie;
import com.todayrestarea.admin.model.entity.Music;
import com.todayrestarea.admin.repository.JpaEmotionRepository;
import com.todayrestarea.admin.service.MovieService;
import com.todayrestarea.admin.service.MusicService;
import com.todayrestarea.diary.entity.Diary;
import com.todayrestarea.diary.model.DiaryAnalysis;
import com.todayrestarea.diary.model.RecommendMovie;
import com.todayrestarea.diary.model.RecommendMusic;
import com.todayrestarea.diary.repository.DiaryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class DiaryAnalysisServiceImpl implements DiaryAnalysisService{
    final private DiaryRepository diaryRepository;
    final private JpaEmotionRepository emotionRepository;
    final private MusicService musicService;
    final private MovieService movieService;
    @Override
    public DiaryAnalysis analyzeDiary(Long diaryId){
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        /**
         * 다음 외부 API 로 감정 컨텐츠 추가 및 일기 감정 수정
         * {
         * 	"emotionalType" : int,
         * 	"movieList" : [string],
         * 	"musicList": [
         *                {
         * 			"musicTitle": string,
         * 			"singer" : string
         *        }
         * 	]
         * }
         *
         * //분노 0
         * //슬픔 1
         * //불안 2
         * //상처 3
         * //당황 4
         * //기쁨 5
         */
        DiaryAnalysis result = new DiaryAnalysis();
        if (diary.isPresent()) {

            //임시 목업
            Long emotionAPIres=2l;
            List<String> apiRecommendMovieList = new ArrayList<>();
            List<APIrecommendMusic> apiRecommendMusicList = new ArrayList<>();
            apiRecommendMovieList.add("name1");
            apiRecommendMovieList.add("name2");
            apiRecommendMovieList.add("name3");
            apiRecommendMusicList.add(new APIrecommendMusic("도망가자","선우정아"));
            apiRecommendMusicList.add(new APIrecommendMusic("우산","윤하"));
            apiRecommendMusicList.add(new APIrecommendMusic("감사","김동률"));


            /**
             * return 에 필요한 컨텐츠 초기화
             */
            //api res 음악,영화 - 감정 매칭 후 저장
            for (String rmdMovie : apiRecommendMovieList) {
               // Optional<Movie> movie=movieService.saveMovie()
                result.getRecommendMovies().add(new RecommendMovie("temp","temp","temp"));
            }
            for (APIrecommendMusic rmdMusic : apiRecommendMusicList) {
                Optional<Music> music = musicService.isExist(rmdMusic.getTitle(), rmdMusic.getArtist());
                Long resultIdx=0l;

                /**
                 * 음악 없으면 저장
                 */
                if (music.isEmpty()) {
                    resultIdx=musicService.saveMusic(new MusicRequest(
                            rmdMusic.getTitle(),
                            rmdMusic.getArtist(),
                            emotionAPIres
                    ));
                }else{
                    resultIdx=music.get().getMusicId();
                }

                /**
                 * 해당 아이템에 해당하는 음악내용 조회후 결과 모델에 저장
                 */
                if (resultIdx != -1l && resultIdx != null) {
                    Optional<Music> musicRes=musicService.findById(resultIdx);
                    result.getRecommendMusics().add(
                            new RecommendMusic(
                                    musicRes.get().getTitle()
                                    ,musicRes.get().getArtist()
                                    ,musicRes.get().getPosterUrl()
                                    ,musicRes.get().getInfoUrl()
                            )
                    );
                }
            }
            /**
             * return 에 필요한 컨텐츠 초기화 - end
             */



            Optional<Emotion> emotion = emotionRepository.findById(emotionAPIres);
            /**
             * 감정분석 미완료된 일기는 감정분석 결과를 넣어준다
             */
            if (diary.get().getEmotion()==null&&emotion.isPresent()) {
                diary.get().setEmotion(emotion.get());
            }
            result.setCreatedData(diary.get().getCreatedDate());
            result.setEmotionId(emotion.get().getEmotionId());
            result.setEmotionName(emotion.get().getEmotionName());

            return result;
        }else{
            return null;
        }
    }
/**
 * "result": {
 *     "createdDate": "string",
 *     "emotionId": "int",
 *     "recommendMusicList": [
 *       {
 *         "title": "string",
 *         "artist": "string",
 *         "poster": "string"
 *       }
 *     ],
 *     "recommendMovieList": [
 *       {
 *         "title": "string",
 *         "poster": "string",
 *         "url": "string"
 *       }
 *     ]
 *   }
 */
}
@Getter
@Setter
@AllArgsConstructor
class APIrecommendMusic{
    private String title;
    private String artist;
}