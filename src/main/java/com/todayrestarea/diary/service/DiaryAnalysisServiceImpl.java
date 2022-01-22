package com.todayrestarea.diary.service;

import com.todayrestarea.admin.common.emotion.EmotionAnalysisApi;
import com.todayrestarea.admin.common.emotion.EmotionAnalysisResponse;
import com.todayrestarea.admin.common.emotion.MovieApiDto;
import com.todayrestarea.admin.common.emotion.MusicApiDto;
import com.todayrestarea.admin.model.dto.MovieRequest;
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

import javax.persistence.EntityManager;
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
    final private EmotionAnalysisApi emotionAnalysisApi;
    @Override
    public DiaryAnalysis getFullAnalysisResult(Long diaryId) throws Exception{
        DiaryAnalysis ret = analyzeDiary(diaryId);
        Long cnt=0l;
        while(ret!=null&&(ret.getRecommendMovies().size()<3||ret.getRecommendMusics().size()<3)){
            cnt++;
            ret=analyzeDiary(diaryId);
        }
        System.out.println("#################\nrequest cnt = " + cnt+"##################");
        return ret;
    }
    @Override
    public DiaryAnalysis analyzeDiary(Long diaryId) throws Exception{
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        DiaryAnalysis result = new DiaryAnalysis();
        if (diary.isPresent()) {

           Optional<EmotionAnalysisResponse> analysisResult = emotionAnalysisApi.getAnalysisResult(diary.get().getContents());
            System.out.println("analysisResult = " + analysisResult.get());
          // System.out.println("READY TO ANALYZE DIARY = " + analysisResult.get().toString());

            if (analysisResult.isEmpty()) {
                throw new Exception("감정 분석 실패");
            }
            /**
             * return 에 필요한 컨텐츠 초기화
             */
            //api res 음악,영화 - 감정 매칭 후 저장
            Long emotionIdx=analysisResult.get().getEmotionId();
            List<MovieApiDto> movieList = analysisResult.get().getMovieList();
            List<MusicApiDto> musicList = analysisResult.get().getMusicList();
            for (MovieApiDto rmdMovie : movieList) {
                Optional<Movie> movie = movieService.isExist(rmdMovie.getTitle(), rmdMovie.getDirector());
                Long resultIdx=movieService.saveMovie(new MovieRequest(
                        rmdMovie.getTitle(), rmdMovie.getDirector(), emotionIdx
                ));
                /**
                 * 해당 아이템에 해당하는 영화 조회후 결과 모델에 저장
                 */
                if (resultIdx != -1l && resultIdx != null) {
                    Optional<Movie> movieRes=movieService.findById(resultIdx);
                    result.getRecommendMovies().add( new RecommendMovie(
                            movieRes.get().getTitle() ,movieRes.get().getDirector(),movieRes.get().getPlot()
                            ,movieRes.get().getPosterUrl(),movieRes.get().getInfoUrl()
                            )
                    );
                }
            }
            for (MusicApiDto rmdMusic : musicList) {
                Optional<Music> music = musicService.isExist(rmdMusic.getTitle(), rmdMusic.getArtist());
                Long  resultIdx=musicService.saveMusic(new MusicRequest(
                        rmdMusic.getTitle(), rmdMusic.getArtist(), emotionIdx
                ));
                /**
                 * 해당 아이템에 해당하는 음악내용 조회후 결과 모델에 저장
                 */
                if (resultIdx != -1l && resultIdx != null) {
                    Optional<Music> musicRes=musicService.findById(resultIdx);
                    result.getRecommendMusics().add( new RecommendMusic(
                            musicRes.get().getTitle(),musicRes.get().getArtist()
                            ,musicRes.get().getPosterUrl(),musicRes.get().getInfoUrl()
                            )
                    );
                }
            }
            /**
             * return 에 필요한 컨텐츠 초기화 - end
             */

            Optional<Emotion> emotion = emotionRepository.findById(emotionIdx);
            /**
             * 감정분석 미완료된 일기는 감정분석 결과를 넣어준다
             */
            if (diary.get().getEmotion().getEmotionId()==0l&&emotion.isPresent()) {
                diary.get().setEmotion(emotion.get());
                diaryRepository.save(diary.get());
            }
            result.setCreatedData(diary.get().getCreatedDate());
            result.setEmotionId(emotion.get().getEmotionId());
            result.setEmotionName(emotion.get().getEmotionName());

            return result;
        }else{
            return null;
        }
    }
}
@Getter
@Setter
@AllArgsConstructor
class APIrecommend{
    private String title;
    private String name;
}